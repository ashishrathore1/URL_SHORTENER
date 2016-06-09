package com.urlshortener.entity;

public class UrlShortenerApiResponse {

	private String shortenedUrl;
	private String url;
	private String message; // message will generally contain error message
	
	
	public UrlShortenerApiResponse(String shortenedUrl, String url,
			String message) {
		this.shortenedUrl = shortenedUrl;
		this.url = url;
		this.message = message;
	}

	
	
	public UrlShortenerApiResponse(String shortenedUrl, String url) {
		this.shortenedUrl = shortenedUrl;
		this.url = url;
	}



	public UrlShortenerApiResponse(String message) {
		this.message = message;
	}
	
	public UrlShortenerApiResponse() {
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}	
}
