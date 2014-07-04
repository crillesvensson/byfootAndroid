package se.byfootapp.model;

import java.io.Serializable;
import java.util.List;

public class ListPlace implements Serializable{
    private static final long serialVersionUID = 1L;
    private Place place;
    private List<PlaceImage> placeImages;
    
    public Place getPlace() {
        return place;
    }
    public void setPlace(Place place) {
        this.place = place;
    }
    
    public List<PlaceImage> getPlaceImages() {
        return placeImages;
    }
    public void setPlaceImages(List<PlaceImage> placeImages) {
        this.placeImages = placeImages;
    }
}
