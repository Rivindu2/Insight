package com.example.myapplication;

public class feedModel {
    String id ,Name,Feedback;
    public feedModel(){}

    public feedModel(String id,String Name,String Feedback){
        this.id=id;
        this.Name=Name;
        this.Feedback=Feedback;
    }

    public String getFeedback() {
        return Feedback;
    }

    public void setFeedback(String feedback) {
        Feedback = feedback;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
