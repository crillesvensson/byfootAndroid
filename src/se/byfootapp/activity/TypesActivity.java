package se.byfootapp.activity;

import java.util.HashMap;
import java.util.Map;

import se.byfootapp.fragment.PlacesFragment;
import se.byfootapp.view.Types;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TypesActivity extends Activity{
    
    private Map<String, String> types;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(new Types(this));
        
        types = new HashMap<String, String>();
        types.put("ATM", "atm");
        types.put("Airport", "airport");
        types.put("Cafe", "cafe");
        types.put("Food", "food");
        types.put("Gym", "gym");
        types.put("Hospital", "hospital");
        types.put("Liquor", "liquor_store");
        types.put("Movies", "movie_theater");
        types.put("Club", "night_club");
        types.put("Mall", "shopping_mall");
        types.put("Taxi", "taxi_stand");
        types.put("Zoo", "zoo");
    }
    
    public void changeType(View view){
        Button typeButton = (Button)view;
        PlacesFragment.setType(types.get(typeButton.getText().toString()));
        finish();
    }

}
