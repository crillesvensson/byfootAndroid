package se.byfootapp.database;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import se.byfootapp.model.ImageText;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ImageTextTable {

    //Database table name
    private static final String TABLE_IMAGE_TEXT = "imageText";
    
    //Database table columns    
    private static final String KEY_ID = "id";
    private static final String TEXT = "image";
    private static final String IMAGE_ID = "imageID";
    private static final String DATE  = "date";
    
    //Database create table SQL statement
    private static final String CREATE_TABLE_IMAGE_TEXT = "CREATE TABLE "
            + TABLE_IMAGE_TEXT + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + TEXT + " TEXT,"
            + IMAGE_ID + " INTEGER,"
            + DATE + " DATETIME"
            + ")";
    
    public static void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_IMAGE_TEXT);
    }
    
    public static void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE_TEXT);
        onCreate(db);
    }
    
    public static long createImageText(ImageText imageText, SQLiteDatabase db){
        ContentValues values = buildContentValues(imageText);
        db.close();
        return db.insert(TABLE_IMAGE_TEXT, null, values);
    }
    
    public static ImageText getImageText(Integer imageTextId, SQLiteDatabase db){
        String selectQuery = "SELECT * FROM " + TABLE_IMAGE_TEXT + " WHERE "
                + KEY_ID + " = " + imageTextId;
        Cursor cursor = db.rawQuery(selectQuery, null);
        boolean notEmpty = false;
        
        if(cursor != null)
            notEmpty = cursor.moveToFirst();
        
        ImageText imageText = null;
        if(notEmpty){
            imageText = buildImageText(cursor);
        }
        cursor.close();
        db.close();
        return imageText;
    }
    
    public static List<ImageText> getImageTexts(SQLiteDatabase db){
        String selectQuery = "SELECT * FROM " + TABLE_IMAGE_TEXT;
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        List<ImageText> imageTexts = buildImageTexts(cursor);
        cursor.close();
        db.close();
        return imageTexts;
    }
    
    private static List<ImageText> buildImageTexts(Cursor cursor){
        List<ImageText> imageTexts = new ArrayList<ImageText>();
        boolean notEmpty = false;
        if(cursor != null)
            notEmpty = cursor.moveToFirst();
        
        if(notEmpty){
            do{
                imageTexts.add(buildImageText(cursor));
            }while(cursor.moveToNext());
        }
        return imageTexts;
    }
    
    private static ImageText buildImageText(Cursor cursor){
        ImageText imageText = new ImageText();
        imageText.setText(cursor.getString(cursor.getColumnIndex(TEXT)));
        imageText.setImageId(cursor.getInt(cursor.getColumnIndex(IMAGE_ID)));
        Calendar date = Calendar.getInstance(TimeZone.getDefault());
        date.setTime(new Date(cursor.getLong(cursor.getColumnIndex(DATE))));
        imageText.setDate(date);
        return imageText;
    }
    
    private static ContentValues buildContentValues(ImageText imageText){
        ContentValues values = new ContentValues();
        values.put(TEXT, imageText.getText());
        values.put(IMAGE_ID, imageText.getImageId());
        values.put(DATE, imageText.getDate().getTimeInMillis());
        return values;
    }
}
