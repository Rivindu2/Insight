package com.example.myapplication;

public class BookModel {
    String id , mid , mname , noofseats , showdate , tprice;

    public BookModel(){}

    public BookModel(String id , String mid , String mname , String noofseats , String showdate , String tprice){
        this.id=id;
        this.mid=mid;
        this.mname=mname;
        this.noofseats=noofseats;
        this.showdate=showdate;
       this.tprice=tprice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getNoofseats() {
        return noofseats;
    }

    public void setNoofseats(String noofseats) {
        this.noofseats = noofseats;
    }

    public String getShowdate() {
        return showdate;
    }

    public void setShowdate(String showdate) {
        this.showdate = showdate;
    }

    public String getTprice() {
        return tprice;
    }

    public void setTprice(String tprice) {
        this.tprice = tprice;
    }
}
