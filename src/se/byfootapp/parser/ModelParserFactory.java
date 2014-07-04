package se.byfootapp.parser;

import se.byfootapp.model.ListPlace;

public class ModelParserFactory {
    
    private static final ModelParsers parsers = new ModelParsers();

    @SuppressWarnings("unchecked")
    public static <T> ModelParser<T> getParser(Class<T> targetType) {
        
        if(ListPlace.class.isAssignableFrom(targetType)){
            return (ModelParser<T>) parsers.getPlaceParser();
        }
        
        throw new IllegalArgumentException("Unable to locate model parser for " + targetType.getName());
    }
    
    public static class ModelParsers {
        
        public ModelParser<ListPlace> getPlaceParser(){
            return new PlaceParcer();
        }
    }
}