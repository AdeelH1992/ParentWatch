package com.example.lenovossd.parentwatch.Model;

public class PhoneModel {
    String Name;
    String Number;
    String key;

    public PhoneModel() {
    }

    public PhoneModel(String name, String number,String key) {
        Name = name;
        Number = number;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }
}
