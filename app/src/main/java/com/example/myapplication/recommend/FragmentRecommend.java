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
        Button btnGetResult;
        Button btnBack;
        TextView result, cond1, cond2, cond3, cond4, cond5, cond6;
        RadioGroup group1, group2, group3, group4, group6;
        Spinner group5;

        btnBack = view.findViewById(R.id.btn_goSearch); //추천 페이지로 가는 버튼
        btnGetResult = view.findViewById(R.id.btn_getResult); // 조건 설정 다 하고 누르는 버튼

        result = view.findViewById(R.id.filteringCondition);
        cond1 = view.findViewById(R.id.filteringCondition1); // 조건 확인용 텍스트뷰라서 나중에 지우는게 좋을듯
        cond2 = view.findViewById(R.id.filteringCondition2);
        cond3 = view.findViewById(R.id.filteringCondition3);
        cond4 = view.findViewById(R.id.filteringCondition4);
        cond5 = view.findViewById(R.id.filteringCondition5);
        cond6 = view.findViewById(R.id.filteringCondition6);

        group1 = view.findViewById(R.id.group1);
        group2 = view.findViewById(R.id.group2);
        group3 = view.findViewById(R.id.group3);
        group4 = view.findViewById(R.id.group4);
        group5 = view.findViewById(R.id.group5); //스피너
        group6 = view.findViewById(R.id.group6);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.condition5_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        group5.setAdapter(adapter);

        cond1.setVisibility(View.GONE); //결과보기 아래 조건 확인용 텍스트뷰를 안 보이게 가림
        cond2.setVisibility(View.GONE);
        cond3.setVisibility(View.GONE);
        cond4.setVisibility(View.GONE);
        cond5.setVisibility(View.GONE);
        cond6.setVisibility(View.GONE);

        btnBack.setOnClickListener(view -> {
            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();

            //라디오버튼 선택 초기화하기
            group1.clearCheck(); group2.clearCheck(); group3.clearCheck(); group4.clearCheck(); group6.clearCheck();
            
            ft.remove(FragmentRecommend.this).commit(); // 지금 띄운 추천 페이지 (this) 지우기
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            fm.popBackStack(); // 검색 -> 추천으로 호출했으니까 호출 스택에서 더 위에 있는 추천 프래그먼트를 pop 하는거 같음
        });

        btnGetResult.setOnClickListener(view -> {
            if(group1.getCheckedRadioButtonId() == -1 |
               group2.getCheckedRadioButtonId() == -1 |
               group3.getCheckedRadioButtonId() == -1 |
               group4.getCheckedRadioButtonId() == -1 |
               group5.getSelectedItemPosition() == 0 |
               group6.getCheckedRadioButtonId() == -1) {
                Toast.makeText(view.getContext().getApplicationContext(), "선택하지 않은 조건이 있어요!", Toast.LENGTH_SHORT).show();
            } else {
                result.setText("선택한 조건값 확인 :\n");
                result.append(cond1.getText());
                result.append(cond2.getText());
                result.append(cond3.getText());
                result.append(cond4.getText());
                result.append(cond5.getText());
                result.append(cond6.getText());
            }
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

        group5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cond5.setText(Integer.toString(adapterView.getSelectedItemPosition())); // 선택된 항목의 위치
                //cond5.setText(adapterView.getItemAtPosition(i).toString()); // 선택된 항목의 실제값
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        group6.setOnCheckedChangeListener(((radioGroup, i) -> {
            switch (i){
                case R.id.condition6_1:
                    cond6.setText(getString(R.string.condition6_1));
                    break;

                case R.id.condition6_2:
                    cond6.setText(getString(R.string.condition6_2));
                    break;

                case R.id.condition6_3:
                    cond6.setText(getString(R.string.condition6_3));
                    break;

                case R.id.condition6_4:
                    cond6.setText("X");
                    break;
            }
        }));

        return view;
    }


}