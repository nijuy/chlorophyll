package com.example.myapplication.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Plant;
import com.example.myapplication.R;

import java.util.ArrayList;

public class FragmentFeature extends Fragment {
    private View view;
    private String id = "";
    private String packName;

    public static ArrayList<Plant> plantList;

    Plant selectPlant;
    Button listBtn;
    FragmentHome fragmentHome;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feature, container,false);

        if(getArguments() != null) {
            id = getArguments().getString("id");
            packName = getActivity().getPackageName();

            setUpData();
            getSelectedPlant();
            setValues();

            listBtn = view.findViewById(R.id.button_go_to_list);
            listBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragmentHome = new FragmentHome();
                    ((MainActivity)getActivity()).replaceFragment(fragmentHome);
                }
            });
        }
        return view;
    }

    private void setValues() {
        TextView textView = view.findViewById(R.id.plant_detail_name);
        ImageView imageView = view.findViewById(R.id.plant_detail_image);
        TextView textView1 = view.findViewById(R.id.plant_detail_tip);
        textView.setText(selectPlant.getName());
        String tip = selectPlant.getTip().replace("\\n", System.getProperty("line.separator"));
        textView1.setText(tip);
        imageView.setImageResource(getResources().getIdentifier(selectPlant.getImage(),"drawable",packName));
    }

    private void getSelectedPlant(){
        Log.v("getSelectedPlant", "실행 중");
        int id_num = Integer.parseInt(id)-1;

        selectPlant = plantList.get(id_num);
    }

    private void setUpData(){
        Log.v("setUpData", "실행 중");
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity().getApplicationContext());
        databaseHelper.OpenDatabaseFile();

        plantList = databaseHelper.getTableData("SELECT * FROM plants");
        Log.e("test", String.valueOf(plantList.size()));

        databaseHelper.close();
    }
}