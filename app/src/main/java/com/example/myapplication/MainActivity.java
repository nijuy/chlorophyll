package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import androidx.annotation.NonNull;

import com.example.myapplication.calendar.FragmentPlan;
import com.example.myapplication.home.FragmentAddPlant;
import com.example.myapplication.home.FragmentHome;
import com.example.myapplication.search.FragmentSearch;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private FragmentSearch search;
    private FragmentHome home;
    private FragmentPlan plan;
    private FragmentAddPlant addPlant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
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
        search = new FragmentSearch();
        home = new FragmentHome();
        plan = new FragmentPlan();
        setFrag(0);
    }

    private void setFrag(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch(n){
            case 0:
                ft.replace(R.id.main_frame, home);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, search);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, plan);
                ft.commit();
                break;
        }
    }
    public void replaceFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.main_frame, fragment).commit();
    }
}