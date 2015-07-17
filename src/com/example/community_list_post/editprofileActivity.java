/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package com.example.community_list_post;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.community_list_post.R.layout;
import com.example.community_list_post.database.DBController;
import com.example.community_list_post.database.localisationservice;
import com.example.community_list_post.database.synch_service;
import com.example.community_list_post.library.BootstrapEditText;
import com.example.community_list_post.library.DatabaseHandler;
import com.example.community_list_post.library.UserFunctions;
import com.example.community_list_post.localisation.wheelpic.categorybusiness;
import com.example.community_list_post.model.localisation;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class editprofileActivity extends Activity  {
	Button btnRegister;
	Button btnLinkToLogin;
	EditText inputFullName;
	EditText inputEmail;
	EditText inputPassword ;
	
	TextView registerErrorMsg;
	Location location;
	 String localisastr;
	 localisation localisa;
	 DBController db;
	 List<localisation> local;
	 
	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "uid";
	private static String KEY_NAME = "name";
	private static String KEY_EMAIL = "email";
	private static String DATE = "date";
	UserFunctions userFunction ;
	JSONObject json , json_user;
	TextView fullname ;
	Intent localizationintent;
	LinearLayout layout ;
	localisationservice  locationserv ;
	DatabaseHandler data;
	String emails;
	synch_service environ;
	ImageButton getimage;
	/////////////////// date picker////////
	private TextView tvDisplayDate;
	private DatePicker dpResult;
	private Button btnChangeDate;
     
	private int year;
	private int month;
	private int day;
	BootstrapEditText address;
	static final int DATE_DIALOG_ID = 999;
	private FragmentActivity myContext;
	//////////////////////////////
	@Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.editprofilenew);
	        ActionBar actionBar = getActionBar(); 
			  actionBar.hide();
	        ImageButton getadress = (ImageButton)findViewById(R.id.getadress);
	        ImageButton getimage = (ImageButton)findViewById(R.id.img1);
	    	fullname = (TextView)findViewById(R.id.fullname);
	    	address = (BootstrapEditText)findViewById(R.id.address);
	    	data =  new DatabaseHandler(this);
	    	String getemail =getuserinfos();
	    	 localisa = new localisation();
			Toast.makeText(getApplicationContext(), getemail, Toast.LENGTH_LONG).show();
			localizationintent = new Intent(this, localisationservice.class);
			  startService(localizationintent);
			  address.setText("nothing");
			  db = new DBController(this);
			  local = db.getlocalisation(); 
			  layout =(LinearLayout)findViewById(R.id.searchlocation);
			  //layout.setVisibility(View.GONE);
			  localisa = db.specifilocalisation();
			  //////////////////////// spinners dob //////////////////
			  
			
			  
			  
			  //////////////////////////////////////////////////////
			  
			getadress.setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
				    
					//alertMessage();
					
				    //address.setText( getlocalisation());
				  
				}
			});
			
			getimage.setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
				    
					Intent profilepic = new Intent(getApplicationContext(), profilepic.class);
					profilepic.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(profilepic);
				  
				}
			});
			
				   
				    
				  
			getinfos(getemail);
			getcompleteinfos();
			
	    	
	    }
	   public String getlocalisation()
	   {
		String currentplace ="";
		currentplace = locationserv.currentlocation;
		if(currentplace ==""||currentplace==null){
			currentplace = localisa.getcontent();	
		}
		   
		   return currentplace;   
		   
	   }
	   public String getuserinfos(){
		HashMap<String, String> user = data.getUserDetails();
		
		emails = user.get("email");
		 return emails ;  
	   }
	   //////////////////////////////////////////// get all infos ///////////////////////////
	   public void getcompleteinfos(){
			
		      address.setText(environ.homelocation);
		        
		    }
		    
		   /////////////////// update sqlite /////////////////////
		    
			 public void updateSQLitecomments(String response){
			       
		   }
	  ////////////////////////////////////////////////////////////////////////////////////////
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
	    
	   /////////////////// update sqlite /////////////////////
	    
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
		                    fullname.setText(obj.get("fullname").toString());
		                  
		                }
		                // Reload the Main Activity
		                
		            }
		        } catch (JSONException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
		        //reloadActivity();
		    }
	     ///////////////////////adress picker //////////////
		 public void alertMessage() {
             DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                          switch (which) {
                          case DialogInterface.BUTTON_POSITIVE:
                                 // Yes button clicked
                                 Toast.makeText(getApplicationContext(), "Yes Clicked",
                                               Toast.LENGTH_LONG).show();
                                 address.setText( getlocalisation());
                                 break;

                          case DialogInterface.BUTTON_NEGATIVE:
                                 // No button clicked
                                 // do nothing
                                 Toast.makeText(getApplicationContext(), "No Clicked",
                                               Toast.LENGTH_LONG).show();
                                 address.setText( "Go to your place");
                                 break;
                          }
                    }
             };

             AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
             builder.setMessage("Are you At your home location?")
                          .setPositiveButton("Yes", dialogClickListener)
                          .setNegativeButton("No", dialogClickListener).show();
		 }
		 
		 //////////////////////////////////////////////////
	
	   //////////////////////// date picker ////////////////
		
		
		 
     /////////////////////////////////////////////////////		 
			
	   
}
