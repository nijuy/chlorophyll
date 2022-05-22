package com.example.myapplication;

public class Plant {
    private String id;
    private String name;
    private String use;
    private String size;
    private String flowering;
    private String difficulty;
    private String sunshine;

    public String getId() { return id; }
    public void setId(String id){
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public  String getUse() { return use; }
    public void setUse(String use) { this.use = use; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getFlowering() { return flowering; }
    public void setFlowering(String flowering) { this.flowering = flowering; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public String getSunshine() { return sunshine; }
    public void setSunshine(String sunshine) { this.sunshine = sunshine; }
}
