package com.example.myapplication.home;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.MyPlantList;
import com.example.myapplication.MyPlantListAdapter;
import com.example.myapplication.OnItemClick;
import com.example.myapplication.R;
import com.example.myapplication.calendar.FragmentPlan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FragmentHome extends Fragment implements OnItemClick {

    SharedPreferences pref, listPref;

    String species, nickname, title;

    ArrayList<MyPlantList> myPlantList;
    RecyclerView recyclerView;
    ImageButton addButton;
    MyPlantListAdapter adapter;
    View rootView = null;
    Context context;
    FragmentAddPlant fragmentAddPlant = new FragmentAddPlant();
    FragmentDiary fragmentDiary = new FragmentDiary();
    TextView locationText;
    Button btnLocation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        listPref = context.getSharedPreferences("listPref", Context.MODE_PRIVATE);
        String[] array = (listPref.getString("title", "")).split("/");

        if (array.length == 1) {
            rootView = inflater.inflate(R.layout.fragment_home2, container,false);

            addButton = rootView.findViewById(R.id.add_plant_button);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)getActivity()).replaceFragment(fragmentAddPlant);
                }
            });

            locationText = rootView.findViewById(R.id.locationText);
            btnLocation = rootView.findViewById(R.id.btn_location);

            if(ContextCompat.checkSelfPermission(requireActivity(),android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                if(ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    btnLocation.setVisibility(rootView.GONE);

                    LocationTracker lt = new LocationTracker(container.getContext());
                    String address = getCurrentAddress(lt.getLatitude(), lt.getLongitude());
                    locationText.setText("위도 : "+lt.getLatitude()+" / 경도 : "+lt.getLongitude()+"\n주소 : "+address);
                }

            btnLocation.setOnClickListener(view -> {
                int REQUEST_CODE = 1;
                String[] PERMISSIONS = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                ActivityCompat.requestPermissions((getActivity()), PERMISSIONS, REQUEST_CODE);
            });

        } else {
            rootView = inflater.inflate(R.layout.fragment_home, container,false);
            recyclerView = rootView.findViewById(R.id.my_plant_list);
            myPlantList = new ArrayList<MyPlantList>();

            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            addButton = rootView.findViewById(R.id.add_plant_button);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)getActivity()).replaceFragment(fragmentAddPlant);
                }
            });

            locationText = rootView.findViewById(R.id.locationText);
            btnLocation = rootView.findViewById(R.id.btn_location);

            if(ContextCompat.checkSelfPermission(requireActivity(),android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                if(ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    btnLocation.setVisibility(rootView.GONE);

                    LocationTracker lt = new LocationTracker(container.getContext());
                    String address = getCurrentAddress(lt.getLatitude(), lt.getLongitude());
                    locationText.setText("위도 : "+lt.getLatitude()+" / 경도 : "+lt.getLongitude()+"\n주소 : "+address);
                }

            btnLocation.setOnClickListener(view -> {
                int REQUEST_CODE = 1;
                String[] PERMISSIONS = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                ActivityCompat.requestPermissions((getActivity()), PERMISSIONS, REQUEST_CODE);
            });

            for (int i = 0; i < array.length; i++) {
                if (!array[i].equals("")) {
                    pref = getActivity().getSharedPreferences(array[i], Context.MODE_PRIVATE);

                    species = pref.getString("species", "");
                    nickname = pref.getString("nickname", "");
                    title = array[i];
                    myPlantList.add(new MyPlantList(species, nickname, title));
                }
            }

            adapter = new MyPlantListAdapter(context, myPlantList, this);
            recyclerView.setAdapter(adapter);
        }

        return rootView;
    }

    @Override
    public void onClick(String value, String title){
        if (value.equals("diary")) {
            FragmentDiary fragmentDiary = new FragmentDiary();

            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            fragmentDiary.setArguments(bundle);

            ((MainActivity)getActivity()).replaceFragment(fragmentDiary);
        } else if (value.equals("detail")) {
            FragmentDetail fragmentDetail = new FragmentDetail();

            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            fragmentDetail.setArguments(bundle);

            ((MainActivity)getActivity()).replaceFragment(fragmentDetail);
        }
    }

    public String getCurrentAddress(double latitude, double longtitude) {
        Geocoder geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
        List<Address> addressList;

        try {
            addressList = geocoder.getFromLocation(latitude, longtitude,1);

            if(addressList == null || addressList.size() == 0){
                Log.d("@@", "주소 못 찾음");
            }

            String Address = addressList.get(0).getAddressLine(0);
            int totalAddress = addressList.get(0).getAddressLine(0).length();
            int featureLength = addressList.get(0).getFeatureName().length() + 1; // "a b"에서 " b" 만큼 자를거니까 공백 길이 1 더해줌

            return Address.substring(0, totalAddress-featureLength);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "이게 가면 안된다,,, 이건 그냥 리턴때문에 만들어둔 잉여 문자열이니까,,,,";
    }

    public void getWeather(){

    }

}