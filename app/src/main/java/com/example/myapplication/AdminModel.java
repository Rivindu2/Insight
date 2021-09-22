package com.example.myapplication;

public class AdminModel {
    String id,name,cat,duration;
    public  AdminModel(){}

    public AdminModel(String id,String name,String cat,String duration){
        this.id=id;
        this.cat=cat;
        this.name=name;
        this.duration=duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
