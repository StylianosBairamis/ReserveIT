package com.example.androidergasia;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.os.LocaleList;
import android.util.Pair;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


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
    public static final String COLUMN_IMAGE ="image"; //Εδω αποθηκέυω path της αντίστοιχης εικόνας!
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_LATITUDE = "latitude";

    //Για το Table που κρατάει τις Κρατήσεις.
    public static final String DATABASE_TABLE_RESERVATIONS = "reservations";

    public static final String COLUMN_RESERVATION_DATE = "reservation_date";
    public static final String COLUMN_RESERVATION_TIME = "reservation_time";
    public static final String COLUMN_TRACK_PLACE = "id_of_place";
    public static final String COLUMN_NUMBER_OF_PEOPLE = "number_of_people";
    private static Context context ;

    //Για table που κρατάει τα favourite places
    private static final String COLUMN_FAVOURITE_PLACE_ID="id_of_place";
    private static final String DATABASE_TABLE_FAVORITE = "favorite";
    private final String DB_PATH = "/data/data/com.example.androidergasia/databases/";
    private final String DB_NAME = "myAPP.db";

    private  SQLiteDatabase db = null;


    public DBhandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, version);
        this.context = context;
        copyTable();
        Controller.setDBhandler(this);//Θέτω τον DBhandler στον Controller.
        NEW_VERSION = version ;
        db = getWritableDatabase();
    }

    /**
     * Μέθοδος που αντιγράφει την ΒΔ που υπάρχει στο φάκελο assets, αν δεν υπάρχει το αντίστοιχο αρχείο.
     * Το table places περιέχει πληροφορίες για τα μαγαζία του app.
     */
    private void copyTable()
    {
        try {

            String myPath = DB_PATH + DB_NAME; //Path που αποθηκέυεται η ΒΔ της εφαρμογής.

            File file = new File(myPath);

            if(file.exists())//Αν υπάρχει ήδη ο φάκελος επιστρέφω
            {
                return;
            }
            //Αλλίως γράφω στο παραπάνω path την ΒΔ που υπάρχει στο φάκελο assets.
            InputStream inputStream = context.getAssets().open("myAPP.db");
            File outputFile = context.getDatabasePath("myAPP.db");
            OutputStream outputStream = new FileOutputStream(outputFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0)
            {
                outputStream.write(buffer, 0, length); //Γράφω σταδιακά το αρχείο.
            }
            outputStream.flush();//Κλείσιμο πόρων
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

    }

    /**
     * Δημιουργώ ακόμα 2 tables, 1 για που θα αποθηκεύω τις κρατήσεις και 1
     * για την αποθήκευση των αγαπημένων μαγαζιών.
     * Καλείται η onUpgrade καθώς η ΒΔ προυπαρχεί καθώς περιέχει το table places
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if(oldVersion < newVersion)
        {
            String CREATE_FAVORITE_TABLE = " CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE_FAVORITE + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_FAVOURITE_PLACE_ID + " INTEGER NOT NULL, " +
                    " FOREIGN KEY(" + COLUMN_FAVOURITE_PLACE_ID + ") REFERENCES places(_id)" +
                    ")";

            String CREATE_RESERVATIONS_TABLE = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE_RESERVATIONS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_RESERVATION_DATE + " TEXT NOT NULL," +
                    COLUMN_RESERVATION_TIME + " TEXT NOT NULL," +
                COLUMN_NUMBER_OF_PEOPLE + " INTEGER NOT NULL," +
                COLUMN_TRACK_PLACE + " INTEGER," +
                " FOREIGN KEY(id_of_place) REFERENCES places(_id)" +
                ")";

            db.execSQL(CREATE_RESERVATIONS_TABLE);
            db.execSQL(CREATE_FAVORITE_TABLE);

            this.db = db;
        }
    }

    /**
     * Το PRIMARY KEY EXΕΙ ΑUTOINCREMENT DEN BAZW ID!
     * @param placeToAdd
     */

//    public void addPlace(Place placeToAdd)
//    {
//        ContentValues contentValues = new ContentValues();//KEY-VALUE ΔΟΜΗ
//
//        contentValues.put(COLUMN_NAME, placeToAdd.getName());
//        contentValues.put(COLUMN_TYPE_OF_PLACE,placeToAdd.getTypeOfPlace());
//        contentValues.put(COLUMN_DESCRIPTION,placeToAdd.getDescription());
//        contentValues.put(COLUMN_RATING,placeToAdd.getRating());
//
//        contentValues.put(COLUMN_CHAIRS_AVAILABLE, placeToAdd.getNumberOfChairs());
//        contentValues.put(COLUMN_LATITUDE, placeToAdd.getLatitude());
//        contentValues.put(COLUMN_LONGITUDE,placeToAdd.getLongitude());
//
//        String pathToFile = "/"+placeToAdd.getTypeOfPlace() + "/" + freeFromSpaces(placeToAdd.getName()) + ".jpg";
//
//        contentValues.put(COLUMN_IMAGE, pathToFile);//Περίεχει το Path για την εικόνα του Place
//
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        sqLiteDatabase.insert(DATABASE_TABLE_PLACES,null, contentValues);
//        sqLiteDatabase.close();
//    }

    /**
     * Query που επιστρέφει places με βάση το type_of_place
     * @param typeOfPlaceToSearch
     * @return
     */
    public Cursor findPlaces(String typeOfPlaceToSearch)
    {
        Resources resources = context.getResources();
        //Configurations της συσκευής που μπορεί να επηρεάσουν τα resources του app
        Configuration configuration = resources.getConfiguration();

        LocaleList localeList = configuration.getLocales(); //επιστρέφει λίστα με two-letter lowercase language codes

        String currentLanguage = localeList.get(0).getLanguage(); //γλώσσα που χρησιμοποιείται απο το κινητό.

        String description = currentLanguage.equals("el")?"description_gr" : COLUMN_DESCRIPTION; //Ποία απο τις δυο στήλες θα επιστραφεί.

        String query = "SELECT " + COLUMN_NAME + "," + COLUMN_TYPE_OF_PLACE + "," +
                description + "," + COLUMN_RATING + "," + COLUMN_LATITUDE + "," + COLUMN_LONGITUDE + ","
                + COLUMN_IMAGE +
                " FROM " + DATABASE_TABLE_PLACES +
                " WHERE " + COLUMN_TYPE_OF_PLACE + " = '" + typeOfPlaceToSearch + "' ";

        Cursor cursorForReturn = db.rawQuery(query, null);

        return cursorForReturn;
    }

//    /**
//     * Αφαιρώ τα κένα απο το String, δεν μπορώ να εχώ κενά στο fileSystem
//     * Επίσης το androidFileSystem δεν δέχεται UpperCase γράμματα, επιστρέφω lowerCase String
//     * @param toFree
//     * @return
//     */
//    private String freeFromSpaces(String toFree)
//    {
//        if(!toFree.contains(" "))
//        {
//            return toFree;
//        }
//
//        String[] arrayOfWords = toFree.split(" ");
//        String spaceFree = "";
//        for(int i = 0 ;i < arrayOfWords.length;i++)
//        {
//            spaceFree += arrayOfWords[i];
//        }
//        return spaceFree.toLowerCase();
//    }

    /**
     * @param nameForSearch Όνομα του Place που θα επιστρέψω τα coordinates
     * @return ενα αντικείμενο τύπου Pair με το latitude, longitude
     */

    public Pair<Float,Float> getCoordinates(String nameForSearch)
    {
        String query = "SELECT " + COLUMN_LATITUDE + "," + COLUMN_LONGITUDE +
            " FROM " + DATABASE_TABLE_PLACES +
            " WHERE " + COLUMN_NAME + " = '" + nameForSearch + "' ";

//        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst(); //Ενα μόνο αντικείμενο επιστρέφεται

        Pair<Float, Float> tempPair = new Pair<>(cursor.getFloat(0),cursor.getFloat(1));

        cursor.close(); //απελευθερωση πόρων

        return tempPair;
    }

    /**
     * @param nameForSearch Όνομα του Place που θα προσθέσω στο table favorite
     */

    public void addPlaceToFavorite(String nameForSearch)
    {
        int id = getPlaceID(nameForSearch); // Παίρνω το id απο την μέθοδο getPlaceID

        ContentValues contentValues = new ContentValues();// key,value δομή

        contentValues.put(COLUMN_FAVOURITE_PLACE_ID, id);

        db.insert(DATABASE_TABLE_FAVORITE,null, contentValues);//Προσθήκη στον πίνακα.
    }

    /**
     * Μέθοδος για την αφαίρεση place απο το table favorite, ενημερώνω τον adapter αν βρίσκομαι στο
     * FavoritesActivity.
     * @param nameForDelete όνομα του place προς αφαίρεση.
     */

    public void removePlaceFromFavorite(String nameForDelete)
    {
        int idOfPlace = getPlaceID(nameForDelete); // Παίρνω το id απο την μέθοδο getPlaceID

        String condition = COLUMN_FAVOURITE_PLACE_ID + " = " + "?";

        String[] conditionArgs = {idOfPlace+""};

        db.delete(DATABASE_TABLE_FAVORITE,condition,conditionArgs);

        if(Controller.isTypeOfAdapter())//Αν είναι το recyclerAdapter του FavoritesActivity.
        {
            Controller.getAdapter().removeItem(nameForDelete);
            //Ενημέρωση του adapter ώστε να αφαιρέσει το αντίστοιχο Place απο το recyclerViewer.
        }

    }

    /**
     * Μέθοδος που επιστρέφει όλα τα places που έχει επιλέξει ο χρήστης ως favorite
     * Πραγματοποιείται inner join μεταξύ του πίνακα favorite table και του places table.
     * Τable favorite έχει ως foreign key το primary key των places.
     * @return
     */

    public Cursor getFavoritePlaces()
    {
        Resources resources = context.getResources();
        //Configurations της συσκευής που μπορεί να επηρεάσουν τα resources του app
        Configuration configuration = resources.getConfiguration();

        LocaleList localeList = configuration.getLocales(); //επιστρέφει λίστα με two-letter lowercase language codes

        String currentLanguage = localeList.get(0).getLanguage(); //γλώσσα που χρησιμοποιείται απο το κινητό.

        String description = currentLanguage.equals("el")?"description_gr" : COLUMN_DESCRIPTION; //Ποία απο τις δυο στήλες θα επιστραφεί.

        String query ="SELECT "+ COLUMN_NAME + "," +COLUMN_TYPE_OF_PLACE + ","+
        description + "," + COLUMN_RATING +  "," + COLUMN_LATITUDE + "," + COLUMN_LONGITUDE + ","
                + COLUMN_IMAGE +
                " FROM " + DATABASE_TABLE_PLACES + " INNER JOIN " + DATABASE_TABLE_FAVORITE +
                " ON "+ DATABASE_TABLE_PLACES+"._id" + "=" + DATABASE_TABLE_FAVORITE + ".id_of_place";

        return db.rawQuery(query,null);
    }

    /**
     * Μέθοδος που ελέγχει αν place υπάρχει ως record στον πίνακα των favorite.
     * @param nameOfPlace όνομα του place
     * @return 0 αν δεν βρίσκεται αλλιώς επιστρέφει 1
     */
    public int isInFavoriteTable(String nameOfPlace)
    {

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

        int toReturnCount = cursor.getCount();

        cursor.close();

        return toReturnCount;
    }

    /**
     * Μέθοδος για προσθήκη Reservation στο table reservations
     * @param reservation προς εισαγωγή
     */

    public void addReservation(Reservation reservation)
    {
        ContentValues values = new ContentValues(); //key,value δομή.

        values.put(COLUMN_TRACK_PLACE, reservation.getPlaceId());
        values.put(COLUMN_RESERVATION_DATE, reservation.getDate());
        values.put(COLUMN_RESERVATION_TIME, reservation.getDateTime());
        values.put(COLUMN_NUMBER_OF_PEOPLE, reservation.getNumberOfPeople());

        db.insert(DATABASE_TABLE_RESERVATIONS, null, values);
    }

    /**
     * Μέθοδος για την αφαίρεση reservation απο το table
     * @param idToDelete id του reservation
     */

    public  void removeReservation(int idToDelete)
    {
        String condition = COLUMN_ID + " = " + "?";

        String[] conditionArgs = {idToDelete+""};

        db.delete(DATABASE_TABLE_RESERVATIONS, condition, conditionArgs) ;
    }

    /**
     * Μεθοδος που επιστρέφει όλα τα Reservations που υπάρχουν στο table
     * @return ArrayList με Reservations.
     */
    public ArrayList<Reservation> findReservations()
    {
//        SQLiteDatabase db = this.getReadableDatabase();

        String query ="SELECT "+ "places."+COLUMN_NAME + "," +"reservations." + COLUMN_RESERVATION_TIME  + "," +
                "reservations." + COLUMN_RESERVATION_DATE + "," + "reservations." + COLUMN_NUMBER_OF_PEOPLE
                + "," + "reservations." + COLUMN_ID +
                " FROM " + DATABASE_TABLE_PLACES + " INNER JOIN " + DATABASE_TABLE_RESERVATIONS +
                " ON "+ DATABASE_TABLE_PLACES+"._id" + "=" + DATABASE_TABLE_RESERVATIONS + ".id_of_place";

        return fromCursorToArrayList(db.rawQuery(query, null));
    }

    /**
     * @param cursor απο το query της μέθοδου findReservations.
     * @return ArrayList με Reservations.
     */
    private ArrayList<Reservation> fromCursorToArrayList(Cursor cursor)
    {

        ArrayList<Reservation> toReturn = new ArrayList<>();
        Reservation tempReservation;

        for(int i = 0 ; i < cursor.getCount(); i++)
        {
            cursor.moveToFirst();
            cursor.move(i);

            String nameOfPlace = cursor.getString(0);
            String time = cursor.getString(1);
            String date = cursor.getString(2);
            int numberOfPeople = cursor.getInt(3);
            int reservationID = cursor.getInt(4);

            //Δημιουργεία reservation με τα στοιχεία του query.

            tempReservation = new Reservation(date, time, nameOfPlace, numberOfPeople, reservationID);

            toReturn.add((tempReservation));//προσθήκη στο arrayList.
        }
        return toReturn;
    }

    /**
     * Μέθοδος που δέχεται ώς όρισμα το όνομα ενος Place, επιστρέφει το id του
     */
    public int getPlaceID(String nameForSearch)
    {
        String query = "SELECT " + COLUMN_ID +
                " FROM " + DATABASE_TABLE_PLACES +
                " WHERE " + COLUMN_NAME + " = '" + nameForSearch + "' ";

        Cursor cursor = db.rawQuery(query,null); //Εκτέλεση του query.

        cursor.moveToFirst();

        int toReturn = cursor.getInt(0);

        return toReturn;
    }

}
