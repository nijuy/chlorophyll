package com.example.myapplication.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FragmentAddPlant extends Fragment {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    String species, nickname;

    View rootView = null;
    EditText speciesEdit, nicknameEdit;
    Button photoBtn, doneBtn, cancelBtn;
    ImageView imageView = null;

    FragmentHome fragmentHome;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_plant, container, false);
        imageView = (ImageView)rootView.findViewById(R.id.addPhoto_image);
        //imageView.setImageResource(R.drawable.ic_baseline_camera_alt_24);
        speciesEdit = rootView.findViewById(R.id.plant_species);
        nicknameEdit = rootView.findViewById(R.id.plant_nickname);
        photoBtn = (Button)rootView.findViewById(R.id.addPhoto_btn_upload);
        doneBtn = (Button)rootView.findViewById(R.id.doneBtn);
        cancelBtn = (Button)rootView.findViewById(R.id.cancelBtn);

        fragmentHome = new FragmentHome();

        Context context = getActivity();
        //context = container.getContext();

        String fileName = getTime();
        pref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        editor = pref.edit();

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*species = speciesEdit.getText().toString();
                editor.putString("species", species);

                nickname = nicknameEdit.getText().toString();
                editor.putString("nickname", nickname);

                editor.apply(); // 저장*/

                ((MainActivity)getActivity()).replaceFragment(fragmentHome);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(fragmentHome);
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
}