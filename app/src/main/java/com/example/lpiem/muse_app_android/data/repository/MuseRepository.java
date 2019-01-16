package com.example.lpiem.muse_app_android.data.repository;

import android.content.Context;

import com.choosemuse.libmuse.ConnectionState;
import com.choosemuse.libmuse.Muse;
import com.choosemuse.libmuse.MuseConnectionPacket;
import com.choosemuse.libmuse.MuseDataPacketType;
import com.choosemuse.libmuse.MuseManagerAndroid;
import com.example.lpiem.muse_app_android.presentation.ui.listener.ConnectionListener;
import com.example.lpiem.muse_app_android.presentation.ui.listener.DataListener;
import com.example.lpiem.muse_app_android.presentation.ui.listener.ListenerMuse;

import java.util.ArrayList;
import java.util.List;

public class MuseRepository {

    private MuseManagerAndroid museManager;
    private Muse muse;

    public MuseRepository(){
        this.museManager = MuseManagerAndroid.getInstance();
    }


    public void setContextMuseManager(Context view){
        this.museManager.setContext(view);
    }

    public void setMuseListener(ListenerMuse museListener){
        this.museManager.setMuseListener(museListener);
    }

    public MuseManagerAndroid getMuseManager() {
        return museManager;
    }

    public void refreshListeningDevice(){
        this.museManager.stopListening();
        this.museManager.startListening();
    }

    public void stopListeningDevice(){
        this.museManager.stopListening();
    }

    public ArrayList<Muse> getDeviceAvaibles(){
        return this.museManager.getMuses();
    }


    public void connectDevice(ConnectionListener connectionListener){
        List<Muse> availableMuses = getDeviceAvaibles();

        muse = availableMuses.get(0);

        muse.unregisterAllListeners();
        muse.registerConnectionListener(connectionListener);

        muse.runAsynchronously();
    }

    public void setConntionListener(ConnectionListener conntionListener){
        muse.registerConnectionListener(conntionListener);
    }

    public boolean museIsInstantiate(){
        return this.muse != null;
    }

    public void setDataListenerMuse(DataListener dataListener){
        muse.registerDataListener(dataListener,MuseDataPacketType.EEG);
    }

    public void resetMuse(){
        this.muse = null;
    }
}