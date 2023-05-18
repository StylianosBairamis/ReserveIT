package com.example.androidergasia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toolbar;

public class ActivityForFragment extends AppCompatActivity {

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

    }
}