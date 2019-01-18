package com.example.lpiem.muse_app_android.presentation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.example.lpiem.muse_app_android.R;
import com.example.lpiem.muse_app_android.data.model.Capture;
import com.example.lpiem.muse_app_android.presentation.presenter.CaptureListPresenter;
import com.example.lpiem.muse_app_android.presentation.ui.adapter.CaptureListAdapter;
import com.example.lpiem.muse_app_android.presentation.ui.listener.ConnectionListener;
import com.example.lpiem.muse_app_android.presentation.ui.view.CaptureListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CaptureListActivity extends AppCompatActivity implements CaptureListView, View.OnClickListener {
    private CaptureListPresenter presenter = new CaptureListPresenter(this);

    private RecyclerView captureRecyclerView;
    private CaptureListAdapter captureListAdapter;

    private List<Capture> captureList = new ArrayList<>();

    private Button dateFilter;
    private boolean isIncreased = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_list);


        dateFilter = findViewById(R.id.btnTriDate);
        dateFilter.setOnClickListener(this);


        if(presenter.museIsInstantiate()) {
            presenter.setContextMuseManager(this);

            WeakReference<CaptureListPresenter> weakPresenter = new WeakReference<>(presenter);

            presenter.setConnectionListener(new ConnectionListener(null,null,null , weakPresenter));
        }

        captureRecyclerView = findViewById(R.id.capture_list_rv);
        FloatingActionButton fabAddCapture = findViewById(R.id.fab_addCapture);

        captureRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        captureListAdapter = new CaptureListAdapter(captureList, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CaptureListActivity.this, DetailsCaptureActivity.class);
                intent.putExtra("id", captureList.get(position).getId());
                startActivity(intent);
            }
        });

        fabAddCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!presenter.museIsInstantiate()) {
                    Intent intent = new Intent(CaptureListActivity.this, ConnectDeviceActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(CaptureListActivity.this, NewCaptureDetailsActivity.class);
                    startActivity(intent);
                }

            }
        });

        captureRecyclerView.setAdapter(captureListAdapter);

        presenter.getAllData();
    }

    @Override
    public void onResume(){
        super.onResume();
        captureList.clear();
        presenter.getAllData();
    }

    @Override
    public void updateList(List<Capture> listCapture) {
        List<Capture> listCaptureTemp = new ArrayList<>(listCapture);
        captureList.clear();
        captureList = listCaptureTemp;
        captureListAdapter.updateList(captureList);
    }

    @Override
    public void showMuseDisconnect() {
        presenter.resetMuse();
        Toast toast = Toast.makeText(this, "Appareil deconnecté", Toast.LENGTH_SHORT);
        toast.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTriDate:
                List<Capture> listCap = captureList;
                presenter.filterDate(listCap,isIncreased);
                isIncreased = !isIncreased;
                if(isIncreased){
                    dateFilter.setText(getString(R.string.capture_list_filter_decroissant));

                } else {
                    dateFilter.setText(getString(R.string.capture_list_filter_croissant));
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
