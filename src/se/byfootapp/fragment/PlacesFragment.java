package se.byfootapp.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import se.byfootapp.R;
import se.byfootapp.activity.TypesActivity;
import se.byfootapp.adapter.SavedPlaceAdapter;
import se.byfootapp.common.GooglePlaces;
import se.byfootapp.model.ListPlace;
import se.byfootapp.view.ListHeader;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class PlacesFragment extends ListFragment{
    
    private SavedPlaceAdapter placeAdapter;
    private List<ListPlace> listPlaces;
    private EditText searchBar;
    private Button searchButton;
    private Button typeButton;
    private static List<String> typesList;
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);    
        //Remove divider
        typesList = new ArrayList<String>();
        ListView list = this.getListView();
        if(list != null){
            list.setDividerHeight(0);
            View headerView = new ListHeader(this.getActivity().getBaseContext());
            if(headerView != null){
                list.addHeaderView(headerView);
                searchBar = (EditText)headerView.findViewById(R.id.list_search_input);
                searchButton = (Button)headerView.findViewById(R.id.list_search_button);
                typeButton = (Button)headerView.findViewById(R.id.list_type_button);
            }
        }
        this.listPlaces = new ArrayList<ListPlace>();
        setUp();
              
        if(searchBar != null && searchButton != null){
            searchButton.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View v) {
                    LoadPlaces loadPlaces = new LoadPlaces();
                    String type = searchBar.getText().toString();
                    if(type.equals("")){
                        type = "Gothenburg";
                    }
                    loadPlaces.execute(type);
                }
                
            });
        }
        
        if(typeButton != null){
            typeButton.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), TypesActivity.class);
                    startActivity(intent);
                }
                
            });
        }
        
        
      
    }
    
    private class LoadPlaces extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... locations) {
            GooglePlaces googlePlaces = new GooglePlaces();
            
            Geocoder geoCoder = new Geocoder(getActivity().getBaseContext(), Locale.getDefault());
            String types = "";
            if(typesList.size() > 0){
                for(int i = 0; i < typesList.size(); i++){
                    if(i < typesList.size() -1){
                        types += typesList.get(i) + "|"; 
                    }else{
                        types += typesList.get(i);
                    }
                }
            }else{
                types = "food";
            }
            String locationName = locations[0];
            List<Address> addresses;
            try {
                addresses = geoCoder.getFromLocationName(locationName, 1);
                if(addresses != null && addresses.size() > 0){
                    Address address = addresses.get(0);    
                    listPlaces = googlePlaces.search(getActivity(),address.getLatitude(), address.getLongitude(), 1000, types);
                }else{
                    Toast.makeText(getActivity().getBaseContext(), "Could not find any places", Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
 
            return null;
        }
        
        @Override
        protected void onPostExecute(Void result) {
            //create place adapater and set as this lists adapter
            setUp();
        } 
        
    }
    
    public static void addType(String type){
        typesList.add(type);
    }
    
    public static void removeType(String type){
        typesList.remove(type);
    }
    
    private void setUp(){
        //create place adapater and set as this lists adapter
        placeAdapter = new SavedPlaceAdapter(getActivity(), R.layout.layout_list_item, listPlaces);    
        setListAdapter(placeAdapter);
    }
}
