package com.urlshortener.hits.timertask;

import java.util.Timer;
import java.util.TimerTask;

import com.urlshortener.dao.ApplicationSettingsDao;
import com.urlshortener.hits.hashmap.HashMapDao;


public class DumpHashMapToDB extends TimerTask{
	
	private int seconds;
	
	public DumpHashMapToDB(int seconds)
	{
		this.seconds = seconds;
	}
	
	@Override
	public void run() {
		HashMapDao hashMapDap = new HashMapDao();
		hashMapDap.deleteAllEntriesInHashMapAndUpdateDB();
		ApplicationSettingsDao.getInstance().getROOTURL(); //Keep Hibernate connections alive!! After 8 hours if the application is not used, hibernate connections goes stale.
		//System.out.println("Dumping");
	}
	
	public void startDumpingData()
	{
		TimerTask timerTask = new DumpHashMapToDB(seconds);
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(timerTask, 0, seconds*1000);
	}
}
