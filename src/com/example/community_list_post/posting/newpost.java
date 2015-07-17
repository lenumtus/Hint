
package com.example.community_list_post.posting;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.community_list_post.MainActivity;
import com.example.community_list_post.R;
import com.example.community_list_post.UploadActivity;
import com.example.community_list_post.editprofileActivity;
import com.example.community_list_post.postactivity;
import com.example.community_list_post.profilepic;
import com.example.community_list_post.database.DBController;
import com.example.community_list_post.database.localisationservice;
import com.example.community_list_post.database.synch_service;
import com.example.community_list_post.library.DatabaseHandler;
import com.example.community_list_post.library.JSONParser;
import com.example.community_list_post.library.JSONParseraddress;
import com.example.community_list_post.library.postFunctions;
import com.example.community_list_post.localisation.localisationActivity;
import com.example.community_list_post.localisation.getReverseGeoCoding;
import com.example.community_list_post.localisation.parser_Json;
import com.example.community_list_post.model.localisation;
public class newpost extends Activity {
	Button btnRegister , testlocation;
	//Button btncurrent;
	EditText inputtitle;
	EditText inputlocation;
	EditText inputpost;
	TextView registerErrorMsg;
	String postal;
	String postalobj , postalstring , postalcode;
	Location location;
	GPSTracker gps;
	String conte;
	String cont;
	Geocoder geocoder ;
	 List<Address> address ;
	 Address addr;
	 ProgressBar mProgressBar;
	double lat ;
	double longi;
	getReverseGeoCoding geo ;
	private String test ="" , Address1 = "", Address2 = "", City = "", State = "", Country = "", County = "", PIN = "";
	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	postFunctions postFunction ;
	JSONObject json , json_user;
    String ab;
     JSONObject jobj = null;
     JSONParseraddress jsonparser = new JSONParseraddress();
     TextView tv;
     JSONObject jobj1 = null;
     DBController db;
     List<localisation> local;
     DatabaseHandler data;
 	String emails;
 	Intent localizationintent;
 	 double latitude, longitude;
 	  localisation localisa;
 	 String localisastr;
 	String input;
 	LinearLayout layout ;
 	int iCurrentSelection ;
 	 Spinner spnLocale;
 	 String categoryid;
 	localisationservice  locationserv ;
 	synch_service environ;
 	Button homeB , currentlocation;
 	ImageView image;
 	ImageButton imgselect;
 	String pathimage ;
 	  Bitmap bitmap;
 	private static final int PICK_IMAGE = 1;
 	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
 	 private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
 	String upLoadServerUri = null;
 	 int serverResponseCode = 0;
 	 private Uri fileUri;
 	 ProgressDialog dialog = null;
 	 public static final int MEDIA_TYPE_IMAGE = 1;
 	 public static final int MEDIA_TYPE_VIDEO = 2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
		  setContentView(R.layout.postingnew);
		  ActionBar actionBar = getActionBar(); 
		  actionBar.hide();
		  localizationintent = new Intent(this, localisationservice.class);
		  startService(localizationintent);
		  image = (ImageView) findViewById(R.id.uploadimage);
		    inputtitle = (EditText) findViewById(R.id.titlepost);
		    inputlocation = (EditText) findViewById(R.id.location);
		    inputlocation.setVisibility(View.GONE);
		    inputtitle.setVisibility(View.GONE);
			inputpost = (EditText) findViewById(R.id.post);
			btnRegister = (Button) findViewById(R.id.btnRegister);
			imgselect = (ImageButton)findViewById(R.id.img1);
			homeB = (Button) findViewById(R.id.currentlocation);
			currentlocation = (Button) findViewById(R.id.homeB);
			//btncurrent = (Button) getActivity().findViewById(R.id.currentlocation);
			registerErrorMsg = (TextView) findViewById(R.id.register_error);
			postFunction = new postFunctions();
			layout =(LinearLayout)findViewById(R.id.searchlocation);
			data =  new DatabaseHandler(this);
			HashMap<String, String> user = data.getUserDetails();
			 db = new DBController(this);
			 emails = user.get("email");
			//////////////////////// localisation /////////////////
            inputtitle.setText(emails);
           // layout.setVisibility(View.GONE);
            local = db.getlocalisation();
            Spinner spinner = (Spinner) findViewById(R.id.spinnercategory);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.Category, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            localisa = new localisation();
           localisa = db.specifilocalisation();
           spnLocale = (Spinner)findViewById(R.id.spinnercategory);
           inputlocation.setText(environ.homelocation);
			////////////////// current position /////////////////
//			btncurrent.setOnClickListener(new View.OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					String localisastr = localisa.getcontent();
//					String servicelocation = getlocalisation();
//					if (servicelocation==null || servicelocation==""){
//						inputlocation.setText(localisastr);
//						}else{
//							
//							inputlocation.setText(servicelocation);
//							
//						}
//					
//					
//				  Toast.makeText(getActivity()," "+inputlocation.getText().toString(), Toast.LENGTH_LONG).show();
//				  
//				}
//			});
           ////////////////////////////// button ///////////////////////
           homeB.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String localisastr = localisa.getcontent();
				String servicelocation = getlocalisation();
				currentlocation.setBackgroundResource(R.color.butt3);
				homeB.setBackgroundResource(R.color.butt2);
				if (servicelocation==null || servicelocation==""){
					inputlocation.setText(localisastr);
					}else{
						
						inputlocation.setText(servicelocation);
						
					}
				
				
			  Toast.makeText(getApplicationContext()," "+inputlocation.getText().toString(), Toast.LENGTH_LONG).show();
				
			}
		});
           
           
           
           currentlocation.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				homeB.setBackgroundResource(R.color.butt3);
				currentlocation.setBackgroundResource(R.color.butt2);
				Toast.makeText(getApplicationContext(),"Home location ", Toast.LENGTH_LONG).show();
            	inputlocation.setText(environ.homelocation);
            	
				
			}
		});
           
           
			///////////////////////////img select ////////////////////
           imgselect.setOnClickListener(new OnClickListener() {

        	   @Override
        	   public void onClick(View v) {
        	    selectImageFromGallery();

        	   }
        	  });
	
          
          
         /////////////////////////////////other functions for image /////////////
          
           
        ///////////////////////////////////////////////////////////////////////
			    
			    
			    
			 ///////////////////////////////////Back button ////////////////////
			    
			    
			    
			    
			    
			    
			
			///////////////////////////// spinner /////////////////////////
			    
			   

			    

			     iCurrentSelection = spnLocale.getSelectedItemPosition();

			    spnLocale.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) { 
			        if (iCurrentSelection != i){
			        String spinnervalue = spnLocale.getSelectedItem().toString();
			        	categoryid = String.valueOf(iCurrentSelection) ; 
			        	Toast.makeText(getApplicationContext()," "+categoryid, Toast.LENGTH_LONG).show();
			        }
			        iCurrentSelection = i;
			        categoryid = String.valueOf(i) ;
			        Toast.makeText(getApplicationContext()," "+categoryid, Toast.LENGTH_LONG).show();
			        } 

			        public void onNothingSelected(AdapterView<?> adapterView) {
			            return;
			        } 
			    });
			    
			    
			    
			
			
			
			
			////////////////////////////////////////////////////////////
	
	
			// posting /////////////////////////////////////////////
			btnRegister.setOnClickListener(new View.OnClickListener() {			
				public void onClick(View view) {
					
					
					if (isOnline() ){
					
					////////////////////////////////upload post picture ////////////////
						  
						
						
						
						
						
						
						
						
			       //////////////////////////////////////////////////////////////////////			
					
					final String title = inputtitle.getText().toString();
					final String post = inputpost.getText().toString();
					final String area = inputlocation.getText().toString();
					final String category = categoryid;
					//UserFunctions userFunction = new UserFunctions();
					
					//////////////////////
					Thread thread = new Thread(new Runnable()
					{
					    @Override
					    public void run()
					    {
					    	json = postFunction.posting(area, title, post,category );
					    	
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
					
					
					/////////////////////////////////// uploading image ////////////////////
					
					dialog = ProgressDialog.show(newpost.this, "", "Uploading file...", true);
			           
			          new Thread(new Runnable() {
			                  public void run() {
			                       runOnUiThread(new Runnable() {
			                              public void run() {
			                            	  Toast.makeText(getApplicationContext(), "uploading started.....", Toast.LENGTH_LONG);
			                                  //messageText.setText("uploading started.....");
			                            	  
			                              }
			                          });                     
			                       
			                       uploadFile(pathimage);
			                                                
			                  }
			                }).start();	
					
					
					
					
					
					
					
					
					
					////////////////////////////////////////////////////////////////////////////
					
					
					
					
					
					
					
				
				}else
				{
					
					Toast.makeText(getApplicationContext(), " Check your internet connexion ", Toast.LENGTH_LONG).show();
					
					
					
					
				}
					
					
					
				} 
				
				
				
					
			});
		  
			
        }
	
	 ////////////////////////////////////////////////////////////////////////////////

	


	



       ////////////////////////////

//	 Thread threadloca = new Thread() {
//         @Override
//         public void run() {
//    
//       	   try {
//       		localisastr = localisa.getcontent();
//       		 while (localisastr==null) {
//                //load your data here (fill Adapter)!
//       			localisastr = localisa.getcontent();
//       			 getActivity().startService(localizationintent);
//            	local = db.getlocalisation();
//				Thread.sleep(2000);
//				getActivity().runOnUiThread(new Runnable() {
//	                 @Override
//	                 public void run() {
//	                     // fill ListView, dismiss Dialog
//	                	 layout.setVisibility(View.VISIBLE);
//	                	 btnRegister.setEnabled(false);
//	                     }
//	                 });
//				 
//       		 }
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//             getActivity().runOnUiThread(new Runnable() {
//                 @Override
//                 public void run() {
//                     // fill ListView, dismiss Dialog
//                     layout.setVisibility(View.GONE);
//                     btnRegister.setEnabled(true);
//                     inputlocation.setText(localisastr);
//                     }
//                 });
//             }
//         }; 
         ////////////////////////// get localisation ///////////////////////
         public String getlocalisation()
  	   {
  		String currentplace ="";
  		currentplace = locationserv.currentlocation;
  		if(currentplace ==""||currentplace==null){
  			currentplace = localisa.getcontent();	
  		}
  		   
  		   return currentplace;   
  		   
  	   }
         
         
         
	  
      ////////////////////////////////////////////check online ///////////////////
         public boolean isOnline() {
             ConnectivityManager cm =
                 (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

             return cm.getActiveNetworkInfo() != null && 
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
         }
        
	   ///////////////////////// img function //////////////////////////////////
         public void selectImageFromGallery() {
        	  Intent intent = new Intent();
        	  intent.setType("image/*");
        	  intent.setAction(Intent.ACTION_GET_CONTENT);
        	  startActivityForResult(Intent.createChooser(intent, "Select Picture"),
        	    PICK_IMAGE);
        	 } 
        
         
         protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        	  super.onActivityResult(requestCode, resultCode, data);

        	  if (requestCode == PICK_IMAGE && resultCode == RESULT_OK
        	    && null != data) {
        	   Uri selectedImage = data.getData();
        	   String[] filePathColumn = { MediaStore.Images.Media.DATA };

        	   Cursor cursor = getContentResolver().query(selectedImage,
        	     filePathColumn, null, null, null);
        	   cursor.moveToFirst();

        	   int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        	   String picturePath = cursor.getString(columnIndex);
        	   cursor.close();

        	   decodeFile(picturePath);
        	   pathimage = picturePath ;
        	  }
        	  
        	  if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
        	      if (resultCode == RESULT_OK) {
        	           
        	          // successfully captured the image
        	          // launching upload activity
        	          launchUploadActivity(true);
        	           
        	           
        	      } else if (resultCode == RESULT_CANCELED) {
        	           
        	          // user cancelled Image capture
        	          Toast.makeText(getApplicationContext(),
        	                  "User cancelled image capture", Toast.LENGTH_SHORT)
        	                  .show();
        	       
        	      } else {
        	          // failed to capture image
        	          Toast.makeText(getApplicationContext(),
        	                  "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
        	                  .show();
        	      }
        	   
        	  } else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
        	      if (resultCode == RESULT_OK) {
        	           
        	          // video successfully recorded
        	          // launching upload activity
        	          launchUploadActivity(false);
        	       
        	      } else if (resultCode == RESULT_CANCELED) {
        	           
        	          // user cancelled recording
        	          Toast.makeText(getApplicationContext(),
        	                  "User cancelled video recording", Toast.LENGTH_SHORT)
        	                  .show();
        	       
        	      } else {
        	          // failed to record video
        	          Toast.makeText(getApplicationContext(),
        	                  "Sorry! Failed to record video", Toast.LENGTH_SHORT)
        	                  .show();
        	      }
        	  }
        	  
        	  
        	  
        	  
        	  
        	  
        	  
        	  
        	  
        	 }
      	   
         private void launchUploadActivity(boolean isImage){
             Intent i = new Intent(newpost.this, UploadActivity.class);
             i.putExtra("filePath", fileUri.getPath());
             i.putExtra("isImage", isImage);
             startActivity(i);
         }
        
        ///////////////////////////////// upload ////////////////////////
         public int uploadFile(String sourceFileUri) {
             
             
             String fileName = sourceFileUri;

             HttpURLConnection conn = null;
             DataOutputStream dos = null; 
             String lineEnd = "\r\n";
             String twoHyphens = "--";
             String boundary = "*****";
             int bytesRead, bytesAvailable, bufferSize;
             byte[] buffer;
             int maxBufferSize = 1 * 1024 * 1024;
             File sourceFile = new File(sourceFileUri);
              
             if (!sourceFile.isFile()) {
                  
                  dialog.dismiss();
                   
                
                   
                  runOnUiThread(new Runnable() {
                      public void run() {
                    	  
                    	  Toast.makeText(getApplicationContext(), "is not a file.....", Toast.LENGTH_LONG);
                         
                      }
                  });
                   
                  return 0;
               
             }
             else
             {
                  try {
                       
                        // open a URL connection to the Servlet
                      FileInputStream fileInputStream = new FileInputStream(sourceFile);
                      URL url = new URL(upLoadServerUri);
                       
                      // Open a HTTP  connection to  the URL
                      conn = (HttpURLConnection) url.openConnection();
                      conn.setDoInput(true); // Allow Inputs
                      conn.setDoOutput(true); // Allow Outputs
                      conn.setUseCaches(false); // Don't use a Cached Copy
                      conn.setRequestMethod("POST");
                      conn.setRequestProperty("Connection", "Keep-Alive");
                      conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                      conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                      conn.setRequestProperty("uploaded_file", fileName);
                       
                      dos = new DataOutputStream(conn.getOutputStream());
             
                      dos.writeBytes(twoHyphens + boundary + lineEnd);
                      dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""+ fileName + "\"" + lineEnd);
                       
                      dos.writeBytes(lineEnd);
             
                      // create a buffer of  maximum size
                      bytesAvailable = fileInputStream.available();
             
                      bufferSize = Math.min(bytesAvailable, maxBufferSize);
                      buffer = new byte[bufferSize];
             
                      // read file and write it into form...
                      bytesRead = fileInputStream.read(buffer, 0, bufferSize); 
                         
                      while (bytesRead > 0) {
                           
                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);  
                         
                       }
             
                      // send multipart form data necesssary after file data...
                      dos.writeBytes(lineEnd);
                      dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
             
                      // Responses from the server (code and message)
                      serverResponseCode = conn.getResponseCode();
                      String serverResponseMessage = conn.getResponseMessage();
                        
                      Log.i("uploadFile", "HTTP Response is : "
                              + serverResponseMessage + ": " + serverResponseCode);
                       
                      if(serverResponseCode == 200){
                           
                          runOnUiThread(new Runnable() {
                               public void run() {
                                    
                                 
                                    
                                 
                                   Toast.makeText(getApplicationContext(), "File Upload Complete.",
                                                Toast.LENGTH_SHORT).show();
                               }
                           });               
                      }   
                       
                      //close the streams //
                      fileInputStream.close();
                      dos.flush();
                      dos.close();
                        
                 } catch (MalformedURLException ex) {
                      
                     dialog.dismiss(); 
                     ex.printStackTrace();
                      
                     runOnUiThread(new Runnable() {
                         public void run() {
                             
                             Toast.makeText(getApplicationContext(), "MalformedURLException",
                                                                 Toast.LENGTH_SHORT).show();
                         }
                     });
                      
                     Log.e("Upload file to server", "error: " + ex.getMessage(), ex); 
                 } catch (Exception e) {
                      
                     dialog.dismiss(); 
                     e.printStackTrace();
                      
                     runOnUiThread(new Runnable() {
                         public void run() {
                             
                             Toast.makeText(getApplicationContext(), "Got Exception : see logcat ",
                                     Toast.LENGTH_SHORT).show();
                         }
                     });
                     Log.e("Upload file to server Exception", "Exception : "
                                                      + e.getMessage(), e); 
                 }
                 dialog.dismiss();      
                 return serverResponseCode;
                  
              } // End else block
            }
      	   public void decodeFile(String filePath) {
      			  // Decode image size
      			  BitmapFactory.Options o = new BitmapFactory.Options();
      			  o.inJustDecodeBounds = true;
      			  BitmapFactory.decodeFile(filePath, o);

      			  // The new size we want to scale to
      			  final int REQUIRED_SIZE = 1024;

      			  // Find the correct scale value. It should be the power of 2.
      			  int width_tmp = o.outWidth, height_tmp = o.outHeight;
      			  int scale = 1;
      			  while (true) {
      			   if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
      			    break;
      			   width_tmp /= 2;
      			   height_tmp /= 2;
      			   scale *= 2;
      			  }

      			  // Decode with inSampleSize
      			  BitmapFactory.Options o2 = new BitmapFactory.Options();
      			  o2.inSampleSize = scale;
      			  bitmap = BitmapFactory.decodeFile(filePath, o2);
      			  image.setImageBitmap(bitmap);
      			 }
         
         /////////////////////////////////////////////////////////////////////
}
