package se.byfootapp.model;

import java.util.List;

public class ListPlace {

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
