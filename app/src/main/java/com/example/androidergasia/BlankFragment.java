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
import android.text.format.DateFormat;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;



/*
Υλοποιούνται όλες οι λειτουργίες του fragment.Εδώ ο χρήστης μπορεί να προσθέσει το μαγαζί στα αγαπημένα του, να δει την τοποθεσία του
και να δώσει τις απαραίτητες πληροφορίες για την κράτηση που θέλει να κάνει (Αριθμός ατόμων, ημερομηνία, ώρα).
Αφου δώσει τις πληροφορίες αυτές, μπορεί να πατήσει το κουμπί της κράτησης και να καταχωρηθεί στη βάση δεδομένων.
 */
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

    private  boolean isSameDate = false;

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

        pickTime.setEnabled(false); //περιμένω πρώτα να βάλει ημερομηνια.

        numOfPersons = view.findViewById(R.id.numOfPersons);

        incrementButton = view.findViewById(R.id.increment);

        decrementButton = view.findViewById(R.id.decrement);

        showLocation = view.findViewById(R.id.locationShow);

        favorite = view.findViewById(R.id.favoriteIcon);

        calendarView = view.findViewById(R.id.calendarView);

        calendarView.setDate(System.currentTimeMillis(), false, true);

        nameView = view.findViewById(R.id.placeName);

        nameView.setText(nameOfPlace);

        nameView.setPaintFlags(nameView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        //Τα 2 κουμπια "συν" και "πλην" για να ρυθμίζει ο χρήστης τον αριθμό των ατόμων
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

                Calendar currentDate = Calendar.getInstance();

                int currentYear = currentDate.get(Calendar.YEAR);
                int currentMonth = currentDate.get(Calendar.MONTH);
                int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);

                currentDate.set(currentYear,currentMonth,currentDay); // Τρέχουσα ημερομηνια.

                selectedDate.set(year, month, dayOfMonth); // Ημερομηνια που επιλέχθηκε.

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

                datePicked = dateFormat.format(selectedDate.getTime());

               String dateCurrent = dateFormat.format(currentDate.getTime());

                //'Ελεγχος για το αν η ημερομηνια που επιλέχθηκε είναι πριν απο την τρέχουσα
                // δεν έχει επιλέξει εγκυρη ημερομηνια
                validRequest[1] = !selectedDate.before(currentDate); // έχει επιλέξει εγκυρη ημερομηνία.

                Toast toast = new Toast(getActivity());

                toast.setText(datePicked);

                toast.show();

                pickTime.setEnabled(validRequest[1]); // αν μπορώ να διαλέξω ώρα

                isSameDate  =  dateCurrent.equals(datePicked);

                checkValidness();
            }
        });

        showLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getContext(),MapsActivity.class);//intent για χάρτη που δείχνει την τοποθεσία του μαγαζιού

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
        //παράθυρο στο οποίο περιμένει ελάχτιστα ο χρήστης μέχρι να καταχωρηθεί η κρατησή του.
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Making a reservation");
        progressDialog.setMessage("please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();//για να σταματήεσι την καταχώρηση της κράτησης
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

                alertDialog.setMessage(message);//Εμφανίζεται μήνυμα επιτυχίας καθώς και τα στοιχεία της κρατησής του χρήστη.

                int placeID = Controller.getDBhandler().getPlaceID(nameOfPlace);

                Reservation reservation = new Reservation(placeID, datePicked, timePicked, Integer.parseInt(numOfPersons.getText().toString()));
                //Δημιουργήθηκε αντικείμενο Reservation και καταχωρείται στη βάση.
                Controller.getDBhandler().addReservation(reservation);
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        getActivity().finish();//κλείνει το παράθυρο του progressDialog
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
                Calendar selectedTime = Calendar.getInstance();

                if(!isSameDate)
                {
                    selectedTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                    selectedTime.set(Calendar.MINUTE, selectedMinute);

                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat format = new SimpleDateFormat("hh:mm a");

                    timePicked = (format.format(selectedTime.getTime()));//μεταβλητή που κρατάει την ώρα την οποία επέλεξε ο χρήστης.

                    validRequest[2] = true;

                    checkValidness();

                    return;
                }

                selectedTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                selectedTime.set(Calendar.MINUTE, selectedMinute);

                int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
                int currentMinute = currentTime.get(Calendar.MINUTE);

                currentTime.set(Calendar.HOUR_OF_DAY,currentHour);
                currentTime.set(Calendar.MINUTE,currentMinute);

                int result = selectedTime.compareTo(currentTime);

                validRequest[2] = result >= 0 ;

                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat format = new SimpleDateFormat("hh:mm a");//νεο format για την επιλεγμενη ωρα

                timePicked = (format.format(selectedTime.getTime()));//εφαρμόζεται το format

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
        //Ελέγχει στη βάση αν το μαγαζί είναι στα αγαπημένα και βάζει την αντίστοιχη εικόνα,
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

    /*
    Με αυτή τη μέθοδο ο χρήστης πρέπει πρώτα να δηλώσει μια ημερομηνία για την κράτηση και έπειτα
    θα μπορεί να επιλέξει την ωρα που θέλει. Αφού επιλεχθεί η ημερομηνία και η ώρα της κράτησης,
    πρέπει να επιλέξει κάποιο αριθμό ατόμων για να ενεργοποιηθεί το κουμπί της κράτησης
    και να μπορέσει να το πατήσει ο χρήστης.
     */
    private void checkValidness() {
        boolean allValid = true;

        for (boolean isValid : validRequest) {
            if (!isValid) {
                allValid = false;
                break;
            }
        }

        submit.setEnabled(allValid);//ενεργοποίηση της λειτουργίας του κουμπιού

        if (allValid)
        {
            submit.setBackgroundColor(Color.parseColor("#9C27B0"));//αλλάζει το χρώμα του για να ειδοποιήσει το χρήστη ότι μπορεί να κάνει κράτηση.
        }
        else
        {
            submit.setBackgroundColor(Color.rgb(227, 227, 227));
        }
    }
}