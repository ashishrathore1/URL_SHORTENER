package com.urlshortener.hits.hashmap;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.Semaphore;

import com.urlshortener.hits.dao.HitsDao;
import com.urlshortener.hits.entity.UrlHits;

public class HashMapDao {

	private static HashMap<String, Integer> hashMap;
	public static final int MAX_HASH_MAP_KEYS = 10;
	public static Semaphore acquireHashMap ;
	
	static
	{
		acquireHashMap = new Semaphore(1);
	}
	
	public HashMapDao()
	{
		if(hashMap==null)
			hashMap = new HashMap<String, Integer>();
	}
	
	public void addOrUpdateInHashMap(String key)
	{
		Integer value = hashMap.get(key);
		if(value!=null)
			hashMap.put(key, value.intValue()+1);
		else
			hashMap.put(key, 1);
		
	}
	
	public void deleteAllEntriesInHashMapAndUpdateDB()
	{
		try 
		{
			acquireHashMap.acquire();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		HitsDao dbDao = new HitsDao();
		Set<String> keyList = hashMap.keySet();
		//System.out.println("executing");
		for(String key: keyList)
		{
			UrlHits urlHitsTracker = new UrlHits();
			urlHitsTracker.setCount(hashMap.get(key));
			urlHitsTracker.setShortenedurl(key);
			dbDao.addOrUpdateInDB(urlHitsTracker );
		}
		hashMap.clear();
		acquireHashMap.release();
		
	}
	public boolean checkHashMapSize()
	{
		if(hashMap.size() >= MAX_HASH_MAP_KEYS)
			return true;
		return false;
	}
}

