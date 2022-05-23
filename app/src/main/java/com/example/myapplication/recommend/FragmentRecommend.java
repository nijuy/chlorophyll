package com.example.myapplication.recommend;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;

public class FragmentRecommend extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recommend, container,false);
        StringBuilder resultText, cond1, cond2, cond3, cond4, cond6;
        RadioGroup group1, group2, group3, group4, group6;
        Button btnGetResult;
        Button btnBack;

        btnBack = view.findViewById(R.id.btn_goSearch);
        btnGetResult = view.findViewById(R.id.btn_getResult);

        resultText = new StringBuilder("select * from plants where ");
        cond1 = new StringBuilder();
        cond2 = new StringBuilder();
        cond3 = new StringBuilder();
        cond4 = new StringBuilder();
        cond6 = new StringBuilder();
        
        group1 = view.findViewById(R.id.group1);
        group2 = view.findViewById(R.id.group2);
        group3 = view.findViewById(R.id.group3);
        group4 = view.findViewById(R.id.group4);
        group6 = view.findViewById(R.id.group6);

        btnBack.setOnClickListener(view -> {
            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
          
            group1.clearCheck(); group2.clearCheck(); group3.clearCheck(); group4.clearCheck(); group6.clearCheck(); //라디오버튼 선택 초기화하기

            ft.remove(FragmentRecommend.this).commit(); // 지금 띄운 추천 페이지 (this) 지우기
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE); // 애니메이션
            fm.popBackStack(); // 검색 -> 추천으로 호출했으니까 호출 스택에서 더 위에 있는 추천 프래그먼트를 pop 하는거 같음
        });

        btnGetResult.setOnClickListener(view -> {
            if(group1.getCheckedRadioButtonId() == -1 | group2.getCheckedRadioButtonId() == -1 | group3.getCheckedRadioButtonId() == -1 |
               group4.getCheckedRadioButtonId() == -1 | group6.getCheckedRadioButtonId() == -1)
                Toast.makeText(view.getContext().getApplicationContext(), "선택하지 않은 조건이 있어요!", Toast.LENGTH_SHORT).show();

            else {
                resultText.setLength(27); //resultText 초기화 (초기값으로 만듦 - 아래에서 조건을 append로 연결중이라 초기화 필요)
                resultText.append(new StringBuilder().append("use = \"").append(cond1).append("\" "));
                resultText.append(new StringBuilder().append("and size = \"").append(cond2).append("\" "));
                resultText.append(new StringBuilder().append("and flowering = \"").append(cond3).append("\" "));
                resultText.append(new StringBuilder().append("and difficulty = \"").append(cond4).append("\" "));
                resultText.append(new StringBuilder().append("and sunshine = \"").append(cond6).append("\" "));

                FragmentResult resultPage = new FragmentResult();
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();

                group1.clearCheck(); group2.clearCheck(); group3.clearCheck(); group4.clearCheck(); group6.clearCheck();

                Bundle resultQuery = new Bundle();
                resultQuery.putString("bundleKey", resultText.toString());
                getParentFragmentManager().setFragmentResult("rk", resultQuery);

                ft.remove(FragmentRecommend.this);
                ft.replace(R.id.fragment_search, resultPage);
                ft.commit();
            }
        });

        group1.setOnCheckedChangeListener((radioGroup, i) -> {
            cond1.setLength(0); // 초기화
            switch (i){
                case R.id.condition1_1:
                    cond1.append(getString(R.string.condition1_1));
                    break;

                case R.id.condition1_2:
                    cond1.append(getString(R.string.condition1_2));
                    break;

                case R.id.condition1_3:
                    cond1.append(getString(R.string.condition1_3));
                    break;

                case R.id.condition1_4:
                    cond1.append("X");
                    break;
            }
        });

        group2.setOnCheckedChangeListener(((radioGroup, i) -> {
            cond2.setLength(0);
            switch (i){
                case R.id.condition2_1:
                    cond2.append(getString(R.string.condition2_1));
                    break;

                case R.id.condition2_2:
                    cond2.append(getString(R.string.condition2_2));
                    break;

                case R.id.condition2_3:
                    cond2.append(getString(R.string.condition2_3));
                    break;

                case R.id.condition2_4:
                    cond2.append("X");
                    break;
            }
        }));

        group3.setOnCheckedChangeListener(((radioGroup, i) -> {
            cond3.setLength(0);
            switch (i){
                case R.id.condition3_1:
                    cond3.append(getString(R.string.condition3_1));
                    break;

                case R.id.condition3_2:
                    cond3.append(getString(R.string.condition3_2));
                    break;

                case R.id.condition3_3:
                    cond3.append(getString(R.string.condition3_3));
                    break;

                case R.id.condition3_4:
                    cond3.append(getString(R.string.condition3_4));
                    break;

                case R.id.condition3_5:
                    cond3.append("X");
                    break;
            }
        }));

        group4.setOnCheckedChangeListener(((radioGroup, i) -> {
            cond4.setLength(0);
            switch (i){
                case R.id.condition4_1:
                    cond4.append(getString(R.string.condition4_1));
                    break;

                case R.id.condition4_2:
                    cond4.append(getString(R.string.condition4_2));
                    break;

                case R.id.condition4_3:
                    cond4.append(getString(R.string.condition4_3));
                    break;

                case R.id.condition4_4:
                    cond4.append("X");
                    break;
            }
        }));

        group6.setOnCheckedChangeListener(((radioGroup, i) -> {
            cond6.setLength(0);
            switch (i){
                case R.id.condition6_1:
                    cond6.append(getString(R.string.condition6_1));
                    break;

                case R.id.condition6_2:
                    cond6.append(getString(R.string.condition6_2));
                    break;

                case R.id.condition6_3:
                    cond6.append(getString(R.string.condition6_3));
                    break;

                case R.id.condition6_4:
                    cond6.append("X");
                    break;
            }
        }));

        return view;
    }
}