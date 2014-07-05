package se.byfootapp.activity;

import se.byfootapp.MainByFootActivity;
import se.byfootapp.R;
import se.byfootapp.database.DatabaseHelper;
import se.byfootapp.fragment.SavedFragment;
import se.byfootapp.model.Place;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PlaceActivity extends Activity{

    private GoogleMap map;
    private Place place;
    private DatabaseHelper db;
    
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
    
    public void deletePlace(View view){
        this.db.deletePlace(this.place);
        finish();
    }
    
    private void setAction(boolean newPlace){
        Button images = (Button)findViewById(R.id.images_button);
        Button add = (Button)findViewById(R.id.place_button);
        if(newPlace){
            images.setEnabled(false);
            images.setVisibility(View.GONE);
            add.setEnabled(true);
            add.setVisibility(View.VISIBLE);
        }else{
            Button delete = (Button)findViewById(R.id.delete_place_button);
            delete.setVisibility(View.VISIBLE);
            add.setEnabled(false);
            add.setVisibility(View.GONE);
            images.setEnabled(true);
            images.setVisibility(View.VISIBLE);
        }
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Call finish when up menu item is pressed
        switch (item.getItemId()) {
         case android.R.id.home:
             finish();
             break;
          default:
              break;
          }
        return true;
      }
}
