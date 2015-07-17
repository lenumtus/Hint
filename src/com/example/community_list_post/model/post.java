package com.example.community_list_post.model;



public class post {
	private String title, thumbnailUrl;
	private String year;
    private String content;
    private int version , id_ser;
    private String like;
    private String promoted , post_ads;
	public post() {
	}

	public post(String name , int version, String like, String promoted , String post_ads, String content, String thumbnailUrl, String year , int id_ser ) {
		this.title = name;
		this.thumbnailUrl = thumbnailUrl;
		this.year = year;
		this.content = content;
		this.version = version;
		this.like = like;
		this.promoted = promoted;
		this.post_ads = promoted;
		this.id_ser = id_ser;
	
	}
	public int getversion() {
		return version;
	}

	public void setversion(int version) {
		this.version = version;
	}
	public String getlike() {
		return like;
	}

	public void setlike(String like) {
		this.like = like;
	}
	
	public String getpromoted() {
		return promoted;
	}

	public void setpromoted(String promoted) {
		this.promoted = promoted;
	}
	
	public String getpost_ads() {
		return post_ads;
	}

	public void setpost_ads(String post_ads) {
		this.post_ads = post_ads;
	}
	
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String name) {
		this.title = name;
	}
	public String getcontent() {
		return content;
	}

	public void setcontent(String content) {
		this.content = content;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getdate() {
		return year;
	}

	public void setdate(String year) {
		this.year = year;
	}
	public int getid_ser() {
		return id_ser;
	}

	public void setid_ser(int id_ser) {
		this.id_ser = id_ser;
	}


}
