package se.byfootapp.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import se.byfootapp.http.HTTPClient;
import se.byfootapp.model.ListPlace;
import se.byfootapp.model.PlaceImage;
import se.byfootapp.parser.ModelParser;
import se.byfootapp.parser.ModelParserFactory;
import android.content.Context;

public class GooglePlaces {

    private final String API_KEY = "AIzaSyALBJqkR9FgV3XuhxqtaC2Ef5F0rxtZIs4";
    private final String GOOGLE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";
    
    public List<ListPlace> search(Context context, double lat, double lon, double radius, String types){
        List<ListPlace> listPlaces = new ArrayList<ListPlace>();
        
        try{
             String url = GOOGLE_URL + lat + "," + lon + "&rankby=distance&types=" + types + "&key=" + API_KEY;
             HTTPClient httpClient = new HTTPClient();
             
             JSONObject response = httpClient.getResponseAsJSON(url);
             System.out.println(response);
             ModelParser<ListPlace> placeParser = ModelParserFactory.getParser(ListPlace.class);
             if(response != null && !response.isNull("results")){
                 JSONArray googlePlaces = response.getJSONArray("results");
                 for(int i = 0; i < googlePlaces.length(); i++){
                     JSONObject googlePlace = googlePlaces.getJSONObject(i);
                     ListPlace listPlace = placeParser.doParse(googlePlace);
                     
                     if(googlePlace.has("icon") && !googlePlace.isNull("icon")){
                         String iconAsString = googlePlace.getString("icon");
                         URL URL = new URL(iconAsString);
                         byte[] icon = this.readBytes(context, URL);
                         List<PlaceImage> placeImages = new ArrayList<PlaceImage>();
                         PlaceImage placeImage = new PlaceImage();
                         placeImage.setImage(icon);
                         placeImages.add(placeImage);
                         listPlace.setPlaceImages(placeImages);
                     }
                     
                     listPlaces.add(listPlace);
                 }
             }  
        }catch(Exception e){
            System.out.println(e.toString());
        } 
        return listPlaces;
    }
    
    private byte[] readBytes(Context context, URL url) throws IOException {
        ByteArrayOutputStream bais = new ByteArrayOutputStream();
        InputStream is = null;
        byte[] byteChunk = null;
        try {
          is = url.openStream ();
          byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
          int n;

          while ( (n = is.read(byteChunk)) > 0 ) {
            bais.write(byteChunk, 0, n);
          }
        }
        catch (IOException e) {
          System.err.printf ("Failed while reading bytes from %s: %s", url.toExternalForm(), e.getMessage());
          e.printStackTrace ();
        }
        finally {
          if (is != null){ 
              is.close(); 
          }
        }
        return byteChunk;
    }
}
