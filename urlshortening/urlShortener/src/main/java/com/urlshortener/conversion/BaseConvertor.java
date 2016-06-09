package com.urlshortener.conversion;

import java.util.Date;

import org.hibernate.HibernateException;

import com.urlshortener.dao.URLShortenerDao;
import com.urlshortener.exceptions.DuplicateDefaultSlugGeneratedException;

public class BaseConvertor
{
	private static final int MAX_RETRIES = 5;
		
	public static String base36ToBase10(String base36)
	{
		return Integer.valueOf(base36, 36).toString();
	}
	
	public static String base10ToCustomBase36(String base10) throws DuplicateDefaultSlugGeneratedException
	{
		String base36 = Integer.toString(Math.abs(((((int) new Date().getTime()) -(Integer.parseInt(base10))*100))), 36).toString();
		System.out.println(base36 + "~~~~" + ((int) new Date().getTime()) + "~~~~~" + Integer.parseInt(base10));
		boolean entityExistsInDB;
		for(int i =0 ;i<MAX_RETRIES; i++)
		{
			try
			{
				entityExistsInDB = (URLShortenerDao.getEntityFromShortenedUrl(base36) == null) ? true : false;
			}
			catch(HibernateException e)
			{
				continue;
			}
			if(entityExistsInDB)
				return base36;
		}
		throw new DuplicateDefaultSlugGeneratedException();
	}
}
