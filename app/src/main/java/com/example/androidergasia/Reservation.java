package com.example.androidergasia;

//κλάση κράτησης.
public class Reservation {
    private int placeId;
    private String date;
    String dateTime;
    int numberOfPeople;

    public Reservation(int placeId, String date, String dateTime, int numberOfPeople) {
        this.placeId = placeId;
        this.date = date;
        this.dateTime = dateTime;
        this.numberOfPeople = numberOfPeople;
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

}
