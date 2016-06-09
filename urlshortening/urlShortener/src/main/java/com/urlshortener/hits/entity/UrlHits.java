package com.urlshortener.hits.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="urlhitcount")
public class UrlHits {

	@Id
	@Column(name="shortenedurl")
	private String shortenedurl;
	
	@Column(name="count")
	private int count;
	
	public UrlHits() {
		super();
	}

	@Override
	public String toString() {
		return "UrlHits [shortenedurl=" + shortenedurl + ", count=" + count
				+ "]";
	}

	public UrlHits(String shortenedurl, int count) {
		super();
		this.shortenedurl = shortenedurl;
		this.count = count;
	}

	public String getShortenedurl() {
		return shortenedurl;
	}

	public void setShortenedurl(String shortenedurl) {
		this.shortenedurl = shortenedurl;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}	
}
