package com.example.myapplication.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.search.FragmentSearchPlant;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FragmentAddPlant extends Fragment {

    SharedPreferences listPref, pref;
    SharedPreferences.Editor listEditor, editor;

    String value, species, nickname, image, list;
    String id, packName;
    int water = -1, sun = -1, split = -1;
    int resID;

    View rootView = null;
    TextView speciesText;
    EditText nicknameEdit;
    Button photoBtn, doneBtn, cancelBtn;
    ImageView imageView = null;

    Switch waterSwitch, sunSwitch, splitSwitch;
    TextView waterText, sunText, splitText;
    EditText waterEdit, sunEdit, splitEdit;

    FragmentHome fragmentHome;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Context context = getActivity();
        fragmentHome = new FragmentHome();
        rootView = inflater.inflate(R.layout.fragment_add_plant, container, false);
        imageView = rootView.findViewById(R.id.addPhoto_image);
        speciesText = rootView.findViewById(R.id.plant_species);
        nicknameEdit = rootView.findViewById(R.id.plant_nickname);
        doneBtn = rootView.findViewById(R.id.doneBtn);
        cancelBtn = rootView.findViewById(R.id.cancelBtn);

        packName = getActivity().getPackageName();
        resID = getResources().getIdentifier(image, "drawable", packName);
        speciesText.setText(species);
        imageView.setImageResource(resID);

        waterEdit = rootView.findViewById(R.id.water_edit);
        waterText = rootView.findViewById(R.id.water_textview);
        waterSwitch = rootView.findViewById(R.id.water_switch);
        waterSwitch.setChecked(true);
        waterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b) { // ?????? ??????
                    waterEdit.setEnabled(false);
                    waterEdit.setTextColor(Color.parseColor("#9e9b9b"));
                    waterText.setTextColor(Color.parseColor("#9e9b9b"));
                    water = 0;
                } else {
                    waterEdit.setEnabled(true);
                    waterEdit.setTextColor(Color.parseColor("#000000"));
                    waterText.setTextColor(Color.parseColor("#000000"));
                    value = waterEdit.getText().toString();

                    if (value.equals("")) water = 0;
                    else water = Integer.parseInt(value);
                }
            }
        });

        sunEdit = rootView.findViewById(R.id.sun_edit);
        sunText = rootView.findViewById(R.id.sun_textview);
        sunSwitch = rootView.findViewById(R.id.sun_switch);
        sunSwitch.setChecked(true);
        sunSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b) { // ?????? ??????
                    sunEdit.setEnabled(false);
                    sunEdit.setTextColor(Color.parseColor("#9e9b9b"));
                    sunText.setTextColor(Color.parseColor("#9e9b9b"));
                    sun = 0;
                } else {
                    sunEdit.setEnabled(true);
                    sunEdit.setTextColor(Color.parseColor("#000000"));
                    sunText.setTextColor(Color.parseColor("#000000"));
                    value = sunEdit.getText().toString();

                    if (value.equals("")) sun = 0;
                    else sun = Integer.parseInt(value);
                }
            }
        });

        splitEdit = rootView.findViewById(R.id.split_edit);
        splitText = rootView.findViewById(R.id.split_textview);
        splitSwitch = rootView.findViewById(R.id.split_switch);
        splitSwitch.setChecked(true);
        splitSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b) { // ?????? ??????
                    splitEdit.setEnabled(false);
                    splitEdit.setTextColor(Color.parseColor("#9e9b9b"));
                    splitText.setTextColor(Color.parseColor("#9e9b9b"));
                    split = 0;
                } else {
                    splitEdit.setEnabled(true);
                    splitEdit.setTextColor(Color.parseColor("#000000"));
                    splitText.setTextColor(Color.parseColor("#000000"));
                    value = splitEdit.getText().toString();

                    if (value.equals("")) split = 0;
                    else split = Integer.parseInt(value);
                }
            }
        });

        fragmentHome = new FragmentHome();

        listPref = context.getSharedPreferences("listPref", Context.MODE_PRIVATE);
        listEditor = listPref.edit();

        String fileName = getTime();
        pref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        editor = pref.edit();

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean res = true;

                if (water == -1) {
                    if((waterEdit.getText().toString()).equals("")) {
                        Toast.makeText(context, "??? ????????? ??????????????????", Toast.LENGTH_SHORT).show();
                        res = false;
                    } else {
                        water = Integer.parseInt(waterEdit.getText().toString());

                        if (water == 0) {
                            Toast.makeText(context, "0?????? ??? ?????? ??????????????????", Toast.LENGTH_SHORT).show();
                            water = -1;
                            res = false;
                        }
                    }
                }

                if (sun == -1) {
                    if((sunEdit.getText().toString()).equals("")) {
                        Toast.makeText(context, "?????? ????????? ??????????????????", Toast.LENGTH_SHORT).show();
                        res = false;
                    } else {
                        sun = Integer.parseInt(sunEdit.getText().toString());

                        if (sun == 0) {
                            Toast.makeText(context, "0?????? ??? ?????? ??????????????????", Toast.LENGTH_SHORT).show();
                            sun = -1;
                            res = false;
                        }
                    }
                }

                if (split == -1) {
                    if((splitEdit.getText().toString()).equals("")) {
                        Toast.makeText(context, "????????? ????????? ??????????????????", Toast.LENGTH_SHORT).show();
                        res = false;
                    } else {
                        split = Integer.parseInt(splitEdit.getText().toString());

                        if (split == 0) {
                            Toast.makeText(context, "0?????? ??? ?????? ??????????????????", Toast.LENGTH_SHORT).show();
                            split = -1;
                            res = false;
                        }
                    }
                }

                if (res) {
                    if(water < 0 || sun < 0 || split < 0) {
                        Toast.makeText(getActivity(), "????????? ???????????? ??????????????????", Toast.LENGTH_SHORT).show();
                    } else {
                        species = speciesText.getText().toString();
                        editor.putString("species", species);

                        nickname = nicknameEdit.getText().toString();
                        editor.putString("nickname", nickname);

                        editor.putString("id", id);
                        editor.putString("image", image);

                        editor.putInt("water", water);
                        editor.putInt("sun", sun);
                        editor.putInt("split", split);

                        editor.apply(); // ??????

                        list = listPref.getString("title", "/");
                        listEditor.putString("title", list + "/" + fileName);
                        listEditor.apply();

                        ((MainActivity) getActivity()).replaceFragment(fragmentHome);
                    }
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(getId(),fragmentHome);
                ft.commit();
            }
        });

        return rootView;
    }

    private String getTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_hh_mm_ss");
        String getTime = dateFormat.format(date);
        return getTime;
    }

    public void setSpecies(String species){
        this.species = species;
    }

    public void setImage(String image) { this.image = image; }

    public void setId(String id) { this.id = id; }
}