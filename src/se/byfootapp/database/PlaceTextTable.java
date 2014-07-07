package se.byfootapp.database;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import se.byfootapp.model.PlaceText;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PlaceTextTable {

  //Database table name
    private static final String TABLE_PLACE_TEXT = "placeText";
    
    //Database table columns    
    private static final String KEY_ID = "id";
    private static final String TEXT = "text";
    private static final String PLACE_ID = "placeID";
    private static final String DATE  = "date";
    
    //Database create table SQL statement
    private static final String CREATE_TABLE_IMAGE_TEXT = "CREATE TABLE "
            + TABLE_PLACE_TEXT + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + TEXT + " TEXT,"
            + PLACE_ID + " INTEGER,"
            + DATE + " DATETIME"
            + ")";
    
    public static void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_IMAGE_TEXT);
    }
    
    public static void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACE_TEXT);
        onCreate(db);
    }
    
    public static long createPlaceText(PlaceText placeText, SQLiteDatabase db){
        ContentValues values = buildContentValues(placeText);
        return db.insert(TABLE_PLACE_TEXT, null, values);
    }
    
    public static PlaceText getPlaceText(Integer placeTextId, SQLiteDatabase db){
        String selectQuery = "SELECT * FROM " + TABLE_PLACE_TEXT + " WHERE "
                + KEY_ID + " = " + placeTextId;
        Cursor cursor = db.rawQuery(selectQuery, null);
        boolean notEmpty = false;
        
        if(cursor != null)
            notEmpty = cursor.moveToFirst();
        
        PlaceText placeText = null;
        if(notEmpty){
            placeText = buildPlaceText(cursor);
        }
        return placeText;
    }
    
    public static List<PlaceText> getPlaceTexts(SQLiteDatabase db){
        String selectQuery = "SELECT * FROM " + TABLE_PLACE_TEXT;
        
        Cursor cursor = db.rawQuery(selectQuery, null);
        return buildPlaceTexts(cursor);
    }
    
    public static List<PlaceText> getPlaceTextForPlace(Integer placeId, SQLiteDatabase db){
        String selectQuery = "SELECT * FROM " + TABLE_PLACE_TEXT
                + " WHERE " + PLACE_ID + " == " + placeId;
        Cursor cursor = db.rawQuery(selectQuery, null);
        return buildPlaceTexts(cursor);
    }
    
    public static void deletePlaceText(PlaceText placeText, SQLiteDatabase db){
        db.delete(TABLE_PLACE_TEXT, KEY_ID + " = ?", new String[]{ String.valueOf(placeText.getId()) });
        db.close();
    }
    
    private static List<PlaceText> buildPlaceTexts(Cursor cursor){
        List<PlaceText> placeTexts = new ArrayList<PlaceText>();
        boolean notEmpty = false;
        if(cursor != null)
            notEmpty = cursor.moveToFirst();
        
        if(notEmpty){
            do{
                placeTexts.add(buildPlaceText(cursor));
            }while(cursor.moveToNext());
        }
        return placeTexts;
    }
    
    private static PlaceText buildPlaceText(Cursor cursor){
       PlaceText placeText = new PlaceText();
       placeText.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
       placeText.setText(cursor.getString(cursor.getColumnIndex(TEXT)));
       placeText.setPlaceId(cursor.getInt(cursor.getColumnIndex(PLACE_ID)));
       
       Calendar date = Calendar.getInstance(TimeZone.getDefault());
       date.setTime(new Date(cursor.getLong(cursor.getColumnIndex(DATE))));
       placeText.setDate(date);
       
       return placeText;
    }
    
    private static ContentValues buildContentValues(PlaceText placeText){
        ContentValues values = new ContentValues();
        values.put(TEXT, placeText.getText());
        values.put(PLACE_ID, placeText.getPlaceId());
        values.put(DATE, placeText.getDate().getTimeInMillis());
        return values;
    }
}
