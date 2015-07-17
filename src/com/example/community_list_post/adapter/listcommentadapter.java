package com.example.community_list_post.adapter;

import com.example.community_list_post.General_list;
import com.example.community_list_post.LoginActivity;
import com.example.community_list_post.R;
import com.example.community_list_post.list_comments;
import com.example.community_list_post.app.AppController;
import com.example.community_list_post.model.comments;
import com.example.community_list_post.model.post;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class listcommentadapter extends BaseAdapter implements ListAdapter{
	private Activity activity;
	private LayoutInflater inflater;
	private List<comments> commentItems;
	private String ads ;
	int numlike;
	private int success;
	private static final String TAG_SUCCESS = "success";
	ProgressDialog pDialog;
	private JSONObject json;
	private JSONParser jsonParser;
	Fragment fragment = null;
	private static final String URL = "http://172.168.0.8/community/updatelike.php";
	/////////// like ////////

	
	
	
	///////////////////////////
//	private Context context;

	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public listcommentadapter(Activity activity, List<comments> commentsItems) {
		this.activity = activity;
		this.commentItems = commentsItems;
	}

	@Override
	public int getCount() {
		return commentItems.size();
	}

	@Override
	public Object getItem(int location) {
		return commentItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		
		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.list_comment, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
		NetworkImageView thumbNail = (NetworkImageView) convertView
				.findViewById(R.id.thumbnail);
		TextView title = (TextView) convertView.findViewById(R.id.title);
		TextView content = (TextView) convertView.findViewById(R.id.content);
		TextView year = (TextView) convertView.findViewById(R.id.releaseYear);
		
        
        
        ///////////////////////////////// like button //////////////////////
        
       
        
        ///////////////////////////////////////////////////////////////////////
		
        
        /////////////////////////////////////////
        
  
        
        
        
        
        
        
        //////////////////////////////////////////
        
        

		comments m = commentItems.get(position);

		// thumbnail image
		thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
		
		// title
		title.setText(m.getTitle());
		
		// content
		content.setText(String.valueOf(m.getcontent()));
	    //like
		year.setText(String.valueOf(m.getdate()));
		
	 

		return convertView;
	}
	

	
	
	/////////////////// update like ///////////////////////////////////////////////////
	
		
		
		
		
		
		
   ////////////////////////////////////////////////////////////////////////////		
	}

	

	

	

