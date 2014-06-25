package se.byfootapp.model;

import java.util.Calendar;

public class PlaceImage {
    private byte[] image;
    private Integer placeId;
    private Calendar date;
    
    public byte[] getImage(){
        return this.image;
    }
    
    public void setImage(byte[] image){
        this.image = image;
    }
    
    public Integer getPlaceId(){
        return this.placeId;
    }
    
    public void setPlaceId(Integer placeId){
        this.placeId = placeId;
    }
    
    public Calendar getDate(){
        return this.date;
    }
    
    public void setDate(Calendar date){
        this.date = date;
    }
    
}
