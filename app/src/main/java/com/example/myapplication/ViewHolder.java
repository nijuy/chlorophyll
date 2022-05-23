package com.example.myapplication;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class ViewHolder extends RecyclerView.ViewHolder {

    TextView test1_textView;
    TextView test2_textView;

    ViewHolder(View itemView) {
        super(itemView);

        test1_textView = itemView.findViewById(R.id.test1_textview);
        test2_textView = itemView.findViewById(R.id.test2_textview);
    }
}