package com.example.androidergasia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity
{
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        textView = findViewById(R.id.textView5);

        CountDownTimer countDownTimer = new CountDownTimer(30000,1000)
        {
            @Override
            public void onTick(long l)
            {
                textView.setText("Seconds remaining: " + l / 1000);
            }

            @Override
            public void onFinish()
            {
                textView.setText("Timer finished");

            }
        }.start();
    }
}