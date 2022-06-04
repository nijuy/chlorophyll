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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class FragmentEdit extends Fragment {
    private View rootView;
    private String title = "", species, nickname, value, image, packName;
    private int water, sun, split;
    private int newWater, newSun, newSplit;

    Context context;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    TextView plantSpecies;
    EditText plantNickname;
    EditText waterEdit, sunEdit, splitEdit;
    TextView waterTextView, sunTextView, splitTextView;
    Switch waterSwitch, sunSwitch, splitSwitch;
    ImageView imageView;

    Button completeBtn, cancelBtn;

    FragmentDetail fragmentDetail;
    FragmentHome fragmentHome;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_edit, container,false);
        context = getActivity();

        if (getArguments() != null) {
            title = getArguments().getString("title");
            image = getArguments().getString("image");
            pref = context.getSharedPreferences(title, Context.MODE_PRIVATE);
            editor = pref.edit();

            species = pref.getString("species", "");
            nickname = pref.getString("nickname", "");
            water = pref.getInt("water", 0);
            sun = pref.getInt("sun", 0);
            split = pref.getInt("split", 0);

            newWater = water;
            newSun = sun;
            newSplit = split;

            plantSpecies = rootView.findViewById(R.id.plant_species);
            plantNickname = rootView.findViewById(R.id.plant_nickname);
            plantSpecies.setText(species);
            plantNickname.setText(nickname);

            waterEdit = rootView.findViewById(R.id.water_edit);
            sunEdit = rootView.findViewById(R.id.sun_edit);
            splitEdit = rootView.findViewById(R.id.split_edit);

            waterTextView = rootView.findViewById(R.id.water_textview);
            sunTextView = rootView.findViewById(R.id.sun_textview);
            splitTextView = rootView.findViewById(R.id.split_textview);

            waterSwitch = rootView.findViewById(R.id.water_switch);
            sunSwitch = rootView.findViewById(R.id.sun_switch);
            splitSwitch = rootView.findViewById(R.id.split_switch);

            completeBtn = rootView.findViewById(R.id.completeBtn);
            cancelBtn = rootView.findViewById(R.id.cancelBtn);

            imageView = rootView.findViewById(R.id.addPhoto_image);
            packName = context.getPackageName();
            int resID = context.getResources().getIdentifier(image, "drawable", packName);
            imageView.setImageResource(resID);

            if (water == 0) {
                waterSwitch.setChecked(false);
                waterEdit.setEnabled(false);
                waterTextView.setTextColor(Color.parseColor("#9e9b9b"));
                waterEdit.setTextColor(Color.parseColor("#9e9b9b"));
            } else {
                waterSwitch.setChecked(true);
                waterEdit.setEnabled(true);
                waterEdit.setText(Integer.toString(water));
                waterTextView.setTextColor(Color.parseColor("#000000"));
                waterEdit.setTextColor(Color.parseColor("#000000"));
            }

            if (sun == 0) {
                sunSwitch.setChecked(false);
                sunEdit.setEnabled(false);
                sunTextView.setTextColor(Color.parseColor("#9e9b9b"));
                sunEdit.setTextColor(Color.parseColor("#9e9b9b"));
            } else {
                sunSwitch.setChecked(true);
                sunEdit.setEnabled(true);
                sunEdit.setText(Integer.toString(sun));
                sunTextView.setTextColor(Color.parseColor("#000000"));
                sunEdit.setTextColor(Color.parseColor("#000000"));
            }

            if (split == 0) {
                splitSwitch.setChecked(false);
                splitEdit.setEnabled(false);
                splitTextView.setTextColor(Color.parseColor("#9e9b9b"));
                splitEdit.setTextColor(Color.parseColor("#9e9b9b"));
            } else {
                splitSwitch.setChecked(true);
                splitEdit.setEnabled(true);
                splitEdit.setText(Integer.toString(split));
                splitTextView.setTextColor(Color.parseColor("#000000"));
                splitEdit.setTextColor(Color.parseColor("#000000"));
            }

            waterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (!b) { // 설정 안함
                        waterEdit.setEnabled(false);
                        waterEdit.setTextColor(Color.parseColor("#9e9b9b"));
                        waterTextView.setTextColor(Color.parseColor("#9e9b9b"));
                        newWater = 0;
                    } else {
                        waterEdit.setEnabled(true);
                        waterEdit.setTextColor(Color.parseColor("#000000"));
                        waterTextView.setTextColor(Color.parseColor("#000000"));
                        newWater = -1;
                    }
                }
            });

            sunSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (!b) { // 설정 안함
                        sunEdit.setEnabled(false);
                        sunEdit.setTextColor(Color.parseColor("#9e9b9b"));
                        sunTextView.setTextColor(Color.parseColor("#9e9b9b"));
                        newSun = 0;
                    } else {
                        sunEdit.setEnabled(true);
                        sunEdit.setTextColor(Color.parseColor("#000000"));
                        sunTextView.setTextColor(Color.parseColor("#000000"));
                        newSun = -1;
                    }
                }
            });

            splitSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (!b) { // 설정 안함
                        splitEdit.setEnabled(false);
                        splitEdit.setTextColor(Color.parseColor("#9e9b9b"));
                        splitTextView.setTextColor(Color.parseColor("#9e9b9b"));
                        newSplit = 0;
                    } else {
                        splitEdit.setEnabled(true);
                        splitEdit.setTextColor(Color.parseColor("#000000"));
                        splitTextView.setTextColor(Color.parseColor("#000000"));

                        newSplit = -1;
                    }
                }
            });

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragmentDetail = new FragmentDetail();
                    Bundle bundle = new Bundle();
                    bundle.putString("title", title);
                    fragmentDetail.setArguments(bundle);
                    ((MainActivity)getActivity()).replaceFragment(fragmentDetail);
                }
            });

            completeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragmentHome = new FragmentHome();

                    boolean res1 = true;
                    boolean res2 = true;
                    boolean res3 = true;
                    boolean res4 = true;

                    if(newWater == -1) {
                        value = waterEdit.getText().toString();
                        if (value.equals("")) {
                            Toast.makeText(getActivity(), "물주기를 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                            res1 = false;
                        } else {
                            newWater = Integer.parseInt(value);
                            editor.putInt("water", newWater);
                        }
                    } else {
                        editor.putInt("water", newWater);
                    }

                    if (newSun == -1) {
                        value = sunEdit.getText().toString();
                        if (value.equals("")) {
                            Toast.makeText(getActivity(), "햇빛주기를 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                            res2 = false;
                        } else {
                            newSun = Integer.parseInt(value);
                            editor.putInt("sun", newSun);
                        }
                    } else {
                        editor.putInt("sun", newSun);
                    }

                    if (newSplit == -1) {
                        value = splitEdit.getText().toString();
                        if (value.equals("")) {
                            Toast.makeText(getActivity(), "분갈이 주기를 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                            res3 = false;
                        } else {
                            newSplit = Integer.parseInt(value);
                            editor.putInt("split", newSplit);
                        }
                    } else {
                        editor.putInt("split", newSplit);
                    }

                    if ((plantNickname.getText().toString()).equals("")) {
                        Toast.makeText(getActivity(), "식물의 별칭을 입력해주세요", Toast.LENGTH_SHORT).show();
                        res4 = false;
                    } else {
                        nickname = plantNickname.getText().toString();
                        editor.putString("nickname", nickname);
                    }

                    if (res1 && res2 && res3 && res4) {
                        species = plantSpecies.getText().toString();
                        editor.putString("species", species);

                        editor.apply(); // 저장

                        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                        ft.replace(getId(), fragmentHome);
                        ft.commit();
                    }
                }
            });
        }

        return rootView;
    }
}