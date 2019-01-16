package com.example.lpiem.muse_app_android.presentation.ui.listener;

import com.choosemuse.libmuse.Muse;
import com.choosemuse.libmuse.MuseArtifactPacket;
import com.choosemuse.libmuse.MuseDataListener;
import com.choosemuse.libmuse.MuseDataPacket;
import com.example.lpiem.muse_app_android.presentation.presenter.ConnectDevicePresenter;
import com.example.lpiem.muse_app_android.presentation.presenter.NewCapturePresenter;

import java.lang.ref.WeakReference;

public class DataListener extends MuseDataListener {
    final WeakReference<NewCapturePresenter> activityRef;

    public DataListener(final WeakReference<NewCapturePresenter> activityRef) {
        this.activityRef = activityRef;
    }

    @Override
    public void receiveMuseDataPacket(final MuseDataPacket p, final Muse muse) {
        activityRef.get().receiveMuseDataPacket(p, muse);
    }

    @Override
    public void receiveMuseArtifactPacket(final MuseArtifactPacket p, final Muse muse) {
        //activityRef.get().receiveMuseArtifactPacket(p, muse);
    }
}