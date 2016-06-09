package com.urlshortener.BL;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.urlshortener.dao.ApplicationSettingsDao;
import com.urlshortener.dao.URLShortenerDao;
import com.urlshortener.entity.URLShortener;
import com.urlshortener.exceptions.CustomSlugLimitExceededException;
import com.urlshortener.exceptions.DuplicateDefaultSlugGeneratedException;
import com.urlshortener.exceptions.DuplicateCustomUrlException;

public class DeleteAddUrl {
	
	private static String ROOTURL = ApplicationSettingsDao.getInstance().getValueWithKeyName("rooturl");
	private static final Logger logger = LoggerFactory.getLogger(DeleteAddUrl.class);
	/*public static boolean addCustomUrlStatus1(String url, String customUrl)
	{
		try
		{
			return URLShortenerDao.addCustomUrl(url, customUrl);
		}
		catch(HibernateException e)
		{
			return false;
		}

	}
	
	public static boolean addCustomUrlFromFile1(String url, String customUrl)
	{
		try
		{
			return URLShortenerDao.addCustomUrl(url, customUrl);
		}
		catch(Exception e)
		{
			return false;
		}
	}*/
	
	
	public static String addCustomUrl(String url,String customUrl, String userID)throws DuplicateCustomUrlException, CustomSlugLimitExceededException
    {
		try
		{
			if(URLShortenerDao.addCustomUrl(url, customUrl, userID))
			{
				System.out.println("CUSTOM URL" + customUrl);
				return ROOTURL+customUrl;
			}
			else
			{
				throw new DuplicateCustomUrlException();
				//return "url already exists for this customUrl "+ customUrl ;
			}
		}
		catch(HibernateException e)
		{
			System.out.println("CUSTOM SLUG EXISTS " +customUrl);
			return ROOTURL+URLShortenerDao.getShortenedUrlFromUrl(url);
		}
    }
	
	public static String addDefaultUrl(String url, String userID)
    {
		URLShortener entity = new URLShortener();
		entity.setUrl(url);
		entity.setUserID(userID);
		try
		{
			if(URLShortenerDao.addOrUpdateToDb(entity))
			{
				return ROOTURL+entity.getShortenedUrl();
			}
			else
			{
				return "Please enter Valid URL";
			}
		}
		catch(DuplicateDefaultSlugGeneratedException e)
		{
			return "Please try again later"; 
		}
    }
	
	@Deprecated
	public static String deleteEntityFromShortenedUrl(String delete)
    {
		if(URLShortenerDao.deleteEntityFromShortenedUrl(delete))
		{
			return "successfully deleted record";
		}
		else
		{
			logger.warn("Deletion not possible");
			return "oops.. something went wrong, please try again";
		}
    }
	
	/*public static String addCustomUrl(String url,String customUrl)throws DuplicateCustomUrlException
    {
		try
		{
			if(URLShortenerDao.addCustomUrl(url, customUrl))
			{
				return ROOTURL+customUrl;
			}
			else
			{
				throw new DuplicateCustomUrlException();
				//return "url already exists for this customUrl "+ customUrl ;
			}
		}
		catch(HibernateException e)
		{
			return ROOTURL+URLShortenerDao.getShortenedUrlFromUrl(url);
		}
    }
	
	public static String addDefaultUrl(String url)
    {
		URLShortener entity = new URLShortener();
		entity.setUrl(url);
		try
		{
			if(URLShortenerDao.addOrUpdateToDb(entity))
			{
				return ROOTURL+entity.getShortenedUrl();
			}
			else
			{
				return "Please enter the url";
			}
		}
		catch(DuplicateDefaultSlugGeneratedException e)
		{
			return "Please try again later"; 
		}
    }
	
	public static String deleteEntityFromShortenedUrl(String delete)
    {
		if(URLShortenerDao.deleteEntityFromShortenedUrl(delete))
		{
			return "successfully deleted record";
		}
		else
		{
			logger.warn("Deletion not possible");
			return "oops.. something went wrong, please try again";
		}
    }*/

}
