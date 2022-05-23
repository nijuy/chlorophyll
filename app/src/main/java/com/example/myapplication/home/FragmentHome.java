package com.example.myapplication.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.myapplication.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    String species, nickname;

    ArrayList<MyPlantList> myPlantList;
    RecyclerView recyclerView;
    ImageButton addButton;
    MyPlantListAdapter adapter;
    View rootView = null;
    Context context;
    FragmentAddPlant fragmentAddPlant = new FragmentAddPlant();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container,false);
        recyclerView = rootView.findViewById(R.id.my_plant_list);
        myPlantList = new ArrayList<MyPlantList>();

        context = getActivity();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        addButton = rootView.findViewById(R.id.add_plant_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(fragmentAddPlant);
            }
        });

        String direc = "/data/data/" + context.getPackageName() + "/shared_prefs";
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + direc;
        File directory = new File(direc);
        File[] files = directory.listFiles();

        List<String> filesNameList = new ArrayList<>();

        for (int i = 0; i < files.length; i++) {
            filesNameList.add(files[i].getName());
        }

        for (int i = 0; i < filesNameList.size(); i++) {
            pref = getActivity().getSharedPreferences(filesNameList.get(i), Context.MODE_PRIVATE);
            editor = pref.edit();

            species = pref.getString("species", "");
            nickname = pref.getString("nickname", "");

            myPlantList.add(new MyPlantList(species, nickname));
        }

        adapter = new MyPlantListAdapter(context, myPlantList);
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}