package com.example.lpiem.muse_app_android.presentation.presenter;

import android.content.Context;
import android.os.Handler;

import com.choosemuse.libmuse.ConnectionState;
import com.choosemuse.libmuse.Eeg;
import com.choosemuse.libmuse.Muse;
import com.choosemuse.libmuse.MuseConnectionPacket;
import com.choosemuse.libmuse.MuseDataPacket;
import com.example.lpiem.muse_app_android.MuseApplication;
import com.example.lpiem.muse_app_android.data.model.Sensors;
import com.example.lpiem.muse_app_android.data.repository.MuseRepository;
import com.example.lpiem.muse_app_android.presentation.ui.listener.ConnectionListener;
import com.example.lpiem.muse_app_android.presentation.ui.listener.DataListener;
import com.example.lpiem.muse_app_android.presentation.ui.view.NewCaptureView;

public class NewCapturePresenter {

    private NewCaptureView view;

    private MuseRepository repository = MuseApplication.getInstance().getRepository();

    private boolean eegStale;
    private final double[] eegBuffer = new double[6];

    private final Handler handler = new Handler();

    private Boolean captureIsStart = false;

    public NewCapturePresenter(NewCaptureView view){
        this.view = view;
    }

    public void setContextMuseManager(Context view){
        this.repository.setContextMuseManager(view);
    }

    public void setDataListenerMuse(DataListener dataListener) {
        this.repository.setDataListenerMuse(dataListener);
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

    public void setCaptureIsStart(boolean isStart){
        this.captureIsStart = isStart;
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
                if(captureIsStart) {
                    view.updateEeg(eegBuffer);

                }
            }
            handler.postDelayed(tickUi, 60);
        }
    };

    public void setConnectionListener(ConnectionListener connectionListener) {
        repository.setConntionListener(connectionListener);
    }

    public void stopHandler(){
        handler.removeCallbacks(tickUi);
    }

    public void receiveMuseConnectionPacket(final MuseConnectionPacket p, final Muse muse) {
        final ConnectionState current = p.getCurrentConnectionState();
        if(current == ConnectionState.DISCONNECTED){
            repository.resetMuse();
            view.showMuseDisconnect();
        }
    }

    public void resetMuse(){
        repository.resetMuse();
    }

    public boolean insertData(String nom, String description, String date, String temps, int etat, Sensors muse){
        return repository.insertData(nom,description,date,temps,etat,muse);
    }

}
