package com.example.androidergasia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity
{
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView1 = findViewById(R.id.recycler_View);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView1.setLayoutManager(linearLayoutManager);

        DBhandler db = new DBhandler(this,null,null,1);

//Set my Adapter for the RecyclerView
        adapter = new RecyclerAdapter(db,this);

        recyclerView1.setAdapter(adapter);


//        Resources res = getResources();
//        int resourceId = R.drawable.first;
//        Bitmap bitmap = BitmapFactory.decodeResource(res, resourceId);
//
//        try {
//            DBhandler.test(bitmap);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

    }

    public void test(View view)
    {
        Intent intent = new Intent(this,ActivityForFragment.class);
        startActivity(intent);
    }

    public void addPlace(View view)
    {
        DBhandler db = new DBhandler(this,null,null,1);

        Place place = new Place(0,"The Hungry Wolf","polu kalo","Cafe");

        db.addPlace(place);
        place = new Place(0,"La Cucina Bella","polu kalo","Cafe");
        db.addPlace(place);


        place = new Place(0,"The Spice Route","polu kalo","Bar");
        db.addPlace(place);


        place = new Place(0,"The Sizzling Pan","polu kalo","Bar");
        db.addPlace(place);


        place = new Place(0,"The Fork & Knife","polu kalo","Restaurant");
        db.addPlace(place);
    }

}