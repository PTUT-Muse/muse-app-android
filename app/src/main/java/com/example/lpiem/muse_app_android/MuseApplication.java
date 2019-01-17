package com.example.lpiem.muse_app_android;

import android.app.Application;

import com.example.lpiem.muse_app_android.data.repository.MuseRepository;

import androidx.multidex.MultiDex;

public class MuseApplication extends Application {
    private static MuseApplication app;
    private MuseRepository repository;

    public static MuseApplication getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        app = this;
        repository = new MuseRepository(this);
    }

    public MuseRepository getRepository() {
        return repository;
    }
}