package se.byfootapp.model;

import java.io.Serializable;
import java.util.Calendar;

public class PlaceImage implements Serializable{
    private static final long serialVersionUID = 1L;
    private byte[] image;
    private long placeId;
    private Calendar date;
    
    public byte[] getImage(){
        return this.image;
    }
    
    public void setImage(byte[] image){
        this.image = image;
    }
    
    public long getPlaceId(){
        return this.placeId;
    }
    
    public void setPlaceId(long placeId){
        this.placeId = placeId;
    }
    
    public Calendar getDate(){
        return this.date;
    }
    
    public void setDate(Calendar date){
        this.date = date;
    }
    
}
