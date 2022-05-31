package com.example.myapplication.home;

import android.Manifest;
import android.annotation.SuppressLint;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.Sheet;
import jxl.read.biff.BiffException;

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
            addButton.setOnClickListener(view -> ((MainActivity)getActivity()).replaceFragment(fragmentAddPlant));
        }
        else {
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

        showLocation(container);
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

    public boolean checkedPermissions(){
        if(ContextCompat.checkSelfPermission(requireActivity(),android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
           && ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            return true;

        else
            return false;
    }

    public void showLocation(@Nullable ViewGroup container){
        locationText = rootView.findViewById(R.id.locationText);
        btnLocation = rootView.findViewById(R.id.btn_location);

        locationText.setVisibility(View.GONE);

        if(checkedPermissions()) {
            btnLocation.setVisibility(View.GONE);
            locationText.setVisibility(View.VISIBLE);

            LocationTracker lt = new LocationTracker(container.getContext());
            String address = getCurrentAddress(lt.getLatitude(), lt.getLongitude());
            locationText.setText("위도 : "+lt.getLatitude()+" / 경도 : "+lt.getLongitude()+"\n주소 : "+address);
        }

        btnLocation.setOnClickListener(view -> {
            int REQUEST_CODE = 1;
            String[] PERMISSIONS = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            ActivityCompat.requestPermissions((getActivity()), PERMISSIONS, REQUEST_CODE);
        });

    }

    public String getCurrentAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
        List<Address> addressList;

        try {
            addressList = geocoder.getFromLocation(latitude, longitude,1);

            if(addressList == null || addressList.size() == 0){
                Log.d("@@", "주소 못 찾음");
                return "위도 경도를 똑바로 넣자...........";
            }
            else {
                String Address = addressList.get(0).getAddressLine(0);
                int totalAddress = addressList.get(0).getAddressLine(0).length();
                int featureLength = addressList.get(0).getFeatureName().length() + 1; // "a b"에서 " b" 만큼 자를거니까 b 길이에 공백 길이 1 더해줌

                getCoordinates(addressList.get(0).getThoroughfare());
                return Address.substring(0, totalAddress-featureLength);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "이게 가면 안된다,,, 이건 리턴때문에 만들어둔 잉여 문자열이니까,,,,";
    }

    public void getCoordinates(String localName) {
        Log.d("@@", localName);
        try {
            InputStream is = getActivity().getApplicationContext().getResources().getAssets().open("location_name.xls");
            Workbook wb = Workbook.getWorkbook(is);

            if (wb != null) {
                Sheet sheet = wb.getSheet(0);   // 시트 불러오기
                if (sheet != null) {
                    int colTotal = sheet.getColumns();    // 전체 컬럼
                    int rowTotal = sheet.getColumn(colTotal - 1).length;

                    for (int row = 1 ; row < rowTotal ; row++) {
                        String contents = sheet.getCell(4, row).getContents();
                        if (contents.contains(localName)) {
                            String x = sheet.getCell(5, row).getContents();
                            String y = sheet.getCell(6, row).getContents();
                            makeRequestMsg(x, y);
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            Log.i("READ_EXCEL1", e.getMessage());
            e.printStackTrace();
        } catch (BiffException e) {
            Log.i("READ_EXCEL1", e.getMessage());
            e.printStackTrace();
        }
    }

    public void makeRequestMsg(String xText, String yText){
        int x = Integer.parseInt(xText);
        int y = Integer.parseInt(yText);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDate1 = new SimpleDateFormat("yyyyMMdd"); //20220531
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDate2 = new SimpleDateFormat("HH"); //시
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDate3 = new SimpleDateFormat("mm"); //분
        Date today = new Date();
        StringBuilder msg = new StringBuilder();
        String min, hour;

        String serviceKey = "hMACbDiw3Z1Pm4vTng3uNgdRCNx%2FoGkO2BfR0P4G9%2F%2Fi8KeBMivV%2Fa9VvMTRiWr2Oj1pPqPo9ZoxUAtQ6aV1uQ%3D%3D";
        String date = simpleDate1.format(today);
        int hr = Integer.parseInt(simpleDate2.format(today));
        int minute = Integer.parseInt(simpleDate3.format(today));

        if(minute <= 40){
            hour = Integer.toString(hr-1);
            min = "30";
        }
        else {
            hour = Integer.toString(hr);
            min = "00";
        }

        msg.append("https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst?serviceKey=")
                .append(serviceKey)
                .append("&pageNo=1")
                .append("&numOfRows=1000")
                .append("&dataType=JSON")
                .append("&base_date=").append(date)
                .append("&base_time=").append(hour).append(min)
                .append("&nx=").append(x).append("&ny=").append(y);

        Log.d("@@", msg.toString());

        /*
        new Thread(() -> {
            sendRequestMsg(msg);
        }).start();
        */
    }

    public void sendRequestMsg(StringBuilder msg){
        try {
            URL url = new URL(msg.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            BufferedReader rd;

            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300)
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            else
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));

            StringBuilder sb = new StringBuilder();
            while(rd.readLine() != null)
                sb.append(rd.readLine());

            rd.close();
            conn.disconnect();
            String result= sb.toString();
            Log.d("@@", result);

        } catch (Exception e){
            Log.d("@@", "sendRequestmsg에서 에러 발생!");
            e.printStackTrace();
        }
    }

}