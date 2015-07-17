package com.example.community_list_post.database;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.community_list_post.postactivity;
import com.example.community_list_post.localisation.getReverseGeoCoding;
import com.example.community_list_post.localisation.parser_Json;
import com.example.community_list_post.posting.GPSTracker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.community_list_post.library.JSONParseraddress;

public class localisationservice extends Service {
	GPSTracker gps;
	getReverseGeoCoding geo ;
	JSONObject jobj = null;
	 JSONParseraddress jsonparser = new JSONParseraddress();
	 DBController db;
	 String te;
	  double latitude, longitude , longtest;
	 private String test ="" , Address1 = "", Address2 = "", City = "", State = "", Country = "", County = "", PIN = "";
	 public static String currentlocation;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	 @Override
	    public void onCreate() {
	        Toast.makeText(this, "localisation Service was created", Toast.LENGTH_LONG).show();
	       // te =localize (); 
	        if(
	        		isOnline()){ te = localize();}
	        else{
	        	Toast.makeText(this, " "+currentlocation, Toast.LENGTH_SHORT).show();
	        	this.onDestroy();
	        }
	    }
	    @Override
	    public void onStart(Intent intent, int startId) {
	    	if(isOnline()){
	    	db = new DBController(this);
	        te = localize();
	        //longtest = localize();
	      if(te==""||te==null){
	    	  Toast.makeText(this, "No Area available ye ", Toast.LENGTH_SHORT).show();
	      }else{
	    	  db.addlocalisation(te);
		    	Toast.makeText(this, " you are at "+te, Toast.LENGTH_SHORT).show();
		    	currentlocation = te;
	    	  
	      }
	    	}else{ currentlocation = "No internet connection"; 
	    	
	    	 Toast.makeText(this, " "+currentlocation, Toast.LENGTH_SHORT).show();
	    	
	    	}
	    }
	    @Override
	    public void onDestroy() {
	        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
	 
	    }
	    
	    
	    /////////////////////////////// functions /////////////////////
	
	    //////////////////////////////////////////////////////////////
	    public String localize (){
	    	
	    	gps = new GPSTracker(this);	
			geo = new getReverseGeoCoding();
			if(gps.canGetLocation()){
                 
               latitude = gps.getLatitude();
              longitude = gps.getLongitude();
               // postal = geo.getCountry();
               
                
                
                
                /////////////////
             
            	class retrievedata extends AsyncTask<String,String,String>{
            		 
            		@Override
            		protected String doInBackground(String... arg0) {
            		// TODO Auto-generated method stub
            		jobj = jsonparser.makeHttpRequest("http://maps.googleapis.com/maps/api/geocode/json?latlng="+ latitude +","+ longitude +"&sensor=true");
            		 
            		// check your log for json response
            		Log.d("Login attempt", jobj.toString());
            		 
            		try {
            			 JSONObject jsonObj = parser_Json.getJSONfromURL("http://maps.googleapis.com/maps/api/geocode/json?latlng="+ latitude +","+ longitude +"&sensor=no");
	                        String Status = jsonObj.getString("status");
	                        if (Status.equalsIgnoreCase("OK")) {
	                            JSONArray Results = jsonObj.getJSONArray("results");
	                            JSONObject zero = Results.getJSONObject(0);
	                            JSONArray address_components = zero.getJSONArray("address_components");

	                            for (int i = 0; i < address_components.length(); i++) {
	                                JSONObject zero2 = address_components.getJSONObject(i);
	                                String long_name = zero2.getString("long_name");
	                                JSONArray mtypes = zero2.getJSONArray("types");
	                                String Type = mtypes.getString(0);

	                                if (TextUtils.isEmpty(long_name) == false || !long_name.equals(null) || long_name.length() > 0 || long_name != "") {
	                                    if (Type.equalsIgnoreCase("street_number")) {
	                                        Address1 = long_name + " ";
	                                    } else if (Type.equalsIgnoreCase("route")) {
	                                        Address1 = Address1 + long_name;
	                                    } else if (Type.equalsIgnoreCase("sublocality")) {
	                                        Address2 = long_name;
	                                    } else if (Type.equalsIgnoreCase("sublocality_level_1")) {
	                                        test = long_name;
	                                    }else if (Type.equalsIgnoreCase("locality")) {
	                                        // Address2 = Address2 + long_name + ", ";
	                                        City = long_name;
	                                    } else if (Type.equalsIgnoreCase("administrative_area_level_2")) {
	                                        County = long_name;
	                                    } else if (Type.equalsIgnoreCase("administrative_area_level_1")) {
	                                        State = long_name;
	                                    } else if (Type.equalsIgnoreCase("country")) {
	                                        Country = long_name;
	                                    } else if (Type.equalsIgnoreCase("postal_code")) {
	                                        PIN = long_name;
	                                    }
	                                }

	                                // JSONArray mtypes = zero2.getJSONArray("types");
	                                // String Type = mtypes.getString(0);
	                                // Log.e(Type,long_name);
	                            }
	                        }
            		} catch (JSONException e) {
            		// TODO Auto-generated catch block
            		e.printStackTrace();
            		}
					return test;
            		}
            		protected void onPostExecute(String Address1){
            		 
            		//Toast.makeText(getApplicationContext()," "+test , Toast.LENGTH_LONG).show();
            		}
            		 
            		}
                
                
            	 new retrievedata().execute();
             
                
               ////////////////////// 
                
                
     
                // \n is for new line
                
                
			}else{
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gps.showSettingsAlert();
            }
			
			return test;	
			
	   
	    }
	    
		protected void runOnThread(Runnable runnable) {
			// TODO Auto-generated method stub
			
		}
	    
	    
		
		
		///////////////////////////////////check online ///////////////
	    public boolean isOnline() {
	        ConnectivityManager cm =
	            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

	        return cm.getActiveNetworkInfo() != null && 
	           cm.getActiveNetworkInfo().isConnectedOrConnecting();
	    }
	    
	    
	    
	    
	    
	    
	    
	    //////////////////////////////////////////////////////////////


}
    		