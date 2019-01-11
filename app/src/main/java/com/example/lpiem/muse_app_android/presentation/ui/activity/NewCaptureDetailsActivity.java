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
    ImageView imgEtatContent;
    ImageView imgEtatColere;
    ImageView imgEtatEtonne;
    ImageView imgEtatMove;
    ImageView imgEtatNeutre;
    ImageView imgEtatTriste;
    Button btnSuivant;
    Button btnCapture;
    private ImageView currentStateSelected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_capture_details);
        this.setTitle(R.string.new_capture_title_bar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSuivant = findViewById(R.id.btnSuivant);
        btnSuivant.setOnClickListener(this);
        btnCapture = findViewById(R.id.btnCapture);
        btnCapture.setOnClickListener(this);
        imgEtatContent = findViewById(R.id.imgEtatContent);
        imgEtatContent.setOnClickListener(this);
        imgEtatColere = findViewById(R.id.imgEtatColere);
        imgEtatColere.setOnClickListener(this);
        imgEtatEtonne = findViewById(R.id.imgEtatEtonne);
        imgEtatEtonne.setOnClickListener(this);
        imgEtatMove = findViewById(R.id.imgEtatMove);
        imgEtatMove.setOnClickListener(this);
        imgEtatNeutre = findViewById(R.id.imgEtatNeutre);
        imgEtatNeutre.setOnClickListener(this);
        imgEtatTriste = findViewById(R.id.imgEtatTriste);
        imgEtatTriste.setOnClickListener(this);

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

            case R.id.imgEtatContent:
                setState(imgEtatContent);
                break;
            case R.id.imgEtatColere:
                setState(imgEtatColere);
                break;
            case R.id.imgEtatEtonne:
                setState(imgEtatEtonne);
                break;
            case R.id.imgEtatMove:
                setState(imgEtatMove);
                break;
            case R.id.imgEtatNeutre:
                setState(imgEtatNeutre);
                break;
            case R.id.imgEtatTriste:
                setState(imgEtatTriste);
                break;
            case R.id.btnSuivant:
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
