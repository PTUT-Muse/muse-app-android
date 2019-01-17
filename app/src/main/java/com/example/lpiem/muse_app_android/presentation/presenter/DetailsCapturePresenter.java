package com.example.lpiem.muse_app_android.presentation.presenter;

import com.example.lpiem.muse_app_android.MuseApplication;
import com.example.lpiem.muse_app_android.data.model.Capture;
import com.example.lpiem.muse_app_android.data.repository.MuseRepository;
import com.example.lpiem.muse_app_android.presentation.ui.view.DetailsCaptureView;

import java.io.IOException;

public class DetailsCapturePresenter {
    private DetailsCaptureView view;

    private MuseRepository repository = MuseApplication.getInstance().getRepository();
    
    public DetailsCapturePresenter(DetailsCaptureView view){
        this.view = view;
    }

    public void getDataByID(int id){
        view.showCapture(repository.getDataByID(id));
    }

    public boolean deleteCapture(int id) {
        return repository.deleteCapture(id);
    }

    public boolean updateCapture(int id, String nom, String description) {
        return repository.updateCapture(id,nom,description);
    }

    public void exportCSV(Capture capture) throws IOException {
        repository.export(capture);
    }

}
