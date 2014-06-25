package se.byfootapp.model;

public class Place {
    private String name;
    private String type;
    private Double lat;
    private Double lon;
    
    public String getName(){
        return this.name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getType(){
        return this.type;
    }
    
    public void setType(String type){
        this.type = type;
    }
    
    public Double getLat(){
        return this.lat;
    }
    
    public void setLat(Double lat){
        this.lat = lat;
    }
    
    public Double getLon(){
        return this.lon;
    }
    
    public void setLon(Double lon){
        this.lon = lon;
    }
}
