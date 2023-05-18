package com.example.androidergasia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.MatrixCursor;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toolbar;

public class RecyclerViewActivity extends MainActivity
{
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_activity);

        RecyclerView recyclerView1 = findViewById(R.id.recycler_View);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView1.setLayoutManager(linearLayoutManager);

        DBhandler db = new DBhandler(this,null,null,1);

        MapsActivity.setDBHandler(db);

        Bundle bundle = getIntent().getExtras();

        String searchType = bundle.getString("search");

        adapter = new RecyclerAdapter(db,this,searchType);

        recyclerView1.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return true;
    }

}