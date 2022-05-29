package com.example.myapplication.calendar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Date;
import java.util.List;

public class FragmentPlan extends Fragment {
    private View view;

    private MaterialCalendarView mv;
    private TextView tv;
    private RecyclerView rv;

    private ArrayList<ItemToDo> itemList = new ArrayList<>();   // 모든 일정을 ItemList 하나에 저장
    private ArrayList<ItemToDo> showList = new ArrayList<>();   // itemList 중 보여줄 일정
    private ItemViewAdapter adapter = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plan, container,false);

        // 연결
        mv = view.findViewById(R.id.plan_cv);
        tv = view.findViewById(R.id.plan_tv);
        rv = view.findViewById(R.id.plan_rv);

        // 일정 추가 - 홈에서 일정 추가 어떻게?
        try {
            new AddItem(itemList,
                    "장미", "물주기", "17:00:00", 4);
            new AddItem(itemList,
                    "백합", "물주기", "13:00:00", 5);
        } catch (ParseException e) { e.printStackTrace(); }

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
                item.setWhen(i.getWhen());
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
}
