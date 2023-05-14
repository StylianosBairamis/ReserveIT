package com.example.androidergasia;

public class Place
{
    private int _id;
    private String name;
    private String description;
    private double rating;

    private int numberOfChairs;

    private double longitude;

    public int getNumberOfChairs() {
        return numberOfChairs;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    private double latitude;

    public String getTypeOfPlace() {
        return typeOfPlace;
    }

    private String typeOfPlace;

//    public Place(int _id, String name,String description,String typeOfPlace) {
//        this._id = _id;
//        this.name = name;
//        this.description = description;
//        this.typeOfPlace = typeOfPlace;
//    }


    public Place(String name, String description, double rating, int numberOfChairs, double longitude, double latitude, String typeOfPlace) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.numberOfChairs = numberOfChairs;
        this.longitude = longitude;
        this.latitude = latitude;
        this.typeOfPlace = typeOfPlace;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
