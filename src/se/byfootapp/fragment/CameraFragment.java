package se.byfootapp.fragment;

import se.byfootapp.R;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class CameraFragment extends Fragment{

    private Bitmap bitmap;
    private ImageView imageView;
    private View layout;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstancesState){
        this.layout = inflater.inflate(R.layout.layout_camera, container,false);
        this.imageView = (ImageView)this.layout.findViewById(R.id.camera_image);
        Button takeImageButton = (Button)this.layout.findViewById(R.id.camera_button);
        takeImageButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            } 
        });
        
        Button saveImageButton = (Button)this.layout.findViewById(R.id.camera_save);
        saveImageButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                saveToDB();
            } 
        });
        return this.layout;
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Button capture = (Button) this.layout.findViewById(R.id.camera_button);
        capture.setText(getResources().getString(R.string.camera_capture_new));
        
        Button save = (Button) this.layout.findViewById(R.id.camera_save);
        save.setVisibility(View.VISIBLE);
        this.bitmap = (Bitmap) data.getExtras().get("data");
        this.imageView.setImageBitmap(this.bitmap);
     }
    
    private void saveToDB(){
        //byte[] image = ImageUtils.convertBitmapToByteArray(this.bitmap);
    }
}
