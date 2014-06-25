package se.byfootapp.adapter;

import java.util.List;

import se.byfootapp.R;
import se.byfootapp.model.Place;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class PlaceAdapter extends ArrayAdapter<Place>{
    
    private final Context context;
    private final List<Place> places;

    public PlaceAdapter(Context context, int textViewResourceId,
            List<Place> places) {
        super(context, textViewResourceId, places);
        this.context = context;
        this.places = places;
    }
    
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.layout_saved, parent, false);
        return rowView;
    }
}
