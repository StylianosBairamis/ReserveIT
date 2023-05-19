package com.example.androidergasia;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityForFragment extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_fragment);

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        BlankFragment blankFragment = new BlankFragment();


        Bundle bundle = getIntent().getExtras();

        blankFragment.setNameOfPlace(bundle.getString("name")); // Παιρνάω το name στο blankFragment

        fragmentTransaction.add(R.id.fragmentContainerView, blankFragment);

        fragmentTransaction.commit();

        Toolbar toolbar = findViewById(R.id.mytoolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // up Button

    }
}