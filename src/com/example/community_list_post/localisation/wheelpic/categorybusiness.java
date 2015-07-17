package com.example.community_list_post.localisation.wheelpic;

import com.example.community_list_post.R;
import com.example.community_list_post.listcomments;
import com.example.community_list_post.localisation.localisationActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class categorybusiness extends Activity
	{
		// TODO: Externalize string-array
	String wheelMenu1[] = new String[]{"Business and Legal", "Entertainment", "ATM", "Bakery", "Restaurant" , "Beauty"
			, "Store: Other", "Accomodation", "Car Rental", "Car Repair", "Car Wash", "Place of Worship", "Store: Clothing", "Convenience Store", "Dentist"		
			, "Health Care", "Electrician", "Electronics Store", "Emergency Services", "Florist", "Fuel Station", "Contractor", "Supermarket", "Gym", "Laundry"
			, "Library", "Liquor Store", "Locksmith", "Take Away", "Painter", "Pet Care", "Pharmacy", "Plumber", "post_office", "Property","Roofing","Education"
	        , "Storage","Travel","Store: All"};
//    String wheelMenu2[] = new String[]{"state 1", "state 2", "state 3"};
//    String wheelMenu3[] = new String[]{"Ahmedabad", "Surat","Pune","Bangalore","Mumbai","Delhi"};


		// Wheel scrolled flag
		private boolean wheelScrolled = false;

		private TextView text;
		private EditText text1;
		private Button searchplaces;
		Context  ctx;

		@Override
		public void onCreate(Bundle savedInstanceState)
			{
				super.onCreate(savedInstanceState);
				setContentView(R.layout.mainwheel);
				ctx = categorybusiness.this;
				initWheel1(R.id.p1);
				

				text1 = (EditText) this.findViewById(R.id.r1);
				
				text = (TextView) this.findViewById(R.id.result);
				text.setVisibility(View.GONE);
				text1.setVisibility(View.GONE);
				searchplaces = (Button) this.findViewById(R.id.goplaces);
				searchplaces.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						String choose = text.getText().toString();
						
						Toast.makeText(getApplicationContext(), "place  "+choose, Toast.LENGTH_LONG).show();
		 				Intent localisationact = new Intent(getApplicationContext(), localisationActivity.class);
		 	        	//pagecomments.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		 				localisationact.putExtra("place", choose);
		 	        	startActivity(localisationact);
					}
				});
			}

		// Wheel scrolled listener
		OnWheelScrollListener scrolledListener = new OnWheelScrollListener()
			{
				public void onScrollStarts(WheelView wheel)
					{
						wheelScrolled = true;
					}

				public void onScrollEnds(WheelView wheel)
					{
						wheelScrolled = false;
						updateStatus();
					}

				@Override
				public void onScrollingStarted(WheelView wheel) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onScrollingFinished(WheelView wheel) {
					// TODO Auto-generated method stub
					
				}
			};

		// Wheel changed listener
		private final OnWheelChangedListener changedListener = new OnWheelChangedListener()
			{
				public void onChanged(WheelView wheel, int oldValue, int newValue)
					{
						if (!wheelScrolled)
							{
								updateStatus();
							}
					}
			};

		/**
		 * Updates entered PIN status
		 */
		private void updateStatus()
			{
				text1.setText(wheelMenu1[getWheel(R.id.p1).getCurrentItem()]);
//				text2.setText(wheelMenu2[getWheel(R.id.p2).getCurrentItem()]);
//				text3.setText(wheelMenu3[getWheel(R.id.p3).getCurrentItem()]);

				text.setText(wheelMenu1[getWheel(R.id.p1).getCurrentItem()] );
			}

		/**
		 * Initializes wheel
		 * 
		 * @param id
		 *          the wheel widget Id
		 */

		private void initWheel1(int id)
			{
				WheelView wheel = (WheelView) findViewById(id);
				wheel.setViewAdapter(new ArrayWheelAdapter(ctx,wheelMenu1));
				wheel.setVisibleItems(2);
				wheel.setCurrentItem(0);
				wheel.addChangingListener(changedListener);
				wheel.addScrollingListener(scrolledListener);
			}



		/**
		 * Returns wheel by Id
		 * 
		 * @param id
		 *          the wheel Id
		 * @return the wheel with passed Id
		 */
		private WheelView getWheel(int id)
			{
				return (WheelView) findViewById(id);
			}

		/**
		 * Tests wheel value
		 * 
		 * @param id
		 *          the wheel Id
		 * @param value
		 *          the value to test
		 * @return true if wheel value is equal to passed value
		 */
		private int getWheelValue(int id)
			{
				return getWheel(id).getCurrentItem();
			}
	}
