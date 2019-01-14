package com.example.lpiem.muse_app_android.presentation.ui.listener;

import com.choosemuse.libmuse.Muse;
import com.choosemuse.libmuse.MuseConnectionListener;
import com.choosemuse.libmuse.MuseConnectionPacket;
import com.example.lpiem.muse_app_android.presentation.presenter.ConnectDevicePresenter;
import com.example.lpiem.muse_app_android.presentation.ui.activity.CaptureListActivity;
import com.example.lpiem.muse_app_android.presentation.ui.activity.ConnectDeviceActivity;
import com.example.lpiem.muse_app_android.presentation.ui.activity.NewCaptureActivity;

import java.lang.ref.WeakReference;

public class ConnectionListener extends MuseConnectionListener {
    final WeakReference<ConnectDevicePresenter> activityRef;

    public ConnectionListener(final WeakReference<ConnectDevicePresenter> activityRef) {
        this.activityRef = activityRef;
    }

    @Override
    public void receiveMuseConnectionPacket(final MuseConnectionPacket p, final Muse muse) {
        //activityRef.get().receiveMuseConnectionPacket(p, muse);
    }
}