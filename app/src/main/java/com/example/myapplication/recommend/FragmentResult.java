package com.example.myapplication.recommend;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Plant;
import com.example.myapplication.R;
import com.example.myapplication.DatabaseHelper;

import java.util.ArrayList;

public class FragmentResult extends Fragment {
    private View view;
    private String query;
    private DatabaseHelper databaseHelper;

    FragmentResult(){
        try {
            databaseHelper = new DatabaseHelper(getActivity().getApplicationContext());
            databaseHelper.OpenDatabaseFile();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_result, container,false);
        ArrayList<Plant> plantList;
        ImageButton btnBack;
        TextView queryView;
        RecyclerView recyclerView;

        btnBack = view.findViewById(R.id.btn_goRecommend);
        queryView = view.findViewById(R.id.queryText);
        recyclerView = view.findViewById(R.id.resultRecommend);

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

        /*
        plantList = getFilteredPlant(query);
        showResult();

        Context ct = container.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(ct));

        RecyclerAdapter adapter;
        adapter = new RecyclerAdapter(ct, plantList);
        recyclerView.setAdapter(adapter);
        */


        return view;
    }

    public ArrayList<Plant> getFilteredPlant(String sql){
        return databaseHelper.getTableData(sql);
    }

    public void showResult(){

    }
}
