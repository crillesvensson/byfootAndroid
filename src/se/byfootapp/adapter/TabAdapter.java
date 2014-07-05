package se.byfootapp.adapter;

import se.byfootapp.fragment.PlacesFragment;
import se.byfootapp.fragment.ProfileFragment;
import se.byfootapp.fragment.SavedFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter{

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int tab) {
        switch(tab){
        case 0:
            return new ProfileFragment();
        case 1:
            return new PlacesFragment();
        case 2:
            return new SavedFragment();     
        }
        
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

}
