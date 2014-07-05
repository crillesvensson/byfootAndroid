package se.byfootapp.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import se.byfootapp.R;
import se.byfootapp.activity.PlaceActivity;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
    private static String type;
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);    
        //Remove divider
        type = "food";
        ListView list = this.getListView();
        if(list != null){
            list.setDividerHeight(0);
            View headerView = new ListHeader(this.getActivity().getBaseContext());
            if(headerView != null){
                if(list.getAdapter() != null){
                    list.setAdapter(null);
                }
                list.addHeaderView(headerView);
                searchBar = (EditText)headerView.findViewById(R.id.list_search_input);
                searchButton = (Button)headerView.findViewById(R.id.list_search_button);
                typeButton = (Button)headerView.findViewById(R.id.list_type_button);
            }
        }
        list.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                    long arg3) {
                Intent intent = new Intent(getActivity(), PlaceActivity.class);
                // position - 1 because header counts as first position
                intent.putExtra("place", listPlaces.get(position - 1).getPlace());
                getActivity().startActivity(intent);
            }
            
        });
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
            String locationName = locations[0];
            List<Address> addresses;
            try {
                addresses = geoCoder.getFromLocationName(locationName, 1);
                if(addresses != null && addresses.size() > 0){
                    Address address = addresses.get(0);    
                    listPlaces = googlePlaces.search(getActivity(),address.getLatitude(), address.getLongitude(), 1000, type);
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
    
    public static void setType(String newType){
        type = newType;
    }
    
    private void setUp(){
        //create place adapater and set as this lists adapter
        placeAdapter = new SavedPlaceAdapter(getActivity(), R.layout.layout_list_item, listPlaces);    
        setListAdapter(placeAdapter);
    }
}
