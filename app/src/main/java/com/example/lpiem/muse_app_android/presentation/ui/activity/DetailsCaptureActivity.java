package com.example.lpiem.muse_app_android.presentation.ui.activity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lpiem.muse_app_android.R;
import com.example.lpiem.muse_app_android.data.model.Capture;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.example.lpiem.muse_app_android.presentation.presenter.DetailsCapturePresenter;
import com.example.lpiem.muse_app_android.presentation.ui.view.DetailsCaptureView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DetailsCaptureActivity extends AppCompatActivity implements View.OnClickListener, DetailsCaptureView, OnChartValueSelectedListener {
    private DetailsCapturePresenter presenter = new DetailsCapturePresenter(this);

    Button btnModify;
    ImageView imgState;
    TextView editName;
    TextView editDescription;
    TextView editTime;
    Capture capture;


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

        int idCapture = getIntent().getIntExtra("id", 0);
        presenter.getDataByID(idCapture);
    }

    private void realtimeChart() {
        LineChart chart = findViewById(R.id.graph);
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

                    if (isModified == true) {
                        Toast.makeText(DetailsCaptureActivity.this, "Capture modifiée", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(DetailsCaptureActivity.this, "Erreur à la modification de la capture", Toast.LENGTH_LONG).show();
                    }
                }
                break;

            default:
                break;
        }

    }

    @Override
    public void showCapture(Capture capture) {
        this.capture = capture;
        this.setTitle(capture.getTitle());
        editName.setText(capture.getTitle());
        editDescription.setText(capture.getDescription());
        editTime.setText(capture.getTime());
        setStateImage(capture.getState());
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
                // TODO export
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
                        if(isDelete == true){
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
}
