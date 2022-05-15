package com.example.myapplication.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.recommend.FragmentRecommend;

public class FragmentSearch extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container,false);
        FragmentRecommend rec = new FragmentRecommend();

        Button btnRecommend = view.findViewById(R.id.button); //추천 페이지로 가는 버튼 초기화
        btnRecommend.setOnClickListener(view -> {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_search, rec);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();

            //getChildFragmentManager().beginTransaction().replace(R.id.search_frame, rec).commit();
            //위처럼 한 줄로 해도 되는데 애니메이션 넣고 싶어서 ft 선언해서 함 (나중에 바꿀 수 있나 찾아보기)
        });

        return view;
    }
}
