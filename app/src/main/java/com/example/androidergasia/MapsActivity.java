package com.example.androidergasia;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Pair;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.androidergasia.databinding.ActivityMapsBinding;

public class  MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private static double currentLongitude = 40.633052; // Συντεταγμένες απο Ημιώροφο βιολογίας
    private static double currentLatitude = 22.957192;

    private static DBhandler DBhandler = null;

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

        Pair<Float, Float> coordinates = DBhandler.getCoordinates(nameOfPlace);

        LatLng coordinatesOfPlace = new LatLng(coordinates.first, coordinates.second);

        mMap.addMarker(new MarkerOptions().position(coordinatesOfPlace).title(nameOfPlace));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinatesOfPlace));

        LatLng currentLocation = new LatLng(currentLongitude, currentLatitude);

        mMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));

    }

    public static void setDBHandler(DBhandler DBhandlerForSet)
    {
        DBhandler = DBhandlerForSet;
    }
}