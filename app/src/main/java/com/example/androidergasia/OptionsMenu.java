package com.example.androidergasia;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class OptionsMenu extends AppCompatActivity {

    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String language = preferences.getString("language", ""); // Get the current language preference

        // Set the language when the activity is created
        if (!language.isEmpty()) {
            LocaleHelper.setLocale(this, language);
        }

        // Other initialization code
        // ...
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }*/

    boolean isGreekSelected = false;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        String language = "";

//        // Handle menu item selection
//        if (id == R.id.action_english) {
//            language = "en"; // English language code
//        } else if (id == R.id.action_greek) {
//            language = "el"; // Greek language code
//            isGreekSelected = true;
//            updateTexts(findViewById(R.id.textView));
//            return true;
//        }

//        // Save the language preference
//        preferences.edit().putString("language", language).apply();
//
//        // Restart the activity to apply the language change
//        Intent intent = getIntent();
//        finish();
//        startActivity(intent);

        return super.onOptionsItemSelected(item);

    }

//    private void updateTexts(View view) {
//        if (isGreekSelected) {
//            // Set the Greek translations for the UI elements
//            TextView tv1 = findViewById(R.id.textView);
//            TextView tv2 = findViewById(R.id.description);
//            TextView tv3 = findViewById(R.id.textView3);
//            TextView tv4 = findViewById(R.id.textView2);
//            TextView tv5 = findViewById(R.id.textView4);
//            Button b1 = findViewById(R.id.button);
//            Button b2 = findViewById(R.id.locationShow);
//            MenuItem mi1 = findViewById(R.id.person);
//            MenuItem mi2  = findViewById(R.id.favorite);
//            MenuItem mi3  = findViewById(R.id.reservation);
//            MenuItem mi4 = findViewById(R.id.action_greek);
//            MenuItem mi5 = findViewById(R.id.action_english);
//
//            tv1.setText(R.string.select_a_type_el);
//            tv2.setText(R.string.textViewForDescription_el);
//            tv3.setText(R.string.place_name_el);
//            tv4.setText(R.string.choose_a_date_for_your_reservation_el);
//            tv5.setText(R.string.number_of_people_for_the_reservation_el);
//            b1.setText(R.string.book_el);
//            b2.setText(R.string.view_place_location_el);
//            mi1.setTitle(R.string.profile_el);
//            mi2.setTitle(R.string.favorites_el);
//            mi3.setTitle(R.string.reservations_el);
//            mi4.setTitle(R.string.greek_el);
//            mi5.setTitle(R.string.english_el);
//            // Update other UI elements with Greek translations
//
//        }
//    }
}