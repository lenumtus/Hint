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

public class commentsfunctions {
	
	private JSONParser jsonParser;

	private static String commentURL = "http://172.168.0.8/community/commentting.php";
	
	
	private static String commenting_tag = "commenting";
	
	// constructor
	public commentsfunctions(){
		jsonParser = new JSONParser();
	}
	

	
	/**
	 * function make Login Request
	 * @param name
	 * @param email
	 * @param password
	 * */
	public JSONObject commenting(String userid, String comments_content, String post_id ){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", commenting_tag));
		params.add(new BasicNameValuePair("userid", userid));
		params.add(new BasicNameValuePair("comments_content", comments_content));
		params.add(new BasicNameValuePair("post_id", post_id));
		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl(commentURL, params);
		// return json
		return json;
	}
	

	
}
