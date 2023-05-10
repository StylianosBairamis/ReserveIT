package com.example.androidergasia;

import java.sql.Date;

public class Reservation {
    private int id;
    private String placeName;
    private Date date;
    String dateTime;

    public Reservation(int id, String placeName, Date date, String dateTime) {
        this.id = id;
        this.placeName = placeName;
        this.date = date;
        this.dateTime = dateTime;
    }

    public int getId(){
        return id;
    }

    public String getPlaceName(){
        return placeName;
    }

    public Date getDate(){
        return date;
    }

    public String getDateTime(){
        return dateTime;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setPlaceName(String placeName){
        this.placeName = placeName;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public void setDateTime(String dateTime){
        this.dateTime = dateTime;
    }

}
