package com.example.myapplication.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.recommend.FragmentRecommend;

public class FragmentSearch extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_search, container, false);
        FragmentRecommend rec = new FragmentRecommend();
        FragmentSearchPlant searchPlant = new FragmentSearchPlant();
        FragmentBestFive bestFive = new FragmentBestFive();
        Button btnRecommend = view1.findViewById(R.id.button); //추천 페이지로 가는 버튼 초기화
        Button btnSearch = view1.findViewById(R.id.search_button);
        ImageButton purify_best_five = view1.findViewById(R.id.purify_best5_button);
        ImageButton herb_best_five = view1.findViewById(R.id.herb_best5_button);

        btnRecommend.setOnClickListener(view -> {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_search, rec);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();

            //getChildFragmentManager().beginTransaction().replace(R.id.search_frame, rec).commit();
            //위처럼 한 줄로 해도 되는데 애니메이션 넣고 싶어서 ft 선언해서 함 (나중에 바꿀 수 있나 찾아보기)
        });

        btnSearch.setOnClickListener(view -> {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_search, searchPlant);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();
        });

        purify_best_five.setOnClickListener(view -> {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            Bundle resultQuery = new Bundle();
            resultQuery.putString("bundleKey", "SELECT * FROM plants WHERE use = \"공기정화용\" LIMIT 5");
            getChildFragmentManager().setFragmentResult("rk", resultQuery);
            ft.replace(R.id.fragment_search, bestFive);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();
        });

        herb_best_five.setOnClickListener(view -> {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            Bundle resultQuery = new Bundle();
            resultQuery.putString("bundleKey", "SELECT * FROM plants WHERE use = \"식용\" LIMIT 5");
            getChildFragmentManager().setFragmentResult("rk", resultQuery);
            ft.replace(R.id.fragment_search, bestFive);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();
        });

        return view1;
    }
}
