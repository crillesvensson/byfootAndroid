package se.byfootapp.activity;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import se.byfootapp.R;
import se.byfootapp.database.DatabaseHelper;
import se.byfootapp.model.Place;
import se.byfootapp.model.PlaceText;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewTextActivity extends Activity{
    
    private DatabaseHelper db;
    private Place place;
    private EditText editText;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_text);
        this.db = DatabaseHelper.getInstance(this);
        this.place = (Place)getIntent().getExtras().getSerializable("place");
        this.editText = (EditText)findViewById(R.id.new_text_edittext);
    }
    
    

    public void addComment(View view){
        PlaceText placeText = new PlaceText();
        String text = editText.getText().toString();
        if(text.length() > 0){
            placeText.setText(text);
            Calendar today = Calendar.getInstance(TimeZone.getDefault());
            today.setTime(new Date());
            placeText.setDate(today);
            placeText.setPlaceId(this.place.getId());
            this.db.createPlaceText(placeText);
            finish();
        }else{
            Toast.makeText(this, "Cannot save empty comment", Toast.LENGTH_SHORT).show();
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
