package com.example.androidergasia;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ReservationsActivity extends BaseActivity {
    RecyclerView recyclerView;
    DBhandler dBhandler;
    reservationsRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.reservations_activity);

        dBhandler = Controller.getDBhandler();

        recyclerView = findViewById(R.id.recycler_View2);

        adapter = new reservationsRecyclerAdapter(this);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Toolbar toolbar = findViewById(R.id.mytoolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}