package se.byfootapp.database;

import java.util.List;

import se.byfootapp.model.ImageText;
import se.byfootapp.model.Place;
import se.byfootapp.model.PlaceImage;
import se.byfootapp.model.PlaceText;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
    
    //Singleton instance;
    private static DatabaseHelper sInstance;
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "by_foot_database";
    
    public static DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
          sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
      }
    
    
    private DatabaseHelper(Context context){     
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db){
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        PlaceTable.onCreate(db);
        PlaceImageTable.onCreate(db);
        PlaceTextTable.onCreate(db);
        ImageTextTable.onCreate(db);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        PlaceTable.onUpgrade(db, oldV, newV);
        PlaceImageTable.onUpgrade(db, oldV, newV);
        PlaceTextTable.onUpgrade(db, oldV, newV);
        ImageTextTable.onUpgrade(db, oldV, newV);
    }
    
    //Place methods
    public long createPlace(Place place){
        return PlaceTable.createPlace(place, getWritableDatabase());
    }
    
    public Place getPlace(Integer placeId){
        return PlaceTable.getPlace(placeId, getWritableDatabase());
    }
    
    public List<Place> getPlaces(){
        return PlaceTable.getPlaces(getWritableDatabase());
    }
    
    public void deletePlace(Place place){
        PlaceTable.deletePlace(place, getWritableDatabase());
    }
    
    //Place Image methods
    public long createPlaceImage(PlaceImage placeImage){
        return PlaceImageTable.createPlaceImage(placeImage, getWritableDatabase());
    }
    
    public PlaceImage getPlaceImage(Integer placeImageId){
        return PlaceImageTable.getPlaceImage(placeImageId, getWritableDatabase());
    }
    
    public List<PlaceImage> getPlaceImages(){
        return PlaceImageTable.getPlaceImages(getWritableDatabase());
    }
    
    public List<PlaceImage> getPlaceImagesForPlace(Integer placeId){
        return PlaceImageTable.getPlaceImagesForPlace(placeId, getWritableDatabase());
    }
    
    public void deletePlaceImage(PlaceImage placeImage){
        PlaceImageTable.deletePlaceImage(placeImage, getWritableDatabase());
    }
    
    //Place text methods
    public long createPlaceText(PlaceText placeText){
        return PlaceTextTable.createPlaceText(placeText, getWritableDatabase());
    }
    
    public PlaceText getPlaceText(Integer placeTextId){
        return PlaceTextTable.getPlaceText(placeTextId, getWritableDatabase());
    }
    
    public List<PlaceText> getPlaceTexta(){
        return PlaceTextTable.getPlaceTexts(getWritableDatabase());
    }
    
    public List<PlaceText> getPlaceTextForPlace(Integer placeId){
        return PlaceTextTable.getPlaceTextForPlace(placeId, getWritableDatabase());
    }
    
    //Image text methods
    public long createImageText(ImageText imageText){
        return ImageTextTable.createImageText(imageText, getWritableDatabase());
    }
    
    public ImageText getImageText(Integer imageTextId){
        return ImageTextTable.getImageText(imageTextId, getWritableDatabase());
    }
    
    public List<ImageText> getImageTexts(){
        return ImageTextTable.getImageTexts(getWritableDatabase());
    }  
}
