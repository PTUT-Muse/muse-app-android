package com.example.lpiem.muse_app_android.data.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.lpiem.muse_app_android.R;
import com.example.lpiem.muse_app_android.data.model.Capture;
import com.example.lpiem.muse_app_android.data.model.Sensors;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SQLiteDataBase extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "captures_table";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "NOM";
    private static final String COL_3 = "DESCRIPTION";
    private static final String COL_4 = "DATE";
    private static final String COL_5 = "TEMPS";
    private static final String COL_6 = "ETAT";
    private static final String COL_7 = "MUSE";

    public SQLiteDataBase(Context context){
        super(context, context.getResources().getString(R.string.db_name), null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOM TEXT, DESCRIPTION TEXT, DATE TEXT, TEMPS TEXT, ETAT INTEGER, MUSE TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String nom, String description, String date, String temps, int etat, Sensors muse){
        SQLiteDatabase db = this.getWritableDatabase();
        Gson gson = new Gson();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, nom);
        contentValues.put(COL_3, description);
        contentValues.put(COL_4, date);
        contentValues.put(COL_5, temps);
        contentValues.put(COL_6, etat);
        contentValues.put(COL_7, gson.toJson(muse));
        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }
    
    public ArrayList<Capture> getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Capture> captureList = new ArrayList<>();
        Cursor result = db.rawQuery("select * from "+TABLE_NAME, null);

        while (result.moveToNext()) {
            int idTemp = result.getInt(0);
            String titleTemp = result.getString(1);
            String descriptionTemp = result.getString(2);
            String dateTemp = result.getString(3);
            String timeTemp = result.getString(4);
            int stateTemp = result.getInt(5);
            Capture captureTemp = new Capture(idTemp,stateTemp, titleTemp, descriptionTemp, dateTemp, timeTemp, null);
            captureList.add(captureTemp);
        }

        return captureList;
    }

    public Capture getDataByID(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Capture capture = null;
        Cursor result = db.rawQuery("select * from "+TABLE_NAME+" WHERE "+COL_1+"="+id, null);

        while (result.moveToNext()) {
            int idTemp = result.getInt(0);
            String titleTemp = result.getString(1);
            String descriptionTemp = result.getString(2);
            String dateTemp = result.getString(3);
            String timeTemp = result.getString(4);
            int stateTemp = result.getInt(5);
            String museTemp = result.getString(6);
            Gson gson = new Gson();
            Sensors sensors = gson.fromJson(museTemp, Sensors.class);
            capture = new Capture(idTemp,stateTemp, titleTemp, descriptionTemp, dateTemp, timeTemp, sensors);
        }
        return capture;
    }

    public boolean deleteCapture(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME,
                COL_1 + " = ? ",
                new String[] { Integer.toString(id) });

        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean updateCapture(int id, String nom, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, nom);
        contentValues.put(COL_3, description);
        db.update(TABLE_NAME, contentValues, COL_1 + " = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

}
