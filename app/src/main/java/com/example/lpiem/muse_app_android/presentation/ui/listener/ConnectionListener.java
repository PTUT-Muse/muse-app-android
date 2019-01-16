package com.example.lpiem.muse_app_android.presentation.ui.listener;

import com.choosemuse.libmuse.Muse;
import com.choosemuse.libmuse.MuseConnectionListener;
import com.choosemuse.libmuse.MuseConnectionPacket;
import com.example.lpiem.muse_app_android.presentation.ui.activity.ConnectDeviceActivity;

import java.lang.ref.WeakReference;

public class ConnectionListener extends MuseConnectionListener {
    final WeakReference<ConnectDeviceActivity> activityRef;

    public ConnectionListener(final WeakReference<ConnectDeviceActivity> activityRef) {
        this.activityRef = activityRef;
    }

    @Override
    public void receiveMuseConnectionPacket(final MuseConnectionPacket p, final Muse muse) {
        //activityRef.get().receiveMuseConnectionPacket(p, muse);
    }
}