package com.example.community_list_post;

import com.example.community_list_post.adapter.NavDrawerListAdapter;
import com.example.community_list_post.adapter.listview_adapter;
import com.example.community_list_post.app.AppController;
import com.example.community_list_post.database.DBController;
import com.example.community_list_post.database.synch_service;

import com.example.community_list_post.listwidget.RefreshableListView;
import com.example.community_list_post.model.NavDrawerItem;
import com.example.community_list_post.model.localisation;
import com.example.community_list_post.model.post;
import com.example.community_list_post.posting.newpost;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.community_list_post.listwidget.RefreshableListView.OnUpdateTask;
import com.example.community_list_post.listwidget.RefreshableListView.OnPullUpUpdateTask;
import com.example.community_list_post.listwidget.MotionEventCompat;
import com.example.community_list_post.listwidget.ListHeaderView;
import com.example.community_list_post.listwidget.ListBottomView;
import com.example.community_list_post.listwidget.extend.PullToRefreshListView;
import com.example.community_list_post.localisation.listbusiness;
import com.example.community_list_post.localisation.localisationActivity;
import com.example.community_list_post.localisation.wheelpic.categorybusiness;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

public class postactivity extends Activity {
	
	
	
	////////////////// sliding menu /////////
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
 
    // nav drawer title
    private CharSequence mDrawerTitle;
 
    // used to store app title
    private CharSequence mTitle;
      
    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
 
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter slideadapter;
   /////////////////////////////
    TextView titlelist;
	
	////////////////////////////////////////
	// Log tag
	private static final String TAG = postactivity.class.getSimpleName();

	// Movies json url
	private static final String url = "http://172.168.0.8/community/getallposts.php";
	private ProgressDialog pDialog;
	
	private RefreshableListView listView;
	private listview_adapter adapter;
	DBController controller = new DBController(this);
	HashMap<String, String> queryValues;
	ProgressDialog prgDialog;
	Spinner spinner;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		/////// three dot menu /////
		getOverflowMenu();
		
        titlelist =(TextView)findViewById(R.id.titlelist) ;
		    
		
		
		
		///////////////////////////

//////////////////////slide menu /////////////////////
        mTitle = mDrawerTitle = getTitle();
        
        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
 
        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);
 
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
 
        navDrawerItems = new ArrayList<NavDrawerItem>();
 
        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Find People
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Photos
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Communities, Will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        // Pages
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        // places
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
         
        
         
 
        // Recycle the typed array
        navMenuIcons.recycle();
 
        // setting the nav drawer list adapter
        slideadapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(slideadapter);
 
        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
       // getActionBar().setHomeButtonEnabled(true);
       ////////////////////////////// spinner //////////////
       
        
        
        
        /////////////////////////////////////////////////////
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ){
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }
 
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
 
        if (savedInstanceState == null) {
            // on first time display view for first nav item
           displayView(0);
        	
        }
		

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());	
	
	
}
	private class SlideMenuClickListener implements
    ListView.OnItemClickListener {
@Override
public void onItemClick(AdapterView<?> parent, View view, int position,
        long id) {
    // display view for selected nav drawer item
    displayView(position);
}
}

/**
* Diplaying fragment view for selected nav drawer list item
* */
private void displayView(int position) {
// update the main content by replacing fragments
Fragment fragment = null;

switch (position) {
case 0:
    fragment = new General_list();
    break;
case 1:
	   fragment = new events();
	    
	    break ;
case 2:
 
    Intent newpost = new Intent(getApplicationContext(), newpost.class);
	newpost.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	startActivity(newpost);
    break;
 
case 3:
	Intent profileactivity = new Intent(getApplicationContext(), editprofileActivity.class);
	profileactivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	startActivity(profileactivity);
    
    break;
case 4:
	Intent location = new Intent(getApplicationContext(), categorybusiness.class);
	location.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	startActivity(location);
    break;

case 5:
    Intent help = new Intent(getApplicationContext(), Help.class);
    help.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
 	startActivity(help);
     break;

default:
	fragment = new General_list();
    break;
}

if (fragment != null) {
    android.app.FragmentManager fragmentManager = getFragmentManager();
    fragmentManager.beginTransaction()
            .replace(R.id.frame_container, fragment).commit();

    // update selected item and title, then close the drawer
    mDrawerList.setItemChecked(position, true);
    mDrawerList.setSelection(position);
    setTitle(navMenuTitles[position]);
    mDrawerLayout.closeDrawer(mDrawerList);
} else {
    // error in creating fragment
    Log.e("MainActivity", "Error in creating fragment");
}
}
@Override
public void setTitle(CharSequence title) {
    mTitle = title;
    getActionBar().setTitle(mTitle);
}

/**
 * When using the ActionBarDrawerToggle, you must call it during
 * onPostCreate() and onConfigurationChanged()...
 */

@Override
protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    // Sync the toggle state after onRestoreInstanceState has occurred.
    mDrawerToggle.syncState();
}

@Override
public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    // Pass any configuration change to the drawer toggls
    mDrawerToggle.onConfigurationChanged(newConfig);
}
@Override
public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
getMenuInflater().inflate(R.menu.main, menu);
return true;
}


//////////////////// three dot menu ///////////////


private void getOverflowMenu() {

    try {
       ViewConfiguration config = ViewConfiguration.get(this);
       Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
       if(menuKeyField != null) {
           menuKeyField.setAccessible(true);
           menuKeyField.setBoolean(config, false);
       }
   } catch (Exception e) {
       e.printStackTrace();
   }
 }

Fragment fragment = null;
public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    case R.id.General:
    	
       fragment = new General_list();
        break;
    case R.id.event:

    	fragment = new events();
        break;
    default:
    	
        fragment = new General_list();
        break;
    }

    
    if (fragment != null) {
        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
        .replace(R.id.frame_container, fragment).commit();

    }
    else {
        // error in creating fragment
        Log.e("MainActivity", "Error in creating fragment");
    }
    return false;
}





///////////////////////////////////////////////////


//////////////////////////////////////////////////


}
	










