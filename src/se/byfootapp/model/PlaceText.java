package se.byfootapp.model;

import java.util.Calendar;

public class PlaceText {
    private Integer id;
    private String text;
    private Integer placeId;
    private Calendar date;
    
    public String getText(){
        return this.text;
    }
    
    public void setText(String text){
        this.text = text;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
