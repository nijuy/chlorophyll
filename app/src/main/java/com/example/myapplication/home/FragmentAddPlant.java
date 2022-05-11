package com.example.myapplication.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class FragmentAddPlant extends Fragment {
    private View view;
    private Button button;
    private ImageView imageView = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_plant, container, false);
        imageView = (ImageView)view.findViewById(R.id.addPhoto_image);
        imageView.setImageResource(R.drawable.ic_baseline_camera_alt_24);
        button = (Button)view.findViewById(R.id.addPhoto_btn_upload);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        return view;
    }
}
