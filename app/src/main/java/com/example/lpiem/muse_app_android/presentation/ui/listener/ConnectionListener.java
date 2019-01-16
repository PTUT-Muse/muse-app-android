package com.example.lpiem.muse_app_android.presentation.ui.listener;

import com.choosemuse.libmuse.Muse;
import com.choosemuse.libmuse.MuseConnectionListener;
import com.choosemuse.libmuse.MuseConnectionPacket;
import com.example.lpiem.muse_app_android.presentation.presenter.ConnectDevicePresenter;
import com.example.lpiem.muse_app_android.presentation.presenter.NewCapturePresenter;

import java.lang.ref.WeakReference;

public class ConnectionListener extends MuseConnectionListener {
    final WeakReference<NewCapturePresenter> activityNewCapturePresenter;
    final WeakReference<ConnectDevicePresenter> connectDevicePresenter;


    public ConnectionListener(final WeakReference<NewCapturePresenter> activityNewCapturePresenter,final WeakReference<ConnectDevicePresenter> connectDevicePresenter) {
        this.activityNewCapturePresenter = activityNewCapturePresenter;
        this.connectDevicePresenter = connectDevicePresenter;
    }

    @Override
    public void receiveMuseConnectionPacket(final MuseConnectionPacket p, final Muse muse) {
        if(activityNewCapturePresenter != null){
            activityNewCapturePresenter.get().receiveMuseConnectionPacket(p,muse);
        } else {
            connectDevicePresenter.get().receiveMuseConnectionPacket(p,muse);
        }
    }
}