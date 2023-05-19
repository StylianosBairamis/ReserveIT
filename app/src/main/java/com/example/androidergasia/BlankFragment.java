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
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

public class BlankFragment extends Fragment
{
    Button submit;

    CalendarView calendarView;
    Button showLocation;
    TextView timePicked;
    TextView numOfPersons;
    ImageButton imageButton;
    static String nameOfPlace;

    private boolean selected = false;
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

        submit = view.findViewById(R.id.button);

        submit.setOnClickListener(this::submit);

        submit.setEnabled(false);

        numOfPersons = view.findViewById(R.id.numOfPersons);

        ImageButton incrementButton = view.findViewById(R.id.increment);

        ImageButton decrementButton = view.findViewById(R.id.decrement);

        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int num = Integer.parseInt(numOfPersons.getText().toString());
                num++;
                numOfPersons.setText(num + "");
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
                }
            }
        });

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
                //submit.setEnabled(timePicked.length() > 0); // Αν έχει επιλεχθεί ώρα, έχει μπεί τιμή στο editText
            }
        });

        showLocation = view.findViewById(R.id.locationShow);

        showLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(getContext(),MapsActivity.class);

                intent.putExtra("name", nameOfPlace);
                startActivity(intent);
            }
        });

        imageButton = view.findViewById(R.id.imageButton);

        int value = Controller.getDBhandler().isInFavoriteTable(nameOfPlace);

        if(value == 0 )
        {
            imageButton.setImageResource(R.mipmap.favorite_empty);
        }
        else
        {
            imageButton.setImageResource(R.mipmap.favorite_filled);
        }

        imageButton.setOnClickListener(this::addToFavorite);





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
                //na mpei kodikas reservations
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

    public void setNameOfPlace(String nameOfPlace) {
        this.nameOfPlace = nameOfPlace;
    }

    public void addToFavorite(View view)
    {
//        DBhandler handler = Controller.getDBhandler();
//
//        SQLiteDatabase db = handler.getReadableDatabase();
//
//        String query = "SELECT " + "_id" +
//                " FROM " + "places " +
//                " WHERE " + "placeName" + " = '" + nameOfPlace + "' ";
//
//        Cursor cursor = db.rawQuery(query,null);
//
//        cursor.moveToFirst();
//
//        int id = cursor.getInt(0);
//
//        query = "SELECT " + " * " +
//                " FROM " + " favorite " +
//                " WHERE " + "id_of_place = " + id ;
//
//        cursor = db.rawQuery(query, null);

        int value = Controller.getDBhandler().isInFavoriteTable(nameOfPlace);

        if(value == 0)
        {
            imageButton.setImageResource(R.mipmap.favorite_filled);
            Controller.getDBhandler().addPlaceToFavorite(nameOfPlace);
        }
        else
        {
            imageButton.setImageResource(R.mipmap.favorite_empty);
            Controller.getDBhandler().removePlaceFromFavorite(nameOfPlace);
            //Controller.notifyRecyclerAdapter();
       }
    }


}