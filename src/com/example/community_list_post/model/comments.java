package com.example.community_list_post.model;



public class comments {
	private String usercomment, commentthumbnailUrl;
	private String commentyear;
    private String commentcontent;
    private int commentversion;
    private String commentlike;
    private String commentpromoted , commentpost_ads;
	public comments() {
	}

	public comments(String name , int commentversion, String commentlike, String commentpromoted , String commentpost_ads, String commentcontent, String commentthumbnailUrl, String commentyear) {
		this.usercomment = name;
		this.commentthumbnailUrl = commentthumbnailUrl;
		this.commentyear = commentyear;
		this.commentcontent = commentcontent;
		this.commentversion = commentversion;
		this.commentlike = commentlike;
		this.commentpromoted = commentpromoted;
		this.commentpost_ads = commentpromoted;
	
	}
	public int getversion() {
		return commentversion;
	}

	public void setversion(int commentversion) {
		this.commentversion = commentversion;
	}



	
	
	
	public String getTitle() {
		return usercomment;
	}

	public void setTitle(String name) {
		this.usercomment = name;
	}
	public String getcontent() {
		return commentcontent;
	}

	public void setcontent(String commentcontent) {
		this.commentcontent = commentcontent;
	}
	public String getThumbnailUrl() {
		return commentthumbnailUrl;
	}

	public void setThumbnailUrl(String commentthumbnailUrl) {
		this.commentthumbnailUrl = commentthumbnailUrl;
	}

	public String getdate() {
		return commentyear;
	}

	public void setdate(String commentyear) {
		this.commentyear = commentyear;
	}



}
