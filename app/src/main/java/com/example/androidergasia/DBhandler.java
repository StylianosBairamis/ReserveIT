package com.example.androidergasia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Pair;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DBhandler extends SQLiteOpenHelper
{
    public static int DATABASE_VERSION = 1;
    private static int NEW_VERSION;
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

    public static final String COLUMN_RESERVATION_ID = "reservation_id";
    public static final String COLUMN_DATE = "reservation_date";
    public static final String COLUMN_RESERVATION_TIME = "reservation_time";
    public static final String COLUMN_TRACK_PLACE = "id_of_place";
    public static final String COLUMN_NUMBER_OF_PEOPLE = "number_of_people";
    private static Context context ;

    //Για table που κρατάει τα favourite places
    private static final String COLUMN_FAVOURITE_PLACE_ID="id_of_place";
    private static final String DATABASE_TABLE_FAVORITE = "favorite";

    private String DB_PATH = "/data/data/com.example.androidergasia/databases/";

    private String DB_NAME = "myAPP.db";


    public DBhandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, 2);
        this.context = context;
        copyTable();
        Controller controller = new Controller(this);
        NEW_VERSION = 2 ;
    }

    private void copyTable()
    {
        try {

            String myPath = DB_PATH + DB_NAME;
            File file = new File(myPath);
            if(file.exists())
            {
                return;
            }
            InputStream inputStream = context.getAssets().open("myAPP.db");
            File outputFile = context.getDatabasePath("myAPP.db");
            OutputStream outputStream = new FileOutputStream(outputFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
//        String CREATE_PLACE_TABLE = "CREATE TABLE " + DATABASE_TABLE_PLACES + "("
//                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//                + COLUMN_NAME + " TEXT NOT NULL," +
//                COLUMN_TYPE_OF_PLACE + " TEXT,"+
//                COLUMN_DESCRIPTION  + " TEXT NOT NULL," +
//                COLUMN_RATING + " REAL DEFAULT '0.0'," +
//                COLUMN_CHAIRS_AVAILABLE + " INTEGER," +
//                COLUMN_LATITUDE + " FLOAT NOT NULL," +
//                COLUMN_LONGITUDE + " FLOAT NOT NULL," +
//                COLUMN_IMAGE+ " TEXT," +
//                "CHECK(type_of_place IN ('Restaurant', 'Bar', 'Cafe'))" + ")";
//                //" UNIQUE(placeName)" + ")";

//        String CREATE_RESERVATIONS_TABLE = "CREATE TABLE " + DATABASE_TABLE_RESERVATIONS + "("
//                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                COLUMN_TIMESTAMP + " TEXT NOT NULL," +
//                COLUMN_NUMBER_OF_PEOPLE + " INTEGER NOT NULL," +
//                COLUMN_TRACK_PLACE + " INTEGER," +
//                " FOREIGN KEY(id_of_place) REFERENCES places(_id)" +
//                ")";


//        String CREATE_FAVORITE_TABLE = " CREATE TABLE " + DATABASE_TABLE_FAVORITE + "("
//                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                COLUMN_FAVOURITE_PLACE_ID + " INTEGER NOT NULL, " +
//                " FOREIGN KEY(" + COLUMN_FAVOURITE_PLACE_ID + ") REFERENCES places(_id)" +
//                ")";

//
//
//        //db.execSQL(CREATE_PLACE_TABLE);
//        //db.execSQL(CREATE_RESERVATIONS_TABLE); // Δημιουργεία του reservation table
//
        //.execSQL(CREATE_FAVORITE_TABLE); // Δημιουργεία του favourite table
//
//        Controller controller = new Controller(this);

    }

    /**
     * Βάση έχει δημιουργηθεί ήδη καλείται η onUpdate για να βάλω τα table reservations, favorite.
     * @param db
     * @param oldVersion
     * @param newVersion
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if(oldVersion < NEW_VERSION)
        {
            String CREATE_FAVORITE_TABLE = " CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE_FAVORITE + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_FAVOURITE_PLACE_ID + " INTEGER NOT NULL, " +
                    " FOREIGN KEY(" + COLUMN_FAVOURITE_PLACE_ID + ") REFERENCES places(_id)" +
                    ")";

            String CREATE_RESERVATIONS_TABLE = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE_RESERVATIONS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE + " TEXT NOT NULL," +
                    COLUMN_RESERVATION_TIME + " TEXT NOT NULL," +
                COLUMN_NUMBER_OF_PEOPLE + " INTEGER NOT NULL," +
                COLUMN_TRACK_PLACE + " INTEGER," +
                " FOREIGN KEY(id_of_place) REFERENCES places(_id)" +
                ")";

            db.execSQL(CREATE_RESERVATIONS_TABLE);
            db.execSQL(CREATE_FAVORITE_TABLE);
        }

//        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_PLACES);
//        onCreate(db);
    }

    /**
     * Το PRIMARY KEY EXΕΙ ΑUTOINCREMENT DEN BAZW ID!
     * @param placeToAdd
     */

    public void addPlace(Place placeToAdd)
    {
        ContentValues contentValues = new ContentValues();//KEY-VALUE ΔΟΜΗ

        contentValues.put(COLUMN_NAME, placeToAdd.getName());
        contentValues.put(COLUMN_TYPE_OF_PLACE,placeToAdd.getTypeOfPlace());
        contentValues.put(COLUMN_DESCRIPTION,placeToAdd.getDescription());
        contentValues.put(COLUMN_RATING,placeToAdd.getRating());

        contentValues.put(COLUMN_CHAIRS_AVAILABLE, placeToAdd.getNumberOfChairs());
        contentValues.put(COLUMN_LATITUDE, placeToAdd.getLatitude());
        contentValues.put(COLUMN_LONGITUDE,placeToAdd.getLongitude());

        String pathToFile = "/"+placeToAdd.getTypeOfPlace() + "/" + freeFromSpaces(placeToAdd.getName()) + ".jpg";

        contentValues.put(COLUMN_IMAGE, pathToFile);//Περίεχει το Path για την εικόνα του Place

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
                COLUMN_DESCRIPTION + "," + COLUMN_RATING + ","+COLUMN_CHAIRS_AVAILABLE + "," + COLUMN_LATITUDE + "," + COLUMN_LONGITUDE + ","
                + COLUMN_IMAGE +
                " FROM " + DATABASE_TABLE_PLACES +
                " WHERE " + COLUMN_TYPE_OF_PLACE + " = '" + typeOfPlaceToSearch + "' ";

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursorForReturn = db.rawQuery(query, null);

        return cursorForReturn;
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

    public Pair<Float,Float> getCoordinates(String nameForSearch)
    {
        String query = "SELECT " + COLUMN_LATITUDE + "," + COLUMN_LONGITUDE +
            " FROM " + DATABASE_TABLE_PLACES +
            " WHERE " + COLUMN_NAME + " = '" + nameForSearch + "' ";

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        Pair<Float, Float> tempPair = new Pair<>(cursor.getFloat(0),cursor.getFloat(1));

        return tempPair;
    }

    public void addPlaceToFavorite(String nameForSearch)
    {
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT " + COLUMN_ID +
                " FROM "  + DATABASE_TABLE_PLACES +
                " WHERE " + COLUMN_NAME + " = '" + nameForSearch + "' ";

        Cursor cursor = db.rawQuery(query,null);

        db = getWritableDatabase();

        cursor.moveToFirst();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FAVOURITE_PLACE_ID, cursor.getInt(0));

        db.insert(DATABASE_TABLE_FAVORITE,null, contentValues);

        db.close();

    }

    public void removePlaceFromFavorite(String nameForDelete)
    {
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT " + COLUMN_ID +
                " FROM "  + DATABASE_TABLE_PLACES +
                " WHERE " + COLUMN_NAME + " = '" + nameForDelete + "' ";

        Cursor cursor = db.rawQuery(query,null);

        db = getWritableDatabase();

        cursor.moveToFirst();

        int idOfPlace = cursor.getInt(0);

        String condition = COLUMN_FAVOURITE_PLACE_ID + " = " + "?";

        String[] conditionArgs = {idOfPlace+""}; // Replace "123" with the value of the ID to delete

        db.delete(DATABASE_TABLE_FAVORITE,condition,conditionArgs);

        db.close();

    }

    public Cursor getFavoritePlaces()
    {
        String query ="SELECT "+ COLUMN_NAME + "," +COLUMN_TYPE_OF_PLACE + ","+
        COLUMN_DESCRIPTION + "," + COLUMN_RATING + ","+COLUMN_CHAIRS_AVAILABLE + "," + COLUMN_LATITUDE + "," + COLUMN_LONGITUDE + ","
                + COLUMN_IMAGE +
                " FROM " + DATABASE_TABLE_PLACES + " INNER JOIN " + DATABASE_TABLE_FAVORITE +
                " ON "+ DATABASE_TABLE_PLACES+"._id" + "=" + DATABASE_TABLE_FAVORITE + ".id_of_place";

        SQLiteDatabase db = getReadableDatabase();

        return db.rawQuery(query,null);
    }

    public int isInFavoriteTable (String nameOfPlace)
    {
        DBhandler handler = Controller.getDBhandler();

        SQLiteDatabase db = handler.getReadableDatabase();

        String query = "SELECT " + "_id" +
                " FROM " + "places " +
                " WHERE " + "placeName" + " = '" + nameOfPlace + "' ";

        Cursor cursor = db.rawQuery(query,null);

        cursor.moveToFirst();

        int id = cursor.getInt(0);

        query = "SELECT " + " * " +
                " FROM " + " favorite " +
                " WHERE " + "id_of_place = " + id ;

        cursor = db.rawQuery(query, null);

        return cursor.getCount();
    }

    public void addReservation(Reservation reservation)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TRACK_PLACE, reservation.getPlaceId());
        values.put(COLUMN_DATE, reservation.getDate());
        values.put(COLUMN_RESERVATION_TIME, reservation.getDateTime());
        values.put(COLUMN_NUMBER_OF_PEOPLE, reservation.getNumberOfPeople());

        db.insert(DATABASE_TABLE_RESERVATIONS, null, values);
        db.close();
    }

}
