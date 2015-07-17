package com.example.community_list_post;
 
import java.util.HashMap;
import java.util.List;

import com.example.community_list_post.adapter.listview_adapter;
import com.example.community_list_post.database.DBController;
import com.example.community_list_post.database.localisationservice;
import com.example.community_list_post.database.synch_service;
import com.example.community_list_post.listwidget.RefreshableListView;
import com.example.community_list_post.listwidget.RefreshableListView.OnPullUpUpdateTask;
import com.example.community_list_post.listwidget.RefreshableListView.OnUpdateTask;
import com.example.community_list_post.model.post;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
 
public class events extends Fragment {
	 
	private listview_adapter adapter;
	DBController controller = new DBController(this.getActivity());
	HashMap<String, String> queryValues;
	ProgressDialog proDialog;
	ProgressDialog dialog;
    ImageButton likebutton ;
    Intent serviceIntent , localizationintent;
    LinearLayout layout ;
    RefreshableListView listView ;
    List<post> postList ;
    DBController db; 
    TextView titlelist ;
    int categoryid ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    
        View rootView = inflater.inflate(R.layout.general_list, container, false);
        
       super.onCreateView(inflater, container, savedInstanceState);
     

	 //////////////////////////// listview update ////////////
		
          
        return rootView;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
    	super.onActivityCreated(savedInstanceState);
    	
    	  proDialog = new ProgressDialog(this.getActivity());
          proDialog.setMessage("Loading post please wait.....");
          layout = (LinearLayout)getActivity().findViewById(R.id.progressbar_view);
   		//////////////////////// start service ///////////////////////////
          localizationintent = new Intent(this.getActivity(), localisationservice.class);
   	      serviceIntent = new Intent(this.getActivity(), synch_service.class);
   	   getActivity().startService(localizationintent);
   	      getActivity().startService(serviceIntent);
   	     //////////////////////// load data ////////////////////////////
   	      categoryid = 2;
   	      db = new DBController(this.getActivity());
   	      listView = (RefreshableListView)getActivity().findViewById(R.id.list);
   	      postList = db.getAllposts1(categoryid);
   	      adapter = new listview_adapter(this.getActivity(), postList);
   	      titlelist= (TextView) getActivity().findViewById(R.id.titlelist);
   	      titlelist.setText("Events");
   	      ///////////////////////////////// test list content ///////////////
   	    
   	            thread.start(); 

  	    ////////////////////////// spinner ////////////////////////////////////
   	    
   	            
   	  ////////////////////////////// update listview pullup  ///////////////////////////////// 
  	    listView.setOnUpdateTask(new OnUpdateTask() {

			public void updateBackground() {
				// simulate long times operation.
				   
			
			}

			public void updateUI() {
				listView.setAdapter(null);
				resetadapter();
				listView.setAdapter(adapter);
				Toast.makeText(getActivity(), "update UI", Toast.LENGTH_LONG).show();
			}

			public void onUpdateStart() {
				getActivity().startService(serviceIntent);
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
				
				
			}

		});
	///////////////////////////////////end update listview pullup ///////////////////////
    	
    }


	public void resetadapter(){
		 //listView.setAdapter(null);
		 DBController db = new DBController(this.getActivity());
		 List<post> postList = db.getAllposts1(categoryid);
		adapter = new listview_adapter(this.getActivity(), postList);
		adapter.notifyDataSetChanged();
	 }
    
    
    /////////////////   update de listview           ////////////////
   
    	
    	
    	Runnable myRunnable = new Runnable() {
    	      @Override
    	      public void run() {
    	           while (postList.isEmpty()) {
    	        	   
    	        	   try {
    	        		test();
    	                Thread.sleep(1000); // Waits for 1 second (1000 milliseconds)
    	                listView.setVisibility(View.GONE);
    	            	layout.setVisibility(View.VISIBLE);
    	            	
    	        	   } catch (Exception e) {
    	     	           e.printStackTrace();
    	     	       }
    	        	   
    	           }
            
    	           datafull();
    		
    	}
    	      
    	};
    	
  /////////
    	Thread thread = new Thread() {
            @Override
            public void run() {
       
          	   try {
          		 while (postList.isEmpty()) {
                   //load your data here (fill Adapter)!
               	layout.setVisibility(View.VISIBLE);
               	getActivity().startService(serviceIntent);
             	     //////////////////////// load data ////////////////////////////  
               	postList = db.getAllposts1(categoryid); 
             	     resetadapter();
				Thread.sleep(2000);
				 
          		 }
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // fill ListView, dismiss Dialog
                        listView.setAdapter(adapter);
                        layout.setVisibility(View.GONE);
                        }
                    });
                }
            };
    	
    	
  /////  	
    
    public void datafull(){
    	adapter = new listview_adapter(this.getActivity(), postList);
    	layout.setVisibility(View.GONE);
    	listView.setAdapter(adapter);
    	listView.setVisibility(View.VISIBLE);
    	listView.setAdapter(adapter);
    }  	
    public void test(){
		 List<post> postList = db.getAllposts1(categoryid);
		 adapter = new listview_adapter(this.getActivity(), postList);
    } 	
    //////////////////////////////////////////////////////////////////////////   
    //////////// press back button //////

    
    
    
    
    
    ////////////////////////////////////////
}