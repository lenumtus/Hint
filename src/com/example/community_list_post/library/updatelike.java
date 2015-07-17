/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package com.example.community_list_post.library;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class updatelike {
	
	private JSONParser jsonParser;
	
	
	private static String updatelikeURL = "http://172.168.0.8/community/updatelike.php";
	
	private static String login_tag = "login";
	private static String Tag = "increaselike";
	private static String Tags = "decreaselike";
	
	// constructor
	public updatelike(){
		jsonParser = new JSONParser();
	}
	

	
	/**
	 * function make Login Request
	 * @param name
	 * @param email
	 * @param password
	 * */
	public JSONObject uptatinglike(String idlike){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", Tag));
		params.add(new BasicNameValuePair("idlike", idlike));
		// getting JSON Object
		
		JSONObject json = jsonParser.getJSONFromUrl(updatelikeURL, params);
		// return json
		return json;
	}
	
	public JSONObject uptatinglikes(String idlike){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", Tags));
		params.add(new BasicNameValuePair("idlike", idlike));
		// getting JSON Object
		
		JSONObject json = jsonParser.getJSONFromUrl(updatelikeURL, params);
		// return json
		return json;
	}
	

	
}
