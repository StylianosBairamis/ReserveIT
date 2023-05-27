package com.example.androidergasia;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity
{

    private DBhandler DBhandler;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    public boolean onSupportNavigateUp()
    {
        onBackPressed(); // Navigate back to the previous activity
        return true;
    }


//    @Override
////    public boolean onOptionsItemSelected(MenuItem item) {
//        int itemId = item.getItemId();
//
//        if (itemId == android.R.id.home) {
//            onBackPressed();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}