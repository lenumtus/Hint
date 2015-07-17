package com.example.community_list_post.database;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.community_list_post.postactivity;
import com.example.community_list_post.library.DatabaseHandler;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


public class synch_service extends Service {
	ProgressDialog prgDialog;
	HashMap<String, String> queryValues;
	DBController controller = new DBController(this);
	SQLiteDatabase community ;
	DatabaseHandler data;
	int new_version , old_version ;
	public static Boolean isRunning ;
	public static String homelocation;
	public static String Gender ;
	public static String email ;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
    @Override
    public void onCreate() {
        //Toast.makeText(this, "Service was created", Toast.LENGTH_LONG).show();
    	data =  new DatabaseHandler(this);
    }
    @Override
    public void onStart(Intent intent, int startId) {
    	//Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        syncSQLiteMySQLDB();
        isRunning = true;
        //Toast.makeText(this, "Sync post finish", Toast.LENGTH_LONG).show();
        synccomments();
        Toast.makeText(this, "Sync comments finish", Toast.LENGTH_LONG).show();
         email = getuserinfos();
        
        getinfos(email);
    }
    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
 
    }
//************************************ take personnal information ****************
    public void getinfos(String em) {
        // Create AsycHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        // Http Request Params Object
        RequestParams params = new RequestParams();
        // Show ProgressBar
       // prgDialog.show();
        // Make Http call to getusers.php
        client.post("http://172.168.0.8/community/getuserinfos.php?email="+em, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(String response) {
                    // Hide ProgressBar
                   // prgDialog.hide();
                    
                    // Update SQLite DB with response sent by getusers.php
                	Toast.makeText(getApplicationContext(), "success good", Toast.LENGTH_LONG).show();
                	
                	treatresponse(response);
                    
                   
                }
                // When error occured
                @Override
                public void onFailure(int statusCode, Throwable error, String content) {
                    // TODO Auto-generated method stub
                    // Hide ProgressBar
                   // prgDialog.hide();
                    if (statusCode == 404) {
                        Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                    } else if (statusCode == 500) {
                        Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]",
                                Toast.LENGTH_LONG).show();
                    }
                }
        });
        
    }   
	 public void treatresponse(String response){
	        ArrayList<HashMap<String, String>> usersynclist;
	        usersynclist = new ArrayList<HashMap<String, String>>();
	        // Create GSON object
	       // Gson gson = new GsonBuilder().create();
	       
	        try {
	            // Extract JSON array from the response
	            JSONArray arr = new JSONArray(response);
	            System.out.println(arr.length());
	            // If no of array elements is not zero
	            if(arr.length() != 0){ 
	                // Loop through each array element, get JSON object which has userid and username
	                for (int i = 0; i < arr.length(); i++) {
	                	
	                    // Get JSON object
	                    JSONObject obj = (JSONObject) arr.get(i);
	                    System.out.println(obj.get("fullname"));
	                    homelocation = obj.get("homelocation").toString();
	                    Gender = obj.get("Gender").toString();
	                    Toast.makeText(getApplicationContext(), "your homelocation is" + homelocation, Toast.LENGTH_LONG).show();
	                    Toast.makeText(getApplicationContext(), "your Gender is" + Gender, Toast.LENGTH_LONG).show();
	                }
	                // Reload the Main Activity
	                
	            }
	        } catch (JSONException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        //reloadActivity();
	    }
    
    ////////////////////////////get user email ////
	  public String getuserinfos(){
			HashMap<String, String> user = data.getUserDetails();
			
			String emails = user.get("email");
			 return emails ;  
		   }
    
    
    
    
//********************************************************************************    
//************************************ synchronisation posts *********************
    public void syncSQLiteMySQLDB() {
        // Create AsycHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        // Http Request Params Object
        RequestParams params = new RequestParams();
        // Show ProgressBar
       // prgDialog.show();
        // Make Http call to getusers.php
        client.post("http://172.168.0.8/community/getallposts.php", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(String response) {
                    // Hide ProgressBar
                   // prgDialog.hide();
                    
                    // Update SQLite DB with response sent by getusers.php
                	
                    updateSQLite(response);
                    
                   
                }
                // When error occured
                @Override
                public void onFailure(int statusCode, Throwable error, String content) {
                  
                    
                   // prgDialog.hide();
                    if (statusCode == 404) {
                        Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                    } else if (statusCode == 500) {
                        Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]",
                                Toast.LENGTH_LONG).show();
                    }
                }
        });
        
    }
    
   /////////////////// update sqlite /////////////////////
    
	 public void updateSQLite(String response){
	        ArrayList<HashMap<String, String>> usersynclist;
	        usersynclist = new ArrayList<HashMap<String, String>>();
	        // Create GSON object
	       // Gson gson = new GsonBuilder().create();
	       
	        try {
	            // Extract JSON array from the response
	            JSONArray arr = new JSONArray(response);
	            System.out.println(arr.length());
	            // If no of array elements is not zero
	            if(arr.length() != 0){ 
	                // Loop through each array element, get JSON object which has userid and username
	                for (int i = 0; i < arr.length(); i++) {
	                	
	                    // Get JSON object
	                    JSONObject obj = (JSONObject) arr.get(i);
	                    System.out.println(obj.get("post_id"));
	                    
	                    System.out.println(obj.get("post_content"));
	                   
	                    System.out.println(obj.get("username"));
	                    
	                    System.out.println(obj.get("date_post"));
	          
	                    System.out.println(obj.get("postal_code"));
	                    System.out.println(obj.get("category_id"));
	                    System.out.println(obj.get("img_url"));
	                    System.out.println(obj.get("number_like"));
	                    System.out.println(obj.get("promoted"));
	                    System.out.println(obj.get("post_ads"));
	                    // DB QueryValues Object to insert into SQLite
	                    queryValues = new HashMap<String, String>();
	                    // Add userID extracted from Object
	                    queryValues.put("post_id_ser", obj.get("post_id").toString());
	                    queryValues.put("post_content", obj.get("post_content").toString());
	                    queryValues.put("post_title", obj.get("username").toString());
	                    queryValues.put("date_post", obj.get("date_post").toString());
	                    queryValues.put("postal_code", obj.get("postal_code").toString());
	                    queryValues.put("category_id", obj.get("category_id").toString());
	                    queryValues.put("img_url", obj.get("img_url").toString());
	                    queryValues.put("number_like", obj.get("number_like").toString());
	                    queryValues.put("promoted", obj.get("promoted").toString());
	                    queryValues.put("post_ads", obj.get("post_ads").toString());
	                    
	                    // Add userName extracted from Object
	                    // Insert User into SQLite DB
	                    
	                    controller.insertpost(queryValues);
	                    HashMap<String, String> map = new HashMap<String, String>();
	                    //Toast.makeText(getApplicationContext(), "inside", Toast.LENGTH_LONG).show();
	                    
	                    // Add status for each User in Hashmap
	           
	                   
	                }
	                // Reload the Main Activity
	                //reloadActivity();
	            }
	        } catch (JSONException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        //reloadActivity();
	    }
	 
	 //***************************************************************************
     //********************************** sync comments **************************
	 public void synccomments() {
	        // Create AsycHttpClient object
	        AsyncHttpClient client = new AsyncHttpClient();
	        // Http Request Params Object
	        RequestParams params = new RequestParams();
	        // Show ProgressBar
	       // prgDialog.show();
	        // Make Http call to getusers.php
	        client.post("http://172.168.0.8/community/getallcomments.php", params, new AsyncHttpResponseHandler() {
	                @Override
	                public void onSuccess(String response) {
	                    // Hide ProgressBar
	                   // prgDialog.hide();
	                    
	                    // Update SQLite DB with response sent by getusers.php
	                	
	                	updateSQLitecomments(response);
	                    
	                   
	                }
	                // When error occured
	                @Override
	                public void onFailure(int statusCode, Throwable error, String content) {
	                  
	                    
	                   // prgDialog.hide();
	                    if (statusCode == 404) {
	                        Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
	                    } else if (statusCode == 500) {
	                        Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
	                    } else {
	                        Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]",
	                                Toast.LENGTH_LONG).show();
	                    }
	                }
	        });
	        
	    }
	    
	   /////////////////// update sqlite /////////////////////
	    
		 public void updateSQLitecomments(String response){
		        ArrayList<HashMap<String, String>> usersynclist;
		        usersynclist = new ArrayList<HashMap<String, String>>();
		        // Create GSON object
		       // Gson gson = new GsonBuilder().create();
		       
		        try {
		            // Extract JSON array from the response
		            JSONArray arr = new JSONArray(response);
		            System.out.println(arr.length());
		            // If no of array elements is not zero
		            if(arr.length() != 0){ 
		                // Loop through each array element, get JSON object which has userid and username
		                for (int i = 0; i < arr.length(); i++) {
		                	
		                    // Get JSON object
		                    JSONObject obj = (JSONObject) arr.get(i);
		                    
		                    System.out.println("----------------- comments------------------------");
		                    
		                    System.out.println(obj.get("post_id"));
		                    
		                    System.out.println(obj.get("comments_contents"));
		                   
		                    System.out.println(obj.get("username"));
		                    System.out.println(obj.get("date_comments"));
		                    System.out.println(obj.get("img_url"));
		                    System.out.println(obj.get("like_comments"));
		                    // DB QueryValues Object to insert into SQLite
		                    queryValues = new HashMap<String, String>();
		                    // Add userID extracted from Object
		                    queryValues.put("post_id_ser", obj.get("post_id").toString());
		                    queryValues.put("comment_id_ser", obj.get("id_comments").toString());
		                    queryValues.put("comments_contents", obj.get("comments_contents").toString());
		                    queryValues.put("username", obj.get("username").toString());
		                    queryValues.put("date_comments", obj.get("date_comments").toString());
		                    queryValues.put("img_url", obj.get("img_url").toString());
		                    queryValues.put("number_like", obj.get("like_comments").toString());
		                    // Add userName extracted from Object
		                    // Insert User into SQLite DB
		                    System.out.println("------------------End comments-----------------------");
		                    controller.insertcomments(queryValues);
		                    HashMap<String, String> map = new HashMap<String, String>();
		                    //Toast.makeText(getApplicationContext(), "inside", Toast.LENGTH_LONG).show();
		                    
		                    // Add status for each User in Hashmap
		           
		                   
		                }
		                // Reload the Main Activity
		                //reloadActivity();
		            }
		        } catch (JSONException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
		        //reloadActivity();
		    }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 //*****************************************************************************
}