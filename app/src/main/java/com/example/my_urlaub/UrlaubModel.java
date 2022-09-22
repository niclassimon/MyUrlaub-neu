package com.example.my_urlaub;


public class UrlaubModel {

    public String location;
    public String description;
    public String startDate;


    public UrlaubModel(String location, String description, String startDate) {
        this.location = location;
        this.description = description;
        this.startDate = startDate;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getStartDate() {
        return startDate;
    }
}
