/*package com.urlshortener.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.urlshortener.BL.CheckUrl;
import com.urlshortener.BL.DeleteAddUrl;
import com.urlshortener.BL.RedirectURL;
import com.urlshortener.BL.ValidateApiKey;
import com.urlshortener.entity.URLShortener;
import com.urlshortener.entity.UrlShortenerApiResponse;
import com.urlshortener.exceptions.CustomSlugLimitExceededException;
import com.urlshortener.exceptions.DuplicateCustomUrlException;
import com.urlshortener.gson.UrlShortenerGsonTypeAdapter;

@RestController
public class ApiController {
	
	private Gson customGson;
	
	private GsonBuilder gsonBuilder;
	
	{
		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(URLShortener.class, new UrlShortenerGsonTypeAdapter());
		gsonBuilder.setPrettyPrinting();
		customGson = gsonBuilder.create();
	}
	
	
	@RequestMapping(value="urlshortener/api/setnewurl",method=RequestMethod.POST)
	public ResponseEntity<String> setNewUrl(HttpServletRequest request)
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		String apikey = request.getHeader("apikey");
		System.out.println(apikey);
		if(!ValidateApiKey.isApiKeyValid(apikey))
			return new ResponseEntity<String>(gson.toJson(new UrlShortenerApiResponse("Api key Invalid")),HttpStatus.FORBIDDEN); //status code 403

		String url = request.getParameter("url");
		String customUrl = request.getParameter("customUrl");
		String generatedShortenedUrl = "";
		try
		{
			if (!url.contains("://")) {
				url = "http://" + url;
			}
			if (((!CheckUrl.isUrlValid(url)) )) {
				
				//System.out.println(message + "URL OR CUSTOM SLUG NOT  VALID");
				if(customUrl!=null)
					return new ResponseEntity<String>(gson.toJson(new UrlShortenerApiResponse("Url invalid")),HttpStatus.OK); //status code 403
				else
					return new ResponseEntity<String>(gson.toJson(new UrlShortenerApiResponse("Url invalid")),HttpStatus.OK);
			}
			else if(customUrl!=null &&  (!CheckUrl.isCustomSlugValid(customUrl)))
			{
				return new ResponseEntity<String>(gson.toJson(new UrlShortenerApiResponse(customUrl, url, "CustomSlug invalid")),HttpStatus.OK); //status code 403
			}
			else 
			{
				if ((!(customUrl == "" || customUrl == null)) && (!(request.getParameter("url") == "" || request.getParameter("url") == null))) 
				{
					generatedShortenedUrl = DeleteAddUrl.addCustomUrl(url, customUrl, apikey);
				}
				else if (!(request.getParameter("url") == null || request.getParameter("url") == "")) 
				{
					generatedShortenedUrl = DeleteAddUrl.addDefaultUrl(url, apikey);
				}
			}
		}
		catch(CustomSlugLimitExceededException e)
		{
			return new ResponseEntity<String>(gson.toJson(new UrlShortenerApiResponse("Custom Slug cannot be greater than 14 characters")),HttpStatus.OK); //status code 403
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
			return new ResponseEntity<String>(gson.toJson(new UrlShortenerApiResponse("No Url Added")),HttpStatus.OK);
		}
		catch(DuplicateCustomUrlException e)
		{
			e.printStackTrace();
			return new ResponseEntity<String>(gson.toJson(new UrlShortenerApiResponse("Custom slug already exists for your input")),HttpStatus.OK); 
		}
		catch(IndexOutOfBoundsException e)
		{
			e.printStackTrace();
			if(customUrl!=null)
				return new ResponseEntity<String>(gson.toJson(new UrlShortenerApiResponse("Url Invalid")),HttpStatus.OK);
			else
				return new ResponseEntity<String>(gson.toJson(new UrlShortenerApiResponse("Url Invalid")),HttpStatus.OK);
		}
		catch(Exception e)
		{
			
			return new ResponseEntity<String>(gson.toJson("Something broke. Please contact admin"),HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<String>(gson.toJson(new UrlShortenerApiResponse(generatedShortenedUrl, url)),HttpStatus.OK);
	}
	
	@RequestMapping(value="urlshortener/api/getredirecturl",method=RequestMethod.GET)
	public ResponseEntity<String> getRedirectUrl(HttpServletRequest request)
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String shortenedUrl = request.getParameter("shortenedUrl");
		String url = RedirectURL.getRedirectURL(shortenedUrl);
		if(url==null)
		{
			return new ResponseEntity<String>(gson.toJson(new UrlShortenerApiResponse("url not found")),HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>(gson.toJson(new UrlShortenerApiResponse(shortenedUrl, url)),HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="urlshortener/api/setcustomurl",method=RequestMethod.POST)
	public ResponseEntity<String> setCustomUrl(HttpServletRequest request)
	{
		String url = request.getParameter("url");
		String customUrl = request.getParameter("customUrl");
		try
		{
			if (!url.contains("://")) {
				url = "http://" + url;
			}
			if(CheckUrl.isUrlValid(url)&&CheckUrl.isCustomSlugValid(customUrl)&&DeleteAddUrl.addCustomUrlStatus(url, customUrl))
			{
				ApiEntity apiEntity = new ApiEntity(url, customUrl);
				return new ResponseEntity<String>(gson.toJson(apiEntity), HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<String>(gson.toJson("parameters not valid or custom url exists"), HttpStatus.OK);
			}
		}
		catch(NullPointerException e)
		{
			logger.warn(e.getMessage());
			return new ResponseEntity<String>(gson.toJson("url or customurl not set"),HttpStatus.BAD_REQUEST);
		}
		catch(IndexOutOfBoundsException e)
		{
			logger.warn(e.getMessage());
			return new ResponseEntity<String>(gson.toJson("not valid"),HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			logger.warn(e.getMessage());
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
*/