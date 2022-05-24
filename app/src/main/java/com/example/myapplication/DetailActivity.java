package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.search.FragmentSearchPlant;

public class DetailActivity extends AppCompatActivity {
    Plant selectPlant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSelectedPlant();

        setValues();
    }

    private void setValues() {
        TextView textView = findViewById(R.id.plant_detail_name);
        ImageView imageView = findViewById(R.id.plant_detail_image);

        textView.setText(selectPlant.getName());
        imageView.setImageResource(getResources().getIdentifier(selectPlant.getImage(),"drawable",getPackageName()));
    }

    private void getSelectedPlant(){
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        int id_num = Integer.parseInt(id)-1;

        selectPlant = FragmentSearchPlant.plantList.get(id_num);
    }
}