package com.example.androidergasia;
//απαραιτητα imports
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Σε αυτή την κλάση ορίζεται ο recyclerAdapter του reservationsActivity
 * Εδώ υλοποιούντε λειτουργίες όπως
 */
public class reservationsRecyclerAdapter extends RecyclerView.Adapter<reservationsRecyclerAdapter.reservationsViewHolder> {
    private Context context;
    private DBhandler dBhandler;
    private Cursor cursor;

    //recyclerAdapter constructor
    public reservationsRecyclerAdapter(Context context)
    {
        this.context = context;
        this.dBhandler = Controller.getDBhandler();

        cursor = dBhandler.findReservations();

    }

    //View holder οπου εντοπιζει ολα τα στοιχεια του view για να μπορεσουμε να τους αλλαξουμε τιμες
    static class reservationsViewHolder extends RecyclerView.ViewHolder {
        private TextView nameOfPlace;

        private TextView valueOfPlace;

        private TextView timeOfReservation;

        private TextView valueOfTime;

        private TextView dateOfReservation;

        private TextView valueOfDate;

        private TextView numOfPeople;

        private TextView valueOfPeople;

        private ImageButton removeReservation;

        //viewHolder constructor
        public reservationsViewHolder(View itemView)
        {
            super(itemView);
            //βρισκω ολα τα στοιχεια με το id
            nameOfPlace = itemView.findViewById(R.id.place);
            valueOfPlace = itemView.findViewById(R.id.valueOfPlace);

            timeOfReservation = itemView.findViewById(R.id.time);
            valueOfTime = itemView.findViewById(R.id.valueOfTime);

            dateOfReservation = itemView.findViewById(R.id.date);
            valueOfDate = itemView.findViewById(R.id.valueOfDate);

            numOfPeople = itemView.findViewById(R.id.numberOfPeople);
            valueOfPeople= itemView.findViewById(R.id.valueOfPeople);

            removeReservation = itemView.findViewById(R.id.removeReservation);


        }
    }

    @NonNull
    @Override
    public reservationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reservations_entry, parent, false);
        return new reservationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull reservationsViewHolder holder, int position) {

         cursor.moveToFirst();//Παω τον Cursor

         cursor.move(position);// Παω την θέση που θέλω είναι offset, δεν κάνει μεταπήδηση.

        //Αλλάζω τιμή μέσω του holder
         holder.valueOfPlace.setText(cursor.getString(0));
         holder.valueOfTime.setText(cursor.getString(1));
         holder.valueOfDate.setText(cursor.getString(2));
         holder.valueOfPeople.setText(cursor.getInt(3)+"");

         int idOfReservation = cursor.getInt(4); // Παίρνω το id του reservation

        int pos = position;

         holder.removeReservation.setOnClickListener(new View.OnClickListener() {
             /**
              *Λειτουργεία ώστε ο χρήστης να μπορεί να αφαιρέση μια κράτηση αν αλλάξει γνώμη
              * και ζητάται επιβεβαίωση μέσω ενός alertDialog. Μεσω του dbhandler αφαιρείται
              * η κράτηση από τη βάση δεδομένων
              */
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
                alertDialog.setTitle("Alert");
                String message = "Are you sure you want to cancel your reservation?";

                alertDialog.setMessage(message);

                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dBhandler.removeReservation(idOfReservation);

                        changeCursor(pos);
                        notifyItemRemoved(pos);
                    }
                });

                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });

                alertDialog.show();
            }
        });
    }

    /**

     Ενημερώνει τον δρομέα (cursor) αφαιρώντας ένα ειδικό στοιχείο στη δοθείσα θέση.

     @param indexForRemove Η θέση του στοιχείου που θα αφαιρεθεί από τον δρομέα.
     */
    private void changeCursor(int indexForRemove)
    {
        MatrixCursor matrixCursor = new MatrixCursor(cursor.getColumnNames());

        for(int i = 0 ; i < cursor.getCount(); i++)
        {
            if( i != indexForRemove)
            {
                cursor.moveToFirst();

                cursor.move(i);

                Object[] objectArray = new Object[5];
                objectArray[0] = cursor.getString(0);
                objectArray[1] = cursor.getString(1);
                objectArray[2] = cursor.getString(2);
                objectArray[3] = cursor.getInt(3);
                objectArray[4] = cursor.getInt(4);

                matrixCursor.addRow(objectArray);
            }
        }
        cursor = matrixCursor;
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

}
