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

    DBhandler dBhandler;
    reservationsRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservations_activity);

        dBhandler = Controller.getDBhandler();

        recyclerView = findViewById(R.id.reservationsRecycler);
        Cursor cursor = dBhandler.findReservations();
        adapter = new reservationsRecyclerAdapter(this, cursor);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Toolbar toolbar = findViewById(R.id.mytoolbar);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); // up Button


    }

}