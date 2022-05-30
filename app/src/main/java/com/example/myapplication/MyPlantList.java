package com.example.myapplication;

public class MyPlantList {

    private String test1;
    private String test2;
    private String title;

    public MyPlantList(String test1, String test2, String title) {
        this.test1 = test1;
        this.test2 = test2;
        this.title = title;
    }

    public String getTest1() { return test1; }
    public String getTest2() { return test2; }
    public String getTitle() { return title; }

    public void setTest1(String test1) { this.test1 = test1; }
    public void setTest2(String test2) { this.test2 = test2; }
    public void setTitle(String title) { this.title = title; }
}