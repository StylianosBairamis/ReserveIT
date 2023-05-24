package com.example.androidergasia;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class reservationsRecyclerAdapter extends RecyclerView.Adapter<reservationsRecyclerAdapter.reservationsViewHolder> {
    private Context context;
    private DBhandler dBhandler;
    private Cursor cursor;

    public reservationsRecyclerAdapter(Context context)
    {
        this.context = context;
        this.dBhandler = Controller.getDBhandler();

        cursor = dBhandler.findReservations();
    }

    static class reservationsViewHolder extends RecyclerView.ViewHolder {
        TextView nameOfPlace;

        TextView valueOfPlace;

        TextView timeOfReservation;

        TextView valueOfTime;

        TextView dateOfReservation;

        TextView valueOfDate;

        TextView numOfPeople;

        TextView valueOfPeople;

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

         holder.valueOfPlace.setText(cursor.getString(0));
         holder.valueOfTime.setText(cursor.getString(1));
         holder.valueOfDate.setText(cursor.getString(2));
         holder.valueOfPeople.setText(cursor.getInt(3)+"");
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

}
