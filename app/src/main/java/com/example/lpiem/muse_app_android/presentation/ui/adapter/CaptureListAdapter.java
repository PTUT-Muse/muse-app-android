package com.example.lpiem.muse_app_android.presentation.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.lpiem.muse_app_android.R;
import com.example.lpiem.muse_app_android.data.model.Capture;
import com.example.lpiem.muse_app_android.presentation.ui.viewholder.CaptureListViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CaptureListAdapter extends RecyclerView.Adapter<CaptureListViewHolder> {
    private List<Capture> captureList;
    private AdapterView.OnItemClickListener onCaptureClick;

    public CaptureListAdapter(List<Capture> captureList, AdapterView.OnItemClickListener click) {
        this.captureList = captureList;
        this.onCaptureClick = click;
    }


    @NonNull
    @Override
    public CaptureListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_capture_item, parent, false);
        return new CaptureListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CaptureListViewHolder holder, final int position) {
        holder.captureTitre.setText(captureList.get(position).getTitre());
        holder.captureDate.setText(captureList.get(position).getDate());
        holder.captureTemps.setText(captureList.get(position).getTemps());
        Log.d("mlk", "id etat : "+captureList.get(position).getEtat());
        holder.captureEtat.setImageResource(R.mipmap.etonne);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCaptureClick.onItemClick(null,v,position,0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return captureList.size();
    }

    public void updateList(List<Capture> listCapture) {
        this.captureList.clear();
        this.addComicsList(listCapture);
    }

    private void addComicsList(List<Capture> listCapture) {
        this.captureList.addAll(listCapture);
        notifyDataSetChanged();
    }
}
