package com.example.androidergasia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity
{
    private DrawerLayout drawerLayout;
    private Spinner spinner;
    private Button button;

//    private String DB_PATH = "/data/data/com.example.androidergasia/databases/";
//
//    private String DB_NAME = "myAPP.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // importDatabaseFromAssets();

        drawerLayout = findViewById(R.id.drawerLayout);

        Toolbar toolbar = findViewById(R.id.mytoolbar);

        toolbar.setNavigationIcon(R.mipmap.burger_menu);

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.navView);

        navigationView.setItemIconTintList(null);

        spinner = findViewById(R.id.spinner);

        ArrayList<String> types = new ArrayList(Arrays.asList("Select","Restaurant", "Bar", "Cafe"));

        List<String> typeOfPlace = new ArrayList<>();
        typeOfPlace.addAll(types);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, typeOfPlace);// Δες το context
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String itemSelected = parent.getItemAtPosition(position).toString();
                if(!itemSelected.equals(""))
                {
                    Intent intent = new Intent(view.getContext(), RecyclerViewActivity.class);
                    intent.putExtra("search",itemSelected);
                    startActivity(intent);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        button = findViewById(R.id.button);
       // button.setOnClickListener(this::addPlace);

        DBhandler db = new DBhandler(this,null,null,2);

    }

    public void addPlace(View view)
    {
        DBhandler db = new DBhandler(this,null,null,1);

        //Restaurant Places

        Place place = new Place("The Hungry Wolf","A cozy cafe in the heart of downtown, known for its delicious espresso drinks and freshly baked pastries",0.0,100,40.632199, 22.942700,"Restaurant");
        db.addPlace(place);

        place = new Place("La Cucina Bella","A charming cafe located in a picturesque garden setting, offering a variety of organic teas and light vegetarian fare",0.0,100,40.619026, 22.953438,"Restaurant");
        db.addPlace(place);

        place = new Place("The Spice Route","A culinary journey through global flavors with a fusion of spices. ",0.0,100,40.624363, 22.950287,"Restaurant");
        db.addPlace(place);

        place = new Place("The Sizzling Pan","Experience sizzling platters and grilled specialties in a vibrant atmosphere",0.0,100,40.615873, 22.957721,"Restaurant");
        db.addPlace(place);

        place = new Place("The Fork & Knife","An elegant restaurant offering gourmet creations and a refined dining experience",0.0,100,40.604812, 22.977449,"Restaurant");
        db.addPlace(place);

        //Cafe places

        place = new Place("Bean Buzz","Trendy coffee spot with a wide variety of coffee blends. Cozy and modern ambiance",0.0, 100,40.6401,22.9444,"Cafe");
        db.addPlace(place);

        place = new Place("Cuppa Express","Charming coffee shop perfect for those on the go. Quick and convenient coffee experience",0.0,100,40.6394,22.9446,"Cafe");
        db.addPlace(place);

        place = new Place("Brew Grounds"," Popular coffee destination known for artisanal brewing methods. Cozy atmosphere.",0.0,100,40.6358,22.9361,"Cafe");
        db.addPlace(place);

        place = new Place("Perk Stop","Vibrant coffee shop with a wide range of options. Relaxed setting, ideal for catching up",0.0,100,40.6261,22.9492,"Cafe");
        db.addPlace(place);

        place = new Place("Espresso Lane","Charming café specializing in high-quality espresso-based beverages. Cozy seating.",0.0,100,40.6286,22.9497,"Cafe");
        db.addPlace(place);

        //Bar places

        place = new Place("Brews & Bites","A trendy bar offering a wide variety of craft beers and delicious bites.",3.0, 100, 40.6403,22.9444,"Bar");
        db.addPlace(place);

        place = new Place("The Jazz Lounge","An intimate bar known for its live jazz performances and handcrafted cocktails.",3.0,100,40.6328,22.9446,"Bar");
        db.addPlace(place);

        place = new Place("Mediterranean Nights"," A rooftop bar with panoramic views, serving Mediterranean-inspired cocktails and tapas",5.0,100,40.6358,22.9417,"Bar");
        db.addPlace(place);

        place = new Place("The Whisky Den","A cozy bar specializing in an extensive selection of whiskies and offering a laid-back ambiance.",2.5,100,40.6261,22.9492,"Bar");
        db.addPlace(place);

        place = new Place("The Vineyard Terrace","A wine bar featuring a wide range of local and international wines, accompanied by a charming outdoor terrace.",4.0,100,40.6289,22.9467,"Bar");
        db.addPlace(place);

    }

//    private void importDatabaseFromAssets()
//    {
//        try {
//
//            String myPath = DB_PATH + DB_NAME;
//            File file = new File(myPath);
//            if(file.exists())
//            {
//                return;
//            }
//            InputStream inputStream = getAssets().open("myAPP.db");
//            File outputFile = getDatabasePath("myAPP.db");
//            OutputStream outputStream = new FileOutputStream(outputFile);
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((length = inputStream.read(buffer)) > 0) {
//                outputStream.write(buffer, 0, length);
//            }
//            outputStream.flush();
//            outputStream.close();
//            inputStream.close();
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//    }
}



//TODO add navigation_drawer header


