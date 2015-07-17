package com.example.community_list_post.localisation;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.community_list_post.General_list;
import com.example.community_list_post.R;
import com.example.community_list_post.editprofileActivity;
import com.example.community_list_post.events;
import com.example.community_list_post.postactivity;
import com.example.community_list_post.profilepic;

import com.example.community_list_post.localisation.wheelpic.categorybusiness;
import com.example.community_list_post.posting.newpost;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class localisationActivity extends Activity {

	// flag for Internet connection status
	Boolean isInternetPresent = false;

	// Connection detector class
	ConnectionDetector cd;
	
	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();

	// Google Places
	GooglePlaces googlePlaces;

	// Places List
	PlacesList nearPlaces;

	// GPS Location
	GPSTracker gps;

	// Button
	Button btnShowOnMap;

	// Progress dialog
	ProgressDialog pDialog;
	
	// Places Listview
	ListView lv;
	String choose , choices;
	// ListItems data
	ArrayList<HashMap<String, String>> placesListItems = new ArrayList<HashMap<String,String>>();
	
	
	// KEY Strings
	public static String KEY_REFERENCE = "reference"; // id of the place
	public static String KEY_NAME = "name"; // name of the place
	public static String KEY_VICINITY = "vicinity"; // Place area name

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
		Intent intent = getIntent();
		choose = intent.getStringExtra("place");
		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		isInternetPresent = cd.isConnectingToInternet();
		if (!isInternetPresent) {
			// Internet Connection is not present
			alert.showAlertDialog(localisationActivity.this, "Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}

		// creating GPS Class object
		gps = new GPSTracker(this);

		// check if GPS location can get
		if (gps.canGetLocation()) {
			Log.d("Your Location", "latitude:" + gps.getLatitude() + ", longitude: " + gps.getLongitude());
		} else {
			// Can't get user's current location
			alert.showAlertDialog(localisationActivity.this, "GPS Status",
					"Couldn't get location information. Please enable GPS",
					false);
			// stop executing code by return
			return;
		}

		// Getting listview
		lv = (ListView) findViewById(R.id.list);
		
		// button show on map
		btnShowOnMap = (Button) findViewById(R.id.btn_show_map);
		btnShowOnMap.setVisibility(View.GONE);

		// calling background Async task to load Google Places
		// After getting places from Google all the data is shown in listview
		new LoadPlaces().execute();

		/** Button click event for shown on map */

		
		
		/**
		 * ListItem click event
		 * On selecting a listitem SinglePlaceActivity is launched
		 * */
		lv.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
            	// getting values from selected ListItem
                String reference = ((TextView) view.findViewById(R.id.reference)).getText().toString();
                
                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        SinglePlaceActivity.class);
                
                // Sending place refrence id to single place activity
                // place refrence id used to get "Place full details"
                in.putExtra(KEY_REFERENCE, reference);
                startActivity(in);
            }
        });
	}

	/**
	 * Background Async Task to Load Google places
	 * */
	class LoadPlaces extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(localisationActivity.this);
			pDialog.setMessage(Html.fromHtml("<b>Search</b><br/>Loading Places..."));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting Places JSON
		 * */
		protected String doInBackground(String... args) {
			// creating Places class object
			googlePlaces = new GooglePlaces();
			
			try {
				// Separeate your place types by PIPE symbol "|"
				// If you want all types places make it as null
				// Check list of types supported by google
				// /////////////////////////////////////////////////////
				switch (choose) {
				case "Business and Legal":
				    choices = "accounting|bank|courthouse|finance|lawyer|insurance_agency";
				    break;
				case "Entertainment":
				    choices = "amusement_park|aquarium|art_gallery|bowling_alley|casino|movie_theater|museum|night_club|zoo";
				    break;
				case "Bakery":
				    choices = "bakery";
				    break;
				case "ATM":
				    choices = "atm";
				    break;
				case "Restaurant":
				    choices = "bar|cafe|food|restaurant";
				    break;
				case "Beauty":
				    choices = "beauty_salon|hair_care|spa";
				    break;
				case "Store: Other":
				    choices = "bicycle_store|book_store|car_dealer|furniture_store|jewelry_store|movie_rental|home_goods_store|hardware_store";
				    break;
				case "Accomodation":
				    choices = "campground|loging";
				    break; 
				case "Car Rental":
				    choices = "car_rental";
				    break;
				case "Car Repair":
				    choices = "car_repair";
				    break; 
				case "Car Wash":
				    choices = "car_wash";
				    break;
				case "Place of Worship":
				    choices = "church|hindu_temple|mosque|synagogue|place_of_worship";
				    break;
				case "Store: Clothing":
				    choices = "clothing_store|shoe_store|department_store";
				    break;
				case "Convenience Store":
				    choices = "convenience_store";
				    break;
				case "Dentist":
				    choices = "dentist";
				    break;
				case "Health Care":
				    choices = "doctor|health|hospital|physiotherapist";
				    break;
				case "Electrician":
				    choices = "electrician";
				    break;
				case "Electronics Store":
				    choices = "electronics_store";
				    break;
				case "Emergency Services":
				    choices = "fire_station|police";
				    break;
				case "Florist":
				    choices = "florist";
				    break;
				case "Fuel Station":
				    choices = "gas_station";
				    break;
				case "Contractor":
				    choices = "general_contractor";
				    break;
				case "Supermarket":
				    choices = "grocery_or_supermarket";
				    break;
				case "Gym":
				    choices = "gym";
				    break;
				case "Laundry":
				    choices = "laundry";
				    break;
				case "Library":
				    choices = "library";
				    break;
				case "Locksmith":
				    choices = "locksmith";
				    break;
				case "Liquor Store":
				    choices = "liquor_store";
				    break;
				case "Take Away":
				    choices = "meal_delivery|meal_takeaway";
				    break;
				case "Painter":
				    choices = "painter";
				    break;
				case "Pet Care":
				    choices = "pet_store|veterinary_care";
				    break;
				case "Pharmacy":
				    choices = "pharmacy";
				    break;
				case "Plumber":
				    choices = "plumber";
				    break;
				case "post_office":
				    choices = "post_office";
				    break;
				case "Property":
				    choices = "real_estate_agency";
				    break;
				case "Roofing":
				    choices = "roofing_contractor";
				    break;
				case "Education":
				    choices = "school|university";
				    break;
				case "Storage":
				    choices = "storage";
				    break;
				case "Travel":
				    choices = "travel_agency";
				    break;
				case "Store: All":
				    choices = "shopping_mall|store|grocery_or_supermarket|bicycle_store|book_store|car_dealer|furniture_store|jewelry_store|movie_rental|home_goods_store|hardware_store|clothing_store|shoe_store|department_store|convenience_store|electronics_store|florist";
				    break;
				default:
					 choices = "shopping_mall|store|grocery_or_supermarket|bicycle_store|book_store|car_dealer|furniture_store|jewelry_store|movie_rental|home_goods_store|hardware_store|clothing_store|shoe_store|department_store|convenience_store|electronics_store|florist";
				    break;
				}
				
				
				
				
				
				////////////////////////////////////////////////////////
				String types = choices; // Listing places only cafes, restaurants
				
				// Radius in meters - increase this value if you don't find any places
				double radius = 5000; // 1000 meters 
				
				// get nearest places
				nearPlaces = googlePlaces.search(gps.getLatitude(),
						gps.getLongitude(), radius, types);
				

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * and show the data in UI
		 * Always use runOnUiThread(new Runnable()) to update UI from background
		 * thread, otherwise you will get error
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed Places into LISTVIEW
					 * */
					// Get json response status
					String status = nearPlaces.status;
					
					// Check for all possible status
					if(status.equals("OK")){
						// Successfully got places details
						if (nearPlaces.results != null) {
							// loop through each place
							for (Place p : nearPlaces.results) {
								HashMap<String, String> map = new HashMap<String, String>();
								
								// Place reference won't display in listview - it will be hidden
								// Place reference is used to get "place full details"
								map.put(KEY_REFERENCE, p.reference);
								
								// Place name
								map.put(KEY_NAME, p.name);
								
								
								// adding HashMap to ArrayList
								placesListItems.add(map);
							}
							// list adapter
							ListAdapter adapter = new SimpleAdapter(localisationActivity.this, placesListItems,
					                R.layout.list_item,
					                new String[] { KEY_REFERENCE, KEY_NAME}, new int[] {
					                        R.id.reference, R.id.name });
							
							// Adding data into listview
							lv.setAdapter(adapter);
						}
					}
					else if(status.equals("ZERO_RESULTS")){
						// Zero results found
						alert.showAlertDialog(localisationActivity.this, "Near Places",
								"Sorry no places found. Try to change the types of places",
								false);
					}
					else if(status.equals("UNKNOWN_ERROR"))
					{
						alert.showAlertDialog(localisationActivity.this, "Places Error",
								"Sorry unknown error occured.",
								false);
					}
					else if(status.equals("OVER_QUERY_LIMIT"))
					{
						alert.showAlertDialog(localisationActivity.this, "Places Error",
								"Sorry query limit to google places is reached",
								false);
					}
					else if(status.equals("REQUEST_DENIED"))
					{
						alert.showAlertDialog(localisationActivity.this, "Places Error",
								"Sorry error occured. Request is denied",
								false);
					}
					else if(status.equals("INVALID_REQUEST"))
					{
						alert.showAlertDialog(localisationActivity.this, "Places Error",
								"Sorry error occured. Invalid Request",
								false);
					}
					else
					{
						alert.showAlertDialog(localisationActivity.this, "Places Error",
								"Sorry error occured.",
								false);
					}
				}
			});

		}

	}


	

}
