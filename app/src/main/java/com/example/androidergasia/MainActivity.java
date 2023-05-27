package com.example.androidergasia;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.navigation.NavigationView;


import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private Button button;
    private String textOfChip;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        button = findViewById(R.id.confirmSearch);

        button.setEnabled(false);

        drawerLayout = findViewById(R.id.drawerLayout);

        Toolbar toolbar = findViewById(R.id.mytoolbar);

        ChipGroup chipGroup = findViewById(R.id.chipGroup);

        toolbar = findViewById(R.id.mytoolbar);

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.mipmap.burger_menu);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (checkLocationPermissions())
        {
            requestLocation();
        }
        else
        {
            requestLocationPermissions();
        }

        Controller.init();

        DBhandler db = new DBhandler(this, null, null, 2);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && false == Environment.isExternalStorageManager()) {
//            Uri uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID);
//            startActivity(new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri));
//        }
        chipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds)
            {
                if(checkedIds.size() != 0)
                {
                    Chip chipSelected = group.findViewById(checkedIds.get(0));

                    if(!button.isEnabled())
                    {
                        button.setEnabled(true);
                    }

                    textOfChip = chipSelected.getText().toString();

                    switch (textOfChip) {
                        case "Μπάρ":
                            textOfChip = "Bar";
                            break;
                        case "Εστιατόριο":
                            textOfChip = "Restaurant";
                            break;
                        case "Καφέ":
                            textOfChip = "Cafe";
                            break;
                    }
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(view.getContext(), RecyclerViewActivity.class);
                intent.putExtra("search", textOfChip);
                startActivity(intent);
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.navView);

        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(this);

        //button = findViewById(R.id.button);
        // button.setOnClickListener(this::addPlace);

    }


//    public void addPlace(View view)
//    {
//        DBhandler db = new DBhandler(this,null,null,1);
//
//        //Restaurant Places
//
//        Place place = new Place("The Hungry Wolf","A cozy cafe in the heart of downtown, known for its delicious espresso drinks and freshly baked pastries",0.0,100,40.632199, 22.942700,"Restaurant");
//        db.addPlace(place);
//
//        place = new Place("La Cucina Bella","A charming cafe located in a picturesque garden setting, offering a variety of organic teas and light vegetarian fare",0.0,100,40.619026, 22.953438,"Restaurant");
//        db.addPlace(place);
//
//        place = new Place("The Spice Route","A culinary journey through global flavors with a fusion of spices. ",0.0,100,40.624363, 22.950287,"Restaurant");
//        db.addPlace(place);
//
//        place = new Place("The Sizzling Pan","Experience sizzling platters and grilled specialties in a vibrant atmosphere",0.0,100,40.615873, 22.957721,"Restaurant");
//        db.addPlace(place);
//
//        place = new Place("The Fork & Knife","An elegant restaurant offering gourmet creations and a refined dining experience",0.0,100,40.604812, 22.977449,"Restaurant");
//        db.addPlace(place);
//
//        //Cafe places
//
//        place = new Place("Bean Buzz","Trendy coffee spot with a wide variety of coffee blends. Cozy and modern ambiance",0.0, 100,40.6401,22.9444,"Cafe");
//        db.addPlace(place);
//
//        place = new Place("Cuppa Express","Charming coffee shop perfect for those on the go. Quick and convenient coffee experience",0.0,100,40.6394,22.9446,"Cafe");
//        db.addPlace(place);
//
//        place = new Place("Brew Grounds"," Popular coffee destination known for artisanal brewing methods. Cozy atmosphere.",0.0,100,40.6358,22.9361,"Cafe");
//        db.addPlace(place);
//
//        place = new Place("Perk Stop","Vibrant coffee shop with a wide range of options. Relaxed setting, ideal for catching up",0.0,100,40.6261,22.9492,"Cafe");
//        db.addPlace(place);
//
//        place = new Place("Espresso Lane","Charming café specializing in high-quality espresso-based beverages. Cozy seating.",0.0,100,40.6286,22.9497,"Cafe");
//        db.addPlace(place);
//
//        //Bar places
//
//        place = new Place("Brews & Bites","A trendy bar offering a wide variety of craft beers and delicious bites.",3.0, 100, 40.6403,22.9444,"Bar");
//        db.addPlace(place);
//
//        place = new Place("The Jazz Lounge","An intimate bar known for its live jazz performances and handcrafted cocktails.",3.0,100,40.6328,22.9446,"Bar");
//        db.addPlace(place);
//
//        place = new Place("Mediterranean Nights"," A rooftop bar with panoramic views, serving Mediterranean-inspired cocktails and tapas",5.0,100,40.6358,22.9417,"Bar");
//        db.addPlace(place);
//
//        place = new Place("The Whisky Den","A cozy bar specializing in an extensive selection of whiskies and offering a laid-back ambiance.",2.5,100,40.6261,22.9492,"Bar");
//        db.addPlace(place);
//
//        place = new Place("The Vineyard Terrace","A wine bar featuring a wide range of local and international wines, accompanied by a charming outdoor terrace.",4.0,100,40.6289,22.9467,"Bar");
//        db.addPlace(place);
//
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.favorite) {
            Intent intent = new Intent(this, FavoritesActivity.class);
            startActivity(intent);
        }

        if (id == R.id.reservation){
            Intent intent = new Intent(MainActivity.this, ReservationsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
//
//        if(id == R.id.action_settings){
////            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
//            startActivity(intent);
//            return true;
//        }

//        if(id == R.id.reservation){
//            Intent intent = new Intent(MainActivity.this, ReservationsActivity.class);
//        }

        return super.onOptionsItemSelected(item);
    }
    private boolean checkLocationPermissions()
    {
        int permissionCoarse = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int permissionFine = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionCoarse == PackageManager.PERMISSION_GRANTED && permissionFine == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermissions()
    {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
    }

    private void requestLocation() throws SecurityException
    {
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>()
        {
            @Override
            public void onSuccess(Location location) {
                if (location != null)
                {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    Controller.setLatitude(latitude);
                    Controller.setLongitude(longitude);

                } else {
                    Toast.makeText(MainActivity.this, "Location not available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocation();
            }
            else
            {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
