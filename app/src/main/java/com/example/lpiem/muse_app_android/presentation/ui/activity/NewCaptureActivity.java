package com.example.lpiem.muse_app_android.presentation.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.lpiem.muse_app_android.R;
import com.example.lpiem.muse_app_android.presentation.presenter.NewCapturePresenter;
import com.example.lpiem.muse_app_android.presentation.ui.listener.ConnectionListener;
import com.example.lpiem.muse_app_android.presentation.ui.listener.DataListener;
import com.example.lpiem.muse_app_android.presentation.ui.view.NewCaptureView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class NewCaptureActivity extends AppCompatActivity implements View.OnClickListener, NewCaptureView {

    private NewCapturePresenter presenter = new NewCapturePresenter(this);

    Button btnDetails;
    ImageButton btnStart;
    ImageButton btnStop;
    ImageButton btn3d;
    FloatingActionButton addCapture;

    private DataListener dataListener;

    private final Handler handler = new Handler();

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

        presenter.setContextMuseManager(this);

        WeakReference<NewCapturePresenter> weakPresenter = new WeakReference<>(presenter);


        dataListener = new DataListener(weakPresenter);

        presenter.setConnectionListener(new ConnectionListener(weakPresenter,null));
        presenter.setDataListenerMuse(dataListener);

        handler.post(presenter.tickUi);
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

                presenter.setCaptureIsStart(true);

                break;
            case R.id.btnStop:
                btnStop.setVisibility(View.INVISIBLE);
                btnStart.setVisibility(View.VISIBLE);

                presenter.setCaptureIsStart(false);
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

    @Override
    public void updateEeg(double[] eegBuffer) {
        Log.d("mlk", "test eegBuffer 1 : "+String.format("%6.2f", eegBuffer[0]));
        Log.d("mlk", "test eegBuffer 2 : "+String.format("%6.2f", eegBuffer[1]));
        Log.d("mlk", "test eegBuffer 3 : "+String.format("%6.2f", eegBuffer[2]));
        Log.d("mlk", "test eegBuffer 4 : "+String.format("%6.2f", eegBuffer[3]));

    }

    @Override
    public void showMuseDisconnect() {
        //TODO Ecouter sur toutes les pages, reset muse dans le repository
        Log.d("mlkk", "disconnected");
        presenter.stopHandler();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Appareil déconnecté");
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                        Intent intent = new Intent(NewCaptureActivity.this, ConnectDeviceActivity.class);
                        startActivity(intent);
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stopHandler();
    }
}
