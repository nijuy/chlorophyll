// 할 시간 삭제
package com.example.myapplication.calendar;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MyPlantList;
import com.example.myapplication.R;
import com.example.myapplication.calendar.decorator.EventDecorator;
import com.example.myapplication.calendar.decorator.SaturdayDecorator;
import com.example.myapplication.calendar.decorator.SundayDecorator;
import com.example.myapplication.calendar.decorator.TodayDecorator;
import com.example.myapplication.calendar.recyclerview.ItemToDo;
import com.example.myapplication.calendar.recyclerview.ItemViewAdapter;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragmentPlan extends Fragment {
    private View view;
    private Context context;

    private MaterialCalendarView mv;
    private TextView tv;
    private RecyclerView rv;

    private ArrayList<ItemToDo> itemList = new ArrayList<>();   // 모든 일정을 ItemList 하나에 저장
    private ArrayList<ItemToDo> showList = new ArrayList<>();   // itemList 중 보여줄 일정
    private ItemViewAdapter adapter = null;

    private SharedPreferences pref, listPref;
    private String plantName, startDate, title;
    private Integer water, sun, split;

    private AlarmManager alarmManager;
    private NotificationManager notificationManager;
    NotificationCompat.Builder builder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plan, container,false);
        context = getActivity();

        // 연결
        mv = view.findViewById(R.id.plan_cv);
        tv = view.findViewById(R.id.plan_tv);
        rv = view.findViewById(R.id.plan_rv);

        // 알림
        notificationManager
                = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        alarmManager
                = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // SharedPreference - 파일명=등록날짜
        listPref = context.getSharedPreferences("listPref", Context.MODE_PRIVATE);
        String[] array = (listPref.getString("title", "")).split("/");

        this.itemList.clear();

        // SharedPreference 읽고 itemlist에 추가
        for (int i = 0; i < array.length; i++) {
            if (!array[i].equals("")) {
                pref = context.getSharedPreferences(array[i], Context.MODE_PRIVATE);

                plantName = pref.getString("nickname", "");
                water = pref.getInt("water", 0);    // 물주기
                sun = pref.getInt("sun", 0);        // 햇빛
                split = pref.getInt("split", 0);    // 분갈이
                // 등록 날짜: title -> yyyy년 MM월 dd일 -> startDate
                title = array[i];   // 등록날짜?
                try {
                    startDate = Title2Start(title);
                } catch (ParseException e) { e.printStackTrace(); }

                // 물주기 주기가 0 아니면 일정 itemList에 추가
                if (water != 0) {
                    try {
                        new AddItem(itemList,
                                plantName, startDate, "물주기", water);

                        setAlarm(plantName, startDate, "물주기", water);
                    } catch (ParseException e) { e.printStackTrace(); }
                }
                // 햇빛 주기가 0 아니면 일정 itemList에 추가
                if (sun != 0) {
                    try {
                        new AddItem(itemList,
                                plantName, startDate, "햇빛", sun);

                        setAlarm(plantName, startDate, "햇빛", sun);
                    } catch (ParseException e) { e.printStackTrace(); }
                }
                // 분갈이 주기가 0 아니면 일정 itemList에 추가
                if (split != 0) {
                    try {
                        new AddItem(itemList,
                                plantName, startDate, "분갈이", split);

                        setAlarm(plantName, startDate, "분갈이", split);
                    } catch (ParseException e) { e.printStackTrace(); }
                }
            }
        }

        /*
        // 일정 추가 - 테스트
        startDate = Date2String(new Date());    // 오늘
        try {
            new AddItem(itemList,
                    "장미", startDate, "물주기", 4);
            new AddItem(itemList,
                    "백합", startDate, "물주기",  5);
        } catch (ParseException e) { e.printStackTrace(); }
        */

        // RecyclerView 세팅 - showList
        adapter = new ItemViewAdapter(showList);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        // 출력 - 오늘
        Show(new Date());
        // 출력 - 날짜 선택
        mv.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Show(date.getDate());
            }
        });

        // 커스텀 캘린더 꾸미기
        Decorator();

        return view;
    }

    // 알람 설정
    private void setAlarm(String plantName, String startDate, String todoWhat, int cycle)
            throws ParseException {

        Intent receiverIntent = new Intent(context, AlarmRecevier.class);
        receiverIntent.putExtra("plantName", plantName);
        receiverIntent.putExtra("todoWhat", todoWhat);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(context, 0, receiverIntent, 0);

        // Date date = String2Date(startDate);
        // 테스트 바로 출력
        Date date = new Date();
        Long milli = date.getTime();

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, milli,
                cycle * AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    // 날짜 선택하면 TextView와 RecyclerView에 출력
    private void Show(Date date) {
        // TextView에 표시
        this.tv.setText(Date2String(date));

        // RecyclerView에 표시
        SelectToDo(date);
        this.adapter.notifyDataSetChanged(); // RecyclerView 갱신
    }
    // 날짜 선택하면 showList 갱신
    private void SelectToDo(Date date) {
        this.showList.clear();                  // showList 비우기
        String str_date = Date2String(date);    // 선택한 날짜

        for (ItemToDo i : itemList) {
            // 선택한 날짜와 같은 날짜만 추가
            if (str_date.equals(i.getDate())) {
                ItemToDo item = new ItemToDo();

                item.setPlant(i.getPlant());
                item.setWhat(i.getWhat());
                item.setDate(i.getDate());

                this.showList.add(item);
            }
        }
    }

    // 커스텀 캘린더 꾸미기
    private void Decorator() {
        // 꾸미기 - 선택 색 변경
        this.mv.setSelectedDate(CalendarDay.today());

        // 꾸미기 - 오늘과 주말 색 변경
        this.mv.addDecorators(
                new SundayDecorator(),      // 일요일
                new SaturdayDecorator(),    // 토요일
                new TodayDecorator()        // 오늘
        );

        // 꾸미기 - 날짜 밑에 점 표시
        try {
            this.mv.addDecorator(new EventDecorator(Color.RED, DotEvent()));
        } catch (ParseException e) { e.printStackTrace(); }

        // 꾸미기 - 화살표 사이 연, 월 폰트 스타일
        this.mv.setHeaderTextAppearance(R.style.CalendarWidgetHeader);

        // 꾸미기- 월, 요일을 한글로
        this.mv.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getTextArray(R.array.custom_months)));
        this.mv.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays)));
    }
    // 날짜 밑에 점 표시 - itemToDo -> calendarDays 생성
    private List<CalendarDay> DotEvent() throws ParseException {
        Date date = new Date();
        CalendarDay calendarDay = CalendarDay.from(date);
        ArrayList<CalendarDay> calendarDays = new ArrayList<>();

        for (ItemToDo i : itemList) {
            date = String2Date(i.getDate());            // String -> Date
            calendarDays.add(calendarDay.from(date));   // CalendarDay에 추가
        }

        return calendarDays;
    }

    // Date -> String 형 변환
    private String Date2String(Date date) {
        SimpleDateFormat d2s = new SimpleDateFormat("yyyy년 MM월 dd일");

        return d2s.format(date);
    }
    // String -> Date 형 변환
    private Date String2Date(String date) throws ParseException {
        SimpleDateFormat s2d = new SimpleDateFormat("yyyy년 MM월 dd일");

        return s2d.parse(date);
    }

    // String "yyyyMMdd" -> String "yyyy년 MM월 dd일"
    private String Title2Start(String title) throws ParseException {
        Date date = new Date();
        String str = title.substring(0,8);        // yyyyMMdd

        // yyyyMMdd -> Date
        SimpleDateFormat s2d = new SimpleDateFormat("yyyyMMdd");
        date = s2d.parse(str);

        // Date -> yyyy년 MM월 dd일
        return Date2String(date);
    }
}
