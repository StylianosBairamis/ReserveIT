package com.example.androidergasia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //RecyclerView recyclerView = findViewById(R.id.RecycleView);

       // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

       // recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void addPlace(View view)
    {
        DBhandler dBhandler = new DBhandler(this,null,null,1);
        Place place = new Place(0,"sindos","polu kalo",5.5);
        dBhandler.addPlace(place);
    }
}