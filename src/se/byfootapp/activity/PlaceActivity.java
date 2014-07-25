package se.byfootapp.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import se.byfootapp.MainByFootActivity;
import se.byfootapp.R;
import se.byfootapp.adapter.ReviewsAdapter;
import se.byfootapp.common.Common;
import se.byfootapp.database.DatabaseHelper;
import se.byfootapp.fragment.SavedFragment;
import se.byfootapp.http.HTTPClient;
import se.byfootapp.model.Place;
import se.byfootapp.model.Review;
import se.byfootapp.parser.ModelParser;
import se.byfootapp.parser.ModelParserFactory;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PlaceActivity extends Activity{

    private GoogleMap map;
    private Place place;
    private DatabaseHelper db;
    private String website;
    private ListView reviewList;
    private List<Review> reviews;
    private String[] references;
    private final String DETAILED_URL = "https://maps.googleapis.com/maps/api/place/details/json?placeid=";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        
        this.db = DatabaseHelper.getInstance(this.getApplicationContext());
        
        TextView name = (TextView)findViewById(R.id.place_name);
        TextView address = (TextView)findViewById(R.id.place_address);
        
        this.place = (Place) this.getIntent().getSerializableExtra("place");
        
        //Hide add place or images button depending if new place or not  
        boolean newPlace = this.getIntent().getBooleanExtra("isNewPlace", true);
        setAction(newPlace);
        
        
        this.map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        
        if(this.place != null){
            name.setText(this.place.getName());
            address.setText(this.place.getAddress());
            
            if(this.map != null){
                LatLng position = new LatLng(this.place.getLat(), this.place.getLon());
                markPlace(position);
            }
        }
        
        this.reviewList = (ListView)findViewById(R.id.reviews_list);
        this.reviewList.setDividerHeight(0);
        
        LoadPlaceDetailed loadPlaceDetailed = new LoadPlaceDetailed();
        loadPlaceDetailed.execute();
    }
    
    
    private void markPlace(LatLng position){
        this.map.addMarker(new MarkerOptions().position(position).title(this.place.getName()));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 15), 15, null);
    }
    
    public void addPlace(View view){
        long placeId = this.db.createPlace(this.place);
        this.place.setId((int)placeId);
        setAction(false);
        SavedFragment.setBackFromDetailed(true);
        MainByFootActivity.setTab(2);
    }
    
    public void images(View view){
        Intent intent = new Intent(this, ImagesActivity.class);
        intent.putExtra("place", this.place);
        startActivity(intent);
    }
    
    public void texts(View view){
        Intent intent = new Intent(this, TextActivity.class);
        intent.putExtra("place", this.place);
        startActivity(intent);
    }
    
    public void deletePlace(View view){
        this.db.deletePlace(this.place);
        finish();
    }
    
    private void setAction(boolean newPlace){
        Button images = (Button)findViewById(R.id.images_button);
        Button texts = (Button)findViewById(R.id.texts_button);
        Button add = (Button)findViewById(R.id.place_button);
        
        if(newPlace){
            images.setEnabled(false);
            images.setVisibility(View.GONE);
            texts.setEnabled(false);
            texts.setVisibility(View.GONE);
            add.setEnabled(true);
            add.setVisibility(View.VISIBLE);
        }else{
            Button delete = (Button)findViewById(R.id.delete_place_button);
            delete.setVisibility(View.VISIBLE);
            add.setEnabled(false);
            add.setVisibility(View.GONE);
            images.setEnabled(true);
            images.setVisibility(View.VISIBLE);
            texts.setEnabled(true);
            texts.setVisibility(View.VISIBLE);
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.place_activity, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Call finish when up menu item is pressed
        switch (item.getItemId()) {
         case android.R.id.home:
             finish();
             break;
         case R.id.website:
             if(website != null && website.length() > 0){
                 Intent intent = new Intent(this, WebActivity.class);
                 intent.putExtra("url", website);
                 startActivity(intent);
             }else{
                 Toast.makeText(this, "No Website found for this place", Toast.LENGTH_LONG).show();
             }
             break;
         case R.id.images:
             if(references.length > 0){
                 Intent intent = new Intent(this, ImagesActivity.class);
                 intent.putExtra("place", this.place);
                 intent.putExtra("photoreferences", references);
                 intent.putExtra("loadPhotos", true);
                 startActivity(intent);
             }else{
                 Toast.makeText(this, "No images found for this place", Toast.LENGTH_SHORT).show();
             }
             break;
          default:
              break;
          }
        return true;
      }
    
    private void setUpReview(){
        this.reviewList.setAdapter(new ReviewsAdapter(this, R.layout.layout_list_reviews, this.reviews));
    }
    
    private class LoadPlaceDetailed extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean loadResult = true;
            String url = DETAILED_URL + place.getPlaceID() + "&key=" + Common.GOOGLE_API_KEY;
            HTTPClient httpClient = new HTTPClient();
            try {
                JSONObject response = httpClient.getResponseAsJSON(url);
                if(response.has("result") && !response.isNull("result")){
                    JSONObject result  = response.getJSONObject("result");
                    if(result.has("website") && ! result.isNull("website")){
                        website = result.getString("website");
                    }
                    if(result.has("photos") && !result.isNull("photos")){
                        System.out.println("has photots");
                        JSONArray photos = result.getJSONArray("photos");
                        references = new String[photos.length()]; 
                        for(int i = 0; i < photos.length(); i++){
                            JSONObject photo = photos.getJSONObject(i);
                            if(photo.has("photo_reference") && !photo.isNull("photo_reference")){
                                references[i] = photo.getString("photo_reference");
                            }
                        }
                    }else{
                        references = new String[0];
                    }
                    if(result.has("reviews") && !result.isNull("reviews")){
                        ModelParser<Review> reviewParser = ModelParserFactory.getParser(Review.class);
                        JSONArray reviewsJSON = result.getJSONArray("reviews");
                        reviews = new ArrayList<Review>(); 
                        for(int i = 0; i < reviewsJSON.length(); i++){
                            JSONObject review = reviewsJSON.getJSONObject(i);
                            reviews.add(reviewParser.doParse(review));
                        }
                    }else{
                        loadResult = false;
                    }
                    
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return loadResult;
        }
        
        @Override
        protected void onPostExecute(Boolean result) {
            if(result){
                setUpReview();
            }
        }
        
    }
}
