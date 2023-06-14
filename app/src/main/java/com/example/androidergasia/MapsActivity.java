package com.example.androidergasia;

import androidx.fragment.app.FragmentActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.androidergasia.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.PolylineOptions;

import com.google.maps.internal.PolylineEncoding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class  MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private final double currentLatitude = Controller.getUserLatitude(); //Είναι συντεταγμένες του user.
    private final double currentLongitude = Controller.getUserLongitude();
    private Pair<Float ,Float> coordinates = null; // Είναι συντεταγμένες του place.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        Bundle bundle = getIntent().getExtras();

        String nameOfPlace = bundle.getString("name"); //Παίρνω το όνομα του Place απο το Bundle

        coordinates = Controller.getDBhandler().getCoordinates(nameOfPlace);

        LatLng coordinatesOfPlace = new LatLng(coordinates.first, coordinates.second);

        //Βάζω point στον χάρτη με τα coordinates του place, βάζω και title στο point
        mMap.addMarker(new MarkerOptions().position(coordinatesOfPlace).title(nameOfPlace));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinatesOfPlace));

        LatLng currentLocation = new LatLng(currentLatitude, currentLongitude);//Location του user

        mMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        //zoom την κάμερα στην θέση του user.

        MyTask myTask = new MyTask();
        myTask.execute();
    }

    /**
     * Δημιουργώ ενα asyncTask για να στείλω ενα GET request στο directions API,
     * ουσιαστικά παίρνω το μονοπάτι απο την τοποθεσία του χρήστη προς την τοποθεσία του place
     * και σχεδιάζω ενα polyline στο map.
     */

    private class MyTask extends AsyncTask<Void,Void, ArrayList<LatLng>>
    {
        @Override
        protected ArrayList<LatLng> doInBackground(Void... voids)
        {

            OkHttpClient client = new OkHttpClient().newBuilder().build();

            String origins = currentLatitude + "," + currentLongitude; //coordinates του user

            String destinations = coordinates.first + "," + coordinates.second; // coordinates του place

            try//Συμφώνα με τα docs της google
            {
                Request request = new Request.Builder()
                        .url("https://maps.googleapis.com/maps/api/directions/json?origin=" + origins + "&destination=" + destinations + "+&key=AIzaSyDCh6JAZ2QP1_SAfKB_ch-ag5flQC5eZT4")
                        .method("GET",null)
                        .build();
                Response response = client.newCall(request).execute();

                JSONObject jsonObject = new JSONObject(response.body().string()); // https://developers.google.com/maps/documentation/directions/get-directions#DirectionsResponses

                JSONArray routes = jsonObject.getJSONArray("routes");

                JSONObject elementOfArray = routes.getJSONObject(0);

                JSONObject overview_polyline = elementOfArray.getJSONObject("overview_polyline");

                String points = overview_polyline.getString("points"); // τιμή του overview_polyline

                List<com.google.maps.model.LatLng> decodedCoordinates = PolylineEncoding.decode(points);

                ArrayList<LatLng> typeCasted = new ArrayList<>();

                for(int i = 0 ;i < decodedCoordinates.size();i++)
                {
                    LatLng coordinatesForAdd = new LatLng(decodedCoordinates.get(i).lat,decodedCoordinates.get(i).lng);
                    typeCasted.add(coordinatesForAdd);
                }

                return typeCasted;

            }
            catch (IOException | JSONException e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<LatLng> points)
        {
            super.onPostExecute(points);

            PolylineOptions polylineOptions = new PolylineOptions().addAll(points); //Δημιουργώ το polyline

            mMap.addPolyline(polylineOptions); // Το προσθέτω στο map.
        }
    }
}