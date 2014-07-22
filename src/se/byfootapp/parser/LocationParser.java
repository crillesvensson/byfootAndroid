package se.byfootapp.parser;

import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

public class LocationParser implements ModelParser<LatLng>{

    @Override
    public LatLng doParse(JSONObject json) throws Exception {
        if(json.has("geometry") && !json.isNull("geometry")){
            JSONObject geometry = json.getJSONObject("geometry");
            if(geometry.has("location") && !geometry.isNull("location")){
                JSONObject location = geometry.getJSONObject("location");
                Double lat = 0d;
                Double lng = 0d;
                if(location.has("lng") && !location.isNull("lng")){
                    lng = location.getDouble("lng");
                }
                
                if(location.has("lat") && !location.isNull("lat")){
                    lat = location.getDouble("lat");
                }
                
                return new LatLng(lat, lng);
            }
        }
        return null;
    }
    
}
