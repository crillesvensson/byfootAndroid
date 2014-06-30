package se.byfootapp.adapter;

import java.util.List;

import se.byfootapp.R;
import se.byfootapp.model.ListPlace;
import se.byfootapp.utils.ImageUtils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SavedPlaceAdapter extends ArrayAdapter<ListPlace>{
    
    private final Context context;
    private final List<ListPlace> listPlaces;

    public SavedPlaceAdapter(Context context, int textViewResourceId,
            List<ListPlace> places) {
        super(context, textViewResourceId, places);
        this.context = context;
        this.listPlaces = places; 
    }
    
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.layout_list_item, parent, false);
        ListPlace listPlace = this.listPlaces.get(position);
        
        if(listPlace.getPlaceImages().size() > 0){
            ImageView image = (ImageView)rowView.findViewById(R.id.list_image);
            image.setBackground(ImageUtils.byteToDrawable(listPlace.getPlaceImages().get(0).getImage()));
        }
        
        String placeName = listPlace.getPlace().getName();
        if(placeName != null){
            TextView listText = (TextView)rowView.findViewById(R.id.list_title);
            listText.setText(placeName);
        }
        
        return rowView;
    }
}
