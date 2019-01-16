package com.example.lpiem.muse_app_android.presentation.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.lpiem.muse_app_android.R;
import com.example.lpiem.muse_app_android.data.manager.SQLiteDataBase;
import com.example.lpiem.muse_app_android.data.model.Capture;
import com.example.lpiem.muse_app_android.presentation.presenter.CaptureListPresenter;
import com.example.lpiem.muse_app_android.presentation.ui.adapter.CaptureListAdapter;
import com.example.lpiem.muse_app_android.presentation.ui.view.CaptureListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CaptureListActivity extends AppCompatActivity implements CaptureListView {
    private CaptureListPresenter presenter = new CaptureListPresenter(this);

    private RecyclerView captureRecyclerView;
    private CaptureListAdapter captureListAdapter;
    SQLiteDataBase db;

    private List<Capture> captureList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_list);

        db = new SQLiteDataBase(this);
        getDonneesCapture();

        captureRecyclerView = findViewById(R.id.capture_list_rv);
        FloatingActionButton fabAddCapture = findViewById(R.id.fab_addCapture);

        captureRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        captureListAdapter = new CaptureListAdapter(captureList, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CaptureListActivity.this, DetailCaptureActivity.class);
                Log.d("mlk", "id passé :"+captureList.get(position).getId());
                intent.putExtra("id", captureList.get(position).getId());
                startActivity(intent);
            }
        });

        fabAddCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CaptureListActivity.this, ConnectDeviceActivity.class);
                startActivity(intent);
            }
        });

        captureRecyclerView.setAdapter(captureListAdapter);
        //presenter.getAllCaptures();
        Log.d("mlk", "mlk"+presenter.isPaired());
    }

    @Override
    public void onResume(){
        super.onResume();
        captureList.clear();
        db = new SQLiteDataBase(this);
        getDonneesCapture();
        captureListAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateList(List<Capture> listCapture) {
        captureList.clear();
        captureList = listCapture;
        captureListAdapter.updateList(captureList);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void getDonneesCapture() {
        Cursor data = db.getAllData();
        Log.d("mlk", "data : "+data.toString());

        while (data.moveToNext()) {
            int idTemp = data.getInt(0);
            String nomTemp = data.getString(1);
            String descriptionTemp = data.getString(2);
            String dateTemp = data.getString(3);
            String tempsTemp = data.getString(4);
            int etatTemp = data.getInt(5);
            // String museTemp = data.getString(6);
            Capture captureTemp = new Capture(idTemp,etatTemp, nomTemp, descriptionTemp, dateTemp, tempsTemp);
            captureList.add(captureTemp);
            System.out.println("captureTemp : " + captureTemp);
        }
    }
}
