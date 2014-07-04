package se.byfootapp.activity;

import se.byfootapp.R;
import se.byfootapp.model.ListPlace;
import android.app.Activity;
import android.os.Bundle;

public class PlaceActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        ListPlace listPlace = (ListPlace) this.getIntent().getSerializableExtra("listplace");
    }
    
}
