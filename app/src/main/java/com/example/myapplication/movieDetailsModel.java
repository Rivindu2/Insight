package com.example.myapplication;

public class movieDetailsModel {


    String id, duration,  cat, name;


    public movieDetailsModel(String id,String duration, String cat,String name){

        this.id=id;
        this.duration=duration;
        this.cat=cat;
        this.name=name;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }





}
