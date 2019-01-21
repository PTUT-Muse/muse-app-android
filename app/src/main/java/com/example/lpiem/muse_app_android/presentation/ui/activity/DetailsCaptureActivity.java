package com.example.lpiem.muse_app_android.presentation.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lpiem.muse_app_android.R;
import com.example.lpiem.muse_app_android.data.model.Capture;
import com.example.lpiem.muse_app_android.data.model.MyMarkerView;
import com.example.lpiem.muse_app_android.data.model.Sensors;
import com.example.lpiem.muse_app_android.presentation.presenter.DetailsCapturePresenter;
import com.example.lpiem.muse_app_android.presentation.ui.view.DetailsCaptureView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.io.IOException;
import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class DetailsCaptureActivity extends AppCompatActivity implements View.OnClickListener, DetailsCaptureView, OnChartValueSelectedListener {
    private DetailsCapturePresenter presenter = new DetailsCapturePresenter(this);

    private Button btnModify;
    private ImageButton btn3D;
    private ImageView imgState;
    private TextView editName;
    private TextView editDescription;
    private TextView editTime;

    private Capture capture;

    private LineChart chart;

    private BarChart barChart;

    private PieChart pieChart;

    private int idChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_capture);


        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnModify = findViewById(R.id.btnModify);
        btnModify.setOnClickListener(this);
        editName = findViewById(R.id.txtEditName);
        editDescription = findViewById(R.id.txtEditDescription);
        editTime = findViewById(R.id.txtChrono);
        imgState = findViewById(R.id.imgState);
        chart = findViewById(R.id.graph);
        barChart = findViewById(R.id.bar_chart);
        pieChart = findViewById(R.id.pie_chart);
        btn3D = findViewById(R.id.btn3D);
        btn3D.setOnClickListener(this);

        idChart = 0;
        realtimeChart();
        pieChart();
        barChart();

        int idCapture = getIntent().getIntExtra("id", 0);
        presenter.getDataByID(idCapture);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_capture, menu);
        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnModify:

                if (isInputNull()) {
                    Toast.makeText(DetailsCaptureActivity.this, "Tous les champs ne sont pas remplis", Toast.LENGTH_LONG).show();
                } else {
                    boolean isModified = presenter.updateCapture(capture.getId(), editName.getText().toString(), editDescription.getText().toString());

                    if (isModified) {
                        Toast.makeText(DetailsCaptureActivity.this, "Capture modifiée", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(DetailsCaptureActivity.this, "Erreur à la modification de la capture", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.btn3D:
                idChart++;
                if (idChart == 0) {
                    barChart.setVisibility(View.INVISIBLE);
                    pieChart.setVisibility(View.INVISIBLE);
                    chart.setVisibility(View.VISIBLE);
                }
                else if (idChart == 1) {
                    barChart.setVisibility(View.VISIBLE);
                    pieChart.setVisibility(View.INVISIBLE);
                    chart.setVisibility(View.INVISIBLE);
                }
                else if (idChart == 2) {
                    barChart.setVisibility(View.INVISIBLE);
                    pieChart.setVisibility(View.VISIBLE);
                    chart.setVisibility(View.INVISIBLE);
                    idChart = -1;
                }

            default:
                break;
        }

    }


    private void pieChart() {

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.setOnChartValueSelectedListener(this);

        pieChart.animateY(1400, Easing.EaseInOutQuad);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setEntryLabelTextSize(12f);
    }

    private void barChart() {
        barChart.getDescription().setEnabled(false);

        barChart.setMaxVisibleValueCount(60);

        barChart.setPinchZoom(false);

        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        barChart.getAxisLeft().setDrawGridLines(false);

        barChart.animateY(1500);

        barChart.getLegend().setEnabled(false);
    }

    @Override
    public void showCapture(Capture capture) {
        this.capture = capture;
        this.setTitle(capture.getTitle());
        editName.setText(capture.getTitle());
        editDescription.setText(capture.getDescription());
        editTime.setText(capture.getTime());
        setStateImage(capture.getState());

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

            Sensors sensors = capture.getSensors();

            for (int i = 0; i < sensors.getSensor1().size(); i++) {
                data.addEntry(new Entry(set.getEntryCount(), sensors.getSensor1().get(i).floatValue() ), 0);
                data.addEntry(new Entry(set1.getEntryCount(), sensors.getSensor2().get(i).floatValue()), 1);
                data.addEntry(new Entry(set2.getEntryCount(), sensors.getSensor3().get(i).floatValue()), 2);
                data.addEntry(new Entry(set3.getEntryCount(), sensors.getSensor4().get(i).floatValue()), 3);
            }

            data.notifyDataChanged();

            chart.notifyDataSetChanged();

            chart.setVisibleXRangeMaximum(120);

            chart.moveViewToX(data.getEntryCount());

            float val1 = 0, val2 = 0, val3 = 0, val4 = 0;

            for (int i = 0; i<sensors.getSensor1().size(); i++) {
                val1 += sensors.getSensor1().get(i);
                val2 += sensors.getSensor2().get(i);
                val3 += sensors.getSensor3().get(i);
                val4 += sensors.getSensor4().get(i);
            }
            val1 /= sensors.getSensor1().size();
            val2 /= sensors.getSensor1().size();
            val3 /= sensors.getSensor1().size();
            val4 /= sensors.getSensor1().size();

            ArrayList<BarEntry> barEntries = new ArrayList<>();

            barEntries.add(new BarEntry(0, val1));
            barEntries.add(new BarEntry(1, val2));
            barEntries.add(new BarEntry(2, val3));
            barEntries.add(new BarEntry(3, val4));

            BarDataSet barSet;

            if (barChart.getData() != null &&
                    barChart.getData().getDataSetCount() > 0) {
                barSet = (BarDataSet) barChart.getData().getDataSetByIndex(0);
                barSet.setValues(barEntries);
                barChart.getData().notifyDataChanged();
                barChart.notifyDataSetChanged();
            } else {
                barSet = new BarDataSet(barEntries, "Données capteurs");
                barSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
                barSet.setDrawValues(false);

                ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                dataSets.add(barSet);

                BarData barData = new BarData(dataSets);
                barChart.setData(barData);
                barChart.setFitBars(true);
            }

            barChart.invalidate();

            ArrayList<PieEntry> entries = new ArrayList<>();

            entries.add(new PieEntry(val1));
            entries.add(new PieEntry(val2));
            entries.add(new PieEntry(val3));
            entries.add(new PieEntry(val4));

            PieDataSet dataSet = new PieDataSet(entries, "Données capteurs");

            dataSet.setDrawIcons(false);

            dataSet.setSliceSpace(3f);
            dataSet.setIconsOffset(new MPPointF(0, 40));
            dataSet.setSelectionShift(5f);

            // add a lot of colors

            ArrayList<Integer> colors = new ArrayList<>();

            for (int c : ColorTemplate.VORDIPLOM_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.JOYFUL_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.COLORFUL_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.LIBERTY_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.PASTEL_COLORS)
                colors.add(c);

            colors.add(ColorTemplate.getHoloBlue());

            dataSet.setColors(colors);
            //dataSet.setSelectionShift(0f);

            PieData pieData = new PieData(dataSet);
            pieData.setValueTextSize(11f);
            pieData.setValueTextColor(Color.WHITE);
            pieChart.setData(pieData);

            // undo all highlights
            pieChart.highlightValues(null);

            pieChart.invalidate();
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {
    }
    
    public boolean isInputNull(){
        if (TextUtils.isEmpty(editName.getText().toString()) || TextUtils.isEmpty(editDescription.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(0, 0);
                return true;
            case R.id.menu_export:

                ensurePermissions();

                try {
                    presenter.exportCSV(capture);
                    Toast.makeText(DetailsCaptureActivity.this, "Capture exporté", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return true;
            case R.id.menu_delete:
                confirmDeleteDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void confirmDeleteDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this)
                .setTitle("Supprimer la capture ?")
                .setPositiveButton("Oui",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        boolean isDelete = presenter.deleteCapture(capture.getId());
                        if(isDelete){
                            Toast.makeText(DetailsCaptureActivity.this, "Capture supprimée", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(DetailsCaptureActivity.this, "Erreur lors de la suppression", Toast.LENGTH_LONG).show();
                        }
                        DetailsCaptureActivity.this.finish();
                    }
                })
                .setNegativeButton("Non",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.getButton(alertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorBlue));
        alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorBlue));
    }

    private void setStateImage(int stateId){
        switch (stateId) {
            case 0:
                imgState.setImageResource(R.mipmap.content);
                break;
            case 1:
                imgState.setImageResource(R.mipmap.colere);
                break;
            case 2:
                imgState.setImageResource(R.mipmap.etonne);
                break;
            case 3:
                imgState.setImageResource(R.mipmap.move);
                break;
            case 4:
                imgState.setImageResource(R.mipmap.neutre);
                break;
            case 5:
                imgState.setImageResource(R.mipmap.triste);
                break;
        }

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

    private void realtimeChart() {

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

    private void ensurePermissions() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {

            DialogInterface.OnClickListener buttonListener =
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which){
                            dialog.dismiss();
                            ActivityCompat.requestPermissions(DetailsCaptureActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    0);
                        }
                    };

            final android.app.AlertDialog introDialog = new android.app.AlertDialog.Builder(this)
                    .setTitle(R.string.permission_dialog_title)
                    .setMessage(R.string.permission_dialog_description)
                    .setPositiveButton(R.string.permission_dialog_understand, buttonListener)
                    .create();

            introDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    introDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorBlue));
                }
            });
            introDialog.show();

        }


    }
}
