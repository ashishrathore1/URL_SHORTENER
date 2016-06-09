package com.urlshortener.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "urldetails")
public class URLShortener
{
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;
	
	@Column(name = "shortenedurl",unique=true)
	String shortenedUrl;
	
	@Column(name = "url",nullable=false)
	String url;

	@Column(name = "userid", nullable=false)
	String userID;
	
	public URLShortener() {
		
	}
	
	public URLShortener(int id, String shortenedUrl, String url, String userID) {
		this.id = id;
		this.shortenedUrl = shortenedUrl;
		this.url = url;
		this.userID = userID;
	}

	public URLShortener(String shortenedURL, String url){
		this.shortenedUrl = shortenedURL;
		this.url = url;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getShortenedUrl() {
		return shortenedUrl;
	}

	public void setShortenedUrl(String shortenedUrl) {
		this.shortenedUrl = shortenedUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	@Override
	public String toString() {
		return "URLShortener [id=" + id + ", shortenedUrl=" + shortenedUrl
				+ ", url=" + url + ", userID=" + userID + "]";
	}
		
}
