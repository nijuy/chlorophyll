package com.example.myapplication;

public class MyPlantList {

    private String id;
    private String species;
    private String nickname;
    private String image;
    private String title;

    public MyPlantList(String id, String species, String nickname, String image, String title) {
        this.id = id;
        this.species = species;
        this.nickname = nickname;
        this.image = image;
        this.title = title;
    }

    public String getId() { return id; }
    public String getSpecies() { return species; }
    public String getNickname() { return nickname; }
    public String getImage() { return image; }
    public String getTitle() { return title; }

    public void setId(String id) { this.id = id; }
    public void setSpecies(String species) { this.species = species; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public void setImage(String image) { this.image = image; }
    public void setTitle(String title) { this.title = title; }
}