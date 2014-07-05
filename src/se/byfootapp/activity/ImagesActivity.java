package se.byfootapp.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import se.byfootapp.R;
import se.byfootapp.adapter.ImageAdapter;
import se.byfootapp.database.DatabaseHelper;
import se.byfootapp.model.Place;
import se.byfootapp.model.PlaceImage;
import se.byfootapp.utils.ImageUtils;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class ImagesActivity extends Activity{
    
    private DatabaseHelper db;
    private Place place;
    private List<PlaceImage> placeImages;
    private GridView gridView;
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        this.gridView = (GridView)findViewById(R.id.images);
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {
 
                // Sending image id to FullScreenActivity
                Intent intent = new Intent(getApplicationContext(), ImageActivity.class);
                // passing array index
                intent.putExtra("placeimage", placeImages.get(position));
                startActivity(intent);
            }
        });
        this.db = DatabaseHelper.getInstance(this.getApplicationContext());
    }

    public void addImage(View view){
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }
    
    @Override
    public void onResume(){
        super.onResume();
        setUp();
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);       
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        byte[] image = ImageUtils.convertBitmapToByteArray(bitmap);
        long placeId = this.place.getId();
        PlaceImage placeImage = new PlaceImage();
        Calendar today = Calendar.getInstance(TimeZone.getDefault());
        today.setTime(new Date());
        placeImage.setDate(today);
        placeImage.setImage(image);
        placeImage.setPlaceId(placeId);
        this.db.createPlaceImage(placeImage);
        setUp();
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
    
    private void setUp(){
        this.place = (Place)getIntent().getExtras().getSerializable("place");
        if(this.place != null){
            this.placeImages = db.getPlaceImagesForPlace(this.place.getId());
        }else{
            this.placeImages = new ArrayList<PlaceImage>();
        }
        
        gridView.setAdapter(new ImageAdapter(this, R.layout.layout_list_images_grid, placeImages));
    }
}
