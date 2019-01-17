package com.example.lpiem.muse_app_android.data.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lpiem.muse_app_android.R;
import com.example.lpiem.muse_app_android.data.model.Capture;

import java.util.ArrayList;

public class SQLiteDataBase extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "captures_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NOM";
    public static final String COL_3 = "DESCRIPTION";
    public static final String COL_4 = "DATE";
    public static final String COL_5 = "TEMPS";
    public static final String COL_6 = "ETAT";
    public static final String COL_7 = "MUSE";

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

    public boolean insertData(String nom, String description, String date, String temps, int etat, String muse){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, nom);
        contentValues.put(COL_3, description);
        contentValues.put(COL_4, date);
        contentValues.put(COL_5, temps);
        contentValues.put(COL_6, etat);
        contentValues.put(COL_7, muse);
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
            // String museTemp = data.getString(6);
            Capture captureTemp = new Capture(idTemp,stateTemp, titleTemp, descriptionTemp, dateTemp, timeTemp);
            captureList.add(captureTemp);
            System.out.println("captureTemp : " + captureTemp);
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
            // String museTemp = data.getString(6);
            capture = new Capture(idTemp,stateTemp, titleTemp, descriptionTemp, dateTemp, timeTemp);
            System.out.println("capture : " + capture);
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
