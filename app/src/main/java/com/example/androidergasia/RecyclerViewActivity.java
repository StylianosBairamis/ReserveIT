package com.example.androidergasia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

/*
recyclerViewActivity για το recycler που ενεργοποιείται από intent στο MainActivity
Παίρνει τον τύπο των μαγαζιών που θα μπούν στον recycler από το bundle μέσω του recyclerAdapter
 */
public class RecyclerViewActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter<RecyclerAdapter.ViewHolder> adapter;

    private String searchType = "";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_activity);

        if(!(savedInstanceState == null))
        {
            searchType = savedInstanceState.getString("searchType");
        }
        else
        {
            Bundle bundle = getIntent().getExtras();
            searchType = bundle.getString("search"); // Τι τύπος μαγαζιου παίρνω απο το main-activity
        }

        RecyclerView recyclerView1 = findViewById(R.id.recycler_View);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView1.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter(this, searchType, true);

        recyclerView1.setAdapter(adapter);
        //o adapter γεμίζει τον recycler με τα μαγαζία

        //Toolbar με upButton
        Toolbar toolbar = findViewById(R.id.mytoolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // up Button

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        outState.putString("searchType", searchType);
        super.onSaveInstanceState(outState);
    }
}