package com.example.myapplication.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.DetailActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Plant;
import com.example.myapplication.PlantAdapter;
import com.example.myapplication.R;
import com.example.myapplication.home.FragmentAddPlant;

import java.util.ArrayList;

public class FragmentSearchPlant extends Fragment {
    private View view;
    public static ArrayList<Plant> plantList;
    ListView listView;
    ActivityResultLauncher<Intent> activityResultLauncher;
    FragmentAddPlant fragmentAddPlant = new FragmentAddPlant();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_plant, container,false);
        setUpData();
        setUpList();
        searchPlant();
        setUpOnClickListener();

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == Activity.RESULT_OK) {
                Intent intent = result.getData();
                fragmentAddPlant.setSpecies(intent.getStringExtra("name"));
                ((MainActivity)getActivity()).replaceFragment(fragmentAddPlant);
            }
        });

        ImageButton btnBack;

        btnBack = view.findViewById(R.id.btn_goSearch);
        btnBack.setOnClickListener(view ->{
            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            ft.remove(FragmentSearchPlant.this).commit(); // 지금 띄운 추천 페이지 (this) 지우기
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            fm.popBackStack();
        });

        return view;
    }

    private void searchPlant(){
        SearchView searchView = view.findViewById(R.id.plant_search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Plant> filterPlant = new ArrayList<>();
                for(int i=0; i<plantList.size(); i++){
                    Plant plant = plantList.get(i);
                    if(plant.getName().toLowerCase().contains(newText.toLowerCase())){
                        filterPlant.add(plant);
                    }
                }

                PlantAdapter adapter = new PlantAdapter(getActivity().getApplicationContext(),0,filterPlant);
                listView.setAdapter(adapter);
                return false;
            }
        });
    }

    private void setUpData(){
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity().getApplicationContext());
        databaseHelper.OpenDatabaseFile();

        plantList = databaseHelper.getTableData("SELECT * FROM plants");
        Log.e("test", String.valueOf(plantList.size()));

        databaseHelper.close();
    }

    private void setUpList(){
        listView = view.findViewById(R.id.plant_listView);

        PlantAdapter adapter = new PlantAdapter(getActivity().getApplicationContext(),0,plantList);
        listView.setAdapter(adapter);
    }

    private void setUpOnClickListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Plant selectPlant = (Plant) listView.getItemAtPosition(i);
                Intent showDetail = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
                showDetail.putExtra("id", selectPlant.getId());
                showDetail.putExtra("pageKey", "1");

                activityResultLauncher.launch(showDetail);
            }
        });
    }
}
