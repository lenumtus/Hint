package com.example.community_list_post.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;





import java.util.Random;

import com.example.community_list_post.model.comments;
import com.example.community_list_post.model.localisation;
import com.example.community_list_post.model.post;

import android.content.ClipData.Item;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DBController  extends SQLiteOpenHelper {
	int categoryid;
	public DBController(Context applicationcontext) {
        super(applicationcontext, "community.db", null, 1);
    }
	//Creates Table
	@Override
	public void onCreate(SQLiteDatabase database) {
		String query , query1 , query2 , query3;
		query = "CREATE TABLE post (post_id INTEGER PRIMARY KEY AUTOINCREMENT, post_content VARCHAR(255), post_title VARCHAR(255), date_post DATE, postal_code INTEGER, category_id VARCHAR(255), img_url VARCHAR(255) , number_like VARCHAR(255) DEFAULT 0 , promoted VARCHAR(255) DEFAULT 0, post_ads VARCHAR(255) , post_id_ser VARCHAR(255) ,UNIQUE(post_id_ser))";
		query1 = "CREATE TABLE comments (comments_id INTEGER PRIMARY KEY AUTOINCREMENT, comments_content VARCHAR(255), username VARCHAR(255), date_comments DATE , img_url VARCHAR(255) , number_like VARCHAR(255) DEFAULT 0 , post_id_ser VARCHAR(255) ,comment_id_ser VARCHAR(255) , UNIQUE(comment_id_ser))";
		query2 = "CREATE TABLE places (place_id INTEGER PRIMARY KEY AUTOINCREMENT, place_content VARCHAR(255))";
		query3 = "CREATE TABLE system (system_id INTEGER PRIMARY KEY AUTOINCREMENT, system_url VARCHAR(255), system_version VARCHAR(255) )";
        database.execSQL(query);
        database.execSQL(query1);
        database.execSQL(query2);
        database.execSQL(query3);
	}
	@Override
	public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
		   Log.e("version log", version_old + " to " + current_version
			          + ", which will destroy all old data");
		String query;
		query = "DROP TABLE IF EXISTS post";
		database.execSQL(query);
        onCreate(database);
	}

	public void deletedatabase(SQLiteDatabase database){
		String query;
		query = "DROP TABLE IF EXISTS post";
		database.execSQL(query);
        onCreate(database);
		
	} 
	/**
	 * Inserts posts into SQLite DB
	 * @param queryValues
	 */
	public void insertpost(HashMap<String, String> queryValues) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("post_id_ser", queryValues.get("post_id_ser"));
		values.put("post_content", queryValues.get("post_content"));
		values.put("post_title", queryValues.get("post_title"));
		values.put("date_post", queryValues.get("date_post"));
		values.put("postal_code", queryValues.get("postal_code"));
		values.put("category_id", queryValues.get("category_id"));
		values.put("img_url", queryValues.get("img_url"));
		values.put("number_like", queryValues.get("number_like"));
		values.put("promoted", queryValues.get("promoted"));
		values.put("post_ads", queryValues.get("post_ads"));
		
		database.insert("post", null, values);
		database.close();
	}
	
	/**
	 * Inserts comments into SQLite DB
	 * @param queryValues
	 */
	public void insertcomments(HashMap<String, String> queryValues) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("post_id_ser", queryValues.get("post_id_ser"));
		values.put("comment_id_ser", queryValues.get("comment_id_ser"));
		values.put("comments_content", queryValues.get("comments_contents"));
		values.put("username", queryValues.get("username"));
		values.put("date_comments", queryValues.get("date_comments"));
		values.put("img_url", queryValues.get("img_url"));
		values.put("number_like", queryValues.get("number_like"));
		database.insert("comments", null, values);
		database.close();
	}
	
	/**
	 *  Insert Localisation 
	 *
	 */
	
	   void addlocalisation(String place) {
	        SQLiteDatabase database = this.getWritableDatabase();
	        ContentValues values = new ContentValues();
	        values.put("place_content", place); // Contact Name
	        database.insert("places", null, values);
	        database.close(); // Closing database connection
	    }
	
	
	
	
	/**
	 * Get list of Users from SQLite DB as Array List
	 * @return
	 */
	public ArrayList<HashMap<String, String>> getAllposts() {
		ArrayList<HashMap<String, String>> usersList;
		usersList = new ArrayList<HashMap<String, String>>();
		String selectQuery = "SELECT  * FROM post ";
	    SQLiteDatabase database = this.getWritableDatabase();
	    Cursor cursor = database.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        do {
	        	HashMap<String, String> map = new HashMap<String, String>();
	        	map.put("post_id", cursor.getString(0));
	        	map.put("post_content", cursor.getString(1));
	        	map.put("post_title", cursor.getString(2));
	        	map.put("date_post", cursor.getString(3));
	        	map.put("postal_code", cursor.getString(4));
	        	map.put("category_id", cursor.getString(5));
	        	map.put("img_url", cursor.getString(6));
                usersList.add(map);
	        } while (cursor.moveToNext());
	    }
	    database.close();
	    return usersList;
	}
	  public List<post> getAllposts1(int categoryid) {
	      List<post> postList = new ArrayList<post>();
	      // Select All Query
	      String selectQuery = "SELECT  * FROM post where category_id ="+categoryid+" and promoted = 0 and post_ads = 0 ORDER BY date_post DESC";
	     // String selectQuery1 = "SELECT  * FROM post where category_id = 1 and promoted = 1 ORDER BY RANDOM() LIMIT 1";
	      SQLiteDatabase database = this.getWritableDatabase();
	      SQLiteDatabase db = this.getWritableDatabase();
	      Cursor cursor = db.rawQuery(selectQuery, null);
	      //Cursor cursor1 = db.rawQuery(selectQuery1, null);
	       int i = 1 ;
	      // looping through all rows and adding to list
	      //////////////////// postlist for promoted ////////////

	      //////////////////////////////////////////////////////
	       String selectQuery1 = "SELECT  * FROM post where category_id = 1 and promoted = 0 and post_ads = 1 ORDER BY date_post DESC";
		      Cursor cursor1 = db.rawQuery(selectQuery1, null);
		      post postsads = new post();
	      if (cursor1.moveToFirst()) {
	          
	    	  do {
	              postsads.setTitle(cursor1.getString(2));
	              postsads.setcontent(cursor1.getString(1));
	              postsads.setdate(cursor1.getString(3));
	              postsads.setThumbnailUrl(cursor1.getString(6));
	              postsads.setlike(cursor1.getString(7));
	              postsads.setpromoted(cursor1.getString(8));
	              postsads.setpost_ads(cursor1.getString(9));
	              postsads.setid_ser(cursor1.getInt(10));
	              postList.add(postsads);
	              
	    	  } while (cursor1.moveToNext());
	      }
	      if (cursor.moveToFirst()) {
	    	   
	    		  
	          do {
	        	  
	              post posts = new post();
	              posts.setTitle(cursor.getString(2));
	              posts.setcontent(cursor.getString(1));
	              posts.setdate(cursor.getString(3));
	              posts.setThumbnailUrl(cursor.getString(6));
	              posts.setlike(cursor.getString(7));
	              posts.setpromoted(cursor.getString(8)); 
	              posts.setpost_ads(cursor.getString(9));
	              posts.setid_ser(cursor.getInt(10));
	              // Adding posts to list
	             
	   	         	 
	              postList.add(posts);
	          } while (cursor.moveToNext());
	          
	          
	          
	      }   
	      // return contact list
	      database.close();
	      return postList;
	  }
	  
	  
	  
	  
      public  post post_ads () {
    	  String selectQuery1 = "SELECT  * FROM post where category_id = 1 and promoted = 0 and post_ads = 1 ORDER BY date_post DESC";
	      SQLiteDatabase database = this.getWritableDatabase();
	      SQLiteDatabase db = this.getWritableDatabase();
	      Cursor cursor1 = db.rawQuery(selectQuery1, null);
	      post postsads = new post();
      if (cursor1.moveToFirst()) {
          
    	  do {
              postsads.setTitle(cursor1.getString(2));
              postsads.setcontent(cursor1.getString(1));
              postsads.setdate(cursor1.getString(3));
              postsads.setThumbnailUrl(cursor1.getString(6));
              postsads.setlike(cursor1.getString(7));
              postsads.setpromoted(cursor1.getString(8));
              postsads.setpost_ads(cursor1.getString(9));
              
    	  } while (cursor1.moveToNext());		
         
      } 
        return postsads ;
      }
      public  post specificpost (int id) {
    	  String selectQuery1 = "SELECT  * FROM post where post_id =88";
	      
	      SQLiteDatabase db = this.getWritableDatabase();
	      Cursor cursor1 = db.rawQuery(selectQuery1, null);
	      post specificpost = new post();
      if (cursor1.moveToFirst()) {
          
    	  do {
    		  specificpost.setTitle(cursor1.getString(2));
    		  specificpost.setcontent(cursor1.getString(1));
    		  specificpost.setdate(cursor1.getString(3));
    		  specificpost.setThumbnailUrl(cursor1.getString(6));
    		  specificpost.setlike(cursor1.getString(7));
    		  specificpost.setpromoted(cursor1.getString(8));
    		  specificpost.setpost_ads(cursor1.getString(9));
    		  specificpost.setid_ser(cursor1.getInt(10));
    	  } while (cursor1.moveToNext());		
         
      } 
        return specificpost ;
      }
//	  public randompromoted ()
//	  {
//		  
//		post postspromo = promoted();
//		Random random = new Random();
//		int index = random.nextInt(postspromo.si); 
//		Item item = postspromo.get(index);  
//		  
//		  
//		  
//	  }
      public  post specificpost () {
    	  String selectQuery1 = "SELECT  * FROM post where post_id = 1 ";
	      SQLiteDatabase database = this.getWritableDatabase();
	      SQLiteDatabase db = this.getWritableDatabase();
	      Cursor cursor1 = db.rawQuery(selectQuery1, null);
	      post specificpost = new post();
      if (cursor1.moveToFirst()) {
          
    	  do {
    		  specificpost.setTitle(cursor1.getString(2));
    		  specificpost.setcontent(cursor1.getString(1));
    		  specificpost.setdate(cursor1.getString(3));
    		  specificpost.setThumbnailUrl(cursor1.getString(6));
    		  specificpost.setlike(cursor1.getString(7));
    		  specificpost.setpromoted(cursor1.getString(8));
    		  specificpost.setpost_ads(cursor1.getString(9));
    		  specificpost.setid_ser(cursor1.getInt(10));
    	  } while (cursor1.moveToNext());		
         
      } 
        return specificpost ;
      }
      public  post specificpostfor (int postid) {
    	  String selectQuery1 = "SELECT  * FROM post where post_id_ser ="+postid;
	     
	      SQLiteDatabase db = this.getWritableDatabase();
	      Cursor cursor1 = db.rawQuery(selectQuery1, null);
	      post specificpost = new post();
      if (cursor1.moveToFirst()) {
          
    	  do {
    		  specificpost.setTitle(cursor1.getString(2));
    		  specificpost.setcontent(cursor1.getString(1));
    		  specificpost.setdate(cursor1.getString(3));
    		  specificpost.setThumbnailUrl(cursor1.getString(6));
    		  specificpost.setlike(cursor1.getString(7));
    		  specificpost.setpromoted(cursor1.getString(8));
    		  specificpost.setpost_ads(cursor1.getString(9));
    		  specificpost.setid_ser(cursor1.getInt(10));
    	  } while (cursor1.moveToNext());		
         
      } 
        return specificpost ;
      }
	  ///////////////////////////////specificlocalization /////////
      public  localisation specifilocalisation() {
    	  String selectQuery1 = "SELECT * FROM places ORDER BY place_id DESC LIMIT 1";
	      SQLiteDatabase database = this.getWritableDatabase();
	      SQLiteDatabase db = this.getWritableDatabase();
	      Cursor cursor1 = db.rawQuery(selectQuery1, null);
	      localisation specifilocalisation = new localisation();
      if (cursor1.moveToFirst()) {
          
    	  do {
    		  
    		  specifilocalisation.setcontent(cursor1.getString(1));
    		  
    	  } while (cursor1.moveToNext());		
         
      } 
        return specifilocalisation ;
      }
      
      
      
	  //////////////////////// localisation ///////////////
      public List<localisation> getlocalisation () {
    	  List<localisation> localis = new ArrayList<localisation>();
    	  String selectQuery1 = "SELECT * FROM places ";
	      SQLiteDatabase db = this.getWritableDatabase();
	      Cursor cursor1 = db.rawQuery(selectQuery1, null);
	      localisation local = new localisation();
      if (cursor1.moveToFirst()) {
          
    	  do {
    		 
    		  local.setcontent(cursor1.getString(1));
    	  } while (cursor1.moveToNext());		
         
      } 
        return localis  ;
      }
    
      
      ///////////////////////////////////////////////////////
	  
	  public List<post> getAllposts2() {
	      List<post> postList = new ArrayList<post>();
	      // Select All Query
	      String selectQuery = "SELECT  * FROM post where category_id = 2 order by date_post DESC ";
	      SQLiteDatabase database = this.getWritableDatabase();
	      SQLiteDatabase db = this.getWritableDatabase();
	      Cursor cursor = db.rawQuery(selectQuery, null);
	   
	      // looping through all rows and adding to list
	      if (cursor.moveToFirst()) {
	          do {
	              post posts = new post();
	              posts.setTitle(cursor.getString(2));
	              posts.setcontent(cursor.getString(1));
	              posts.setdate(cursor.getString(3));
	              posts.setThumbnailUrl(cursor.getString(6));
	              posts.setlike(cursor.getString(7));
	              posts.setpromoted(cursor.getString(9));
	             // posts.setversion(cursor.getInt(8));
	              
	              // Adding contact to list
	              postList.add(posts);
	          } while (cursor.moveToNext());
	      }
	   
	      // return contact list
	      database.close();
	      return postList;
	  }
	  ////////////////////////// comments //////////////////////
	  public List<comments> getAllcomments() {
	      List<comments> commentList = new ArrayList<comments>();
	      // Select All Query
	      String selectQuery = "SELECT * FROM comments ORDER BY date_comments DESC ";
	      SQLiteDatabase database = this.getWritableDatabase();
	      SQLiteDatabase db = this.getWritableDatabase();
	      Cursor cursor = db.rawQuery(selectQuery, null);
	   
	      // looping through all rows and adding to list
	      if (cursor.moveToFirst()) {
	          do {
	              comments comments = new comments();
	              comments.setTitle(cursor.getString(2));
	              comments.setcontent(cursor.getString(1));
	              comments.setdate(cursor.getString(3));
	              comments.setThumbnailUrl(cursor.getString(4));
	             // posts.setversion(cursor.getInt(8));
	              
	              // Adding contact to list
	              commentList.add(comments);
	          } while (cursor.moveToNext());
	      }
	   
	      // return contact list
	      database.close();
	      return commentList;
	  }
	  
	  ////////////////// specific comments ///////////////
      public  comments specificcomment () {
    	  String selectQuery1 = "SELECT  * FROM comments where comments_id = 1 ";
	      SQLiteDatabase db = this.getWritableDatabase();
	      Cursor cursor1 = db.rawQuery(selectQuery1, null);
	      comments specificcomment = new comments();
      if (cursor1.moveToFirst()) {
          
    	  do {
    		  specificcomment.setTitle(cursor1.getString(2));
    		  specificcomment.setcontent(cursor1.getString(1));
    		  specificcomment.setdate(cursor1.getString(3));
    		  specificcomment.setThumbnailUrl(cursor1.getString(4));
    		 
    	  } while (cursor1.moveToNext());		
         
      } 
        return specificcomment ;
      }
	  
	  
	  
	  
	  
	  /////////////////////////////////////////////////////


}
