package com.example.myapplication.calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class FragmentPlan extends Fragment {
    private View view;
    
    private MaterialCalendarView mv;
    private TextView tv;
    private RecyclerView rv;

    ItemViewAdapter adapter = null;
    ArrayList<ItemToDo> itemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plan, container,false);

        // 연결
        mv = view.findViewById(R.id.plan_cv);
        tv = view.findViewById(R.id.plan_tv);
        rv = view.findViewById(R.id.plan_rv);

        // 리사이클러뷰
        itemList = new ArrayList<>();
        adapter = new ItemViewAdapter(itemList);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        addItem("장미", "물주기", "17:00:00");
        addItem("백합", "물주기", "17:00:10");
        addItem("국화", "물주기", "17:00:20");

        adapter.notifyDataSetChanged();

        // 꾸미기 - 선택 색 변경
        mv.setSelectedDate(CalendarDay.today());
        // 꾸미기 - 주말 색 변경
        mv.addDecorators(
                new SundayDecorator(),      // 일요일
                new SaturdayDecorator(),    // 토요일
                new TodayDecorator(),       // 오늘

                // 일정 있는 날짜에 점 표시
                // 2번째 인자에 표시할 날짜
                new EventDecorator(Color.RED, Collections.singleton(CalendarDay.today()))
        );
        // 꾸미기 - 화살표 사이 연, 월 폰트 스타일
        mv.setHeaderTextAppearance(R.style.CalendarWidgetHeader);
        // 꾸미기- 월, 요일을 한글로
        mv.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getTextArray(R.array.custom_months)));
        mv.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays)));

        // 캘린더를 열면

        // 오늘 날짜 TextView에 표시
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일");
        String strDate = format.format(date);
        tv.setText(strDate);

        // 날짜를 클릭하면
        mv.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                // 선택한 날짜 TextView에 표시
                Date today = date.getDate();            // CalendarDay -> Date
                String strToday = format.format(today); // Date -> String
                tv.setText(strToday);

                // 리사이클러 뷰 변경
            }
        });

        // 달력이 변하면
        mv.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

            }
        });

        return view;
    }

    // 리사이클러뷰 아이템 추가
    private void addItem(String plant, String what, String when) {
        ItemToDo item = new ItemToDo();

        item.setPlant(plant);
        item.setWhat(what);
        item.setWhen(when);

        itemList.add(item);
    }
}
