package com.example.androidergasia;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
{
    DBhandler DBhandler = null;
    Cursor cursor = null;

    public RecyclerAdapter(DBhandler DBhandler)
    {
        this.DBhandler = DBhandler;
        cursor = this.DBhandler.findPlaces("Cafe");

        System.out.println(cursor.getCount());
        System.out.println("hello");
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName;
        TextView itemDescription;
        TextView typeOfPlace;
        TextView rating;
        public ViewHolder(View itemView)
        {
            super(itemView);
            itemImage = itemView.findViewById(R.id.imageView);
            itemName = itemView.findViewById(R.id.textView);
            itemDescription = itemView.findViewById(R.id.textView2);
            typeOfPlace = itemView.findViewById(R.id.textView3);
            rating = itemView.findViewById(R.id.textView4);
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
        holder.rating.setText(cursor.getDouble(3) + "");

        holder.itemImage.setImageBitmap(DBhandler.readImageFromInternalStorage(cursor.getString(4)));


    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

}
