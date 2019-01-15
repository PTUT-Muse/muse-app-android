package com.example.lpiem.muse_app_android.presentation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.lpiem.muse_app_android.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

public class NewCaptureActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnDetails;
    ImageButton btnStart;
    ImageButton btnStop;
    ImageButton btn3d;
    FloatingActionButton addCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_capture);
        this.setTitle(R.string.new_capture_title_bar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                break;
            case R.id.btnStop:
                btnStop.setVisibility(View.INVISIBLE);
                btnStart.setVisibility(View.VISIBLE);
                break;
            case R.id.fab_addCapture:
                Intent intent = new Intent(NewCaptureActivity.this, CaptureListActivity.class);
                startActivity(intent);
                break;
            case R.id.btn3D:
                // TODO : activer la 3D
                break;
            default:
                break;
        }
    }
}
