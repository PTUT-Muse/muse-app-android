package com.example.lpiem.muse_app_android.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.choosemuse.libmuse.ConnectionState;
import com.choosemuse.libmuse.Muse;
import com.choosemuse.libmuse.MuseConnectionPacket;
import com.choosemuse.libmuse.MuseDataPacketType;
import com.choosemuse.libmuse.MuseManagerAndroid;
import com.example.lpiem.muse_app_android.data.manager.SQLiteDataBase;
import com.example.lpiem.muse_app_android.data.model.Capture;
import com.example.lpiem.muse_app_android.data.model.Sensors;
import com.example.lpiem.muse_app_android.presentation.ui.listener.ConnectionListener;
import com.example.lpiem.muse_app_android.presentation.ui.listener.DataListener;
import com.example.lpiem.muse_app_android.presentation.ui.listener.ListenerMuse;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MuseRepository {


    private SQLiteDataBase db;
    private MuseManagerAndroid museManager;
    private Muse muse;

    public MuseRepository(Context context){
        this.museManager = MuseManagerAndroid.getInstance();
        this.db = new SQLiteDataBase(context);
    }


    public void setContextMuseManager(Context view){
        this.museManager.setContext(view);
    }

    public void setMuseListener(ListenerMuse museListener){
        this.museManager.setMuseListener(museListener);
    }

    public MuseManagerAndroid getMuseManager() {
        return museManager;
    }

    public void refreshListeningDevice(){
        this.museManager.stopListening();
        this.museManager.startListening();
    }

    public void stopListeningDevice(){
        this.museManager.stopListening();
    }

    public ArrayList<Muse> getDeviceAvaibles(){
        return this.museManager.getMuses();
    }


    public void connectDevice(ConnectionListener connectionListener){
        List<Muse> availableMuses = getDeviceAvaibles();

        muse = availableMuses.get(0);

        muse.unregisterAllListeners();
        muse.registerConnectionListener(connectionListener);

        muse.runAsynchronously();
    }

    public void setConntionListener(ConnectionListener conntionListener){
        muse.registerConnectionListener(conntionListener);
    }

    public boolean museIsInstantiate(){
        return this.muse != null;
    }

    public void setDataListenerMuse(DataListener dataListener){
        muse.registerDataListener(dataListener,MuseDataPacketType.EEG);
    }

    public void resetMuse(){
        this.muse = null;
    }



    public boolean insertData(String nom, String description, String date, String temps, int etat, Sensors muse){
       return db.insertData(nom,description,date,temps,etat,muse);
    }

    public ArrayList<Capture> getAllData(){
        return db.getAllData();
    }

    public Capture getDataByID(int id){
       return db.getDataByID(id);
    }

    public boolean deleteCapture(int id) {
      return db.deleteCapture(id);
    }

    public boolean updateCapture(int id, String nom, String description) {
       return db.updateCapture(id,nom,description);
    }

    public void export(Capture capture) throws IOException {
        String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String fileName = "AnalysisData-"+ Long.toString(timestamp.getTime()) + ".csv";
        String filePath = baseDir + File.separator + fileName;
        File f = new File(filePath );
        CSVWriter writer;
        if(f.exists() && !f.isDirectory()){
            FileWriter mFileWriter = new FileWriter(filePath , true);
            writer = new CSVWriter(mFileWriter);
        }
        else {
            writer = new CSVWriter(new FileWriter(filePath));
        }
        String[] data = {"capteur 1", "capteur 2", "capteur 3", "capteur 4"};

        writer.writeNext(data);

        for(int i = 0; i<capture.getSensors().getSensor1().size(); i++){
            String[] dataSensor = {capture.getSensors().getSensor1().get(i).toString(), capture.getSensors().getSensor2().get(i).toString(), capture.getSensors().getSensor3().get(i).toString(), capture.getSensors().getSensor4().get(i).toString()};
            writer.writeNext(dataSensor);
        }

        writer.close();
    }
}