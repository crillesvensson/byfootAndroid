package se.byfootapp.parser;

import com.google.android.gms.maps.model.LatLng;

import se.byfootapp.model.ListPlace;
import se.byfootapp.model.Review;

public class ModelParserFactory {
    
    private static final ModelParsers parsers = new ModelParsers();

    @SuppressWarnings("unchecked")
    public static <T> ModelParser<T> getParser(Class<T> targetType) {
        
        if(ListPlace.class.isAssignableFrom(targetType)){
            return (ModelParser<T>) parsers.getPlaceParser();
        }else if(LatLng.class.isAssignableFrom(targetType)){
            return (ModelParser<T>) parsers.getLocationParser();
        }else if(Review.class.isAssignableFrom(targetType)){
            return (ModelParser<T>) parsers.getReviewParser();
        }
        
        throw new IllegalArgumentException("Unable to locate model parser for " + targetType.getName());
    }
    
    public static class ModelParsers {
        
        public ModelParser<ListPlace> getPlaceParser(){
            return new PlaceParcer();
        }
        
        public ModelParser<LatLng> getLocationParser(){
            return new LocationParser();
        }
        
        public ModelParser<Review> getReviewParser(){
            return new ReviewParser();
        }
    }
}
