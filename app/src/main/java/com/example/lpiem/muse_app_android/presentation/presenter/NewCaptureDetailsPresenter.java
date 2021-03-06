package com.example.lpiem.muse_app_android.presentation.presenter;

import android.content.Context;

import com.choosemuse.libmuse.ConnectionState;
import com.choosemuse.libmuse.Muse;
import com.choosemuse.libmuse.MuseConnectionPacket;
import com.example.lpiem.muse_app_android.MuseApplication;
import com.example.lpiem.muse_app_android.data.repository.MuseRepository;
import com.example.lpiem.muse_app_android.presentation.ui.listener.ConnectionListener;
import com.example.lpiem.muse_app_android.presentation.ui.view.NewCaptureDetailsView;

public class NewCaptureDetailsPresenter  {

    private NewCaptureDetailsView view;

    private MuseRepository repository = MuseApplication.getInstance().getRepository();


    public NewCaptureDetailsPresenter(NewCaptureDetailsView view){
        this.view = view;
    }

    public void setContextMuseManager(Context view){
        this.repository.setContextMuseManager(view);
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

    public void setConnectionListener(ConnectionListener connectionListener) {
        repository.setConntionListener(connectionListener);
    }

    public void stopListeningDevice(){
        this.repository.stopListeningDevice();
    }

    public void disconnectMuse(){
        repository.disconnectMuse();
    }
}
