package com.example.lpiem.muse_app_android.presentation.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

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

public class NewCaptureDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imgEtatContent;
    ImageView imgEtatColere;
    ImageView imgEtatEtonne;
    ImageView imgEtatMove;
    ImageView imgEtatNeutre;
    ImageView imgEtatTriste;
    Button btnSuivant;
    Button btnCapture;
    EditText editNom;
    EditText editDescription;
    private ImageView currentStateSelected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_capture_details);
        this.setTitle(R.string.new_capture_title_bar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editNom = findViewById(R.id.txtEditNom);
        editDescription = findViewById(R.id.txtEditDescription);

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
                if (isInputNull()) {
                    Toast.makeText(NewCaptureDetailsActivity.this, "Tous les champs ne sont pas remplis", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(NewCaptureDetailsActivity.this, NewCaptureActivity.class);
                    intent.putExtra("nom", editNom.getText().toString());
                    intent.putExtra("description", editDescription.getText().toString());
                    intent.putExtra("idEtat", getResources().getResourceName(currentStateSelected.getId()));
                    //intent.putExtra("idEtat", getResources().getDrawable(currentStateSelected.getId()));
                    //  currentStateSelected.getId());
                    // varimg.setImageDrawable(id);

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
        if (TextUtils.isEmpty(editNom.getText().toString()) || TextUtils.isEmpty(editDescription.getText().toString()) || currentStateSelected == null) {
            return true;
        } else {
            return false;
        }
    }
}
