package com.example.lpiem.muse_app_android.presentation.ui.view;

import com.example.lpiem.muse_app_android.data.model.Capture;

import java.util.List;

public interface CaptureListView {
    void updateList(List<Capture> listCapture);
    void showError(String message);
    void showMuseDisconnect();

}
