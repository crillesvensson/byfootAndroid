package se.byfootapp.database;

import java.util.ArrayList;
import java.util.List;

import se.byfootapp.model.Place;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PlaceTable {
  //Database table name
    private static final String TABLE_PLACE = "place";
    
    //Database table columns    
    private static final String KEY_ID = "id";
    private static final String NAME = "name";
    private static final String TYPE = "type";
    private static final String LAT  = "lat";
    private static final String LON  = "lon";
    private static final String ADDRESS = "address";
    
    //Database create table SQL statement
    private static final String CREATE_TABLE_PLACE = "CREATE TABLE "
            + TABLE_PLACE + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + NAME + " TEXT,"
            + TYPE + " TEXT,"
            + LAT + " REAL,"
            + LON + " REAL,"
            + ADDRESS + " TEXT"
            + ")";
    
    public static void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_PLACE);
    }
    
    public static void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACE);
        onCreate(db);
    }
    
    public static long createPlace(Place place, SQLiteDatabase db){
        ContentValues values = buildContentValues(place);
        return db.insert(TABLE_PLACE, null, values);
    }
    
    public static Place getPlace(Integer placeId, SQLiteDatabase db){
        String selectQuery = "SELECT * FROM " + TABLE_PLACE + " WHERE "
                + KEY_ID + " = " + placeId;
        
        Cursor cursor = db.rawQuery(selectQuery, null);
        boolean isNotEmpty = false;
        
        if(cursor != null)
            isNotEmpty = cursor.moveToFirst();
        
        Place place = null;
        if(isNotEmpty){
            place = buildPlace(cursor);
        }
        return place;
    }
    
    public static List<Place> getPlaces(SQLiteDatabase db){
        String selectQuery = "SELECT * FROM " + TABLE_PLACE;
        
        Cursor cursor = db.rawQuery(selectQuery, null);
        return buildPlaces(cursor);
    }
    
    private static List<Place> buildPlaces(Cursor cursor){
        List<Place> places = new ArrayList<Place>();
        boolean notEmpty = false;
        if(cursor != null)
            notEmpty = cursor.moveToFirst();
        
        if(notEmpty){
            do{
                places.add(buildPlace(cursor));
            }while(cursor.moveToNext());
        }
        return places;
    }
    
    public static void deletePlace(Place place, SQLiteDatabase db){
        db.delete(TABLE_PLACE, KEY_ID + " = ?", new String[]{ String.valueOf(place.getId()) });
        db.close();
    }
    
    private static Place buildPlace(Cursor cursor){
        Place place = new Place();
        place.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        place.setName(cursor.getString(cursor.getColumnIndex(NAME)));
        place.setType(cursor.getString(cursor.getColumnIndex(TYPE)));
        place.setLat(cursor.getDouble(cursor.getColumnIndex(LAT)));
        place.setLon(cursor.getDouble(cursor.getColumnIndex(LON)));
        place.setAddress(cursor.getString(cursor.getColumnIndex(ADDRESS)));
        return place;
    }
    
    private static ContentValues buildContentValues(Place place){
        ContentValues values = new ContentValues();
        values.put(NAME, place.getName());
        values.put(TYPE, place.getType());
        values.put(LAT, place.getLat());
        values.put(LON, place.getLon());
        values.put(ADDRESS, place.getAddress());
        return values;
    }

}
