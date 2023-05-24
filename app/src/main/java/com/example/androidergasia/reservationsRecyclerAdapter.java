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
    private ArrayList resId, placeId, date, numOfPeople;

    public reservationsRecyclerAdapter(Context context, ArrayList resId, ArrayList placeId, ArrayList date, ArrayList numOfPeople){
        this.context = context;
        this. resId = resId;
        this.placeId = placeId;
        this.date = date;
        this.numOfPeople = numOfPeople;
    }

    @NonNull
    @Override
    public reservationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reservations_entry, parent, false);
        return new reservationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull reservationsViewHolder holder, int position) {
        holder.resId.setText(String.valueOf(resId.get(position)));
        holder.placeId.setText(String.valueOf(placeId.get(position)));
        holder.date.setText(String.valueOf(date.get(position)));
        holder.numOfPeople.setText(String.valueOf(numOfPeople.get(position)));
    }

    @Override
    public int getItemCount() {
        return resId.size();
    }

    static class reservationsViewHolder extends RecyclerView.ViewHolder {
        TextView resId, placeId, date, numOfPeople;
        public reservationsViewHolder(@NonNull View itemView) {
            super(itemView);

            resId = itemView.findViewById(R.id.reservationId);
            placeId = itemView.findViewById(R.id.placeId);
            date = itemView.findViewById(R.id.date);
            numOfPeople = itemView.findViewById(R.id.people);
        }
    }
}
