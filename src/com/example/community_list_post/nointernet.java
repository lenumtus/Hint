package com.example.community_list_post;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
 
public class nointernet extends Activity {
 
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nointernet);
        
    }
 
}