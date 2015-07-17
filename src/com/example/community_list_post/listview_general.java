
package com.example.community_list_post;

import com.example.community_list_post.adapter.NavDrawerListAdapter;
import com.example.community_list_post.adapter.listview_adapter;
import com.example.community_list_post.app.AppController;
import com.example.community_list_post.database.DBController;
import com.example.community_list_post.database.synch_service;
import com.example.community_list_post.listwidget.RefreshableListView;
import com.example.community_list_post.model.NavDrawerItem;
import com.example.community_list_post.model.post;

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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

public class listview_general extends Activity {


 
	
	

	private listview_adapter adapter;
	DBController controller = new DBController(this);
	HashMap<String, String> queryValues;
	ProgressDialog prgDialog;
    int categoryid;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
//////////////////////slide menu /////////////////////
      
		
////////////////////////////////////////////////////////
///////////////////////// sync ///////////////////////////////////
		
		final Intent serviceIntent = new Intent(this, synch_service.class);
	    startService(serviceIntent);
		
		        //syncSQLiteMySQLDB();
////////////////////////////////////////////////////////////////////////
		        
		 DBController db = new DBController(this);
		//////////////fill userList inside Postlist///////
		
		///////////////////////////////////////////////
		 categoryid = 1 ;
		final RefreshableListView listView = (RefreshableListView) findViewById(R.id.list);
		List<post> postList = db.getAllposts1(categoryid);
		adapter = new listview_adapter(this, postList);
		listView.setAdapter(adapter);

//	        prgDialog.setCancelable(false);
	        //List<post> posts = db.getAllposts1();
//	        for (post cn : posts) {
//	        	//System.out.println(cn.getTitle());
//	        	 String log = "Title: "+cn.getTitle();
//	        	 Log.d("Title: ", log);
//	        	
//	    }

	 
	 //////////////////////////// listview update ////////////
		listView.setOnUpdateTask(new OnUpdateTask() {

			public void updateBackground() {
				// simulate long times operation.
				   
			
			}

			public void updateUI() {
				listView.setAdapter(null);
				resetadapter();
				listView.setAdapter(adapter);
				Toast.makeText(getApplicationContext(), "update UI", Toast.LENGTH_LONG).show();
			}

			public void onUpdateStart() {
				startService(serviceIntent);
			}

		});
	
		listView.setOnPullUpUpdateTask(new OnPullUpUpdateTask() {

			@Override
			public void onUpdateStart() {
				

			}

			@Override
			public void updateBackground() {
				// simulate long times operation.
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void updateUI() {
				//listItemDatas.add("list item" + listItemDatas.size());
				
			}

		});
	
	 
} 

	
	///////////////////////////////////// end on create /////////////////////
	 public void resetadapter(){
		 //listView.setAdapter(null);
		 DBController db = new DBController(this);
		 List<post> postList = db.getAllposts1(categoryid);
		adapter = new listview_adapter(this, postList);
		adapter.notifyDataSetChanged();
	 }

	 
	 /////////////////////////////////////////////////////////
	
}
