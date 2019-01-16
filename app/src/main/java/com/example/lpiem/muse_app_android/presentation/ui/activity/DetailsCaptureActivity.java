package com.example.lpiem.muse_app_android.presentation.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lpiem.muse_app_android.R;
import com.example.lpiem.muse_app_android.data.model.Capture;
import com.example.lpiem.muse_app_android.presentation.presenter.DetailsCapturePresenter;
import com.example.lpiem.muse_app_android.presentation.ui.view.DetailsCaptureView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DetailsCaptureActivity extends AppCompatActivity implements View.OnClickListener, DetailsCaptureView {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_capture, menu);
        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnModify:
                boolean isModified = presenter.updateCapture(capture.getId(), editName.getText().toString(), editDescription.getText().toString());

                if (isModified == true) {
                    Toast.makeText(DetailsCaptureActivity.this, "Capture modifiée", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(DetailsCaptureActivity.this, "Erreur à la modification de la capture", Toast.LENGTH_LONG).show();
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

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(0, 0);
                return true;
            case R.id.menu_settings:
                // TODO : lancer intent settings
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
