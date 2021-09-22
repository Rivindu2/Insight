package com.example.myapplication;

public class BookingModel {

    String id , Mid , Mname , Noofseats , Showdate , Tprice;
    public BookingModel(){}

    public BookingModel(String id , String Mid ,String Mname , String Noofseats , String Showdate , String Tprice){
        this.id = id;
        this.Mid = Mid;
        this.Mname = Mname;
        this.Noofseats = Noofseats;
        this.Showdate = Showdate;
        this.Tprice = Tprice;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMid() {
        return Mid;
    }

    public void setMid(String mid) {
        Mid = mid;
    }

    public String getMname() {
        return Mname;
    }

    public void setMname(String mname) {
        Mname = mname;
    }

    public String getNoofseats() {
        return Noofseats;
    }

    public void setNoofseats(String noofseats) {
        Noofseats = noofseats;
    }

    public String getShowdate() {
        return Showdate;
    }

    public void setShowdate(String showdate) {
        Showdate = showdate;
    }

    public String getTprice() {
        return Tprice;
    }

    public void setTprice(String tprice) {
        Tprice = tprice;
    }
}
