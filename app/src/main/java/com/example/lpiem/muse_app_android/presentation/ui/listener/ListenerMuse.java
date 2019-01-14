package com.example.lpiem.muse_app_android.presentation.ui.listener;

import com.choosemuse.libmuse.MuseListener;
import com.example.lpiem.muse_app_android.presentation.ui.activity.ConnectDeviceActivity;

import java.lang.ref.WeakReference;

public class ListenerMuse extends MuseListener {

    final WeakReference<ConnectDeviceActivity> activityRef;

    public ListenerMuse(final WeakReference<ConnectDeviceActivity> activityRef) {
        this.activityRef = activityRef;
    }

    @Override
    public void museListChanged() {
        activityRef.get().museListChanged();
    }

}