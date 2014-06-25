package se.byfootapp.fragment;

import java.util.List;

import se.byfootapp.R;
import se.byfootapp.adapter.PlaceAdapter;
import se.byfootapp.database.DatabaseHelper;
import se.byfootapp.model.Place;
import android.os.Bundle;
import android.support.v4.app.ListFragment;

public class SavedFragment extends ListFragment{ 
    
    private DatabaseHelper db;
    private PlaceAdapter placeAdapter;
    private List<Place> places;
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        this.db = DatabaseHelper.getInstance(this.getActivity());        
        this.places = this.db.getPlaces();
        this.placeAdapter = new PlaceAdapter(this.getActivity(), R.layout.layout_saved, this.places);    
        this.setListAdapter(placeAdapter);
    }
}
