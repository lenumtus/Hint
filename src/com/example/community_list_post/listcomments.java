package com.example.community_list_post;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.community_list_post.adapter.listcommentadapter;
import com.example.community_list_post.adapter.listview_adapter;
import com.example.community_list_post.app.AppController;
import com.example.community_list_post.database.DBController;
import com.example.community_list_post.database.synch_service;
import com.example.community_list_post.library.DatabaseHandler;
import com.example.community_list_post.library.UserFunctions;
import com.example.community_list_post.listwidget.RefreshableListView;
import com.example.community_list_post.listwidget.RefreshableListView.OnPullUpUpdateTask;
import com.example.community_list_post.listwidget.RefreshableListView.OnUpdateTask;
import com.example.community_list_post.model.comments;
import com.example.community_list_post.model.post;
import com.example.community_list_post.library.commentsfunctions;

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
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
 
public class listcomments extends Activity {
	 
	private listcommentadapter adapter , adapter1;
	DBController controller = new DBController(this);
	HashMap<String, String> queryValues;
	ProgressDialog proDialog;
	ProgressDialog dialog;
    ImageButton likebutton ;
    Intent serviceIntent ;
    LinearLayout layout ;
    RefreshableListView listView ;
    List<comments> commentList ;
    post specificpost;
    comments specificcomments , newspecificomments;
    List<comments> listcomments , newcomments ;
    DBController db; 
    TextView releaseYear,promoted , content,title ;
    NetworkImageView thumbnail ;
    ImageLoader imageLoader;
    Button comments , submitcomments;
    ListView listview;
    EditText commentcont ;
    int  id_ser ;
    private static String KEY_SUCCESS = "success";
    JSONObject json , json_user;
    commentsfunctions commentsfunctions;
    String userid;
    String comments_content;
    String post_id;
   
    @Override
    
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();
		setContentView(R.layout.comments_list);
		imageLoader = AppController.getInstance().getImageLoader();
		db = new DBController(this);
		///////////////////////////////
		Intent intent = getIntent();
		String idstr = intent.getStringExtra("id");
		id_ser = Integer.parseInt(idstr);
		Toast.makeText(getApplicationContext(), ""+id_ser, Toast.LENGTH_LONG).show();
		///////////////////////////////
		listview= (ListView)findViewById(R.id.listcomments);
		specificpost = db.specificpostfor(id_ser);
		specificcomments = db.specificcomment();
		releaseYear = (TextView) findViewById(R.id.releaseYear);
		content = (TextView) findViewById(R.id.content);
		title = (TextView) findViewById(R.id.title);
		thumbnail =(NetworkImageView)findViewById(R.id.thumbnail);
		comments = (Button)findViewById(R.id.btn_addcontact_add);
		title.setText(specificpost.getTitle());
		content.setText(specificpost.getcontent());
		releaseYear.setText(specificpost.getdate());
		submitcomments=(Button)findViewById(R.id.submitcomments);
		commentcont = (EditText)findViewById(R.id.commentcont);
	   // comments.setText(String.valueOf(specificpost.getid_ser()));
	    comments.setText(String.valueOf(id_ser));
		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
		NetworkImageView thumbNail = (NetworkImageView)findViewById(R.id.thumbnail);
		thumbNail.setImageUrl(specificpost.getThumbnailUrl(), imageLoader);
		TextView commenttext = (TextView)findViewById(R.id.commenttext);
		
		
		///////////////////// load data into list /////////////
		listcomments = db.getAllcomments();
		newspecificomments = new comments();
		newcomments = new ArrayList<comments>();
		 adapter = new listcommentadapter(this, listcomments);
		 adapter.notifyDataSetChanged();
		listview.setAdapter(adapter);
		
		//commenttext.setText(specificcomments.getcontent());
		commentsfunctions = new commentsfunctions();
		///////////////////////////////////////////////////////
		
		submitcomments.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				 userid = "55";
				 comments_content = commentcont.getText().toString();
				 post_id= comments.getText().toString();
				
				 newspecificomments.setcontent(comments_content);
				   newspecificomments.setdate("2015-02-02");
				   newspecificomments.setThumbnailUrl("http://172.168.0.8/community/images/img1.jpeg");
				   newspecificomments.setTitle("pierre");
				   listcomments.add(newspecificomments);
				   
				   resetlistview();
				
				
				/////////////////////////////////////
				Thread thread = new Thread(new Runnable()
				{
				    
				    public void run()
				    {
				    	json = commentsfunctions.commenting(userid, comments_content, post_id);
				    	
				    	try {
							   
							if (json.getString(KEY_SUCCESS) != null) {
								
								
								
								///// update ui ///
								
								  runOnUiThread(new Runnable() {
				                        @Override
				                        public void run() {
				                        	
				                        	Toast.makeText(getBaseContext(), "comments successfully saved", Toast.LENGTH_LONG).show();
				                        	resetlistview();
				                        }
				                    });
								
								
								}else{
									// Error in registration
									///// update ui ///
									
									  runOnUiThread(new Runnable() {
					                        @Override
					                        public void run() {
					                        	Toast.makeText(getBaseContext(), "comments not saved", Toast.LENGTH_LONG).show();
					                            
					                        }
					                    });
									
									
									
									////////////////
									
								}
								
								
							
						} catch (JSONException e) {
							e.printStackTrace();
						}
				    }
				});
				thread.start();
			
			/////////////////////////////////////////////
			}
			
		});
		
   } 
    
   //////////////////////////////////////////////////////////////////////////   
   //////////// press back button //////

   public void resetlistview()
   {
	   serviceIntent = new Intent(this, synch_service.class);
   	   startService(serviceIntent);
   	 adapter =new listcommentadapter(this, listcomments);
	   listview.setAdapter(adapter);
	   adapter.notifyDataSetChanged();
	   commentcont.setText(" ");
   }
   
   
   
   
		
          
     
    }
    ////////////////////////////////////////
