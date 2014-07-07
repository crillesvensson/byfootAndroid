package se.byfootapp.activity;

import java.util.ArrayList;
import java.util.List;

import se.byfootapp.R;
import se.byfootapp.adapter.TextAdapter;
import se.byfootapp.database.DatabaseHelper;
import se.byfootapp.model.Place;
import se.byfootapp.model.PlaceText;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class TextActivity extends Activity{
    
    private DatabaseHelper db;
    private ListView list;
    private Place place;
    private List<PlaceText> placeTexts;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        this.list = (ListView)findViewById(R.id.text_list);
        if(this.list != null){
            list.setDividerHeight(0);
        }
        this.db = DatabaseHelper.getInstance(this);
    }
    
    @Override
    public void onResume(){
        super.onResume();
        setUp();
    }

    private void setUp(){
        this.place = (Place)getIntent().getExtras().getSerializable("place");
        if(this.place != null){
            this.placeTexts = db.getPlaceTextForPlace(place.getId());
        }else{
            this.placeTexts = new ArrayList<PlaceText>();
        }
        this.list.setAdapter(new TextAdapter(this, R.layout.layout_text_list_item, this.placeTexts));
    }
    
    public void addText(View view){
        Intent intent = new Intent(this, NewTextActivity.class);
        intent.putExtra("place", this.place);
        startActivity(intent);
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
