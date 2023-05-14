package com.example.androidergasia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

public class RecyclerViewActivity extends AppCompatActivity
{
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        RecyclerView recyclerView1 = findViewById(R.id.recycler_View);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView1.setLayoutManager(linearLayoutManager);

        DBhandler db = new DBhandler(this,null,null,1);

//Set my Adapter for the RecyclerView
        Bundle bundle = getIntent().getExtras();

        String searchType = bundle.getString("search");

        adapter = new RecyclerAdapter(db,this,searchType);

        recyclerView1.setAdapter(adapter);

    }

}