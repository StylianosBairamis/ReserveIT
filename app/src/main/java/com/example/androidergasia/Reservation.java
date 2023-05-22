package com.example.androidergasia;

import java.sql.Date;

public class Reservation {
    private int id;
    private int placeId;
    private String date;
    String dateTime;
    int numberOfPeople;

    public Reservation(int id, int placeId, String date, String dateTime, int numberOfPeople) {
        this.id = id;
        this.placeId = placeId;
        this.date = date;
        this.dateTime = dateTime;
        this.numberOfPeople = numberOfPeople;
    }
    public int getId(){
        return id;
    }

    public int getPlaceId(){
        return placeId;
    }

    public String getDate(){
        return date;
    }

    public String getDateTime(){
        return dateTime;
    }

    public int getNumberOfPeople(){
        return numberOfPeople;
    }

    public void setId(int id){
        this.id = id;
    }


}
