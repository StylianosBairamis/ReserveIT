package com.example.androidergasia;

import android.content.Context;
import android.database.Cursor;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class reservationsRecyclerAdapter extends RecyclerView.Adapter<reservationsRecyclerAdapter.reservationsViewHolder> {
    private Context context;
    private Cursor cursor;
    private DBhandler dBhandler;

    public reservationsRecyclerAdapter(Context context, Cursor cursor){
        this.context = context;
        this.dBhandler = Controller.getDBhandler();
        this.cursor = cursor;
    }

    static class reservationsViewHolder extends RecyclerView.ViewHolder {
        TextView resId, placeId, date, numOfPeople;
        public reservationsViewHolder(View itemView) {
            super(itemView);
            resId = itemView.findViewById(R.id.reservationId);
            placeId = itemView.findViewById(R.id.placeId);
            date = itemView.findViewById(R.id.date);
            numOfPeople = itemView.findViewById(R.id.people);
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

         holder.resId.setText(cursor.getInt(0));
         holder.placeId.setText(cursor.getInt(1));
         holder.date.setText(cursor.getString(2));
         holder.numOfPeople.setText(cursor.getInt(3));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

}
