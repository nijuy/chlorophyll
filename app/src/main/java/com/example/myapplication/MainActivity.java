package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.example.myapplication.calendar.FragmentPlan;
import com.example.myapplication.home.FragmentAddPlant;
import com.example.myapplication.home.FragmentHome;
import com.example.myapplication.search.FragmentSearch;
import com.example.myapplication.search.FragmentSearchPlant;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private FragmentTransaction ft;
    private FragmentSearch fragmentSearch;
    private FragmentHome fragmentHome;
    private FragmentPlan fragmentPlan;
    private FragmentAddPlant fragmentAddPlant=new FragmentAddPlant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String name = getIntent().getStringExtra("name");

        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        setFrag(0);
                        break;
                    case R.id.action_search:
                        setFrag(1);
                        break;
                    case R.id.action_plan:
                        setFrag(2);
                        break;
                }
                return true;
            }
        });
        fragmentSearch = new FragmentSearch();
        fragmentHome = new FragmentHome();
        fragmentPlan = new FragmentPlan();
        setFrag(0);

        if(name != null){
            fragmentAddPlant.setSpecies(name);
            replaceFragment(fragmentAddPlant);
        }
    }

    private void setFrag(int n) {
        fragmentManager = getSupportFragmentManager();
        ft = fragmentManager.beginTransaction();
        switch (n) {
            case 0:
                ft.replace(R.id.main_frame, fragmentHome);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, fragmentSearch);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, fragmentPlan);
                ft.commit();
                break;
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.main_frame, fragment).commit();
    }
}