package com.example.lpiem.muse_app_android.presentation.ui.listener;

import com.choosemuse.libmuse.Muse;
import com.choosemuse.libmuse.MuseConnectionListener;
import com.choosemuse.libmuse.MuseConnectionPacket;
import com.example.lpiem.muse_app_android.presentation.presenter.CaptureListPresenter;
import com.example.lpiem.muse_app_android.presentation.presenter.ConnectDevicePresenter;
import com.example.lpiem.muse_app_android.presentation.presenter.NewCaptureDetailsPresenter;
import com.example.lpiem.muse_app_android.presentation.presenter.NewCapturePresenter;

import java.lang.ref.WeakReference;

public class ConnectionListener extends MuseConnectionListener {
    private final WeakReference<NewCapturePresenter> weakNewCapturePresenter;
    private final WeakReference<ConnectDevicePresenter> weakConnectDevicePresenter;
    private final WeakReference<NewCaptureDetailsPresenter> weakNewCaptureDetailsPresenter;
    private final WeakReference<CaptureListPresenter> weakCaptureListPresenter;


    public ConnectionListener(final WeakReference<NewCapturePresenter> activityNewCapturePresenter,final WeakReference<ConnectDevicePresenter> connectDevicePresenter, final WeakReference<NewCaptureDetailsPresenter> weakNewCaptureDetailsPresenter, final WeakReference<CaptureListPresenter> weakCaptureListPresenter) {
        this.weakNewCapturePresenter = activityNewCapturePresenter;
        this.weakConnectDevicePresenter = connectDevicePresenter;
        this.weakNewCaptureDetailsPresenter = weakNewCaptureDetailsPresenter;
        this.weakCaptureListPresenter = weakCaptureListPresenter;
    }

    @Override
    public void receiveMuseConnectionPacket(final MuseConnectionPacket p, final Muse muse) {
        if(weakNewCapturePresenter != null){
            weakNewCapturePresenter.get().receiveMuseConnectionPacket(p,muse);
        } else if(weakConnectDevicePresenter != null){
            weakConnectDevicePresenter.get().receiveMuseConnectionPacket(p,muse);
        } else if(weakNewCaptureDetailsPresenter != null){
            weakNewCaptureDetailsPresenter.get().receiveMuseConnectionPacket(p,muse);
        } else {
            weakCaptureListPresenter.get().receiveMuseConnectionPacket(p,muse);
        }
    }
}