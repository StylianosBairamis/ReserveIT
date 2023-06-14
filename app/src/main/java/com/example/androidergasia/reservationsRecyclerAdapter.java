package com.example.androidergasia;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
    ArrayList<Reservation> listOfReservation;

    public reservationsRecyclerAdapter(Context context)
    {
        this.context = context;

        listOfReservation = Controller.findReservations();
    }

    static class reservationsViewHolder extends RecyclerView.ViewHolder {

        private TextView valueOfPlace;

        private TextView valueOfTime;

        private TextView valueOfDate;

        private TextView valueOfPeople;

        private ImageButton removeReservation;

        public reservationsViewHolder(View itemView)
        {
            super(itemView);


            valueOfPlace = itemView.findViewById(R.id.valueOfPlace);

            valueOfTime = itemView.findViewById(R.id.valueOfTime);

            valueOfDate = itemView.findViewById(R.id.valueOfDate);

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
    public void onBindViewHolder(@NonNull reservationsViewHolder holder, int position)
    {

        Reservation reservation = listOfReservation.get(position);//Παίρνω το Reservation

        holder.valueOfPlace.setText(reservation.getNameOfPlace()); //Ανάθεση στοιχείων
        holder.valueOfTime.setText(reservation.getDateTime());
        holder.valueOfDate.setText(reservation.getDate());
        holder.valueOfPeople.setText(reservation.getNumberOfPeople()+"");

        int idOfReservation = reservation.getReservationID(); // Παίρνω το id του reservation

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
                        Controller.removeReservation(idOfReservation);

                        listOfReservation.remove(pos);

                        notifyDataSetChanged();
                    }
                });

                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return listOfReservation.size();
    }

}