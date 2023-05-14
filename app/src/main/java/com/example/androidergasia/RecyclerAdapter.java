package com.example.androidergasia;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
{
    DBhandler DBhandler = null;
    static Cursor cursor = null;

    MatrixCursor matrixCursor = null;
    private static Context context = null;
    private static double currentLongitude = 40.633052; // Συντεταγμένες απο Ημιώροφο βιολογίας
    private static double currentLatitude = 22.957192;

    public RecyclerAdapter(DBhandler DBhandler,Context context,String typeOfPlaceToSearch)
    {
        this.DBhandler = DBhandler;
        cursor = this.DBhandler.findPlaces(typeOfPlaceToSearch);
        this.context = context;

        ArrayList<Integer> listOfIndexes = makeRequest();

        changeIndexesOfCursor(listOfIndexes);

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
        holder.ratingBar.setRating(cursor.getFloat(3));

        holder.itemImage.setImageBitmap(DBhandler.readImageFromInternalStorage(cursor.getString(4)));

    }

    @Override
    public int getItemCount() {
        return matrixCursor.getCount();
    }

    private ArrayList<Integer> makeRequest()
    {
        String destinations = "", origins = "";

        origins = currentLongitude + "%2C" + currentLatitude;

        for(int i = 0 ; i < cursor.getCount();i++)
        {
            destinations += cursor.getDouble(4) + "%2C"; //%2C είναι το ',' στα URL Εδω προσθέτω longitude
            destinations += cursor.getDouble(5); // εδώ προσθέτω latitude

            if(i != cursor.getCount() - 1)//Στο τελευταίο value δεν βάζω τον ειδικό χαρακτήρα.
            {
                destinations += "%7C"; // εδω προσθέτω ειδικό χαρακτήρα που συμβολίζει οτι έχω πολλά destination values
            }
        }


        OkHttpClient client = new OkHttpClient().newBuilder().build();

        MediaType mediaType = MediaType.parse("text/plain");

        RequestBody body = RequestBody.create(mediaType, "");

        Request request = new Request.Builder() // Ως transport driving
                .url("https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + origins + "&destinations=" + destinations + "+&key=YOUR_API_KEY")
                .method("GET", body)
                .build();
        try
        {
            Response response = client.newCall(request).execute();
            return parseRequest(response);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return  null;
    }

    private ArrayList<Integer> parseRequest(Response response)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(response.body().string());

            JSONArray jsonArray = jsonObject.getJSONArray("rows");

            ArrayList<Pair<Double, Double>> listOfPairs = new ArrayList<>();

            ArrayList<Integer> listOfIndexes = new ArrayList<>();

            Pair<Double, Double> tempPair = null;

            for (int i=0; i < jsonArray.length(); i++)
            {
                JSONObject oneObject = jsonArray.getJSONObject(i);

                JSONObject distanceObject = oneObject.getJSONObject("distance"); //Παίρνω τις αντίστοιχες τιμές
                String distanceText = distanceObject.getString("text");

                JSONObject durationObject = oneObject.getJSONObject("duration");
                String durationText = distanceObject.getString("text");

                tempPair = new Pair<>(Double.parseDouble(distanceText),Double.parseDouble(durationText)); // Δημιουργώ pair, αφού πρώτα κάνω parse to string σε double

                listOfPairs.add(tempPair);
                listOfIndexes.add(i);
            }

            Collections.sort(listOfIndexes, new Comparator<Integer>() { // Κάνω sort την λίστα με τα ιndexes, η σύγκριση είναι με βάση το distance
                @Override
                public int compare(Integer i1, Integer i2)
                {
                    return Double.compare(listOfPairs.get(i1).first,listOfPairs.get(i2).first); //Σύγκριση των distances
                }
            });
            return listOfIndexes;
        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private void  changeIndexesOfCursor(ArrayList<Integer> listOfIndexes)
    {
        matrixCursor = new MatrixCursor(cursor.getColumnNames());

        for(int i = 0 ;i < cursor.getCount();i++)
        {
            cursor.moveToFirst();

            cursor.move(listOfIndexes.get(i));

            Object[] row = new Object[cursor.getColumnCount()];

            row[0] = cursor.getString(0);
            row[1] = cursor.getString(1);
            row[2] = cursor.getString(2);
            row[3] = cursor.getFloat(3);
            row[4] = cursor.getString(4);

            matrixCursor.addRow(row);
        }
    }

}
