package se.byfootapp.activity;

import se.byfootapp.R;
import se.byfootapp.database.DatabaseHelper;
import se.byfootapp.model.PlaceImage;
import se.byfootapp.utils.ImageUtils;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class ImageActivity extends Activity{
    
    private PlaceImage placeImage;
    private DatabaseHelper db;
    private boolean loadedPlace;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        
        this.db = DatabaseHelper.getInstance(getApplicationContext());
        
        this.placeImage = (PlaceImage)getIntent().getExtras().getSerializable("placeimage");
        ImageView image = (ImageView)findViewById(R.id.image);
        
        if(image != null && placeImage != null){
            image.setBackground(ImageUtils.byteToDrawable(placeImage.getImage()));
        }  
        
        loadedPlace = getIntent().getExtras().getBoolean("loadedPlace");
    }
    
    public void removeImage(View view){
        this.db.deletePlaceImage(this.placeImage);
        finish();
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
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        if(!loadedPlace){
            getMenuInflater().inflate(R.menu.image_activity, menu);
            return true;
        }else{
            return false;
        }
    }

}
