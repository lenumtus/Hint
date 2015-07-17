package com.example.community_list_post.adapter;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.example.community_list_post.MainActivity;
import com.example.community_list_post.R;
import com.example.community_list_post.listcomments;
import com.example.community_list_post.app.AppController;
import com.example.community_list_post.model.post;
import com.example.community_list_post.library.postFunctions;
import com.example.community_list_post.library.updatelike;

public class listview_adapter extends BaseAdapter implements ListAdapter{
	private Activity activity;
	private LayoutInflater inflater;
	private List<post> postItems;
	private String ads ;
	int numlike;
	private int success;
	private static final String TAG_SUCCESS = "success";
	ProgressDialog pDialog;
	private JSONObject json;
	private JSONParser jsonParser;
	int idser;
	 Button like ;
	updatelike updatelike ;
	private static String KEY_SUCCESS = "success";
	Fragment fragment = null;
	private static final String URL = "http://172.168.0.8/community/updatelike.php";
	Context context ;
	TextView idposts_ser;
	 String id_serpub;
	/////////// like ////////
	
	
	
	
	///////////////////////////
//	private Context context;

	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public listview_adapter(Activity activity, List<post> postsItems) {
		this.activity = activity;
		this.postItems = postsItems;
	}

	@Override
	public int getCount() {
		return postItems.size();
	}

	@Override
	public Object getItem(int location) {
		return postItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 
		
		
		//context =getActivity().getApplicationContext();
		
		
		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.list_row, null);

		if (imageLoader == null)
			
			imageLoader = AppController.getInstance().getImageLoader();
		NetworkImageView thumbNail = (NetworkImageView) convertView
				.findViewById(R.id.thumbnail);
		
		TextView title = (TextView) convertView.findViewById(R.id.title);
		TextView content = (TextView) convertView.findViewById(R.id.content);
		TextView year = (TextView) convertView.findViewById(R.id.releaseYear);
		TextView adstextview = (TextView) convertView.findViewById(R.id.promoted);
         like  = (Button) convertView.findViewById(R.id.btn_addcontact_add);
        Button comment = (Button) convertView.findViewById(R.id.comments);
         idposts_ser = (TextView)convertView.findViewById(R.id.idposts_ser);
        ///////////////////////////////// like button //////////////////////
        updatelike = new updatelike();
      
        

//        like.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//			
//				    Button b = (Button)v;
//				    String buttonText = b.getText().toString();
//				    int numlike = (Integer.parseInt(buttonText));
//				    if (!b.isSelected()){
//				    Toast.makeText(activity, buttonText , Toast.LENGTH_SHORT).show(); 			   
//				   numlike = numlike + 1;
//				   b.setSelected(true);
//				   b.setText(String.valueOf(numlike));
//				  // b.setEnabled(false);
//				    }
//				    else {
//				    	
//					    Toast.makeText(activity, buttonText , Toast.LENGTH_SHORT).show(); 
//					   numlike = numlike - 1;
//					   b.setText(String.valueOf(numlike));
//					   b.setSelected(false);
//				    }
//			}
//		});
        
        ///////////////////////////////////////////////////////////////////////
		
        
        /////////////////////////////////////////
        ((Activity) activity).getFragmentManager();
        
       

          
        
        //////////////////////////////////////////
        
        

        final post m = postItems.get(position);

		// thumbnail image
		thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
		
		// title
		title.setText(m.getTitle());
		idposts_ser.setText(String.valueOf(m.getid_ser()));
		// content
		content.setText(String.valueOf(m.getcontent()));
	    //like
		like.setText(String.valueOf(m.getlike()));
	     //promoted
		adstextview.setText(String.valueOf(m.getpost_ads()));
		ads = adstextview.getText().toString();
		int numads = Integer.parseInt(ads);
		// release date
		year.setText(String.valueOf(m.getdate()));
		//year.setText(String.valueOf(m.getid_ser()));
		
		/////////////////////// alternate posts /////////////////
		
		if (position % 2 == 1) {
		    convertView.setBackgroundResource(R.drawable.list_row_selector);  
		} else {
		    convertView.setBackgroundResource(R.drawable.alternate);  
		}
        //////////////////////////////////////////////////////////////
        ////////////////////////// code for promoted ////////////////////////////////////
         if (numads == 1){
        	
   	 convertView.setBackgroundResource(R.drawable.promoted);
      
         }
		////////////////////////////////////////////////////////////
       
         comment.setOnClickListener(new View.OnClickListener() {
         	
 			@Override
 			public void onClick(View v) {
 				 String id_ser = String.valueOf(m.getid_ser());
 				 id_serpub = String.valueOf(m.getid_ser());
 		 		 //idser = Integer.parseInt(id_ser);
 				String idserstr = id_ser;
 				Intent pagecomments = new Intent(activity, listcomments.class);
 	        	//pagecomments.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
 				pagecomments.putExtra("id", idserstr);
 	        	activity.startActivity(pagecomments);  
 				
 				
 				
 				
 			}
 		}); 

         like.setOnClickListener( new View.OnClickListener() {
     		
     		@Override
     		public void onClick(View v) {
     			
     		    Button b = (Button)v;
     		    String buttonText = b.getText().toString();
     		    numlike = (Integer.parseInt(buttonText));
     		    if (!b.isSelected()){
     		    Toast.makeText(activity, buttonText , Toast.LENGTH_SHORT).show(); 			   
     		   numlike = numlike + 1;
     		  String id_ser = String.valueOf(m.getid_ser());
     	 		 idser = Integer.parseInt(id_ser);
     		  ////////////////////
     		   Toast.makeText(activity, " thanks"+ id_ser , Toast.LENGTH_SHORT).show();
     		   String action ="increase";
     		   updatelike (idser , action );
     		   
     		   //////////////////
     		   b.setSelected(true);
     		   b.setText(String.valueOf(numlike));
     		   String numlikestring = (String) b.getText();
     		   String id = "1";
     		  // b.setEnabled(false);
     		    }
     		    else {
     		    	String id_ser = String.valueOf(m.getid_ser());
        	 		 idser = Integer.parseInt(id_ser);
     			    Toast.makeText(activity, buttonText , Toast.LENGTH_SHORT).show(); 
     			   numlike = numlike - 1;
     			   b.setText(String.valueOf(numlike));
     			  Toast.makeText(activity, " thanks"+ id_ser , Toast.LENGTH_SHORT).show();
        		   String action ="decrease";
        		   updatelike (idser , action );
     			   b.setSelected(false);
     		    }	
     			 
     			
     		}
         }); 
         
         
         
         
         
         
		return convertView;
	}
	
	
	
	
	
	/////////////////// update like ///////////////////////////////////////////////////
	
		public void updatelike (int idser , final String action ){
		
			if (isOnline() ){
				
				
				
				final String idlike = String.valueOf(idser) ;
				
				//UserFunctions userFunction = new UserFunctions();
				
				//////////////////////
				Thread thread = new Thread(new Runnable()
				{
				    @Override
				    public void run()
				    {
				    	if ( action == "increase"){
				    	json = updatelike.uptatinglike(idlike);
				    	like.setSelected(false); }else{json = updatelike.uptatinglikes(idlike);}
				    	try {
							   
							if (json.getString(KEY_SUCCESS) != null) {
								
								
								
								///// update ui ///
								
								 ((Activity) activity).runOnUiThread(new Runnable() {
				                        @Override
				                        public void run() {
				                        	//registerErrorMsg.setText("insert successfully");
				                        	like.setSelected(true); 
				                        }
				                    });
								
								
								////////////////
								String res = json.getString(KEY_SUCCESS); 
								if(Integer.parseInt(res) == 1){
								
									  like.setSelected(false); 
								}else{
									// Error in registration
									///// update ui ///
									
									 ((Activity) activity).runOnUiThread(new Runnable() {
					                        @Override
					                        public void run() {
					                        	like.setSelected(false); 
					                       
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
			
			}else
			{
				
				Toast.makeText( ((Activity) activity), " Check your internet connexion ", Toast.LENGTH_LONG).show();
				
				
				
				
			}
				
				
				
			} 
			
			
			
				
	
		
		
		
		
		
   ////////////////////////////////////////////////////////////////////////////	
		
		   ////////////////////////////////////////////check online ///////////////////
        public boolean isOnline() {
            ConnectivityManager cm =
                (ConnectivityManager)  ((Activity) activity).getSystemService(Context.CONNECTIVITY_SERVICE);

            return cm.getActiveNetworkInfo() != null && 
               cm.getActiveNetworkInfo().isConnectedOrConnecting();
        }
       
	   ///////////////////////////// get home location /////////////////////////////////////////////////
		
		
		
	}

	

	

	

