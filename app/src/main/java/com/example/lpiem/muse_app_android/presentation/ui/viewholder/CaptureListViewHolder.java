package com.example.lpiem.muse_app_android.presentation.ui.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lpiem.muse_app_android.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CaptureListViewHolder extends RecyclerView.ViewHolder {
    public ImageView captureState;
    public TextView captureTitle;
    public TextView captureTime;
    public TextView captureDate;

    public CaptureListViewHolder(@NonNull View itemView) {
        super(itemView);
        captureState = itemView.findViewById(R.id.imgState);
        captureTitle = itemView.findViewById(R.id.txtCapture);
        captureTime = itemView.findViewById(R.id.txtTemps);
        captureDate = itemView.findViewById(R.id.txtDate);

    }
}
