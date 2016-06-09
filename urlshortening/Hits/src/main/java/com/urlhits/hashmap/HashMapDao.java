package com.urlhits.hashmap;

import java.util.HashMap;
import java.util.Set;

import org.apache.camel.Converter;
import org.hibernate.annotations.Synchronize;

import com.urlhits.dao.HitsDao;
import com.urlhits.entity.UrlHits;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class HashMapDao {

	private static HashMap<String, Integer> hashMap;
	public static final int MAX_HASH_MAP_KEYS = 10;
	
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
	
	
	@Scheduled(cron = "0 0/1 * * * *")
	public void deleteAllEntriesInHashMap()
	{
		synchronized (hashMap) {
			HitsDao dbDao = new HitsDao();
			Set<String> keyList = hashMap.keySet();
			System.out.println("executing");
			for(String key: keyList)
			{
				UrlHits urlHitsTracker = new UrlHits();
				urlHitsTracker.setCount(hashMap.get(key));
				urlHitsTracker.setShortenedurl(key);
				dbDao.addOrUpdateInDB(urlHitsTracker );
			}
			hashMap.clear();
		}
		
	}
	public boolean checkHashMapSize()
	{
		if(hashMap.size() >= MAX_HASH_MAP_KEYS)
			return true;
		return false;
	}
}
