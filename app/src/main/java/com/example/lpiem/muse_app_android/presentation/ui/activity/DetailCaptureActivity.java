package com.example.lpiem.muse_app_android.presentation.ui.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lpiem.muse_app_android.R;
import com.example.lpiem.muse_app_android.data.manager.SQLiteDataBase;
import com.example.lpiem.muse_app_android.data.model.Capture;

public class DetailCaptureActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnModifier;
    TextView editNom;
    TextView editDescription;
    TextView editTemps;
    SQLiteDataBase db;
    Capture capture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_capture);

        db = new SQLiteDataBase(this);
        getCapture();

        this.setTitle(capture.getTitre());
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnModifier = findViewById(R.id.btnModifier);
        btnModifier.setOnClickListener(this);
        editNom = findViewById(R.id.txtEditNom);
        editDescription = findViewById(R.id.txtEditDescription);
        editTemps = findViewById(R.id.txtChrono);


        editNom.setText(capture.getTitre());
        editDescription.setText(capture.getDescription());
        editTemps.setText(capture.getTemps());
        // TODO : set Imageview Etat
    }

    private void getCapture(){
        int idCapture = getIntent().getIntExtra("id", 0);
        Log.d("mlk", "id : "+idCapture);
        Cursor data = db.getDataByID(idCapture);

        while (data.moveToNext()) {
            int idTemp = data.getInt(0);
            String nomTemp = data.getString(1);
            String descriptionTemp = data.getString(2);
            String dateTemp = data.getString(3);
            String tempsTemp = data.getString(4);
            int etatTemp = data.getInt(5);
            // String museTemp = data.getString(6);
            capture = new Capture(idTemp,etatTemp, nomTemp, descriptionTemp, dateTemp, tempsTemp);
            System.out.println("capture : " + capture);
        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(0, 0);
                return true;
            case R.id.menu_settings:
                // TODO : lancer intent settings
                return true;
            case R.id.menu_exporter:
                // TODO export
                return true;
            case R.id.menu_supprimer:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this)
                        .setTitle("Supprimer la capture ?")
                        .setPositiveButton("Oui",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                db.deleteCapture(capture.getId());
                                Toast.makeText(DetailCaptureActivity.this, "Capture supprimée", Toast.LENGTH_LONG).show();
                                DetailCaptureActivity.this.finish();
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
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_capture, menu);
        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnModifier:
                //updateCapture
                boolean isModified = db.updateCapture(capture.getId(), editNom.getText().toString(), editDescription.getText().toString());

                if (isModified == true) {
                    Toast.makeText(DetailCaptureActivity.this, "Capture modifiée", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(DetailCaptureActivity.this, "Erreur à la modification de la capture", Toast.LENGTH_LONG).show();
                }

                break;
            default:
                break;
        }

    }
}
