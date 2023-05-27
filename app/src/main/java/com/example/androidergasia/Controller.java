package com.example.androidergasia;


public class Controller
{
    private static Controller singleton;
    private static DBhandler DBhandler ;
    private static double latitude = 40.633052; // Συντεταγμένες απο Ημιώροφο βιολογίας
    private static double longitude =  22.957192;

    public static double getLatitude() {
        return latitude;
    }
    public static double getLongitude() {
        return longitude;
    }
    public static void setLatitude(double latitudeSet) {
        latitude = latitudeSet;
    }
    public static void setLongitude(double longitudeSet) {
        longitude = longitudeSet;
    }
    public static RecyclerAdapter getAdapter() {
        return recyclerAdapter;
    }
    private static RecyclerAdapter recyclerAdapter;

    public static void setRecyclerAdapter(RecyclerAdapter recyclerAdapterForSet) {
       recyclerAdapter = recyclerAdapterForSet;
    }
    public static void init()
    {
        if(singleton == null)
        {
            singleton = new Controller();
        }
    }
    public static void setDBhandler(DBhandler DBhandlerSet)
    {
        DBhandler = DBhandlerSet;
    }
    public static DBhandler getDBhandler()
    {
        return DBhandler;
    }

}
