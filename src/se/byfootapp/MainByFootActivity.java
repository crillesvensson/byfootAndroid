package se.byfootapp;

import se.byfootapp.adapter.TabAdapter;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

public class MainByFootActivity extends FragmentActivity implements
                            TabListener {
    
    private TabAdapter tabAdapter;
    private ViewPager tabViewPager;
    private String[] fragments = {"Profil", "Places", "Saved"};
    private ActionBar actionBar;
    private SharedPreferences sp;
    private Resources resources;
    private static Integer selectedTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_by_foot);
        this.resources = this.getResources();
        //Set shared preferences
        this.sp = this.getSharedPreferences(
                            this.resources.getString(R.string.sp_name), Context.MODE_PRIVATE);
        
        //Adapter and pager setup
        this.tabAdapter = new TabAdapter(this.getSupportFragmentManager());
        this.tabViewPager = (ViewPager)findViewById(R.id.pager);
        this.tabViewPager.setAdapter(tabAdapter);
        this.tabViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        actionBar.setSelectedNavigationItem(position);
                    }
                });
        
        //Actionbar setup
        this.actionBar = getActionBar();
        this.actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        for(String tabName : fragments){
            Tab tab = this.actionBar.newTab();
            tab.setText(tabName);
            tab.setTabListener(this);
            this.actionBar.addTab(tab);
        }
        logIn();
    }
    
    @Override
    public void onResume(){
        super.onResume();
        if(this.actionBar != null && this.tabViewPager != null){
            this.actionBar.setSelectedNavigationItem(selectedTab);
            this.tabViewPager.setCurrentItem(selectedTab);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_by_foot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onTabReselected(Tab arg0, FragmentTransaction arg1) {   
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        this.tabViewPager.setCurrentItem(tab.getPosition());
        selectedTab = tab.getPosition();
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }
    
    private void logIn(){
        //Start facebook login
        Session.openActiveSession(this, true, new Session.StatusCallback() {         
            @Override
            public void call(Session session, SessionState state, Exception exception) {
                if(session.isOpened()){
                    Request.newMeRequest(session, new Request.GraphUserCallback() {
                        
                        @Override
                        public void onCompleted(GraphUser user, Response response) {
                            if(user != null){
                                Editor edit = sp.edit();
                                edit.putString(
                                        resources.getString(R.string.user_first_name),
                                        user.getFirstName());
                                edit.putString(resources.getString(R.string.user_last_name), user.getLastName());
                                edit.putString(resources.getString(R.string.user_birthday), user.getBirthday());
                                edit.putString(resources.getString(R.string.user_id), user.getId());
                                edit.commit();
                            }
                        }
                    }).executeAsync();
                }
            }
        });
    }
    
    public static void setTab(int newTab){
        selectedTab = newTab;
    }
    
    public void logOut(View view){
        Session fbSession = Session.getActiveSession();
        if(fbSession.isOpened()){
            fbSession.closeAndClearTokenInformation();
            logIn();
        }
    }
}
