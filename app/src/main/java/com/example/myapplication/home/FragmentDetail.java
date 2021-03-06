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

public class FragmentDetail extends Fragment {
    private View rootView;
    private String title = "", species, nickname, value, list, newList;
    private String packName, image;
    private int water, sun, split;
    private int newWater, newSun, newSplit;

    Context context;
    SharedPreferences listPref, pref;
    SharedPreferences.Editor listEditor, editor;

    TextView plantSpecies, plantNickname;
    TextView waterText, sunText, splitText;
    TextView waterTextView, sunTextView, splitTextView;
    Switch waterSwitch, sunSwitch, splitSwitch;
    ImageView imageView;

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
            image = getArguments().getString("image");
            pref = context.getSharedPreferences(title, Context.MODE_PRIVATE);
            editor = pref.edit();

            species = pref.getString("species", "");
            nickname = pref.getString("nickname", "");
            water = pref.getInt("water", 0);
            sun = pref.getInt("sun", 0);
            split = pref.getInt("split", 0);

            plantSpecies = rootView.findViewById(R.id.plant_species);
            plantNickname = rootView.findViewById(R.id.plant_nickname);
            imageView = rootView.findViewById(R.id.addPhoto_image);

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
            packName = context.getPackageName();
            int resID = context.getResources().getIdentifier(image, "drawable", packName);
            imageView.setImageResource(resID);
            imageView.setClipToOutline(true);

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


            // Switch ?????? on off ?????? ?????? ??????
            waterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(!b) { // ?????? ??????
                        waterTextView.setTextColor(Color.parseColor("#9e9b9b"));
                        waterText.setTextColor(Color.parseColor("#9e9b9b"));
                        newWater = 0;
                        editor.putInt("water", water);
                        editor.apply(); // ??????
                    } else {
                        waterTextView.setTextColor(Color.parseColor("#000000"));
                        waterText.setTextColor(Color.parseColor("#000000"));
                        value = waterText.getText().toString();

                        if (value.equals("")) {
                            newWater = 0;
                            editor.putInt("water", newWater);
                            editor.apply(); // ??????
                        }
                        else {
                            newWater = Integer.parseInt(value);
                            editor.putInt("water", newWater);
                            editor.apply(); // ??????
                        }
                    }
                }
            });

            sunSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(!b) { // ?????? ??????
                        sunTextView.setTextColor(Color.parseColor("#9e9b9b"));
                        sunText.setTextColor(Color.parseColor("#9e9b9b"));
                        newSun = 0;
                        editor.putInt("sun", newSun);
                        editor.apply(); // ??????
                    } else {
                        sunTextView.setTextColor(Color.parseColor("#000000"));
                        sunText.setTextColor(Color.parseColor("#000000"));
                        value = sunText.getText().toString();

                        if (value.equals("")) {
                            newSun = 0;
                            editor.putInt("sun", newSun);
                            editor.apply(); // ??????
                        }
                        else {
                            newSun = Integer.parseInt(value);
                            editor.putInt("sun", newSun);
                            editor.apply(); // ??????
                        }
                    }
                }
            });

            splitSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(!b) { // ?????? ??????
                        splitTextView.setTextColor(Color.parseColor("#9e9b9b"));
                        splitText.setTextColor(Color.parseColor("#9e9b9b"));
                        newSplit = 0;
                        editor.putInt("split", newSplit);
                        editor.apply(); // ??????
                    } else {
                        splitTextView.setTextColor(Color.parseColor("#000000"));
                        splitText.setTextColor(Color.parseColor("#000000"));
                        value = splitText.getText().toString();

                        if (value.equals("")) {
                            newSplit = 0;
                            editor.putInt("split", newSplit);
                            editor.apply(); // ??????
                        }
                        else {
                            newSplit = Integer.parseInt(value);
                            editor.putInt("split", newSplit);
                            editor.apply(); // ??????
                        }
                    }
                }
            });

            // ?????? ?????? ?????? ??? edit fragment??? ?????? ?????? ?????? ??????
            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragmentEdit = new FragmentEdit();

                    Bundle bundle = new Bundle();
                    bundle.putString("title", title);
                    bundle.putString("image", image);
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

                    //editor.clear(); // ?????? ????????? ???????????? ??????. ????????? ????????????..
                    //editor.apply(); // ??????
                    listEditor.apply(); // ??????

                    ((MainActivity)getActivity()).replaceFragment(fragmentHome);

                    Toast.makeText(context, "?????? ????????? ??????????????????", Toast.LENGTH_SHORT).show();
                }
            });
            // ?????? ?????? ?????? ??? ?????? ???????????? (Toast ???????????? ?????? ????????? ?????????) ?????? ?????? ??????
        } else {
            // ?????? ???????????? ????????? ???????????? ?????? ?????? Toast ???????????? ?????? ??????????????? ?????? ?????????
        }

        return rootView;
    }
}