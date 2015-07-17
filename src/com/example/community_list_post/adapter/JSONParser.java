package com.example.community_list_post.adapter;




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
public class JSONParser {
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    // constructor
    public JSONParser()
    {
    }
    public JSONObject getJSONFromUrl(final String url) {
        // Making HTTP request
        try {
            // Construct the client and the HTTP request.
            DefaultHttpClient httpClient = new DefaultHttpClient();
            
            HttpPost httpPost = new HttpPost(url);
            
            // Execute the POST request and store the response locally.
            Log.e("PAERS","PAEr");
            
            HttpResponse httpResponse = httpClient.execute(httpPost);
            Log.e("PAERS","END OF PARSE");
            // Extract data from the response.
            HttpEntity httpEntity = httpResponse.getEntity();
            // Open an inputStream with the data content.
            is = httpEntity.getContent();
            
            if(is==null)
            {
            	Log.e("IS ","iS NULL");
            }else
            {
            	Log.e("IS ","iS NOT NULL"+is.toString());
            }
        } catch (UnsupportedEncodingException e) 
        {
            e.printStackTrace();
       } catch (ClientProtocolException e)
       {
            e.printStackTrace();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
        try {
            // Create a BufferedReader to parse through the inputStream.
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                   is, "iso-8859-1"), 8);

            // Declare a string builder to help with the parsing.
            StringBuilder sb = new StringBuilder();
            // Declare a string to store the JSON object data in string form.
            String line = null;
            // Build the string until null.
            while ((line = reader.readLine()) != null) 
            {
                sb.append(line + "\n");
            }
            // Close the input stream.
            is.close();
            // Convert the string builder data to an actual string.
            if(sb==null)
            {
            	Log.e("SB","SB ERROR NULL");
            }else
            {
//            	Log.e("SB","\n"+sb.toString());
            }
            json = sb.toString();
            Log.e("PROVE","\n"+json.toString());
            
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        // Try to parse the string to a JSON object
        try
        {

            jObj = new JSONObject(json);
        } catch (JSONException e) 
        {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        // Return the JSON Object.
        Log.e("OBJECT 2","\n"+jObj.toString());
        return jObj;
    }
    // function get json from url
    // by making HTTP POST or GET mehtod
    public JSONObject makeHttpRequest(String url, String method,
            List<NameValuePair> params) {
        // Making HTTP request
        try {
            // check for request method
            if(method == "POST")
            {
                // request method is POST
                // defaultHttpClient
//            	Log.e("POST ","inside post");
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));
//                Log.e("POST","before execution");
                HttpResponse httpResponse = httpClient.execute(httpPost);
//                Log.e("POST","after execution");
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
                Log.e("JSONParser.java "," end of post");
                
            }else if(method == "GET")
            {
                // request method is GET
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
                
                
            }          
       } catch (UnsupportedEncodingException e)
       {
            Log.e("JSONParser.java ","UnsupportedEncodingException \n"+e.getMessage());
        } catch (ClientProtocolException e) 
        {           
        	Log.e("JSONParser.java ","ClientProtocolException \n"+e.getMessage());
        } catch (IOException e) 
        {
            Log.e("JSONParser.java ","IOException \n"+e.getMessage());
        }
        try 
        {
//            Log.e("JSONParser.java ", "");
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
            Log.e("post with json","end of if "+json.toString());
        } catch (IOException e) 
        {
            Log.e("JSONParser.java ", "Error converting result " + e.toString());
        }
        // try parse the string to a JSON object
        try 
        {
            jObj = new JSONObject(json);

        } catch (JSONException e) 
        {
            Log.e("JSONParser.java", "Error parsing data " + e.toString());
        }
        // return JSON String
        Log.e("JSONParser.java ###","returned originaly "+jObj.toString());
        return jObj;
    }
}
