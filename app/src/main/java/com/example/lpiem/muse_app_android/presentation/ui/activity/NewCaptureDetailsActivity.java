package com.example.lpiem.muse_app_android.presentation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lpiem.muse_app_android.R;

import androidx.appcompat.app.AppCompatActivity;

public class NewCaptureDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imgStateHappy;
    ImageView imgStateAngry;
    ImageView imgStateSurprise;
    ImageView imgStateMove;
    ImageView imgStateNeutral;
    ImageView imgStateSad;
    Button btnNext;
    Button btnCapture;
    EditText editName;
    EditText editDescription;
    private ImageView currentStateSelected = null;
    private int stateSelectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_capture_details);
        this.setTitle(R.string.new_capture_title_bar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
        editName = findViewById(R.id.txtEditName);
        editDescription = findViewById(R.id.txtEditDescription);
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
                stateSelectedId = 0;
                setState(imgStateHappy);
                break;
            case R.id.imgStateAngry:
                stateSelectedId = 1;
                setState(imgStateAngry);
                break;
            case R.id.imgStateSurprise:
                stateSelectedId = 2;
                setState(imgStateSurprise);
                break;
            case R.id.imgStateMove:
                stateSelectedId = 3;
                setState(imgStateMove);
                break;
            case R.id.imgStateNeutral:
                stateSelectedId = 4;
                setState(imgStateNeutral);
                break;
            case R.id.imgStateSad:
                stateSelectedId = 5;
                setState(imgStateSad);
                break;
            case R.id.btnNext:
            case R.id.btnCapture:
                if (isInputNull()) {
                    Toast.makeText(NewCaptureDetailsActivity.this, "Tous les champs ne sont pas remplis", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(NewCaptureDetailsActivity.this, NewCaptureActivity.class);
                    intent.putExtra("nom", editName.getText().toString());
                    intent.putExtra("description", editDescription.getText().toString());
                    intent.putExtra("idEtat", stateSelectedId);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
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

    public boolean isInputNull(){
        if (TextUtils.isEmpty(editName.getText().toString()) || TextUtils.isEmpty(editDescription.getText().toString()) || currentStateSelected == null) {
            return true;
        } else {
            return false;
        }
    }
}
