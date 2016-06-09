package com.urlshortener.gson;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.urlshortener.entity.URLShortener;

public class UrlShortenerGsonTypeAdapter extends TypeAdapter<URLShortener>{

	@Override
	public URLShortener read(JsonReader in) throws IOException {
		URLShortener urlShortener = new URLShortener();
		in.beginObject();
		while(in.hasNext())
		{
			String name = in.nextName();
			if("id".equals(name))
				urlShortener.setId(in.nextInt());
			else if("shortenedUrl".equals(name))
				urlShortener.setShortenedUrl(in.nextString());
			else if("url".equals(name))
				urlShortener.setUrl(in.nextString());
			else if("userID".equals(name))
				urlShortener.setUserID(in.nextString());
		}
		in.endObject();
		return urlShortener;
	}

	@Override
	public void write(JsonWriter out, URLShortener urlShortener) throws IOException {
		
		out.beginObject();
		out.name("shortenedUrl").value(urlShortener.getShortenedUrl());
		if(urlShortener.getId()!=0)
			out.name("id").value(urlShortener.getId());
		out.name("url").value(urlShortener.getUrl());
		out.endObject();
		
		
	}

}
