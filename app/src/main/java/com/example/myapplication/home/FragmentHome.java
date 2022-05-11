package com.example.myapplication.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class FragmentHome extends Fragment {
    private View rootView;
    private ImageButton addButton;
    FragmentAddPlant fragmentAddPlant = new FragmentAddPlant();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container,false);
        addButton = (ImageButton) rootView.findViewById(R.id.add_plant_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(fragmentAddPlant);
            }
        });
        return rootView;
    }
}
