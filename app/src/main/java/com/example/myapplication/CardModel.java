package com.example.myapplication;

public class CardModel {

    String id , Input1 , Input2 , Input3 , Input4;
    public CardModel(){}

    public CardModel(String id , String Input1 , String Input2 , String Input3 , String Input4){
        this.id = id;
        this.Input1 = Input1;
        this.Input2 = Input2;
        this.Input3 = Input3;
        this.Input4 = Input4;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInput1() {
        return Input1;
    }

    public void setInput1(String input1) {
        Input1 = input1;
    }

    public String getInput2() {
        return Input2;
    }

    public void setInput2(String input2) {
        Input2 = input2;
    }

    public String getInput3() {
        return Input3;
    }

    public void setInput3(String input3) {
        Input3 = input3;
    }

    public String getInput4() {
        return Input4;
    }

    public void setInput4(String input4) {
        Input4 = input4;
    }
}
