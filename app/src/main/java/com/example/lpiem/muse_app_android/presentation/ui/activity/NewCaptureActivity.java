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
import com.example.lpiem.muse_app_android.data.model.MyMarkerView;
import com.example.lpiem.muse_app_android.data.model.Sensors;
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
import java.util.ArrayList;
import java.util.Date;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class NewCaptureActivity extends AppCompatActivity implements View.OnClickListener, NewCaptureView, OnChartValueSelectedListener {

    private NewCapturePresenter presenter = new NewCapturePresenter(this);

    private Button btnDetails;
    private ImageButton btnStart;
    private ImageButton btnStop;
    private ImageButton btnReset;
    private ImageButton btn3d;
    private FloatingActionButton addCapture;

    private DataListener dataListener;
    private final Handler handler = new Handler();

    private LineChart chart;
    private PointerSpeedometer pointerSpeedometer1;
    private PointerSpeedometer pointerSpeedometer2;
    private PointerSpeedometer pointerSpeedometer3;
    private PointerSpeedometer pointerSpeedometer4;
    private Sensors sensors;

    private Chronometer timer;
    private long pauseOffset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_capture);
        this.setTitle(R.string.new_capture_title_bar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        timer = findViewById(R.id.chronometer);
        btnDetails = findViewById(R.id.btnDetails);
        btnDetails.setOnClickListener(this);
        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
        btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(this);
        btnStop = findViewById(R.id.btnStop);
        btnStop.setOnClickListener(this);
        addCapture = findViewById(R.id.fab_addCapture);
        addCapture.setOnClickListener(this);
        btn3d = findViewById(R.id.btn3D);
        btn3d.setOnClickListener(this);
        pointerSpeedometer1 = findViewById(R.id.monitor1);
        pointerSpeedometer2 = findViewById(R.id.monitor2);
        pointerSpeedometer3 = findViewById(R.id.monitor3);
        pointerSpeedometer4 = findViewById(R.id.monitor4);

        sensors = new Sensors();

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
                presenter.stopHandler();
                finish();
                overridePendingTransition(0, 0);
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
                btnReset.setVisibility(View.INVISIBLE);
                btnStart.setVisibility(View.INVISIBLE);
                btnStop.setVisibility(View.VISIBLE);

                addCapture.setAlpha(0.5f);

                presenter.setCaptureIsStart(true);

                timer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                timer.start();

                break;
            case R.id.btnStop:
                btnReset.setVisibility(View.VISIBLE);
                btnStop.setVisibility(View.INVISIBLE);
                btnStart.setVisibility(View.VISIBLE);

                addCapture.setAlpha(1f);

                timer.stop();
                pauseOffset = SystemClock.elapsedRealtime() - timer.getBase();

                presenter.setCaptureIsStart(false);
                break;

             case R.id.btnReset:
                btnStop.setVisibility(View.INVISIBLE);
                btnStart.setVisibility(View.VISIBLE);
                timer.setBase(SystemClock.elapsedRealtime());
                pauseOffset = 0;
                sensors.setSensor1(new ArrayList<Double>());
                sensors.setSensor2(new ArrayList<Double>());
                sensors.setSensor3(new ArrayList<Double>());
                sensors.setSensor4(new ArrayList<Double>());
                LineData data = new LineData();
                data.setValueTextColor(Color.WHITE);
                chart.setData(data);
                chart.invalidate();
                pointerSpeedometer1.speedTo(0f);
                pointerSpeedometer2.speedTo(0f);
                pointerSpeedometer3.speedTo(0f);
                pointerSpeedometer4.speedTo(0f);
                break;

            case R.id.fab_addCapture:
                if (!presenter.getCaptureIsStart()) {
                    Bundle extras = getIntent().getExtras();
                    String currentDate = DateFormat.getDateInstance().format(new Date());
                    boolean isInserted = presenter.insertData(extras.getString("nom"), extras.getString("description"), currentDate, timer.getText().toString(), extras.getInt("idEtat"),sensors);
                    if (isInserted) {
                        Toast.makeText(NewCaptureActivity.this, "Capture ajoutée", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(NewCaptureActivity.this, CaptureListActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(NewCaptureActivity.this, "Erreur à l'ajout de la capture", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void updateEeg(double[] eegBuffer) {
        sensors.pushSensor1(eegBuffer[0]);
        sensors.pushSensor2(eegBuffer[1]);
        sensors.pushSensor3(eegBuffer[2]);
        sensors.pushSensor4(eegBuffer[3]);

        LineData data = chart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            ILineDataSet set1 = data.getDataSetByIndex(1);
            ILineDataSet set2= data.getDataSetByIndex(2);
            ILineDataSet set3 = data.getDataSetByIndex(3);

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

            chart.notifyDataSetChanged();

            chart.setVisibleXRangeMaximum(120);

            chart.moveViewToX(data.getEntryCount());
        }

        pointerSpeedometer1.speedTo((float) eegBuffer[0], 0);
        pointerSpeedometer2.speedTo((float) eegBuffer[1], 0);
        pointerSpeedometer3.speedTo((float) eegBuffer[2], 0);
        pointerSpeedometer4.speedTo((float) eegBuffer[3], 0);
    }

    @Override
    public void showMuseDisconnect() {
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
        pointerSpeedometer1.setUnit("");
        pointerSpeedometer1.setMaxSpeed(1700f);
        pointerSpeedometer1.setTicks(1700f);
        pointerSpeedometer2.setUnit("");
        pointerSpeedometer2.setMaxSpeed(1700f);
        pointerSpeedometer2.setTicks(1700f);
        pointerSpeedometer3.setUnit("");
        pointerSpeedometer3.setMaxSpeed(1700f);
        pointerSpeedometer3.setTicks(1700f);
        pointerSpeedometer4.setUnit("");
        pointerSpeedometer4.setMaxSpeed(1700f);
        pointerSpeedometer4.setTicks(1700f);
    }

    private void realtimeChart() {
        chart = findViewById(R.id.graph_capture);
        chart.setOnChartValueSelectedListener(this);

        chart.getDescription().setEnabled(true);

        chart.setTouchEnabled(true);

        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);

        chart.setPinchZoom(true);

        chart.setBackgroundColor(Color.TRANSPARENT);

        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);

        chart.setData(data);

        Legend l = chart.getLegend();

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
        leftAxis.setAxisMaximum(1700f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

        mv.setChartView(chart);
        chart.setMarker(mv);
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
