package com.example.androidergasia;

//Κλάση για τα μαγαζίά
public class Place
{
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

    public Place(String name, String description, double rating, int numberOfChairs, double latitude, double longitude, String typeOfPlace) {
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

    public String getDescription() {
        return description;
    }

}
