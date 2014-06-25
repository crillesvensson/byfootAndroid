package se.byfootapp.fragment;

import se.byfootapp.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PlacesFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstancesState){
        return inflater.inflate(R.layout.layout_places, container,false);
    }
    
}
