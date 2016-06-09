package com.urlshortener.BL;

import org.hibernate.HibernateException;

import com.urlshortener.dao.ApiEntityDao;
import com.urlshortener.entity.ApiEntity;

public class ValidateApiKey {

	public static boolean isApiKeyValid(String apiKey)
	{
		try
		{
			boolean isKeyEnabled = ApiEntityDao.getApiEntityFromKey(apiKey).isKeyEnabled();
			return isKeyEnabled;
		}
		catch(NullPointerException e)
		{
			//System.out.println(ApiEntityDao.getApiEntityFromKey(apiKey));
			return false;
		}
		catch(HibernateException e)
		{
			System.out.println(ApiEntityDao.getApiEntityFromKey(apiKey));
			return false;
		}
	}
}
