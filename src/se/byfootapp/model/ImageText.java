package se.byfootapp.model;

import java.util.Calendar;

public class ImageText {
    private String text;
    private Integer imageId;
    private Calendar date;
    
    public String getText(){
        return this.text;
    }
    
    public void setText(String text){
        this.text = text;
    }
    
    public Integer getImageId(){
        return this.imageId;
    }
    
    public void setImageId(Integer imageId){
        this.imageId = imageId;
    }
    
    public Calendar getDate(){
        return this.date;
    }
    
    public void setDate(Calendar date){
        this.date = date;
    }
}
