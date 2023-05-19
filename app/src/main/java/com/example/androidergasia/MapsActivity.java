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
    private static double currentLatitude = 40.633052; // Συντεταγμένες απο Ημιώροφο βιολογίας
    private static double currentLongitude = 22.957192;

    private Pair<Float ,Float> coordinates = null;

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

        String nameOfPlace = bundle.getString("name");

        coordinates = Controller.getDBhandler().getCoordinates(nameOfPlace);

        LatLng coordinatesOfPlace = new LatLng(coordinates.first, coordinates.second);

        mMap.addMarker(new MarkerOptions().position(coordinatesOfPlace).title(nameOfPlace));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinatesOfPlace));

        LatLng currentLocation = new LatLng(currentLatitude, currentLongitude);

        mMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));


        MyTask myTask = new MyTask(coordinatesOfPlace,currentLocation);
        myTask.execute();

    }

    private class MyTask extends AsyncTask<Void,Void, ArrayList<LatLng>>
    {
        LatLng placeCoordinates;
        LatLng currentCoordinates;

        public MyTask(LatLng placeCoordinates, LatLng currentCoordinates)
        {
            this.placeCoordinates = placeCoordinates;
            this.currentCoordinates = currentCoordinates;
        }
        @Override
        protected ArrayList<LatLng> doInBackground(Void... voids)
        {

            OkHttpClient client = new OkHttpClient().newBuilder().build();

            String origins = currentLatitude + "," + currentLongitude;

            String destinations = coordinates.first + "," + coordinates.second; // Έιναι του place

            try
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

            PolylineOptions polylineOptions = new PolylineOptions().addAll(points);

            mMap.addPolyline(polylineOptions);
        }
    }
}