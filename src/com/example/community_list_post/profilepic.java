package com.example.community_list_post;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.example.community_list_post.database.synch_service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Config;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class profilepic extends Activity {

 private ImageView image;
 private Button uploadButton;
 private Bitmap bitmap;
 private Button selectImageButton;
 String pathimage ;
 String email = "";
 int serverResponseCode = 0;
 ProgressDialog dialog = null;
  synch_service service ;   
 String upLoadServerUri = null;
 // number of images to select
 private static final int PICK_IMAGE = 1;
 private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
 private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
  
 public static final int MEDIA_TYPE_IMAGE = 1;
 public static final int MEDIA_TYPE_VIDEO = 2;

 private Uri fileUri; // file url to store image/video
 private static final String TAG = MainActivity.class.getSimpleName();
 private Button btnCapturePicture, btnRecordVideo;
 /**
  * called when the activity is first created
  */
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.profilepic);
  email = service.email;
  upLoadServerUri = "http://172.168.0.8/community/uploadpic.php?email="+email;
   
  // find the views
  image = (ImageView) findViewById(R.id.uploadImage);
  uploadButton = (Button) findViewById(R.id.uploadButton);
  getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.action_bar))));
  
  btnCapturePicture = (Button) findViewById(R.id.btnCapturePicture);
  // on click select an image
  selectImageButton = (Button) findViewById(R.id.selectImageButton);
  
  
  btnCapturePicture.setOnClickListener(new View.OnClickListener() {
	  
      @Override
      public void onClick(View v) {
          // capture picture
          captureImage();
      }
  });
  /////////////////// check if device support camera //////////////
  if (!isDeviceSupportCamera()) {
      Toast.makeText(getApplicationContext(),
              "Sorry! Your device doesn't support camera",
              Toast.LENGTH_LONG).show();
      // will close the app if the device does't have camera
      finish();
  }
  
  ///////////////////////////////////////////////////////////////
  // when uploadButton is clicked
  uploadButton.setOnClickListener(new OnClickListener() {           
      @Override
      public void onClick(View v) {
           
          dialog = ProgressDialog.show(profilepic.this, "", "Uploading file...", true);
           
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
          }
      });
	

  
  selectImageButton.setOnClickListener(new OnClickListener() {

	   @Override
	   public void onClick(View v) {
	    selectImageFromGallery();

	   }
	  }); 
  
  
 }
//////////////////////////////////////////////////////////////////////
 
 
 
 //////////////////////////functions camera //////////////////////////////////////////
 private boolean isDeviceSupportCamera() {
     if (getApplicationContext().getPackageManager().hasSystemFeature(
             PackageManager.FEATURE_CAMERA)) {
         // this device has a camera
         return true;
     } else {
         // no camera on this device
         return false;
     }
 }
 private void captureImage() {
     Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

     fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

     intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

     // start the image capture Intent
     startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
 }
 @Override
 protected void onSaveInstanceState(Bundle outState) {
     super.onSaveInstanceState(outState);

     // save file url in bundle as it will be null on screen orientation
     // changes
     outState.putParcelable("file_uri", fileUri);
 }

 @Override
 protected void onRestoreInstanceState(Bundle savedInstanceState) {
     super.onRestoreInstanceState(savedInstanceState);

     // get the file url
     fileUri = savedInstanceState.getParcelable("file_uri");
 }
 


 /**
  * returning image / video
  */
 private static File getOutputMediaFile(int type) {
	  
     // External sdcard location
     @SuppressWarnings("deprecation")
	File mediaStorageDir = new File(
             Environment
                     .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "images");

     // Create the storage directory if it does not exist
     if (!mediaStorageDir.exists()) {
         if (!mediaStorageDir.mkdirs()) {
             Log.d(TAG, "Oops! Failed create images directory");
             return null;
         }
     }

     // Create a media file name
     String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
             Locale.getDefault()).format(new Date());
     File mediaFile;
     if (type == MEDIA_TYPE_IMAGE) {
         mediaFile = new File(mediaStorageDir.getPath() + File.separator
                 + "IMG_" + timeStamp + ".jpg");
     } else if (type == MEDIA_TYPE_VIDEO) {
         mediaFile = new File(mediaStorageDir.getPath() + File.separator
                 + "VID_" + timeStamp + ".mp4");
     } else {
         return null;
     }

     return mediaFile;
 }
 //////////////////////////////////////////////////////////////////////
 /**
  * Opens dialog picker, so the user can select image from the gallery. The
  * result is returned in the method <code>onActivityResult()</code>
  */
 public void selectImageFromGallery() {
  Intent intent = new Intent();
  intent.setType("image/*");
  intent.setAction(Intent.ACTION_GET_CONTENT);
  startActivityForResult(Intent.createChooser(intent, "Select Picture"),
    PICK_IMAGE);
 }

 /**
  * Retrives the result returned from selecting image, by invoking the method
  * <code>selectImageFromGallery()</code>
  */
 @Override
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
     Intent i = new Intent(profilepic.this, UploadActivity.class);
     i.putExtra("filePath", fileUri.getPath());
     i.putExtra("isImage", isImage);
     startActivity(i);
 }
 public Uri getOutputMediaFileUri(int type) {
     return Uri.fromFile(getOutputMediaFile(type));
 }
///////////////////////////////// upload ////////////////////////
 public int uploadFile( String sourceFileUri) {
	
	 
		  
		
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
          
      }
     

     // End else block
    }
 
 
 
 
 
 /////////////////////////////////////////////////////// decode file ///////////////////////////////
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
 
 
 
 ///////////////////////////////////////////////////////////
 

    ////////////////////////////////////////
}