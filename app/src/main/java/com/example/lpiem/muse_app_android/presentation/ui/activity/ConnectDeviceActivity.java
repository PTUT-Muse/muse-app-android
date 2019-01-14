package com.example.lpiem.muse_app_android.presentation.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.choosemuse.libmuse.Muse;
import com.choosemuse.libmuse.MuseDataPacketType;
import com.example.lpiem.muse_app_android.R;
import com.example.lpiem.muse_app_android.presentation.presenter.ConnectDevicePresenter;
import com.example.lpiem.muse_app_android.presentation.ui.listener.ConnectionListener;
import com.example.lpiem.muse_app_android.presentation.ui.listener.DataListener;
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

    private ConnectionListener connectionListener;
    private DataListener dataListener;

    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_device);

        next = findViewById(R.id.goToCreateDetails);
        refresh = findViewById(R.id.refresh);
        spinnerDevice = findViewById(R.id.spinnerDevice);


        next.setOnClickListener(this);
        refresh.setOnClickListener(this);
        spinnerAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item);

        spinnerDevice.setAdapter(spinnerAdapter);
       // spinnerDevice.setOnItemSelectedListener(OnCatSpinnerCL);

        presenter.setContextMuseManager(this);

        WeakReference<ConnectDevicePresenter> weakPresenter = new WeakReference<>(presenter);

        WeakReference<ConnectDeviceActivity> weakActivity = new WeakReference<>(this);

        connectionListener = new ConnectionListener(weakPresenter);
        dataListener = new DataListener(weakPresenter);

        ensurePermissions();

        presenter.setMuseListener(new ListenerMuse(weakActivity));

    }


    /*private AdapterView.OnItemSelectedListener OnCatSpinnerCL = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
            ((TextView) parent.getChildAt(0)).setTextSize(5);

        }

        public void onNothingSelected(AdapterView<?> parent) {

        }
    };*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goToCreateDetails:
                Log.d("mlk", "start");

                presenter.stopListeningDevice();

                List<Muse> availableMuses = presenter.getDeviceAvaibles();

                muse = availableMuses.get(0);

                muse.unregisterAllListeners();
                muse.registerConnectionListener(connectionListener);
                muse.registerDataListener(dataListener, MuseDataPacketType.EEG);

                muse.runAsynchronously();

                break;
            case R.id.refresh:
                Log.d("mlk", "refresh");
                presenter.refreshListeningDevice();
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
        spinnerAdapter.clear();
        for (Muse m : list) {
            spinnerAdapter.add(m.getName() + " - " + m.getMacAddress());
        }
    }



}


