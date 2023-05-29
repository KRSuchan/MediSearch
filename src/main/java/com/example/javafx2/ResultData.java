package com.example.javafx2;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class ResultData {

    private StringProperty name;
    private StringProperty address;
    private StringProperty phoneNum;

    public ResultData(String name, String address, String phoneNum) {

        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.phoneNum = new SimpleStringProperty(phoneNum);
    }



    public StringProperty getName() {
        return name;
    }



    public StringProperty getAddress() {
        return address;
    }


    public StringProperty getPhoneNum() {
        return phoneNum;
    }
}
