package com.example.androidergasia;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;


import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class BlankFragment extends Fragment
{
    Button submit;
    Button time;

    Button showLocation;
    TextView timePicked;
    EditText numOfPersons;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);

        submit = view.findViewById(R.id.submit);

        submit.setOnClickListener(this::submit);

        submit.setEnabled(false);

        time = view.findViewById(R.id.timePicker);

        time.setOnClickListener(this::pickTime);

        timePicked = view.findViewById(R.id.timeShow);

        timePicked.setEnabled(false);

        numOfPersons = view.findViewById(R.id.numOfPersons);

        numOfPersons.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                submit.setEnabled(timePicked.length() > 0); // Αν έχει επιλεχθεί ώρα, έχει μπεί τιμή στο editText
            }
        });

        numOfPersons.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                numOfPersons.setHint("");
                return false;
            }
        });

        showLocation = view.findViewById(R.id.locationShow);

        showLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),MapsActivity.class);

                intent.putExtra("Longitude",40.524204);
                intent.putExtra("Latitude",22.976887); //Θεσσαλονίκη
                startActivity(intent);
            }
        });



        return view;
    }

    private void submit(View view)
    {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Making a reservation");
        progressDialog.setMessage("please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
            }
        });
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                progressDialog.dismiss();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Success");
                alertDialog.setMessage("Your reservation has been made!");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                alertDialog.show();
            }
        }, 2000);
    }

    private void pickTime(View view)
    {
        Calendar currentTime = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), 2, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                Calendar time = Calendar.getInstance();

                time.set(Calendar.HOUR_OF_DAY, selectedHour);
                time.set(Calendar.MINUTE, selectedMinute);

                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat(
                        "hh:mm a");

                timePicked.setText(format.format(time.getTime()));
            }
        },currentTime.get(Calendar.HOUR_OF_DAY),currentTime.get(Calendar.MINUTE), DateFormat.is24HourFormat(getActivity()));

        timePickerDialog.show();
    }
}