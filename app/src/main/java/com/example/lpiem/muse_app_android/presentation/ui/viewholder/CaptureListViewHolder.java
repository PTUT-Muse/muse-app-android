package com.example.lpiem.muse_app_android.presentation.ui.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lpiem.muse_app_android.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CaptureListViewHolder extends RecyclerView.ViewHolder {
    public ImageView captureEtat;
    public TextView captureTitre;
    public TextView captureTemps;
    public TextView captureDate;

    public CaptureListViewHolder(@NonNull View itemView) {
        super(itemView);
        captureEtat = itemView.findViewById(R.id.imgState);
        captureTitre = itemView.findViewById(R.id.txtCapture);
        captureTemps = itemView.findViewById(R.id.txtTemps);
        captureDate = itemView.findViewById(R.id.txtDate);

    }
}
