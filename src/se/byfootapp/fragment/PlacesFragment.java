package se.byfootapp.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import se.byfootapp.R;
import se.byfootapp.activity.PlaceActivity;
import se.byfootapp.activity.TypesActivity;
import se.byfootapp.adapter.SavedPlaceAdapter;
import se.byfootapp.common.GooglePlaces;
import se.byfootapp.http.HTTPClient;
import se.byfootapp.model.ListPlace;
import se.byfootapp.parser.ModelParser;
import se.byfootapp.parser.ModelParserFactory;
import se.byfootapp.view.ListFooter;
import se.byfootapp.view.ListHeader;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class PlacesFragment extends ListFragment{
    
    private SavedPlaceAdapter placeAdapter;
    private List<ListPlace> listPlaces;
    private EditText searchBar;
    private Button searchButton;
    private Button typeButton;
    private static ListView list;
    private Button loadMoreButton;
    private static boolean moreResults;
    private static String pageToken;
    private View footerView;
    private static String type;
    private String location;
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);    
        //Set initial search type
        type = "food";
        //Remove divider
        list = this.getListView();
        if(list != null){
            list.setDividerHeight(0);
            View headerView = new ListHeader(this.getActivity().getBaseContext());
            footerView = new ListFooter(this.getActivity().getBaseContext());
            if(headerView != null && footerView != null){
                if(list.getAdapter() != null){
                    list.setAdapter(null);
                }
                list.addHeaderView(headerView);
                searchBar = (EditText)headerView.findViewById(R.id.list_search_input);
                searchButton = (Button)headerView.findViewById(R.id.list_search_button);
                typeButton = (Button)headerView.findViewById(R.id.list_type_button);
                loadMoreButton = (Button)footerView.findViewById(R.id.more_results_button);
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
                    location = searchBar.getText().toString();
                    if(location.equals("")){
                        location = "Gothenburg";
                    }
                    pageToken = "null";
                    loadPlaces.execute(false);
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
        
        if(loadMoreButton != null){
            loadMoreButton.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View v) {
                    LoadPlaces loadPlaces = new LoadPlaces();
                    loadPlaces.execute(true);
                }
                
            });
        }
        
      
    }
    
    private class LoadPlaces extends AsyncTask<Boolean, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Boolean... lpageToken) {
            boolean result = false;
            
            GooglePlaces googlePlaces = new GooglePlaces();
            boolean loadPlaceToken = lpageToken[0];
            Geocoder geoCoder = new Geocoder(getActivity().getBaseContext(), Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geoCoder.getFromLocationName(location, 1);
                if(addresses != null && addresses.size() > 0){
                    Address address = addresses.get(0); 
                    if(loadPlaceToken){
                        listPlaces.addAll(googlePlaces.search(getActivity(),address.getLatitude(), address.getLongitude(), 1000, type, pageToken));
                    }else{
                        listPlaces = googlePlaces.search(getActivity(),address.getLatitude(), address.getLongitude(), 1000, type, pageToken);
                    }
                    if(listPlaces.size() > 0){
                        result = true;
                    }
                }else{
                    //Geocoder failed, try using google
                    String googleGeocoderString = "http://maps.google.com/maps/api/geocode/json?address=" + Uri.encode(location);
                    HTTPClient httpClient = new HTTPClient();
                    JSONObject response = httpClient.getResponseAsJSON(googleGeocoderString);
                    LatLng coordinates = null;
                    if(response.has("results") && !response.isNull("results")){
                        ModelParser<LatLng> locationParser = ModelParserFactory.getParser(LatLng.class);
                        JSONArray results = response.getJSONArray("results");
                        if(results.length() > 0){
                            coordinates = locationParser.doParse(results.getJSONObject(0));
                        }
                        if(coordinates != null){
                            if(loadPlaceToken){
                                listPlaces.addAll(googlePlaces.search(getActivity(),coordinates.latitude, coordinates.longitude, 1000, type, pageToken));
                            }else{
                                listPlaces = googlePlaces.search(getActivity(),coordinates.latitude, coordinates.longitude, 1000, type, pageToken);
                            }
                            if(listPlaces.size() > 0){
                                result = true;
                            }
                        }
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
 
            return result;
        }
        
        @Override
        protected void onPostExecute(Boolean result) {
            if(result){
                list.removeFooterView(footerView);
                if(moreResults){
                    list.addFooterView(footerView);
                }else{
                    list.removeFooterView(footerView);
                }
                //create place adapater and set as this lists adapter
                setUp();
            }else{
                Toast.makeText(getActivity().getBaseContext(), "Could not find any places", Toast.LENGTH_LONG).show();
            }
        } 
        
    }
    
    public static void showFooterView(boolean show){
        moreResults = show;
    }
    
    public static void setPageToken(String token){
        pageToken = token;
    }
    
    public static void setType(String newType){
        type = newType;
    }
    
    private void setUp(){
        ListView list = this.getListView();
        //Save state of listview
        Parcelable state = list.onSaveInstanceState();
        //create place adapater and set as this lists adapter
        placeAdapter = new SavedPlaceAdapter(getActivity(), R.layout.layout_list_item, listPlaces);    
        setListAdapter(placeAdapter);
        if(state != null){
            //Restore state
            list.onRestoreInstanceState(state);
        }
    }
}
