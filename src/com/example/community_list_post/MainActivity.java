package com.example.community_list_post;


import com.example.community_list_post.LoginActivity;
import com.example.community_list_post.R;
import com.example.community_list_post.library.UserFunctions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;




public class MainActivity extends Activity
{
	UserFunctions userFunctions;
	Button btnLogout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        /**
         * Dashboard Screen for the application
         * */        
        // Check login status in database
        userFunctions = new UserFunctions();
        if(userFunctions.isUserLoggedIn(getApplicationContext())){
        	//setContentView(R.layout.main_activity1);
        	Intent postsactivity = new Intent(getApplicationContext(), postactivity.class);
        	startActivity(postsactivity); 
        	finish();
        	
        }else{
        	// user is not logged in show login screen
        	Intent login = new Intent(getApplicationContext(), LoginActivity.class);
        	login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        	startActivity(login);
        	// Closing dashboard screen
        	finish();
        }
        
        
        
        
    }
}