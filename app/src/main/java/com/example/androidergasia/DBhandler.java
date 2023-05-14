package com.example.androidergasia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DBhandler extends SQLiteOpenHelper
{
    public static int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "myAPP.db";
    public static final String DATABASE_TABLE_PLACES = "places";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TYPE_OF_PLACE = "type_of_place";
    public static final String COLUMN_NAME = "placeName";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_CHAIRS_AVAILABLE = "num_of_chairs";
    public static final String COLUMN_IMAGE ="image"; //Εδω αποθηκέυω path της αντίστοιχης εικόνας!

    public static final String COLUMN_LONGITUDE = "longitude";

    public static final String COLUMN_LATITUDE = "latitude";

    //Για το Table που κρατάει τις Κρατήσεις.
    public static final String DATABASE_TABLE_RESERVATIONS = "reservations";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String COLUMN_TRACK_PLACE = "id_of_place";

    public static final String COLUMN_NUMBER_OF_PEOPLE = "number_of_people";
    private static Context context ;

    public DBhandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_PLACE_TABLE = "CREATE TABLE " + DATABASE_TABLE_PLACES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT NOT NULL," +
                COLUMN_TYPE_OF_PLACE + " TEXT,"+
                COLUMN_DESCRIPTION  + " TEXT NOT NULL," +
                COLUMN_RATING + " REAL DEFAULT '0.0'," +
                COLUMN_CHAIRS_AVAILABLE + " INTEGER," +
                COLUMN_LONGITUDE + " FLOAT NOT NULL," +
                COLUMN_LATITUDE + " FLOAT NOT NULL," +
                COLUMN_IMAGE+ " TEXT," +
                "CHECK(type_of_place IN ('Restaurant', 'Bar', 'Cafe'))" + ")";
                //" UNIQUE(placeName)" + ")";

        String CREATE_RESERVATIONS_TABLE = "CREATE TABLE " + DATABASE_TABLE_RESERVATIONS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TIMESTAMP + " TEXT NOT NULL," +
                COLUMN_NUMBER_OF_PEOPLE + " INTEGER NOT NULL," +
                COLUMN_TRACK_PLACE + " INTEGER," +
                " FOREIGN KEY(id_of_place) REFERENCES places(_id)" +
                ")";

        db.execSQL(CREATE_PLACE_TABLE);
        db.execSQL(CREATE_RESERVATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_PLACES);
        onCreate(db);
    }

    /**
     * Το PRIMARY KEY EXΕΙ ΑUTOINCREMENT DEN BAZW ID!
     * @param placeToAdd
     */

    public void addPlace(Place placeToAdd)
    {
        ContentValues contentValues = new ContentValues();//KEY-VALUE ΔΟΜΗ
        String pathToFile = freeFromSpaces(placeToAdd.getName()); //Αφαίρεση κενών.

        contentValues.put(COLUMN_NAME, placeToAdd.getName());
        contentValues.put(COLUMN_TYPE_OF_PLACE,placeToAdd.getTypeOfPlace());
        contentValues.put(COLUMN_DESCRIPTION,placeToAdd.getDescription());
        contentValues.put(COLUMN_RATING,placeToAdd.getRating());

        contentValues.put(COLUMN_CHAIRS_AVAILABLE, placeToAdd.getNumberOfChairs());
        contentValues.put(COLUMN_LONGITUDE,placeToAdd.getLongitude());
        contentValues.put(COLUMN_LATITUDE, placeToAdd.getLatitude());


        contentValues.put(COLUMN_IMAGE, pathToFile + ".jpg" );//Περίεχει το Path για την εικόνα του Place

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(DATABASE_TABLE_PLACES,null, contentValues);
        sqLiteDatabase.close();
    }

    /**
     * Query που επιστρέφει places με βάση το type_of_place
     * @param typeOfPlaceToSearch
     * @return
     */
    public Cursor findPlaces(String typeOfPlaceToSearch)
    {
        String query = "SELECT "+ COLUMN_NAME + "," +COLUMN_TYPE_OF_PLACE + ","+
                COLUMN_DESCRIPTION + "," + COLUMN_RATING + "," + COLUMN_IMAGE +
                " FROM " + DATABASE_TABLE_PLACES +
                " WHERE " + COLUMN_TYPE_OF_PLACE + " = '" + typeOfPlaceToSearch + "' ";
        String[] args =  {COLUMN_NAME, COLUMN_TYPE_OF_PLACE, COLUMN_DESCRIPTION, COLUMN_RATING, COLUMN_IMAGE};//Arguments για το selection

        SQLiteDatabase db = getReadableDatabase();

        return db.rawQuery(query, null);
    }

    /**
     * Αφαιρώ τα κένα απο το String, δεν μπορώ να εχώ κενά στο fileSystem
     * Επίσης το androidFileSystem δεν δέχεται UpperCase γράμματα, επιστρέφω lowerCase String
     * @param toFree
     * @return
     */
    private String freeFromSpaces(String toFree)
    {
        if(!toFree.contains(" "))
        {
            return toFree;
        }

        String[] arrayOfWords = toFree.split(" ");
        String spaceFree = "";
        for(int i = 0 ;i < arrayOfWords.length;i++)
        {
            spaceFree+=arrayOfWords[i];
        }
        return spaceFree.toLowerCase();
    }

    /**
     * Μέθοδος για εγγραφη εικόνων /data/data/<app_file>/files
     * Δεν την χρησιμοποιώ τελικά τα έκανα drug & drop στο files
     * @param bitmap
     * @param name
     */
    public static void writeImageToInternalStorage(Bitmap bitmap,String name)
    {
        File directory = context.getFilesDir();//Παίρνω reference για το internal storage του app
        File file = new File(directory, "myImage.jpg"); // Δημιουργεία υποφακέλου στον φάκελο files

        try (FileOutputStream fos = new FileOutputStream(file))
        {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @param pathForFile Ονόμα του place, το χρησιμοποιώ για βρω το αντίστοιχο αρχείο στο Internal Storage
     * @return
     */
    public static Bitmap readImageFromInternalStorage(String pathForFile)
    {
        File directory = context.getFilesDir();//Παίρνω reference για το internal storage του app
        File file = new File(directory, pathForFile.toLowerCase()); // Δημιουργεία υποφακέλου στον φάκελο files
        Bitmap bitmap = null;
        try(FileInputStream fileInputStream = new FileInputStream(file))
        {
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return bitmap;
    }

}
