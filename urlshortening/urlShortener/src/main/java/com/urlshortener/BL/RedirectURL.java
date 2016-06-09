package com.urlshortener.BL;

import com.urlshortener.dao.AerospikeDao;
import com.urlshortener.dao.URLShortenerDao;
import com.urlshortener.entity.URLShortener;

public class RedirectURL {	
	public static String getRedirectURL(String shortenedURL)
	{
		String urlFromAerospike = AerospikeDao.getRedirectUrlFromAerospike(shortenedURL);
		if(urlFromAerospike!=null)
		{
			return urlFromAerospike;
		}
		URLShortener entity = URLShortenerDao.getEntityFromShortenedUrl(shortenedURL);
		if(entity==null)
		{
			return null;
		}
		AerospikeDao.setRedirectUrlToAerospike(entity.getShortenedUrl(), entity.getUrl());
		return entity.getUrl();
	}	
}
