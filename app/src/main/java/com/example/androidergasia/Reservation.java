package com.example.androidergasia;

public class Reservation {
    private int placeId;
    private String date;
    private String dateTime;
    private int numberOfPeople;
    private String nameOfPlace;

    private int reservationID;

    public String getNameOfPlace() {
        return nameOfPlace;
    }

    public int getReservationID() {
        return reservationID;
    }

    public Reservation(int placeId, String date, String dateTime, int numberOfPeople) {
        this.placeId = placeId;
        this.date = date;
        this.dateTime = dateTime;
        this.numberOfPeople = numberOfPeople;
    }

    /**
     *  Αυτός ο constructor χρημιμοποιείται απο την κλάση reservationsRecyclerAdapter
     */
    public Reservation(String date, String time, String nameOfPlace, int numberOfPeople,int reservationID)
    {
        this.date = date;
        this.dateTime = time;
        this.nameOfPlace = nameOfPlace;
        this.numberOfPeople = numberOfPeople;
        this.reservationID = reservationID;
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
