package com.example.androidergasia;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class reservationsRecyclerAdapter extends RecyclerView.Adapter<reservationsRecyclerAdapter.reservationsViewHolder> {
    private Context context;
    private DBhandler dBhandler;
    private Cursor cursor;
    private ArrayList<Object[]> listOfObjects;

    public reservationsRecyclerAdapter(Context context)
    {
        this.context = context;

        this.dBhandler = Controller.getDBhandler();

        cursor = dBhandler.findReservations();

        fromCursorToArrayList();
    }

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

        public reservationsViewHolder(View itemView)
        {
            super(itemView);

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

//         cursor.moveToFirst();//Παω τον Cursor
//
//         cursor.move(position);// Παω την θέση που θέλω είναι offset, δεν κάνει μεταπήδηση.
         Object[] data = listOfObjects.get(position);

         holder.valueOfPlace.setText((String) data[0]);
         holder.valueOfTime.setText((String) data[1]);
         holder.valueOfDate.setText((String) data[2]);
         holder.valueOfPeople.setText(data[3]+"");

         int idOfReservation = (int) data[4]; // Παίρνω το id του reservation

         int pos = position;

         holder.removeReservation.setOnClickListener(new View.OnClickListener() {
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

                        listOfObjects.remove(pos);

                        notifyDataSetChanged();
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

//    private void changeCursor(int indexForRemove)
//    {
//        MatrixCursor matrixCursor = new MatrixCursor(cursor.getColumnNames());
//
//        for(int i = 0 ; i < cursor.getCount(); i++)
//        {
//            if( i != indexForRemove)
//            {
//                cursor.moveToFirst();
//
//                cursor.move(i);
//
//                Object[] objectArray = new Object[5];
//                objectArray[0] = cursor.getString(0);
//                objectArray[1] = cursor.getString(1);
//                objectArray[2] = cursor.getString(2);
//                objectArray[3] = cursor.getInt(3);
//                objectArray[4] = cursor.getInt(4);
//
//                matrixCursor.addRow(objectArray);
//            }
//        }
//        cursor = matrixCursor;
//    }

    private void fromCursorToArrayList()
    {
        listOfObjects = new ArrayList<>();

        for(int i = 0 ; i < cursor.getCount(); i++)
        {
            cursor.moveToFirst();
            cursor.move(i);

            Object[] objectArray = new Object[5];
            objectArray[0] = cursor.getString(0);
            objectArray[1] = cursor.getString(1);
            objectArray[2] = cursor.getString(2);
            objectArray[3] = cursor.getInt(3);
            objectArray[4] = cursor.getInt(4);

            listOfObjects.add(objectArray);
        }
    }

    @Override
    public int getItemCount()
    {
        return listOfObjects.size();
    }

}
