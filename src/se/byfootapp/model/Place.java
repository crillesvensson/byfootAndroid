package se.byfootapp.model;

import java.io.Serializable;

public class Place implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String type;
    private Double lat;
    private Double lon;
    private String address;
    private String placeID;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String reference) {
        this.placeID = reference;
    }
}
