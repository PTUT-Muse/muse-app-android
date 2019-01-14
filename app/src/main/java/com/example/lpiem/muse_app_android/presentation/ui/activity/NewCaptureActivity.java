package com.example.lpiem.muse_app_android.presentation.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.lpiem.muse_app_android.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.Date;

public class NewCaptureActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnDetails;
    ImageButton btnStart;
    ImageButton btnStop;
    ImageButton btn3d;
    FloatingActionButton addCapture;

    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_capture);
        this.setTitle(R.string.new_capture_title_bar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chronometer = findViewById(R.id.chronometer);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_capture, menu);
        return true;
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
                if (!running) {
                    chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    chronometer.start();
                    running = true;
                }
                break;
            case R.id.btnStop:
                btnStop.setVisibility(View.INVISIBLE);
                btnStart.setVisibility(View.VISIBLE);
                if (running) {
                    chronometer.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                    running = false;
                }
                break;
            // TODO : faire icône reset
            // case R.id.btnReset:
//            btnStop.setVisibility(View.INVISIBLE);
//            btnStart.setVisibility(View.VISIBLE);
//                chronometer.setBase(SystemClock.elapsedRealtime());
//                pauseOffset = 0;
//                break;
            case R.id.fab_addCapture:
                Bundle extras = getIntent().getExtras();
                String currentDate = DateFormat.getDateInstance().format(new Date());
                boolean isInserted = db.insertData(extras.getString("nom"), extras.getString("description"), currentDate, "04:25", extras.getInt("idEtat"),"donnees");
                if (isInserted == true) {
                    Toast.makeText(NewCaptureActivity.this, "Capture ajoutée", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(NewCaptureActivity.this, CaptureListActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.btn3D:
                // TODO : activer la 3D
                break;
            default:
                break;
        }
    }
}
