/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package com.example.community_list_post;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.community_list_post.library.BootstrapButton;
import com.example.community_list_post.library.BootstrapEditText;
import com.example.community_list_post.library.DatabaseHandler;
import com.example.community_list_post.library.UserFunctions;

public class LoginActivity extends Activity {
	BootstrapButton btnLogin;
	BootstrapButton btnLinkToRegister;
	BootstrapEditText inputEmail;
	BootstrapEditText inputPassword;
	TextView loginErrorMsg;
	UserFunctions userFunction ;
	JSONObject json;
	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "uid";
	private static String KEY_NAME = "name";
	private static String KEY_EMAIL = "email";
	private static String KEY_CREATED_AT = "created_at";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_fragmentnew);

		// Importing all assets like buttons, text fields
		inputEmail = (BootstrapEditText) findViewById(R.id.loginEmail);
		inputPassword = (BootstrapEditText) findViewById(R.id.loginPassword);
		btnLogin = (BootstrapButton) findViewById(R.id.first_sign_in);
		btnLinkToRegister = (BootstrapButton) findViewById(R.id.btnLinkToRegisterScreen);
		loginErrorMsg = (TextView) findViewById(R.id.login_error);
		userFunction = new UserFunctions();
		// Login button Click Event
		btnLogin.setOnClickListener(loginscreen);
	    btnLinkToRegister.setOnClickListener(registerscreen);
	}
	
	private View.OnClickListener loginscreen = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			
			if (isOnline()){
			
			final String email = inputEmail.getText().toString();
			final	String password = inputPassword.getText().toString();
				  userFunction = new UserFunctions();
				Log.d("Button", "Login");
				//JSONObject json = userFunction.loginUser(email, password);
                 ///////////////////
				
				Thread thread = new Thread(new Runnable()
				{
				    @Override
				    public void run()
				    {
				    	 json = userFunction.loginUser(email, password);
				    		// check for login response
							try { 
								if (json.getString(KEY_SUCCESS) != null) {
									loginErrorMsg = (TextView) findViewById(R.id.login_error);
									// update ui
									  runOnUiThread(new Runnable() {
					                        @Override
					                        public void run() {
					                        	loginErrorMsg.setText("");
					                        }
					                    });

					                
									/////////////////
									String res = json.getString(KEY_SUCCESS); 
									if(Integer.parseInt(res) == 1){
										// user successfully logged in
										// Store user details in SQLite Database
										DatabaseHandler db = new DatabaseHandler(getApplicationContext());
										JSONObject json_user = json.getJSONObject("user");
										
										// Clear all previous data in database
										userFunction.logoutUser(getApplicationContext());
										db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_EMAIL));						
										
										// Launch Dashboard Screen
										Intent dashboard = new Intent(getApplicationContext(), MainActivity.class);
										// Close all views before launching Dashboard
										dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
										startActivity(dashboard);
										
										// Close Login Screen
										finish();
									}else{
										// Error in login
										//////////////////// update ui
										  runOnUiThread(new Runnable() {
						                        @Override
						                        public void run() {
						                        	loginErrorMsg.setText("Incorrect username/password");
						                        }
						                    });

						               ///////////////////////// 
										
									}
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
				    }
				});
				thread.start();
				
				
		}else {
			
			
			Intent i = new Intent(getApplicationContext(),
					nointernet.class);
			startActivity(i);
			finish();
			
		}	
			
			}
		 
	};

	// Link to Register Screen
	private View.OnClickListener registerscreen = new View.OnClickListener() {

		public void onClick(View view) {
			Intent i = new Intent(getApplicationContext(),
					RegisterActivity.class);
			startActivity(i);
			finish();
		}
	};
	
	//////////////////////////// check internet //////////////////////////
    public boolean isOnline() {
        ConnectivityManager cm =
            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && 
           cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
	
	//////////////////////////////////////////////////////////////////////
}