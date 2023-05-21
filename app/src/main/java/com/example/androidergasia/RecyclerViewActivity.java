package com.example.androidergasia;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;


public class RecyclerViewActivity extends BaseActivity
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

        //DBhandler db = new DBhandler(this,null,null,1);

        Bundle bundle = getIntent().getExtras();

        String searchType = bundle.getString("search"); // Τι τύπος μαγαζιου παίρνω απο το main-activity

        adapter = new RecyclerAdapter(this, searchType, true);

        recyclerView1.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // up Button

    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.action_settings){
            Intent intent = new Intent(RecyclerViewActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}