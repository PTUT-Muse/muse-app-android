package com.example.lpiem.muse_app_android.presentation.presenter;

import android.content.Context;

import com.choosemuse.libmuse.ConnectionState;
import com.choosemuse.libmuse.Muse;
import com.choosemuse.libmuse.MuseConnectionPacket;
import com.example.lpiem.muse_app_android.MuseApplication;
import com.example.lpiem.muse_app_android.data.repository.MuseRepository;
import com.example.lpiem.muse_app_android.presentation.ui.listener.ConnectionListener;
import com.example.lpiem.muse_app_android.presentation.ui.listener.ListenerMuse;
import com.example.lpiem.muse_app_android.presentation.ui.view.ConnectDeviceView;

import java.util.ArrayList;

public class ConnectDevicePresenter {

    private ConnectDeviceView view;
    private MuseRepository repository = MuseApplication.getInstance().getRepository();


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

    public void connectDevice(ConnectionListener connectionListener) {
        this.repository.connectDevice(connectionListener);
    }

    public void receiveMuseConnectionPacket(final MuseConnectionPacket p, final Muse muse) {
        final ConnectionState current = p.getCurrentConnectionState();
        if(current == ConnectionState.DISCONNECTED){
            repository.resetMuse();
           // view.showMuseDisconnect();
        }
    }

}
