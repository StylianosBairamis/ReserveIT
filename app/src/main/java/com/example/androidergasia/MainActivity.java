package com.example.androidergasia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    DrawerLayout drawerLayout;
    Spinner spinner;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);

        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        spinner = findViewById(R.id.spinner);

        ArrayList<String> types = new ArrayList(Arrays.asList("","Restaurant", "Bar", "Cafe"));

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
        button.setOnClickListener(this::addPlace);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return true;
    }

    public void addPlace(View view)
    {
        DBhandler db = new DBhandler(this,null,null,1);

        Place place = new Place("The Hungry Wolf","A cozy cafe in the heart of downtown, known for its delicious espresso drinks and freshly baked pastries",0.0,100,40.632199, 22.942700,"Cafe");

        db.addPlace(place);
        place = new Place("La Cucina Bella"," A charming cafe located in a picturesque garden setting, offering a variety of organic teas and light vegetarian fare",0.0,100,40.619026, 22.953438,"Cafe");
        db.addPlace(place);

        place = new Place("The Spice Route","polu kalo",0.0,100,40.624363, 22.950287,"Bar");
        db.addPlace(place);


        place = new Place("The Sizzling Pan","polu kalo",0.0,100,40.615873, 22.957721,"Bar");
        db.addPlace(place);


        place = new Place("The Fork & Knife","polu kalo",0.0,100,40.604812, 22.977449,"Restaurant");
        db.addPlace(place);
    }
}