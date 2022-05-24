package com.example.myapplication.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.MyPlantList;
import com.example.myapplication.MyPlantListAdapter;
import com.example.myapplication.OnItemClick;
import com.example.myapplication.R;
import com.example.myapplication.calendar.FragmentPlan;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment implements OnItemClick {

    SharedPreferences pref, listPref;

    String species, nickname;

    ArrayList<MyPlantList> myPlantList;
    RecyclerView recyclerView;
    ImageButton addButton;
    MyPlantListAdapter adapter;
    View rootView = null;
    Context context;
    FragmentAddPlant fragmentAddPlant = new FragmentAddPlant();
    FragmentDiary fragmentDiary = new FragmentDiary();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        listPref = context.getSharedPreferences("listPref", Context.MODE_PRIVATE);
        String[] array = (listPref.getString("title", "")).split("/");

        if (array.length == 1) {
            rootView = inflater.inflate(R.layout.fragment_home2, container,false);

            addButton = rootView.findViewById(R.id.add_plant_button);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)getActivity()).replaceFragment(fragmentAddPlant);
                }
            });

        } else {
            rootView = inflater.inflate(R.layout.fragment_home, container,false);
            recyclerView = rootView.findViewById(R.id.my_plant_list);
            myPlantList = new ArrayList<MyPlantList>();

            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            addButton = rootView.findViewById(R.id.add_plant_button);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)getActivity()).replaceFragment(fragmentAddPlant);
                }
            });

            for (int i = 0; i < array.length; i++) {
                if (!array[i].equals("")) {
                    pref = getActivity().getSharedPreferences(array[i], Context.MODE_PRIVATE);

                    species = pref.getString("species", "");
                    nickname = pref.getString("nickname", "");
                    myPlantList.add(new MyPlantList(species, nickname));
                }
            }

            adapter = new MyPlantListAdapter(context, myPlantList, this);
            recyclerView.setAdapter(adapter);
        }

        return rootView;
    }

    @Override
    public void onClick(String value){
        if (value.equals("diary")) {
            FragmentDiary fragmentDiary = new FragmentDiary();
            ((MainActivity)getActivity()).replaceFragment(fragmentDiary);
        } else if (value.equals("detail")) {
            FragmentDetail fragmentDetail = new FragmentDetail();
            ((MainActivity)getActivity()).replaceFragment(fragmentDetail);
        }
    }
}