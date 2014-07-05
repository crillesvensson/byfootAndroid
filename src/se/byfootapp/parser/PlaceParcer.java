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
        String address = "";
        String typesAsString = "";
        if(json.has("geometry") && !json.isNull("geometry")){
            JSONObject geometry = json.getJSONObject("geometry");
            if(geometry.has("location") && !geometry.isNull("location")){
                JSONObject location = geometry.getJSONObject("location");
                if(location.has("lat") && !location.isNull("lat")){
                    lat = location.getDouble("lat");
                }
                if(location.has("lng") && !location.isNull("lng")){
                    lon = location.getDouble("lng");
                }
            }
        }
        
        if(json.has("name") && !json.isNull("name")){
            name = json.getString("name");
        }
        
        if(json.has("vicinity") && !json.isNull("vicinity")){
            address = json.getString("vicinity");
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
        place.setAddress(address);
        place.setType(typesAsString);
        
        listPlace.setPlace(place);
        
        return listPlace;
    }
}
