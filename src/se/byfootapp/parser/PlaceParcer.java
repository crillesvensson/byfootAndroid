package se.byfootapp.parser;

import org.json.JSONArray;
import org.json.JSONObject;
import se.byfootapp.model.ListPlace;
import se.byfootapp.model.Place;

public class PlaceParcer implements ModelParser<ListPlace>{

    @Override
    public ListPlace doParse(JSONObject json) throws Exception {
        ListPlace listPlace = new ListPlace();
        Place place = new Place();
        Double lat = null;
        Double lon = null;
        String name = "";
        String typesAsString = "";
        if(json.has("geometry") && !json.isNull("geometry")){
            JSONObject geometry = json.getJSONObject("geometry");
            if(geometry.has("lat") && !json.isNull("lat")){
                lat = geometry.getDouble("lat");
            }
            if(geometry.has("lng") && !geometry.isNull("lng")){
                lon = geometry.getDouble("lng");
            }
        }
        
        if(json.has("name") && !json.isNull("name")){
            name = json.getString("name");
        }
        
        if(json.has("types") && !json.isNull("types")){
            JSONArray types = json.getJSONArray("types");
            for(int i = 0; i < types.length(); i++){
                if(i < types.length() - 1){
                    typesAsString += types.getString(i) + ", ";
                }else{
                    typesAsString += types.getString(i);
                }
            }
        }
        
        place.setLat(lat);
        place.setLon(lon);
        place.setName(name);
        place.setType(typesAsString);
        
        listPlace.setPlace(place);
        
        return listPlace;
    }
}
