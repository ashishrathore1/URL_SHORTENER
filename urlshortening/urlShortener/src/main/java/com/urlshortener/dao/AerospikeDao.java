package com.urlshortener.dao;


import com.aerospike.client.AerospikeClient;
import com.aerospike.client.AerospikeException;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;

public class AerospikeDao {
	
	private static AerospikeClient aerospikeClient = AerospikeUtil.getAerospikeClient();
	private static Key urlKey = AerospikeUtil.getUrlKey();
	
    public static String getRedirectUrlFromAerospike(String shortenedUrl)
    {
    	try 
    	{
			Record record = aerospikeClient.get(null, urlKey);
			if(record==null)
			{
				return null;
			}
			String url = (String) record.getValue(shortenedUrl);
			return url;
		}
    	catch (AerospikeException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    public static void setRedirectUrlToAerospike(String shortenedUrl, String url)
    {
    	try 
    	{
			Bin bin = new Bin(shortenedUrl, url);
			aerospikeClient.add(null, urlKey, bin);
		}
    	catch (AerospikeException e) 
    	{
			e.printStackTrace();
		}
    }
}