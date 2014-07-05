package se.byfootapp.fragment;

import java.util.ArrayList;
import java.util.List;

import se.byfootapp.R;
import se.byfootapp.activity.PlaceActivity;
import se.byfootapp.adapter.SavedPlaceAdapter;
import se.byfootapp.database.DatabaseHelper;
import se.byfootapp.model.ListPlace;
import se.byfootapp.model.Place;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SavedFragment extends ListFragment{ 
    
    private DatabaseHelper db;
    private SavedPlaceAdapter placeAdapter;
    private List<ListPlace> listPlaces;
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        //set databasehelper
        this.db = DatabaseHelper.getInstance(this.getActivity());    
        //get list of listplaces
        this.listPlaces = this.getListPlaces();
        
        
        //Remove divider
        ListView list = this.getListView();
        if(list != null){
            if(list.getAdapter() != null){
                list.setAdapter(null);
            }
            list.setDividerHeight(0);
        }
        
      //create place adapater and set as this lists adapter
        this.placeAdapter = new SavedPlaceAdapter(this.getActivity(), R.layout.layout_list_item, this.listPlaces);    
        this.setListAdapter(placeAdapter);
          
        list.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                    long arg3) {
                Intent intent = new Intent(getActivity(), PlaceActivity.class);
                intent.putExtra("place", listPlaces.get(position).getPlace());
                intent.putExtra("isNewPlace", false);
                getActivity().startActivity(intent);
            }      
        });
    }
    
    //Build list of listplaces which is sent to adapter
    private List<ListPlace> getListPlaces(){
        List<ListPlace> listPlaces = new ArrayList<ListPlace>();
        for(Place place : db.getPlaces()){
            ListPlace listPlace = new ListPlace();
            listPlace.setPlace(place);
            listPlace.setPlaceImages(db.getPlaceImagesForPlace(place.getId()));
            listPlaces.add(listPlace);
        }
        return listPlaces;
    }
}
