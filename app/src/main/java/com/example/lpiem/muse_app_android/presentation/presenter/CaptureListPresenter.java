package com.example.lpiem.muse_app_android.presentation.presenter;

import android.content.Context;

import com.choosemuse.libmuse.ConnectionState;
import com.choosemuse.libmuse.Muse;
import com.choosemuse.libmuse.MuseConnectionPacket;
import com.example.lpiem.muse_app_android.MuseApplication;
import com.example.lpiem.muse_app_android.data.model.Capture;
import com.example.lpiem.muse_app_android.data.repository.MuseRepository;
import com.example.lpiem.muse_app_android.presentation.ui.listener.ConnectionListener;
import com.example.lpiem.muse_app_android.presentation.ui.view.CaptureListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class CaptureListPresenter {

    private CaptureListView view;
    private MuseRepository repository = MuseApplication.getInstance().getRepository();

    public CaptureListPresenter(CaptureListView view){
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

    public boolean museIsInstantiate(){
        return repository.museIsInstantiate();
    }

    public void getAllData() {
        view.updateList(repository.getAllData());
    }

    public void filterDate(List<Capture> listCapture, boolean isIncreased){

        Collections.sort(listCapture, new Comparator<Capture>() {
            @Override
            public int compare(final Capture object1, final Capture object2) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
                Date dateCapture1 = null;
                Date dateCapture2 = null;
                try {
                    dateCapture1 = sdf.parse(object1.getDate());
                    dateCapture2 = sdf.parse(object2.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                return dateCapture1.compareTo(dateCapture2);
            }
        });

        if(isIncreased){
            Collections.reverse(listCapture);
        }

        view.updateList(listCapture);

    }


}