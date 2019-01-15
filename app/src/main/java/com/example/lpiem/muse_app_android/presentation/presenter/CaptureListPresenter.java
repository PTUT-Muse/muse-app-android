package com.example.lpiem.muse_app_android.presentation.presenter;

import com.example.lpiem.muse_app_android.MuseApplication;
import com.example.lpiem.muse_app_android.data.repository.MuseRepository;
import com.example.lpiem.muse_app_android.presentation.ui.view.CaptureListView;

public class CaptureListPresenter {
    private CaptureListView view;

    private MuseRepository repository = MuseApplication.getInstance().getRepository();


    public CaptureListPresenter(CaptureListView view){
        this.view = view;
    }

    public boolean isPaired(){
        return repository.isPaired();
    }


}
