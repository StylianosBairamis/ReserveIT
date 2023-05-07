package com.example.androidergasia;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
{
    DBhandler DBhandler = null;
    static Cursor cursor = null;
    private static Context context = null;

    public RecyclerAdapter(DBhandler DBhandler,Context context)
    {
        this.DBhandler = DBhandler;
        cursor = this.DBhandler.findPlaces("Cafe");

        this.context = context;

        System.out.println(cursor.getCount());
        System.out.println("hello");
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName;
        TextView itemDescription;
        TextView typeOfPlace;
        RatingBar ratingBar;

        public ViewHolder(View itemView)
        {
            super(itemView);
            itemImage = itemView.findViewById(R.id.imageView);
            itemName = itemView.findViewById(R.id.nameOfPlace);
            itemDescription = itemView.findViewById(R.id.description);
            typeOfPlace = itemView.findViewById(R.id.typeOfPlace);
            ratingBar = itemView.findViewById(R.id.ratingBar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    cursor.moveToFirst();//Παω τον Cursor

                    cursor.move(position);// Παω την θέση που θέλω είναι offset, δεν κάνει μεταπήδηση.

                    Intent intent = new Intent(context, ActivityForFragment.class);

                    context.startActivity(intent);

                }
            });
        }


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        cursor.moveToFirst();//Παω τον Cursor

        cursor.move(position);// Παω την θέση που θέλω είναι offset, δεν κάνει μεταπήδηση.

        System.out.println();

        holder.itemName.setText(cursor.getString(0));
        holder.typeOfPlace.setText(cursor.getString(1));
        holder.itemDescription.setText(cursor.getString(2));
        holder.ratingBar.setRating((float) cursor.getDouble(3));

        holder.itemImage.setImageBitmap(DBhandler.readImageFromInternalStorage(cursor.getString(4)));

    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

}
