package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.recommend.FragmentResult;
import com.example.myapplication.search.FragmentBestFive;
import com.example.myapplication.search.FragmentSearchPlant;

public class DetailActivity extends AppCompatActivity {
    Plant selectPlant;
    Button add_to_my_plant;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSelectedPlant();

        setValues();
        btnBack = this.findViewById(R.id.btn_goSearch);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        add_to_my_plant = this.findViewById(R.id.button_add_to_my_plant);
        add_to_my_plant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                intent.putExtra("id", selectPlant.getId());
                intent.putExtra("name",selectPlant.getName());
                intent.putExtra("image", selectPlant.getImage());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    private void setValues() {
        TextView textView = findViewById(R.id.plant_detail_name);
        ImageView imageView = findViewById(R.id.plant_detail_image);
        TextView textView1 = findViewById(R.id.plant_detail_tip);
        textView.setText(selectPlant.getName());
        String tip = selectPlant.getTip().replace("\\n", System.getProperty("line.separator"));
        textView1.setText(tip);
        imageView.setImageResource(getResources().getIdentifier(selectPlant.getImage(),"drawable",getPackageName()));
    }

    private void getSelectedPlant(){
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String pageKey = intent.getStringExtra("pageKey");
        int id_num = Integer.parseInt(id)-1;

        switch (pageKey) {
            case "1":
                selectPlant = FragmentSearchPlant.plantList.get(id_num);
                break;

            case "2":
                selectPlant = FragmentResult.plantList.get(id_num);
                break;

            case "3":
                selectPlant = FragmentBestFive.plantList.get(id_num);
                break;
        }

    }
}