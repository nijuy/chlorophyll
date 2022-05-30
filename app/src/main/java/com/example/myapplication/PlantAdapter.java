package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class PlantAdapter extends ArrayAdapter<Plant>{

    public PlantAdapter(Context context, int resource, List<Plant> plantList)
    {
        super(context, resource,plantList);
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Plant plant = (Plant) getItem(position);
      
        if (convertView == null) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.plant_item, parent, false);
            TextView textView = itemView.findViewById(R.id.plant_name_search);
            ImageView imageView = itemView.findViewById(R.id.plant_image_search);
            textView.setText(plant.getName());
            imageView.setImageResource(getContext().getResources().getIdentifier(plant.getImage(),"drawable", getContext().getPackageName()));

            return itemView;
        }
        else{
            TextView textView =  convertView.findViewById(R.id.plant_name_search);
            ImageView imageView = convertView.findViewById(R.id.plant_image_search);
            textView.setText(plant.getName());
            imageView.setImageResource(getContext().getResources().getIdentifier(plant.getImage(),"drawable", getContext().getPackageName()));
            return convertView;
        }
    }
}
