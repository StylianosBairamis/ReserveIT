package com.example.androidergasia;

import android.database.Cursor;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Bοηθητική κλάση χρησιμοποιώ ως ιδεά τον Controller απο το MVC, επίσης χρησιμοποιώ το πρότυπo
 * Singleton ώστε να έχω μόνο ένα στιγμιότυπο της κλάσης.
 *
 * Παρέχει βοηθητικές μεθόδους για παροχή data στα δίαφορα activities.
 */
public class Controller
{
    private static Controller singleton;
    private static DBhandler DBhandler ;
    private static double latitude = 40.633052; // Συντεταγμένες απο Ημιώροφο βιολογίας
    private static double longitude =  22.957192;
    public static double getUserLatitude() {
        return latitude;
    }
    public static double getUserLongitude() {
        return longitude;
    }
    public static void setUserLatitude(double latitudeSet) {
        latitude = latitudeSet;
    }
    public static void setUserLongitude(double longitudeSet) {
        longitude = longitudeSet;
    }
    public static RecyclerAdapter getAdapter() {
        return recyclerAdapter;
    }
    private static RecyclerAdapter recyclerAdapter;

    private static boolean typeOfAdapter; // Ξεχωρίζω ποιο RecyclerAdapter εχω.
    //Αν είναι false χρησιμοποιείται στην αναζήτηση.
    //Αν είναι true χρησιμοποιείται στο RecyclerViewer στο favoritesΑctivity.

    public static boolean isTypeOfAdapter()
    {
        return typeOfAdapter;
    }

    public static void setRecyclerAdapter(RecyclerAdapter recyclerAdapterForSet,boolean typeOfAdapterForSet) {
       recyclerAdapter = recyclerAdapterForSet;
       typeOfAdapter = typeOfAdapterForSet;
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

     //Παροχή πρόσβαση στις μεθόδους της DBhandler
    public static ArrayList<Reservation> findReservations()
    {
        return DBhandler.findReservations();
    }
    public static void removeReservation(int idOfResertvation)
    {
        DBhandler.removeReservation(idOfResertvation);
    }

    public static void addReservation(Reservation reservation)
    {
        DBhandler.addReservation(reservation);
    }

}
