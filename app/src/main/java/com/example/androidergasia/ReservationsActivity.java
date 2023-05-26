package com.example.androidergasia;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//Activity στο οποίο ο χρήστης μπορεί΄να δει τα στοιχεία των κρτήσεών του καθώς και να τα διαγράψει.
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

    //Για να μπορεί να ανοίξει τις ρυθμίσεις και να αλλάξει γλώσσα
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.action_settings){
            Intent intent = new Intent(ReservationsActivity.this, SettingsActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

}