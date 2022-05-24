package com.example.myapplication.recommend;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.Plant;
import com.example.myapplication.PlantAdapter;
import com.example.myapplication.R;
import java.util.ArrayList;

public class FragmentResult extends Fragment {
    private View view;
    private String query;
    private DatabaseHelper databaseHelper;
    private ArrayList<Plant> plantList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        connectDB();
        view = inflater.inflate(R.layout.fragment_result, container,false);
        ImageButton btnBack;
        TextView queryView;

        btnBack = view.findViewById(R.id.btn_goRecommend);
        queryView = view.findViewById(R.id.queryText);

        btnBack.setOnClickListener(view -> {
            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();

            ft.remove(FragmentResult.this).commit();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            fm.popBackStack();
        });

        super.onCreateView(inflater, container, savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("rk", this, (requestKey, result) -> {
            query = result.getString("bundleKey");
            queryView.setText(query);
        });

        plantList = getFilteredPlant(query);
        showResult();
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
            //ArrayList<Plant> temp = databaseHelper.getTableData("select * from plants");
            ArrayList<Plant> temp = databaseHelper.getTableData(sql);
            return temp;
        } catch (NullPointerException e){
            Log.e("","표시할 결과가 없음");
            return new ArrayList<>();
        }
    }

    public void showResult(){
        ListView listView = view.findViewById(R.id.result_recommendList);

        PlantAdapter adapter = new PlantAdapter(getActivity().getApplicationContext(),0,plantList);
        listView.setAdapter(adapter);
    }
}
