package com.urlshortener.BL;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

import org.apache.commons.validator.UrlValidator;
import com.urlshortener.dao.ApplicationSettingsDao;

@SuppressWarnings("deprecation")
public class CheckUrl {
	
	private static String CHECKURL = ApplicationSettingsDao.getInstance().getValueWithKeyName("checkurl");
	private static final String URLSCHEMES = ApplicationSettingsDao.getInstance().getValueWithKeyName("urlschemes");
	public static boolean isUrlValid(String stringUrl)
	{
		try {
			String[] schemes = URLSCHEMES.split("~");
			UrlValidator urlValidator = new UrlValidator(schemes);
			if(!urlValidator.isValid(stringUrl))
			{
				return false;
			}
			URL url = new URL(stringUrl);
			System.out.println("correct");
			if(CHECKURL.equals("enabled"))
			{
				return isUrlLive(url);
			}
			else
			{
				return true;
			}
		} catch (MalformedURLException e) {
			return false;						//to be logged
		}
		
	}
	private static boolean isUrlLive(URL url)
    {	
		try
		{
    		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    		int status = connection.getResponseCode();
    		if(status==404)
    		{
    			return false;					//to be logged
    		}
    		return true;
		}
		catch(IOException e)
		{
			return false;
		}
    }
	
	public static boolean isCustomSlugValid(String customSlug)
	{
		Pattern validPattern = Pattern.compile("[^a-z0-9-_]", Pattern.CASE_INSENSITIVE);
		return !validPattern.matcher(customSlug).find();
	}
	
	
	
}
