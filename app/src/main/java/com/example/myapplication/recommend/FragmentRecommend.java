package com.example.myapplication.recommend;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

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
        Button btnGetResult;
        Button btnBack;
        TextView result, cond1, cond2, cond3, cond4;
        RadioGroup group1, group2, group3, group4;

        btnBack = view.findViewById(R.id.btn_goSearch); //추천 페이지로 가는 버튼
        btnGetResult = view.findViewById(R.id.btn_getResult); // 조건 설정 다 하고 누르는 버튼
        result = view.findViewById(R.id.filteringCondition);
        cond1 = view.findViewById(R.id.filteringCondition1);
        cond2 = view.findViewById(R.id.filteringCondition2);
        cond3 = view.findViewById(R.id.filteringCondition3);
        cond4 = view.findViewById(R.id.filteringCondition4);
        group1 = view.findViewById(R.id.group1);
        group2 = view.findViewById(R.id.group2);
        group3 = view.findViewById(R.id.group3);
        group4 = view.findViewById(R.id.group4);

        btnBack.setOnClickListener(view -> {
            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();

            //라디오버튼 선택 다 초기화하기
            group1.clearCheck(); group2.clearCheck(); group3.clearCheck(); group4.clearCheck();
            
            ft.remove(FragmentRecommend.this).commit(); // 지금 띄운 추천 페이지 (this) 지우기
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            fm.popBackStack(); // 검색 -> 추천으로 호출했으니까 호출 스택에서 더 위에 있는 추천 프래그먼트를 pop 하는거 같음
        });

        btnGetResult.setOnClickListener(view -> {
            // 결과보기 누르면 할 일 : 쿼리 만들기 -> db로 보내기 -> 결과 받아오기 -> 새 창에서 결과 띄우기
            result.setText("\n선택한 조건값 확인 :\n");
            result.append(cond1.getText());
            result.append(cond2.getText());
            result.append(cond3.getText());
            result.append(cond4.getText());
        });

        group1.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i){
                case R.id.condition1_1:
                    cond1.setText(getString(R.string.condition1_1));
                    break;

                case R.id.condition1_2:
                    cond1.setText(getString(R.string.condition1_2));
                    break;

                case R.id.condition1_3:
                    cond1.setText(getString(R.string.condition1_3));
                    break;

                case R.id.condition1_4:
                    cond1.setText("X");
                    break;
            }
        });

        group2.setOnCheckedChangeListener(((radioGroup, i) -> {
            switch (i){
                case R.id.condition2_1:
                    cond2.setText(getString(R.string.condition2_1));
                    break;

                case R.id.condition2_2:
                    cond2.setText(getString(R.string.condition2_2));
                    break;

                case R.id.condition2_3:
                    cond2.setText(getString(R.string.condition2_3));
                    break;

                case R.id.condition2_4:
                    cond2.setText("X");
                    break;
            }
        }));

        group3.setOnCheckedChangeListener(((radioGroup, i) -> {
            switch (i){
                case R.id.condition3_1:
                    cond3.setText(getString(R.string.condition3_1));
                    break;

                case R.id.condition3_2:
                    cond3.setText(getString(R.string.condition3_2));
                    break;

                case R.id.condition3_3:
                    cond3.setText(getString(R.string.condition3_3));
                    break;

                case R.id.condition3_4:
                    cond3.setText(getString(R.string.condition3_4));
                    break;

                case R.id.condition3_5:
                    cond3.setText("X");
                    break;
            }
        }));

        group4.setOnCheckedChangeListener(((radioGroup, i) -> {
            switch (i){
                case R.id.condition4_1:
                    cond4.setText(getString(R.string.condition4_1));
                    break;

                case R.id.condition4_2:
                    cond4.setText(getString(R.string.condition4_2));
                    break;

                case R.id.condition4_3:
                    cond4.setText(getString(R.string.condition4_3));
                    break;

                case R.id.condition4_4:
                    cond4.setText("X");
                    break;
            }
        }));

        return view;
    }
}