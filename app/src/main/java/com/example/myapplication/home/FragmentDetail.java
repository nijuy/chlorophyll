package com.example.myapplication.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class FragmentDetail extends Fragment {
    private View rootView;
    private String title = "", species, nickname, value, list, newList;
    private int water, sun, split;
    private int newWater, newSun, newSplit;

    Context context;
    SharedPreferences listPref, pref;
    SharedPreferences.Editor listEditor, editor;

    TextView plantSpecies, plantNickname;
    TextView waterText, sunText, splitText;
    TextView waterTextView, sunTextView, splitTextView;
    Switch waterSwitch, sunSwitch, splitSwitch;

    Button editBtn, deleteBtn;

    FragmentHome fragmentHome;
    FragmentEdit fragmentEdit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detail, container,false);
        context = getActivity();

        if(getArguments() != null) {
            title = getArguments().getString("title");
            pref = context.getSharedPreferences(title, Context.MODE_PRIVATE);
            editor = pref.edit();

            species = pref.getString("species", "");
            nickname = pref.getString("nickname", "");
            water = pref.getInt("water", 0);
            sun = pref.getInt("sun", 0);
            split = pref.getInt("split", 0);

            plantSpecies = rootView.findViewById(R.id.plant_species);
            plantNickname = rootView.findViewById(R.id.plant_nickname);

            waterText = rootView.findViewById(R.id.water_text);
            sunText = rootView.findViewById(R.id.sun_text);
            splitText = rootView.findViewById(R.id.split_text);

            waterTextView = rootView.findViewById(R.id.water_textview);
            sunTextView = rootView.findViewById(R.id.sun_textview);
            splitTextView = rootView.findViewById(R.id.split_textview);

            waterSwitch = rootView.findViewById(R.id.water_switch);
            sunSwitch = rootView.findViewById(R.id.sun_switch);
            splitSwitch = rootView.findViewById(R.id.split_switch);

            plantSpecies.setText(species);
            plantNickname.setText(nickname);

            editBtn = rootView.findViewById(R.id.toEditBtn);
            deleteBtn = rootView.findViewById(R.id.toDeleteBtn);

            if(water == 0) {
                waterSwitch.setChecked(false);
                waterTextView.setTextColor(Color.parseColor("#9e9b9b"));
                waterText.setTextColor(Color.parseColor("#9e9b9b"));
            } else {
                waterSwitch.setChecked(true);
                waterText.setText(Integer.toString(water));
                waterTextView.setTextColor(Color.parseColor("#000000"));
                waterText.setTextColor(Color.parseColor("#000000"));
            }

            if(sun == 0) {
                sunSwitch.setChecked(false);
                sunTextView.setTextColor(Color.parseColor("#9e9b9b"));
                sunText.setTextColor(Color.parseColor("#9e9b9b"));
            } else {
                sunSwitch.setChecked(true);
                sunText.setText(Integer.toString(sun));
                sunTextView.setTextColor(Color.parseColor("#000000"));
                sunText.setTextColor(Color.parseColor("#000000"));
            }

            if(split == 0) {
                splitSwitch.setChecked(false);
                splitTextView.setTextColor(Color.parseColor("#9e9b9b"));
                splitText.setTextColor(Color.parseColor("#9e9b9b"));
            } else {
                splitSwitch.setChecked(true);
                splitText.setText(Integer.toString(split));
                splitTextView.setTextColor(Color.parseColor("#000000"));
                splitText.setTextColor(Color.parseColor("#000000"));
            }


            // Switch 즉석 on off 기능 추가 필요
            waterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(!b) { // 설정 안함
                        waterTextView.setTextColor(Color.parseColor("#9e9b9b"));
                        waterText.setTextColor(Color.parseColor("#9e9b9b"));
                        newWater = 0;
                        editor.putInt("water", water);
                        editor.apply(); // 저장
                    } else {
                        waterTextView.setTextColor(Color.parseColor("#000000"));
                        waterText.setTextColor(Color.parseColor("#000000"));
                        value = waterText.getText().toString();

                        if (value.equals("")) {
                            newWater = 0;
                            editor.putInt("water", newWater);
                            editor.apply(); // 저장
                        }
                        else {
                            newWater = Integer.parseInt(value);
                            editor.putInt("water", newWater);
                            editor.apply(); // 저장
                        }
                    }
                }
            });

            sunSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(!b) { // 설정 안함
                        sunTextView.setTextColor(Color.parseColor("#9e9b9b"));
                        sunText.setTextColor(Color.parseColor("#9e9b9b"));
                        newSun = 0;
                        editor.putInt("sun", newSun);
                        editor.apply(); // 저장
                    } else {
                        sunTextView.setTextColor(Color.parseColor("#000000"));
                        sunText.setTextColor(Color.parseColor("#000000"));
                        value = sunText.getText().toString();

                        if (value.equals("")) {
                            newSun = 0;
                            editor.putInt("sun", newSun);
                            editor.apply(); // 저장
                        }
                        else {
                            newSun = Integer.parseInt(value);
                            editor.putInt("sun", newSun);
                            editor.apply(); // 저장
                        }
                    }
                }
            });

            splitSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(!b) { // 설정 안함
                        splitTextView.setTextColor(Color.parseColor("#9e9b9b"));
                        splitText.setTextColor(Color.parseColor("#9e9b9b"));
                        newSplit = 0;
                        editor.putInt("split", newSplit);
                        editor.apply(); // 저장
                    } else {
                        splitTextView.setTextColor(Color.parseColor("#000000"));
                        splitText.setTextColor(Color.parseColor("#000000"));
                        value = splitText.getText().toString();

                        if (value.equals("")) {
                            newSplit = 0;
                            editor.putInt("split", newSplit);
                            editor.apply(); // 저장
                        }
                        else {
                            newSplit = Integer.parseInt(value);
                            editor.putInt("split", newSplit);
                            editor.apply(); // 저장
                        }
                    }
                }
            });

            // 수정 버튼 클릭 시 edit fragment로 이동 기능 추가 필요
            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragmentEdit = new FragmentEdit();

                    Bundle bundle = new Bundle();
                    bundle.putString("title", title);
                    fragmentEdit.setArguments(bundle);

                    ((MainActivity)getActivity()).replaceFragment(fragmentEdit);
                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragmentHome = new FragmentHome();

                    listPref = context.getSharedPreferences("listPref", Context.MODE_PRIVATE);
                    listEditor = listPref.edit();
                    list = listPref.getString("title", "");
                    newList = list.replace("/" + title, "");
                    listEditor.putString("title", newList);

                    //editor.clear(); // 파일 자체를 삭제하지 못함. 방법을 모르겠음..
                    //editor.apply(); // 저장
                    listEditor.apply(); // 저장

                    ((MainActivity)getActivity()).replaceFragment(fragmentHome);

                    Toast.makeText(context, "식물 정보를 삭제했습니다", Toast.LENGTH_SHORT).show();
                }
            });
            // 삭제 버튼 클릭 시 정보 삭제되는 (Toast 메시지로 삭제 했다는 알림도) 기능 추가 필요
        } else {
            // 식물 식별자가 제대로 넘어가지 않을 경우 Toast 메시지로 연결 실패했다는 문구 띄우기
        }

        return rootView;
    }
}