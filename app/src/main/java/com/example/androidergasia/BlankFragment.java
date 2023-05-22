package com.example.androidergasia;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toolbar;

import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class BlankFragment extends Fragment
{
    private Button submit;
    private CalendarView calendarView;
    private Button showLocation;
    private Button pickTime;
    private TextView numOfPersons;
    private ImageButton incrementButton;
    private ImageButton decrementButton;
    private ImageView favorite;

    private TextView nameView;
    private static String nameOfPlace;
    private String timePicked;
    private String datePicked;
    private boolean[] validRequest ;

    int selectedHour;
    int selectedMinute;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public BlankFragment() {}
    public BlankFragment(String nameOfPlace)
    {
        this.nameOfPlace = nameOfPlace;
    }

    @SuppressLint("MissingInflatedId")

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);

        validRequest = new boolean[3];

        submit = view.findViewById(R.id.button);

        pickTime = view.findViewById(R.id.timeButton);

        submit.setOnClickListener(this::submit);

        submit.setEnabled(false);

        numOfPersons = view.findViewById(R.id.numOfPersons);

        incrementButton = view.findViewById(R.id.increment);

        decrementButton = view.findViewById(R.id.decrement);

        showLocation = view.findViewById(R.id.locationShow);

        favorite = view.findViewById(R.id.favoriteIcon);

        calendarView = view.findViewById(R.id.calendarView);

        nameView = view.findViewById(R.id.placeName);

        nameView.setText(nameOfPlace);

        nameView.setPaintFlags(nameView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int num = Integer.parseInt(numOfPersons.getText().toString());
                num++;
                numOfPersons.setText(num + "");

                validRequest[0] = true; //έχει επιλέξει άτομα
                checkValidness();
            }
        });

        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = Integer.parseInt(numOfPersons.getText().toString());
                if(num != 0)
                {
                    num--;
                    numOfPersons.setText(num +"");
                    if(num == 0)
                    {
                        validRequest[0]  = false; //δεν έχει επιλέξει άτομα
                    }
                }
                checkValidness();
            }
        });

        pickTime.setOnClickListener(this::pickTime);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth)
            {
                Calendar selectedDate = Calendar.getInstance();

                selectedDate.set(year, month, dayOfMonth);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy", Locale.getDefault());

                datePicked = dateFormat.format(selectedDate.getTime());

                validRequest[1] = true; // έχει επιλέψει ημερομηνία.
                checkValidness();
            }
        });


//        numOfPersons.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
//            {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
//            {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable)
//            {
//                //submit.setEnabled(timePicked.length() > 0); // Αν έχει επιλεχθεί ώρα, έχει μπεί τιμή στο editText
//            }
//        });

       // showLocation = view.findViewById(R.id.locationShow);

        showLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(getContext(),MapsActivity.class);

                intent.putExtra("name", nameOfPlace);
                startActivity(intent);
            }
        });

        int value = Controller.getDBhandler().isInFavoriteTable(nameOfPlace);

        if(value == 0 )
        {
            favorite.setImageResource(R.mipmap.favorite_empty);
        }
        else
        {
            favorite.setImageResource(R.mipmap.favorite_filled);
        }

        favorite.setOnClickListener(this::addToFavorite);

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
                String message = "Your reservation has been made!" + "\n" +
                        "Date of reservation: " + datePicked + "\n" + "Time of reservation: "
                        +timePicked;

                alertDialog.setMessage(message);
                //na mpei kodikas reservations
                Random random = new Random();
                int randomNumber = random.nextInt(9000) + 1000;

                Reservation reservation = new Reservation(randomNumber, 1, datePicked, timePicked, Integer.parseInt(numOfPersons.getText().toString()));
                Controller.getDBhandler().addReservation(reservation);
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
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                Calendar time = Calendar.getInstance();

                time.set(Calendar.HOUR_OF_DAY, selectedHour);
                time.set(Calendar.MINUTE, selectedMinute);

                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat(
                        "hh:mm a");

                timePicked = (format.format(time.getTime()));

                validRequest[2] = true; //έχι επιλέξει ώρα.
                checkValidness();
            }
        }
        ,currentTime.get(Calendar.HOUR_OF_DAY),currentTime.get(Calendar.MINUTE), DateFormat.is24HourFormat(getActivity()));

        timePickerDialog.show();
    }

    public void setNameOfPlace(String nameOfPlace) {
        this.nameOfPlace = nameOfPlace;
    }

    public void addToFavorite(View view)
    {
        int value = Controller.getDBhandler().isInFavoriteTable(nameOfPlace);

        if(value == 0)
        {
            favorite.setImageResource(R.mipmap.favorite_filled);
            Controller.getDBhandler().addPlaceToFavorite(nameOfPlace);
        }
        else
        {
            favorite.setImageResource(R.mipmap.favorite_empty);
            Controller.getDBhandler().removePlaceFromFavorite(nameOfPlace);
            //Controller.notifyRecyclerAdapter();
       }
    }

    private void checkValidness() {
        boolean allValid = true;

        for (boolean isValid : validRequest) {
            if (!isValid) {
                allValid = false;
                break;
            }
        }

        submit.setEnabled(allValid);

        if (allValid) {
            submit.setBackgroundColor(Color.parseColor("#9C27B0"));
        } else {
            submit.setBackgroundColor(Color.rgb(227, 227, 227));
        }
    }
}