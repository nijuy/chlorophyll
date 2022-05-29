package com.example.myapplication.calendar;

import com.example.myapplication.calendar.recyclerview.ItemToDo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddItem {

    private String plantName;   // 식물 이름
    private String todoWhat;    // 할 일
    private String todoWhen;    // 할 시간 - HH:mm:ss
    private String todoDate;    // 할 날짜 - yyyy년 MM월 dd일
    private int todoCycle;   // 주기 - n Day

    // 생성자
    public AddItem(ArrayList<ItemToDo> itemList,
                   String plantName, String todoWhat, String todoWhen, int todoCycle) throws ParseException {
        this.plantName = plantName;
        this.todoWhat = todoWhat;
        this.todoWhen = todoWhen;
        this.todoCycle = todoCycle;
        this.todoDate = Date2String(new Date());

        // itemList에 추가
        for (int i=0; i<10; i++) {  // 10회
            addItem(itemList, plantName, todoWhat, todoWhen, todoDate);
            todoDate = calCycle(todoDate, todoCycle);   // n일 후 추가
        }
    }

    // itemList에 아이템 추가
    private void addItem(ArrayList<ItemToDo> itemList,
                         String plant, String what, String when, String date) {
        ItemToDo item = new ItemToDo();

        item.setPlant(plant);
        item.setWhat(what);
        item.setWhen(when);
        item.setDate(date);

        itemList.add(item);
    }

    // n일 후 계산 - dest_date = src_date + n일
    private String calCycle(String src_date, int n) throws ParseException {
        Calendar cal = Calendar.getInstance();

        cal.setTime(String2Date(src_date));             // Date -> Calendar 형 변환
        cal.add(Calendar.DATE, n);                      // n일 후
        String dest_date = Date2String(cal.getTime());  // Calendar -> Date 형 변환

        return dest_date;
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

    // Get & Set
    public String getPlantName() { return plantName; }
    public void setPlantName(String plantName) { this.plantName = plantName; }

    public String getTodoWhat() { return todoWhat; }
    public void setTodoWhat(String todoWhat) { this.todoWhat = todoWhat; }

    public String getTodoWhen() { return todoWhen; }
    public void setTodoWhen(String todoWhen) { this.todoWhen = todoWhen; }

    public int getTodoCycle() { return todoCycle; }
    public void setTodoCycle(int todoCycle) { this.todoCycle = todoCycle; }
}
