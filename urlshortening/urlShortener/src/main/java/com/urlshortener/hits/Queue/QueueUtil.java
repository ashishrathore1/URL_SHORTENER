package com.urlshortener.hits.Queue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class QueueUtil {

	private static String brokerUri;
	private static String userName;
	private static String password;
	
	static
	{
		try
		{
			BufferedReader inputFromFile = new BufferedReader(new FileReader(/*System.getenv("HOME") +*/"/apps/url-shortner/current/conf/activemq.properties"));
			brokerUri = inputFromFile.readLine().split("=")[1].trim();
			userName = inputFromFile.readLine().split("=")[1].trim();
			password= inputFromFile.readLine().split("=")[1].trim();
			inputFromFile.close();
			Dequeue.startDequeueProcess();
    	}
    	catch(FileNotFoundException e)
    	{
    		e.printStackTrace();
    	}
    	catch(IOException e)
    	{
    		e.printStackTrace();
    	}
	}
	
	public static String getBrokerUri()
	{
		return brokerUri;		
	}
	
	public static String getUserName()
	{
		return userName;
	}
	
	public static String getPassword()
	{
		return password;
	}
}
