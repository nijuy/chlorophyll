package com.example.myapplication.recommend;

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
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class FragmentResult extends Fragment {
    private View view;
    private DatabaseHelper databaseHelper;
    public static ArrayList<Plant> plantList;
    public static ArrayList<Plant> filterPlantList;
    private ListView listView;
    ActivityResultLauncher<Intent> activityResultLauncher;
    FragmentAddPlant fragmentAddPlant;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_result, container,false);
        fragmentAddPlant = new FragmentAddPlant();
        ImageButton btnBack;

        listView = view.findViewById(R.id.result_recommendList);
        btnBack = view.findViewById(R.id.btn_goRecommend);

        connectDB();
        setUpOnClickListener();

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == Activity.RESULT_OK) {
                Intent intent = result.getData();
                fragmentAddPlant.setSpecies(intent.getStringExtra("name")); // 등록 페이지 내 식물 종 자동 설정
                ((MainActivity)getActivity()).replaceFragment(fragmentAddPlant); // 상세 페이지 -> 등록 fragment 대체
            }
        });

        btnBack.setOnClickListener(view -> {
            databaseHelper.CloseDatabaseFile();
            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();

            ft.remove(FragmentResult.this).commit();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            fm.popBackStack();
        });

        super.onCreateView(inflater, container, savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("rk", this, (requestKey, result) -> {
            plantList = getFilteredPlant("SELECT * FROM plants");
            filterPlantList = getFilteredPlant(result.getString("bundleKey"));
            showResult();
            setUpOnClickListener();
        });

        return view;
    }

    public void connectDB(){
        try {
            databaseHelper = new DatabaseHelper(getActivity().getApplicationContext());
            databaseHelper.OpenDatabaseFile();
        } catch (Exception e){
            Log.e("","예외 발생");
        }
    }

    public ArrayList<Plant> getFilteredPlant(String sql){
        try {
            return databaseHelper.getTableData(sql);
        } catch (NullPointerException e){
            Log.e("","표시할 결과가 없음");
            return new ArrayList<>();
        }
    }

    public void showResult(){
        PlantAdapter adapter = new PlantAdapter(getActivity().getApplicationContext(),0,filterPlantList);
        listView.setAdapter(adapter);
    }

    private void setUpOnClickListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Plant selectPlant = (Plant) listView.getItemAtPosition(i);
                Intent showDetail = new Intent(getActivity(), DetailActivity.class);
                showDetail.putExtra("id", selectPlant.getId());
                showDetail.putExtra("pageKey", "2");
                activityResultLauncher.launch(showDetail);
            }
        });
    }

}
