package com.example.community_list_post;
import com.example.community_list_post.library.BootstrapButton;

import com.example.community_list_post.R;
import android.app.ActionBar;
import android.app.Activity;
import android.app.TabActivity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.method.LinkMovementMethod;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;


@SuppressWarnings("deprecation")
public class BusinessProfile extends Activity {
	Spinner sp;
	 Button home,current;
	 RatingBar ratingBar;
	 TextView txtRatingValue;
	 //Button btnSubmit;
	 BootstrapButton btnSubmit;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.companyprof);
			
			ActionBar actionBar = getActionBar(); 
	        actionBar.hide();
	        
	        addListenerOnRatingBar();
	    	addListenerOnButton();
			
	}
	
	public void addListenerOnRatingBar() {
		 
		ratingBar = (RatingBar) findViewById(R.id.ratingBar);
		txtRatingValue = (TextView) findViewById(R.id.txtRatingValue);
	 
		//if rating value is changed,
		//display the current rating value in the result (textview) automatically
		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			public void onRatingChanged(RatingBar ratingBar, float rating,
				boolean fromUser) {
	 
				txtRatingValue.setText(String.valueOf(rating));
	 
			}
		});
	  }
	
	public void addListenerOnButton() {
		 
		ratingBar = (RatingBar) findViewById(R.id.ratingBar);
		btnSubmit = (BootstrapButton) findViewById(R.id.btnSubmit);
	 
		//if click on me, then display the current rating value.
		btnSubmit.setOnClickListener(new View.OnClickListener() {
	 
			@Override
			public void onClick(View v) {
	 
				Toast.makeText(BusinessProfile.this,
					String.valueOf(ratingBar.getRating()),
						Toast.LENGTH_SHORT).show();
	 
			}
	 
		});
	 
	  }
    
}
