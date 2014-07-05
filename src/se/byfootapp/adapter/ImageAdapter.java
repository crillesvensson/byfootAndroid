package se.byfootapp.adapter;

import java.util.List;

import se.byfootapp.R;
import se.byfootapp.model.PlaceImage;
import se.byfootapp.utils.ImageUtils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class ImageAdapter extends ArrayAdapter<PlaceImage>{
    
    private Context context;
    private List<PlaceImage> placeImages;
    
    public ImageAdapter(Context context, int textViewResourceId,
            List<PlaceImage> placeImages) {
        super(context, textViewResourceId, placeImages);
        this.context = context;
        this.placeImages = placeImages;
        if(placeImages.size() == 0)
            System.out.println("empty");
        
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        View gridViewItem = inflater.inflate(R.layout.layout_list_images_grid, parent, false);
        ImageView image = (ImageView)gridViewItem.findViewById(R.id.grid_image);
        image.setBackground(ImageUtils.byteToDrawable(placeImages.get(position).getImage()));
        return gridViewItem;
    }
}
