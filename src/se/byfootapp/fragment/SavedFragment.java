package se.byfootapp.fragment;

import se.byfootapp.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SavedFragment extends Fragment{
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstancesState){
        View view = inflater.inflate(R.layout.layout_saved, container,false);
        return view;
    }

}
