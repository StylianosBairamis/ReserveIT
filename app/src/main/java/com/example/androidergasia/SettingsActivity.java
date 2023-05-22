package com.example.androidergasia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Locale;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    private RadioButton radioEnglish;
    private RadioButton radioGreek;
    private String language;
    private SharedPreferences sharedPreferences;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_view);
        radioEnglish = findViewById(R.id.radio_english);
        radioGreek = findViewById(R.id.radio_greek);
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        language = sharedPreferences.getString("language", "English");

        radioGreek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                language = "Greek";
                changeLanguage(language);
                radioEnglish.setChecked(false);
            }
        });

        radioEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                language = "English";
                changeLanguage(language);
                radioGreek.setChecked(false);
            }
        });

    }

    private void changeLanguage(String language) {
        if (Objects.equals(language, "English")) {
            setLocale("en");

        } else if (Objects.equals(language, "Greek")) {
            setLocale("el");

        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("language", language);
        editor.apply();

        restartApp();
    }

    private void setLocale(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
    }

    private void restartApp() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}