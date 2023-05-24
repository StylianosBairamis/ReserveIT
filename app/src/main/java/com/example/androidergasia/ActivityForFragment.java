package com.example.androidergasia;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;


public class ActivityForFragment extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_fragment);

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = getIntent().getExtras();

        String nameOfPlace = bundle.getString("name"); // Περνάω το name στο blankFragment

        BlankFragment blankFragment = new BlankFragment(nameOfPlace);

        fragmentTransaction.add(R.id.fragmentContainerView, blankFragment);

        fragmentTransaction.commit();

        Toolbar toolbar = findViewById(R.id.mytoolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // up Button

    }

}