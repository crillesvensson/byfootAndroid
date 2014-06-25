package se.byfootapp.fragment;

import com.facebook.widget.ProfilePictureView;

import se.byfootapp.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProfileFragment extends Fragment{

    private SharedPreferences sp;
    private Resources resources;
    private ProfilePictureView profilePicture;
    private TextView firstName;
    private TextView lastName;
    private TextView birthday;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstancesState){
        View view = inflater.inflate(R.layout.layout_profile, container,false);
        //Setup view
        this.resources = getActivity().getResources();
        this.sp = getActivity().getSharedPreferences(this.resources.getString(R.string.sp_name), Context.MODE_PRIVATE);
        this.profilePicture = (ProfilePictureView)view.findViewById(R.id.profile_picture);
        this.profilePicture.setCropped(true);
        this.firstName = (TextView)view.findViewById(R.id.profile_first_name);
        this.lastName = (TextView)view.findViewById(R.id.profile_last_name);
        this.birthday = (TextView)view.findViewById(R.id.profile_birthday);
        
        //Retrive user info
        String id = sp.getString(this.resources.getString(R.string.user_id), "");
        String first = sp.getString(this.resources.getString(R.string.user_first_name), "");
        String last = sp.getString(this.resources.getString(R.string.user_last_name), "");
        String birth = sp.getString(this.resources.getString(R.string.user_birthday), "");
        
        this.profilePicture.setProfileId(id);
        this.firstName.setText(first);
        this.lastName.setText(last);
        this.birthday.setText(birth);
        
        return view;
    }
    
}
