package se.byfootapp.database;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import se.byfootapp.model.PlaceImage;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PlaceImageTable {
    
  //Database table name
    private static final String TABLE_PLACE_IMAGE = "placeImage";
    
    //Database table columns    
    private static final String KEY_ID = "id";
    private static final String IMAGE = "image";
    private static final String PLACE_ID = "placeID";
    private static final String DATE  = "date";
    
    //Database create table SQL statement
    private static final String CREATE_TABLE_PLACE_IMAGE = "CREATE TABLE "
            + TABLE_PLACE_IMAGE + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + IMAGE + " BLOB,"
            + PLACE_ID + " INTEGER,"
            + DATE + " DATETIME"
            + ")";
    
    public static void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_PLACE_IMAGE);
    }
    
    public static void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACE_IMAGE);
        onCreate(db);
    }
    
    public static long createPlaceImage(PlaceImage placeImage, SQLiteDatabase db){
        ContentValues values = buildContentValues(placeImage);
        return db.insert(TABLE_PLACE_IMAGE, null, values);
    }
    
    public static PlaceImage getPlaceImage(Integer placeImageId, SQLiteDatabase db){
        String selectQuery = "SELECT * FROM " + TABLE_PLACE_IMAGE + " WHERE "
                + KEY_ID + " = " + placeImageId;
        
        Cursor cursor = db.rawQuery(selectQuery, null);
        boolean notEmpty = false;
        
        if(cursor != null)
            notEmpty = cursor.moveToFirst();
        
        PlaceImage placeImage = null;
        if(notEmpty){
            placeImage = buildPlaceImage(cursor);
        }
        return placeImage;
    }
    
    public static List<PlaceImage> getPlaceImages(SQLiteDatabase db){
        String selectQuery = "SELECT * FROM " + TABLE_PLACE_IMAGE;
        
        Cursor cursor = db.rawQuery(selectQuery, null);
        return buildPlaceImages(cursor);
    }
    
    public static List<PlaceImage> getPlaceImagesForPlace(Integer placeId, SQLiteDatabase db){
        String selectQuery = "SELECT * FROM " + TABLE_PLACE_IMAGE
                            + " WHERE " + PLACE_ID + " == " + placeId;
        
        Cursor cursor = db.rawQuery(selectQuery, null);
        return buildPlaceImages(cursor);
    }
    
    public static void deletePlaceImage(PlaceImage placeImage, SQLiteDatabase db){
        db.delete(TABLE_PLACE_IMAGE, KEY_ID + " = ?", new String[]{ String.valueOf(placeImage.getPlaceImageId()) });
        db.close();
    }
    
    private static List<PlaceImage> buildPlaceImages(Cursor cursor){
        List<PlaceImage> placeImages = new ArrayList<PlaceImage>();
        boolean notEmpty = false;
        if(cursor != null)
            notEmpty = cursor.moveToFirst();
        
        if(notEmpty){
            do{
                placeImages.add(buildPlaceImage(cursor));
            }while(cursor.moveToNext());
        }
        return placeImages;
    }
    
    private static PlaceImage buildPlaceImage(Cursor cursor){
        PlaceImage placeImage = new PlaceImage();
        placeImage.setPlaceImageId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        placeImage.setImage(cursor.getBlob(cursor.getColumnIndex(IMAGE)));
        placeImage.setPlaceId(cursor.getLong(cursor.getColumnIndex(PLACE_ID)));
        Calendar date = Calendar.getInstance(TimeZone.getDefault());
        date.setTime(new Date(cursor.getLong(cursor.getColumnIndex(DATE))));
        placeImage.setDate(date);
        return placeImage;
    }
    
    private static ContentValues buildContentValues(PlaceImage placeImage){
        ContentValues values = new ContentValues();
        values.put(IMAGE, placeImage.getImage());
        values.put(PLACE_ID, placeImage.getPlaceId());
        values.put(DATE, placeImage.getDate().getTimeInMillis());
        return values;
    }

}
