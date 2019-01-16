package com.example.lpiem.muse_app_android.presentation.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.lpiem.muse_app_android.R;
import com.example.lpiem.muse_app_android.data.manager.SQLiteDataBase;
import com.example.lpiem.muse_app_android.presentation.presenter.NewCapturePresenter;
import com.example.lpiem.muse_app_android.presentation.ui.listener.ConnectionListener;
import com.example.lpiem.muse_app_android.presentation.ui.listener.DataListener;
import com.example.lpiem.muse_app_android.presentation.ui.view.NewCaptureView;
import com.github.anastr.speedviewlib.PointerSpeedometer;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.util.Date;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class NewCaptureActivity extends AppCompatActivity implements View.OnClickListener, NewCaptureView, OnChartValueSelectedListener {

    private NewCapturePresenter presenter = new NewCapturePresenter(this);

    SQLiteDataBase db;
    Button btnDetails;
    ImageButton btnStart;
    ImageButton btnStop;
    ImageButton btn3d;
    FloatingActionButton addCapture;

    private DataListener dataListener;
    private final Handler handler = new Handler();
    private LineChart chart;
    private PointerSpeedometer pointerSpeedometer;
    private Chronometer timer;
    private long pauseOffset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_capture);
        db = new SQLiteDataBase(this);

        this.setTitle(R.string.new_capture_title_bar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        timer = findViewById(R.id.chronometer);
        btnDetails = findViewById(R.id.btnDetails);
        btnDetails.setOnClickListener(this);
        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
        btnStop = findViewById(R.id.btnStop);
        btnStop.setOnClickListener(this);
        addCapture = findViewById(R.id.fab_addCapture);
        addCapture.setOnClickListener(this);
        btn3d = findViewById(R.id.btn3D);
        btn3d.setOnClickListener(this);
        pointerSpeedometer = findViewById(R.id.monitor1);


        realtimeChart();
        speedometer();

        presenter.setContextMuseManager(this);

        WeakReference<NewCapturePresenter> weakPresenter = new WeakReference<>(presenter);

        dataListener = new DataListener(weakPresenter);

        presenter.setConnectionListener(new ConnectionListener(weakPresenter,null,null, null));
        presenter.setDataListenerMuse(dataListener);

        handler.post(presenter.tickUi);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(0, 0);
                return true;
            case R.id.menu_settings:
                // lancer intent settings
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDetails:
                finish();
                overridePendingTransition(0, 0);
                break;
            case R.id.btnStart:
                btnStart.setVisibility(View.INVISIBLE);
                btnStop.setVisibility(View.VISIBLE);

                presenter.setCaptureIsStart(true);
                timer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                timer.start();

                break;
            case R.id.btnStop:
                btnStop.setVisibility(View.INVISIBLE);
                btnStart.setVisibility(View.VISIBLE);

                timer.stop();
                pauseOffset = SystemClock.elapsedRealtime() - timer.getBase();

                presenter.setCaptureIsStart(false);
                break;

            // TODO : faire icône reset
            // case R.id.btnReset:
//            btnStop.setVisibility(View.INVISIBLE);
//            btnStart.setVisibility(View.VISIBLE);
//                timer.setBase(SystemClock.elapsedRealtime());
//                pauseOffset = 0;
//                break;
            case R.id.fab_addCapture:
                Bundle extras = getIntent().getExtras();
                String currentDate = DateFormat.getDateInstance().format(new Date());
                long timeChrono = SystemClock.elapsedRealtime() - timer.getBase();
                boolean isInserted = presenter.insertData(extras.getString("nom"), extras.getString("description"), currentDate, timer.getText().toString(), extras.getInt("idEtat"),"donnees");
                if (isInserted == true) {
                    Toast.makeText(NewCaptureActivity.this, "Capture ajoutée", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(NewCaptureActivity.this, CaptureListActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(NewCaptureActivity.this, "Erreur à l'ajout de la capture", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn3D:
                // TODO : activer la 3D
                break;
            default:
                break;
        }
    }

    @Override
    public void updateEeg(double[] eegBuffer) {
        Log.d("mlk", "test eegBuffer 1 : "+String.format("%6.2f", eegBuffer[0]));
        Log.d("mlk", "test eegBuffer 2 : "+String.format("%6.2f", eegBuffer[1]));
        Log.d("mlk", "test eegBuffer 3 : "+String.format("%6.2f", eegBuffer[2]));
        Log.d("mlk", "test eegBuffer 4 : "+String.format("%6.2f", eegBuffer[3]));

        LineData data = chart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            ILineDataSet set1 = data.getDataSetByIndex(1);
            ILineDataSet set2= data.getDataSetByIndex(2);
            ILineDataSet set3 = data.getDataSetByIndex(3);
            // set.addEntry(...); // can be called as well

            if (set == null && set1 == null && set2 == null && set3 == null) {
                set = createSet(ColorTemplate.getHoloBlue(), "Capteur 1");
                set1 = createSet(Color.GREEN, "Capteur 2");
                set2 = createSet(Color.RED, "Capteur 3");
                set3 = createSet(Color.YELLOW, "Capteur 4");
                data.addDataSet(set);
                data.addDataSet(set1);
                data.addDataSet(set2);
                data.addDataSet(set3);
            }

            data.addEntry(new Entry(set.getEntryCount(), (float) eegBuffer[0]), 0);
            data.addEntry(new Entry(set1.getEntryCount(), (float) eegBuffer[1]), 1);
            data.addEntry(new Entry(set2.getEntryCount(), (float) eegBuffer[2]), 2);
            data.addEntry(new Entry(set3.getEntryCount(), (float) eegBuffer[3]), 3);
            data.notifyDataChanged();

            // let the chart know it's data has changed
            chart.notifyDataSetChanged();

            // limit the number of visible entries
            chart.setVisibleXRangeMaximum(120);
            // chart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            chart.moveViewToX(data.getEntryCount());

            // this automatically refreshes the chart (calls invalidate())
            // chart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }

        pointerSpeedometer.speedTo((float) eegBuffer[0], 0);
    }

    @Override
    public void showMuseDisconnect() {
        Log.d("mlkk", "disconnected");
        presenter.stopHandler();
        presenter.resetMuse();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this)
                .setTitle("Appareil déconnecté")
                .setMessage("Vous allez être redirigé sur la page pour connecter l'appareil")
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();

                        Intent intent = new Intent(NewCaptureActivity.this, ConnectDeviceActivity.class);
                        startActivity(intent);
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getButton(alertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorBlue));
        alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorBlue));

    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stopHandler();
    }

    private void speedometer() {
        pointerSpeedometer.setUnit("");
        pointerSpeedometer.setMaxSpeed(1600f);
        pointerSpeedometer.setTicks(1600f);
    }

    private void realtimeChart() {
        chart = findViewById(R.id.graph_capture);
        chart.setOnChartValueSelectedListener(this);

        // enable description text
        chart.getDescription().setEnabled(true);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true);

        // set an alternative background color
        chart.setBackgroundColor(Color.TRANSPARENT);

        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);

        // add empty data
        chart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(Color.WHITE);
        l.setTextSize(35f);
        l.setFormSize(35f);
        l.setXEntrySpace(20f);

        XAxis xl = chart.getXAxis();
        xl.setTextColor(Color.WHITE);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);
        xl.removeAllLimitLines();

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisMaximum(1600f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    private LineDataSet createSet(int color, String label) {

        LineDataSet set = new LineDataSet(null, label);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(color);
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(1.5f);
        set.setFillAlpha(65);
        set.setFillColor(color);
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }
}
