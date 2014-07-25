package se.byfootapp.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import se.byfootapp.R;
import se.byfootapp.adapter.ImageAdapter;
import se.byfootapp.common.Common;
import se.byfootapp.database.DatabaseHelper;
import se.byfootapp.http.HTTPClient;
import se.byfootapp.model.Place;
import se.byfootapp.model.PlaceImage;
import se.byfootapp.utils.ImageUtils;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
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
    private boolean loadPhotos;
    private String photo_url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=";
    private String[] photoReferences;
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        this.gridView = (GridView)findViewById(R.id.images);
        loadPhotos = getIntent().getExtras().getBoolean("loadPhotos");
        if(loadPhotos){
            photoReferences = getIntent().getExtras().getStringArray("photoreferences");
            placeImages = new ArrayList<PlaceImage>();
        }
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {
 
                // Sending image id to FullScreenActivity
                Intent intent = new Intent(getApplicationContext(), ImageActivity.class);
                // passing array index
                intent.putExtra("placeimage", placeImages.get(position));
                intent.putExtra("loadedPlace", loadPhotos);
                startActivity(intent);
            }
        });
        this.db = DatabaseHelper.getInstance(this.getApplicationContext());
        if(loadPhotos){
            setUp();
        }
    }

    public void addImage(View view){
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }
    
    @Override
    public void onResume(){
        super.onResume();
        if(!loadPhotos){
            setUp();
        }
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
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        if(!loadPhotos){
            getMenuInflater().inflate(R.menu.images_activity, menu);
            return true;
        }else{
            return false;
        }
    }
    
    private void setUp(){
        if(loadPhotos){
            for(String reference : photoReferences){
                LoadPhoto loadPhoto = new LoadPhoto();
                loadPhoto.execute(reference);
            }
        }else{
            this.place = (Place)getIntent().getExtras().getSerializable("place");
            if(this.place != null){
                this.placeImages = db.getPlaceImagesForPlace(this.place.getId());
            }else{
                this.placeImages = new ArrayList<PlaceImage>();
            }
            gridView.setAdapter(new ImageAdapter(this, R.layout.layout_list_images_grid, placeImages));
        }
        
    }
    
    private class LoadPhoto extends AsyncTask<String, Void, Void>{
        Bitmap bitmap;
        
        @Override
        protected Void doInBackground(String... reference) {
            String url = photo_url + reference[0] + "&key=" + Common.GOOGLE_API_KEY;
            System.out.println(url);
            HTTPClient httpClient = new HTTPClient();
            
            try {
                bitmap = httpClient.getReponseAsBitmap(url);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
        
        @Override
        protected void onPostExecute(Void result){
            if(bitmap != null){
                PlaceImage placeImage = new PlaceImage();
                placeImage.setImage(ImageUtils.convertBitmapToByteArray(bitmap));
                placeImages.add(placeImage);
                gridView.setAdapter(new ImageAdapter(getBaseContext(), R.layout.layout_list_images_grid, placeImages));
            }
        }
       
    }
}
