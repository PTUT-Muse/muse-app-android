package com.example.lpiem.muse_app_android.presentation.ui.view;

public interface NewCaptureView {
    void updateEeg(double[] eegBuffer);
    void showMuseDisconnect();
}