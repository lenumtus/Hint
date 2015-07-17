
package com.example.community_list_post;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.community_list_post.library.BootstrapButton;
import com.example.community_list_post.library.BootstrapEditText;
import com.example.community_list_post.library.DatabaseHandler;
import com.example.community_list_post.library.UserFunctions;
import com.example.community_list_post.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	BootstrapButton btnRegister;
	BootstrapButton btnLinkToLogin;
	BootstrapEditText inputFullName;
	BootstrapEditText inputEmail;
	BootstrapEditText inputPassword;
	TextView registerErrorMsg;
	
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
	Spinner sp1,sp2,sp3;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registernew);
		
		// Importing all assets like button, text fields
		inputFullName = (BootstrapEditText) findViewById(R.id.registerName);
		inputEmail = (BootstrapEditText) findViewById(R.id.registerEmail);
		inputPassword = (BootstrapEditText) findViewById(R.id.registerPassword);
		btnRegister = (BootstrapButton) findViewById(R.id.btnRegister);
		btnLinkToLogin = (BootstrapButton) findViewById(R.id.btnLinkToLoginScreen);
		registerErrorMsg = (TextView) findViewById(R.id.register_error);
		userFunction = new UserFunctions();
	    sp1=(Spinner) findViewById (R.id.spy1);
	    ArrayAdapter<CharSequence> ar= ArrayAdapter.createFromResource(this, R.array.mySp,android.R.layout.simple_list_item_1);
	    ar.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
	    sp1.setAdapter(ar);
	    
	    sp2=(Spinner) findViewById (R.id.spy2);
	    ArrayAdapter<CharSequence> ar1= ArrayAdapter.createFromResource(this, R.array.spinner,android.R.layout.simple_list_item_1);
	    ar1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
	    sp2.setAdapter(ar1);
	    
	    sp3=(Spinner) findViewById (R.id.spy3);
	    ArrayAdapter<CharSequence> ar11= ArrayAdapter.createFromResource(this, R.array.mySpinner,android.R.layout.simple_list_item_1);
	    ar11.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
	    sp3.setAdapter(ar11);
		// Register Button Click event
		btnRegister.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View view) {
				final String name = inputFullName.getText().toString();
				final String email = inputEmail.getText().toString();
				final String password = inputPassword.getText().toString();
				//UserFunctions userFunction = new UserFunctions();
				
				//////////////////////
				Thread thread = new Thread(new Runnable()
				{
				    @Override
				    public void run()
				    {
				    	json = userFunction.registerUser(name, email, password);
				    	
				    	try {
							   
							if (json.getString(KEY_SUCCESS) != null) {
								
								
								
								///// update ui ///
								
								  runOnUiThread(new Runnable() {
				                        @Override
				                        public void run() {
				                        	registerErrorMsg.setText("insert successfully");
				                        	
				                        }
				                    });
								
								
								
								////////////////
								String res = json.getString(KEY_SUCCESS); 
								if(Integer.parseInt(res) == 1){
									// user successfully registred
									// Store user details in SQLite Database
									DatabaseHandler db = new DatabaseHandler(getApplicationContext());
									 json_user = json.getJSONObject("user");
									 
									// Clear all previous data in database
									userFunction.logoutUser(getApplicationContext());
									final String username , emailuser , createdat ;
									username = json_user.getString(KEY_NAME);
									emailuser = json_user.getString(KEY_EMAIL) ;
									//createdat = json_user.getString(DATE);
									runOnUiThread(new Runnable() {
				                        @Override
				                        public void run() {
				                        	Toast.makeText(getBaseContext(), "yeppp", Toast.LENGTH_LONG).show();
				                        }
				                    });
									db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_EMAIL));
									 

									// Launch Dashboard Screen
									Intent dashboard = new Intent(getApplicationContext(), MainActivity.class);
									// Close all views before launching Dashboard
									dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(dashboard);
									// Close Registration Screen
									finish();
								}else{
									// Error in registration
									///// update ui ///
									
									  runOnUiThread(new Runnable() {
					                        @Override
					                        public void run() {
					                       registerErrorMsg.setText("Error occured in registration");
					                       
					                        }
					                    });
									
									
									
									////////////////
									
								}
								}
								
							
						} catch (JSONException e) {
							e.printStackTrace();
						}
				    }
				});
				thread.start();
				
				
				// check for login response
			
			}
		});

		// Link to Login Screen
		btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(),
						LoginActivity.class);
				startActivity(i);
				// Close Registration View
				finish();
			}
		});
	}
	
	

	
}
