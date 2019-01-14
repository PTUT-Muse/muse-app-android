package com.example.lpiem.muse_app_android.presentation.presenter;

import android.content.Context;
import android.os.Handler;

import com.choosemuse.libmuse.Eeg;
import com.choosemuse.libmuse.Muse;
import com.choosemuse.libmuse.MuseDataPacket;
import com.example.lpiem.muse_app_android.MuseApplication;
import com.example.lpiem.muse_app_android.data.repository.MuseRepository;
import com.example.lpiem.muse_app_android.presentation.ui.listener.ListenerMuse;
import com.example.lpiem.muse_app_android.presentation.ui.view.ConnectDeviceView;

import java.util.ArrayList;
import java.util.List;

public class ConnectDevicePresenter {

    private ConnectDeviceView view;

    private MuseRepository repository = MuseApplication.getInstance().getRepository();

    private boolean eegStale;
    private final double[] eegBuffer = new double[6];

    private final Handler handler = new Handler();


    public ConnectDevicePresenter(ConnectDeviceView view){
        this.view = view;
    }


    public void setContextMuseManager(Context view){
        this.repository.setContextMuseManager(view);
    }

    public void setMuseListener(ListenerMuse museListener){
        this.repository.setMuseListener(museListener);
    }

    public void refreshListeningDevice(){
        this.repository.refreshListeningDevice();
    }

    public void stopListeningDevice(){
        this.repository.stopListeningDevice();
    }

    public ArrayList<Muse> getDeviceAvaibles(){
        return this.repository.getDeviceAvaibles();
    }

    public void receiveMuseDataPacket(final MuseDataPacket p, final Muse muse) {
        final long n = p.valuesSize();
        switch (p.packetType()) {
            case EEG:
                assert(eegBuffer.length >= n);
                getEegChannelValues(eegBuffer,p);
                eegStale = true;
                break;
            case ACCELEROMETER:
            case ALPHA_RELATIVE:
            case BATTERY:
            case DRL_REF:
            case QUANTIZATION:
            default:
                break;
        }
    }

    private void getEegChannelValues(double[] buffer, MuseDataPacket p) {
        buffer[0] = p.getEegChannelValue(Eeg.EEG1);
        buffer[1] = p.getEegChannelValue(Eeg.EEG2);
        buffer[2] = p.getEegChannelValue(Eeg.EEG3);
        buffer[3] = p.getEegChannelValue(Eeg.EEG4);
        buffer[4] = p.getEegChannelValue(Eeg.AUX_LEFT);
        buffer[5] = p.getEegChannelValue(Eeg.AUX_RIGHT);
    }

    public final Runnable tickUi = new Runnable() {
        @Override
        public void run() {
            if (eegStale) {
                view.updateEeg(eegBuffer);
            }
            handler.postDelayed(tickUi, 60);
        }
    };



}
