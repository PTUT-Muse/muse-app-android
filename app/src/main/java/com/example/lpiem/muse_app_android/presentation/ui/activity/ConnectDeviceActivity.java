package com.example.lpiem.muse_app_android.presentation.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.choosemuse.libmuse.Muse;
import com.example.lpiem.muse_app_android.R;
import com.example.lpiem.muse_app_android.presentation.presenter.ConnectDevicePresenter;
import com.example.lpiem.muse_app_android.presentation.ui.listener.ConnectionListener;
import com.example.lpiem.muse_app_android.presentation.ui.listener.ListenerMuse;
import com.example.lpiem.muse_app_android.presentation.ui.view.ConnectDeviceView;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ConnectDeviceActivity extends AppCompatActivity implements View.OnClickListener, ConnectDeviceView {

    private ConnectDevicePresenter presenter = new ConnectDevicePresenter(this);

    private Muse muse;

    private Button next;
    private Button refresh;
    private Spinner spinnerDevice;
    private Boolean isSelected = false;

    private ArrayAdapter<String> spinnerAdapter;

    private ConnectionListener connectionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_device);
        this.setTitle(R.string.connect_device_title_bar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        next = findViewById(R.id.goToCreateDetails);
        refresh = findViewById(R.id.refresh);
        spinnerDevice = findViewById(R.id.spinnerDevice);


        next.setOnClickListener(this);
        refresh.setOnClickListener(this);
        spinnerAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item);

        spinnerDevice.setAdapter(spinnerAdapter);
        spinnerDevice.setOnItemSelectedListener(OnCatSpinnerCL);

        next.setAlpha(0.5f);

        presenter.setContextMuseManager(this);

        WeakReference<ConnectDevicePresenter> weakPresenter = new WeakReference<>(presenter);

        WeakReference<ConnectDeviceActivity> weakActivity = new WeakReference<>(this);

        connectionListener = new ConnectionListener(null , weakPresenter, null, null);
        presenter.setMuseListener(new ListenerMuse(weakActivity));

        ensurePermissions();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_capture, menu);
        return true;
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

    private AdapterView.OnItemSelectedListener OnCatSpinnerCL = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            parent.getChildAt(0).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            ((TextView) parent.getChildAt(0)).setTextSize(30);
            ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorAccent));
            next.setAlpha(1f);
            isSelected = true;

        }

        public void onNothingSelected(AdapterView<?> parent) {
            next.setAlpha(0.5f);
            isSelected = false;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goToCreateDetails:
                if (isSelected) {
                    Log.d("mlk", "start");
                    presenter.stopListeningDevice();
                    presenter.connectDevice(connectionListener);

                    Intent intent = new Intent(ConnectDeviceActivity.this, NewCaptureDetailsActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, R.string.connect_device_selection, Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.refresh:
                Log.d("mlk", "refresh");
                spinnerAdapter.clear();
                presenter.refreshListeningDevice();

                break;
        }
    }

    private void ensurePermissions() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // We don't have the ACCESS_COARSE_LOCATION permission so create the dialogs asking
            // the user to grant us the permission.

            DialogInterface.OnClickListener buttonListener =
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which){
                            dialog.dismiss();
                            ActivityCompat.requestPermissions(ConnectDeviceActivity.this,
                                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                    0);
                        }
                    };

        }
    }

    public void museListChanged() {

        Log.d("mlk", "refresh");
        final List<Muse> list = presenter.getDeviceAvaibles();
        for (Muse m : list) {
            spinnerAdapter.add(m.getName() + " - " + m.getMacAddress());
        }
    }



}


