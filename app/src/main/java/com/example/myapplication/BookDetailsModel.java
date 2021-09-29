package com.example.myapplication;

public class BookDetailsModel {
    String id ,  movieName , No_Of_Seats , bookDate , Total;

    public BookDetailsModel(){}

    public BookDetailsModel(String id , String movieName , double No_Of_Seats , String bookDate , double Total){
        this.id=id;
        this.movieName=movieName;
        this.No_Of_Seats= String.valueOf(No_Of_Seats);
        this.bookDate=bookDate;
        this.Total= String.valueOf(Total);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getNoofseats() {
        return No_Of_Seats;
    }

    public void setNoofseats(String No_Of_Seats) {
        this.No_Of_Seats = No_Of_Seats;
    }

    public String getBookDate() {
        return bookDate;
    }

    public void setBookDate(String bookDate) {
        this.bookDate = bookDate;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String Total) {
        this.Total = Total;
    }
}
