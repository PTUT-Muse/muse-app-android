package com.example.lpiem.muse_app_android.data.repository;

import android.content.Context;

import com.choosemuse.libmuse.Muse;
import com.choosemuse.libmuse.MuseManagerAndroid;
import com.example.lpiem.muse_app_android.presentation.ui.listener.ListenerMuse;

import java.util.ArrayList;

public class MuseRepository {

    private MuseManagerAndroid museManager;

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

}