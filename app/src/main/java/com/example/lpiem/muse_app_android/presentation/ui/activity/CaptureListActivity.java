package com.example.lpiem.muse_app_android.presentation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.lpiem.muse_app_android.R;
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

    private List<Capture> captureList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_list);

        captureRecyclerView = findViewById(R.id.capture_list_rv);
        FloatingActionButton fabAddCapture = findViewById(R.id.fab_addCapture);

        captureList.add(new Capture());
        captureList.add(new Capture());
        captureList.add(new Capture());
        captureList.add(new Capture());
        captureList.add(new Capture());
        captureList.add(new Capture());

        captureRecyclerView.setLayoutManager(new GridLayoutManager(this,3));


        captureListAdapter = new CaptureListAdapter(captureList, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CaptureListActivity.this, DetailCaptureActivity.class);
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
    public void updateList(List<Capture> listCapture) {
        captureList.clear();
        captureList = listCapture;
        captureListAdapter.updateList(captureList);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
