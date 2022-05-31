// item_todo를 사용하기 위한 클래스
package com.example.myapplication.calendar.recyclerview;

public class ItemToDo {
    private String plant;   // 식물 이름
    private String what;    // 할 일
    private String when;    // 할 날짜
    private String date;    // 하는 날짜

    public String getPlant() {
        return plant;
    }
    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getWhat() {
        return what;
    }
    public void setWhat(String what) {
        this.what = what;
    }

    public String getWhen() {
        return when;
    }
    public void setWhen(String when) {
        this.when = when;
    }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
