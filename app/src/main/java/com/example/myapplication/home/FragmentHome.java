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
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import jxl.Workbook;
import jxl.Sheet;
import jxl.read.biff.BiffException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FragmentHome extends Fragment implements OnItemClick {

    SharedPreferences pref, listPref;

    String id, species, nickname, image, title;

    ArrayList<MyPlantList> myPlantList;
    RecyclerView recyclerView;
    ImageButton addButton;
    MyPlantListAdapter adapter;
    View rootView = null;
    Context context;
    FragmentSelect fragmentSelect = new FragmentSelect();
    TextView locationText, weatherText;
    Button btnLocation;

    int pastHour = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        listPref = context.getSharedPreferences("listPref", Context.MODE_PRIVATE);
        String[] array = (listPref.getString("title", "")).split("/");

        if (array.length == 1) {
            rootView = inflater.inflate(R.layout.fragment_home2, container,false);

            addButton = rootView.findViewById(R.id.add_plant_button);
            addButton.setOnClickListener(view -> ((MainActivity)getActivity()).replaceFragment(fragmentSelect));
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
                    ((MainActivity)getActivity()).replaceFragment(fragmentSelect);
                }
            });

            for (int i = 0; i < array.length; i++) {
                if (!array[i].equals("")) {
                    pref = getActivity().getSharedPreferences(array[i], Context.MODE_PRIVATE);

                    id = pref.getString("id", "0");
                    species = pref.getString("species", "");
                    nickname = pref.getString("nickname", "");
                    image = pref.getString("image", "");
                    title = array[i];
                    myPlantList.add(new MyPlantList(id, species, nickname, image, title));
                }
            }

            adapter = new MyPlantListAdapter(context, myPlantList, this);
            recyclerView.setAdapter(adapter);
        }

        showLocation(container);
        return rootView;
    }

    @Override
    public void onClick(String value, String title, String image){
        if (value.equals("diary")) {
            FragmentFeature fragmentFeature = new FragmentFeature();

            Bundle bundle = new Bundle();
            bundle.putString("id", title);
            fragmentFeature.setArguments(bundle);

            ((MainActivity)getActivity()).replaceFragment(fragmentFeature);
        } else if (value.equals("detail")) {
            FragmentDetail fragmentDetail = new FragmentDetail();

            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            bundle.putString("image", image);
            fragmentDetail.setArguments(bundle);

            ((MainActivity)getActivity()).replaceFragment(fragmentDetail);
        }
    }

    public boolean checkedPermissions(){
        return ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public void showLocation(@Nullable ViewGroup container){
        locationText = rootView.findViewById(R.id.locationText);
        weatherText = rootView.findViewById(R.id.weatherText);
        btnLocation = rootView.findViewById(R.id.btn_location);

        locationText.setVisibility(View.GONE);
        weatherText.setVisibility(View.GONE);

        if(checkedPermissions()) {
            btnLocation.setVisibility(View.GONE);
            locationText.setVisibility(View.VISIBLE);
            weatherText.setVisibility(View.VISIBLE);

            LocationTracker lt = new LocationTracker(container.getContext());
            String address = getCurrentAddress(lt.getLatitude(), lt.getLongitude());
            locationText.setText(address + "의 날씨");
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
                return Address.substring(5, totalAddress-featureLength);
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
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDate1 = new SimpleDateFormat("yyyyMMdd"); //날짜 포맷
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDate2 = new SimpleDateFormat("HH"); //시간 포맷
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDate3 = new SimpleDateFormat("mm"); //분
        String serviceKey = "hMACbDiw3Z1Pm4vTng3uNgdRCNx/oGkO2BfR0P4G9//i8KeBMivV/a9VvMTRiWr2Oj1pPqPo9ZoxUAtQ6aV1uQ=="; //""hMACbDiw3Z1Pm4vTng3uNgdRCNx%2FoGkO2BfR0P4G9%2F%2Fi8KeBMivV%2Fa9VvMTRiWr2Oj1pPqPo9ZoxUAtQ6aV1uQ%3D%3D";
        StringBuilder msg = new StringBuilder();
        Date today = new Date();
        StringBuilder hr = new StringBuilder();

        int x = Integer.parseInt(xText);
        int y = Integer.parseInt(yText);

        String date = simpleDate1.format(today);
        int hour = Integer.parseInt(simpleDate2.format(today));
        int minute = Integer.parseInt(simpleDate3.format(today));

        if(hour < 10)
            hr.append("0");

        if(minute <= 40)
            hr.append(Integer.toString(hour-1));
        else
            hr.append(Integer.toString(hour));

        boolean sendFlag = pastHour == -1 | pastHour != hour;
        pastHour = hour; // 요청 보내는 시간 갱신

        if(sendFlag){
            msg.append("https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst?serviceKey=")
                    .append(serviceKey)
                    .append("&pageNo=1")
                    .append("&numOfRows=1000")
                    .append("&dataType=JSON")
                    .append("&base_date=").append(date)
                    .append("&base_time=").append(hr.toString()).append("00")
                    .append("&nx=").append(x).append("&ny=").append(y);

            Log.d("@@", msg.toString()); // 요청 메시지 확인용
            new Thread(() -> { sendRequestMsg(msg); }).start();
        }
    }

    public void sendRequestMsg(StringBuilder msg){
        try {
            ignoreSsl(); //ssl 인증서 무시
            URL url = new URL(msg.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            int code = conn.getResponseCode();
            if(code >= 200 && code <= 300){
                try{
                    StringBuffer sb = new StringBuffer();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    sb.append(br.readLine());
                    Log.d("@@", sb.toString());
                    getCurrentWeather(sb.toString());
                    br.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            conn.disconnect(); //연결 해제
        } catch (Exception e){
            Log.d("@@", e.getMessage());
        }
    }

    public void getCurrentWeather(String data){
        JSONParser parser = new JSONParser();
        JSONObject obj;

        try {
            //필요한 부분 : response의 body의 items의 item 배열 - 순차적으로 get 해옴
            obj = (JSONObject) parser.parse(data);
            JSONObject response = (JSONObject) obj.get("response");
            JSONObject body = (JSONObject) response.get("body");
            JSONObject items = (JSONObject) body.get("items");
            JSONArray itemArray = (JSONArray) items.get("item");

            StringBuilder weather = new StringBuilder();
            for(int i = 0 ; i < itemArray.size() ; i++) {
                JSONObject item = (JSONObject) itemArray.get(i);
                String value = (String) item.get("obsrValue").toString();

                switch (i){
                    case 1:
                        weather.append("습도 " + value + "% / ");
                        break;

                    case 2:
                        weather.append("강수량 " + value + "mm / ");
                        break;

                    case 3:
                        weather.append("기온 " + value + "℃ / ");
                        break;

                    case 7:
                        weather.append("풍속 " + value + "m/s");
                        break;
                }
            }
            weatherText.setText(weather.toString());
        } catch (ParseException e){
            e.printStackTrace();
        }
    }

    public static void ignoreSsl() throws Exception{
        HostnameVerifier hv = (urlHostName, session) -> true;
        trustAllHttpsCertificates();
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

    public static void trustAllHttpsCertificates() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[1];
        TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }
}

class miTM implements TrustManager,X509TrustManager {
    public X509Certificate[] getAcceptedIssuers() { return null; }
    public boolean isServerTrusted(X509Certificate[] certs) { return true; }
    public boolean isClientTrusted(X509Certificate[] certs) { return true; }
    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
}