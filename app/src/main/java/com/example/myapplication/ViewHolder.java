package com.example.myapplication;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

class ViewHolder extends RecyclerView.ViewHolder {

    ImageView plantImage;
    TextView plantSpecies;
    TextView plantNickname;

    ViewHolder(View itemView) {
        super(itemView);

        plantImage = itemView.findViewById(R.id.my_plant_image);
        plantSpecies = itemView.findViewById(R.id.my_plant_species);
        plantNickname = itemView.findViewById(R.id.my_plant_nickname);
    }
}