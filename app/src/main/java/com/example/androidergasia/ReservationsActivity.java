package com.example.androidergasia;

import android.database.Cursor;
import android.os.Bundle;
import android.view.ContentInfo;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Objects;


public class ReservationsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> date;
    ArrayList<Integer> resId, placeId, numOfPeople;
    DBhandler dBhandler;
    reservationsRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservations_activity);

        dBhandler = Controller.getDBhandler();

        resId = new ArrayList<>();
        placeId = new ArrayList<>();
        numOfPeople = new ArrayList<>();
        date = new ArrayList<>();

        recyclerView = findViewById(R.id.reservationsRecycler);
        adapter = new reservationsRecyclerAdapter(this, resId, placeId, date, numOfPeople);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayData();

        Toolbar toolbar = findViewById(R.id.mytoolbar);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); // up Button


    }

    private void displayData() {
        Cursor cursor = dBhandler.findReservations();
        if(cursor.getCount() == 0){
            Toast.makeText(ReservationsActivity.this, "No reservations", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while(cursor.moveToNext()){
                resId.add(Integer.valueOf(cursor.getString(0)));
                placeId.add(Integer.valueOf(cursor.getString(1)));
                date.add(cursor.getString(2));
                numOfPeople.add(Integer.valueOf(cursor.getString(3)));
            }
        }
    }
}