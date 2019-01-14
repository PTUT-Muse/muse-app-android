package com.example.lpiem.muse_app_android.presentation.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lpiem.muse_app_android.R;

public class NewCaptureDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imgStateHappy;
    ImageView imgStateAngry;
    ImageView imgStateSurprise;
    ImageView imgStateMove;
    ImageView imgStateNeutral;
    ImageView imgStateSad;
    Button btnNext;
    Button btnCapture;
    private ImageView currentStateSelected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_capture_details);
        this.setTitle(R.string.new_capture_title_bar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
        btnCapture = findViewById(R.id.btnCapture);
        btnCapture.setOnClickListener(this);
        imgStateHappy = findViewById(R.id.imgStateHappy);
        imgStateHappy.setOnClickListener(this);
        imgStateAngry = findViewById(R.id.imgStateAngry);
        imgStateAngry.setOnClickListener(this);
        imgStateSurprise = findViewById(R.id.imgStateSurprise);
        imgStateSurprise.setOnClickListener(this);
        imgStateMove = findViewById(R.id.imgStateMove);
        imgStateMove.setOnClickListener(this);
        imgStateNeutral = findViewById(R.id.imgStateNeutral);
        imgStateNeutral.setOnClickListener(this);
        imgStateSad = findViewById(R.id.imgStateSad);
        imgStateSad.setOnClickListener(this);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
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

            case R.id.imgStateHappy:
                setState(imgStateHappy);
                break;
            case R.id.imgStateAngry:
                setState(imgStateAngry);
                break;
            case R.id.imgStateSurprise:
                setState(imgStateSurprise);
                break;
            case R.id.imgStateMove:
                setState(imgStateMove);
                break;
            case R.id.imgStateNeutral:
                setState(imgStateNeutral);
                break;
            case R.id.imgStateSad:
                setState(imgStateSad);
                break;
            case R.id.btnNext:
            case R.id.btnCapture:
                Intent intent = new Intent(NewCaptureDetailsActivity.this, NewCaptureActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);

                break;
            default:
                break;
        }
    }

    public void setState(ImageView stateSelected) {
        if(currentStateSelected != null) {
            currentStateSelected.setAlpha(0.5f);
        }
        stateSelected.setAlpha(1f);
        currentStateSelected = stateSelected;
    }
}
