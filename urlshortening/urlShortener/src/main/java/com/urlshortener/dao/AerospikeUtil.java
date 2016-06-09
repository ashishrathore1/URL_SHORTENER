package com.urlshortener.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.AerospikeException;
import com.aerospike.client.Key;
import com.aerospike.client.policy.ClientPolicy;

public class AerospikeUtil {
	
	private static String aerospikeIP;
	private static int aerospikePort;
	private static AerospikeClient aerospikeClient;
	private static ClientPolicy clientPolicy;
	private static Key urlKey;
	
	public AerospikeUtil() {
	}
	
	static
	{
		try
		{
			System.out.println("IN the try-static block");
			BufferedReader inputFromFile = new BufferedReader(new FileReader(/*System.getenv("HOME") + */"/apps/url-shortner/current/conf/aerospike.properties"));
			aerospikeIP = inputFromFile.readLine().split("=")[1].trim();
			aerospikePort = Integer.parseInt(inputFromFile.readLine().split("=")[1].trim());
			initializeClientPolicy();
			aerospikeClient = new AerospikeClient(clientPolicy, aerospikeIP,aerospikePort);
			urlKey = new Key("urlshortener", "urlmapping","urlkey");
			inputFromFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static AerospikeClient getAerospikeClient()
	{
		if(aerospikeClient == null)
		{
			try 
			{
				return new AerospikeClient(clientPolicy, aerospikeIP, aerospikePort);
			}
			catch (AerospikeException e) 
			{
				e.printStackTrace();
				return null;
			}
		}
		return aerospikeClient;
	}
	
	public static Key getUrlKey()
	{
		if(urlKey == null)
		{
			try
			{	
				urlKey = new Key("urlshortener", "urlmapping","urlkey");
			} 
			catch (AerospikeException e) 
			{
				e.printStackTrace();
				return null;
			}
		}
		return urlKey;
	}
	
	public static void initializeClientPolicy()throws Exception
	{
		clientPolicy = new ClientPolicy();
		try
		{
			clientPolicy.readPolicyDefault.timeout = 4000;    
			clientPolicy.readPolicyDefault.maxRetries = 2;
			clientPolicy.readPolicyDefault.sleepBetweenRetries = 10;
			clientPolicy.writePolicyDefault.timeout = 4000;    
			clientPolicy.writePolicyDefault.maxRetries = 2;
			clientPolicy.writePolicyDefault.sleepBetweenRetries = 50;			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}









/*package com.urlshortener.dao;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import com.aerospike.client.AerospikeClient;
import com.aerospike.client.AerospikeException;
import com.aerospike.client.Host;
import com.aerospike.client.Key;
import com.aerospike.client.policy.ClientPolicy;

public class AerospikeUtil {
	
	private static AerospikeClient aerospikeClient;
	private static Host hosts[];
	private static Key urlKey;
	private static ClientPolicy clientPolicy;
	
	public AerospikeUtil() {
	}
	
	static
	{
		try
		{
			initializeAerospikeHosts();
			initializeClientAndClientPolicy();
			initializeKeys();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void initializeKeys()throws Exception
	{
		urlKey = new Key("urlshortener", "urlmapping","urlkey");
	}
	
	
	public static void initializeAerospikeHosts()throws NumberFormatException, IOException, Exception
	{
		String ip;
		int port;
		int i=0;
		String fileName = System.getenv("HOME") + "/apps/url-shortner/current/conf/aerospike.properties" ;
		int numberOfAerospikeInstances = getNumerOfAerospikeInstances(fileName);
		System.out.println("numberOfAerospikeInstances" + numberOfAerospikeInstances);
		BufferedReader inputFromFile = new BufferedReader(new FileReader(fileName));
		hosts = new Host[numberOfAerospikeInstances];
		while(i<numberOfAerospikeInstances)
		{
			ip = inputFromFile.readLine().split("=")[1].trim();
			port = Integer.parseInt(inputFromFile.readLine().split("=")[1].trim());
			hosts[i] = new Host(ip, port);
			i++;
		}
		inputFromFile.close();
	}
	
	public static void initializeClientAndClientPolicy()throws Exception
	{
		clientPolicy = new ClientPolicy();
		if(hosts == null)
			initializeAerospikeHosts();
		try
		{
			clientPolicy.readPolicyDefault.timeout = 4000;    
			clientPolicy.readPolicyDefault.maxRetries = 2;
			clientPolicy.readPolicyDefault.sleepBetweenRetries = 10;
			clientPolicy.writePolicyDefault.timeout = 4000;    
			clientPolicy.writePolicyDefault.maxRetries = 2;
			clientPolicy.writePolicyDefault.sleepBetweenRetries = 50;
			aerospikeClient = new AerospikeClient(clientPolicy, hosts);			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static AerospikeClient getAerospikeClient()
	{
		if(aerospikeClient == null)
		{
			try 
			{
				return new AerospikeClient(clientPolicy, hosts);
			}
			catch (AerospikeException e) 
			{
				e.printStackTrace();
				return null;
			}
		}
		return aerospikeClient;
	}
	
	public static Key getUrlKey()
	{
		if(urlKey == null)
		{
			try
			{	
				urlKey = new Key("urlshortener", "urlmapping","urlkey");
			} 
			catch (AerospikeException e) 
			{
				e.printStackTrace();
				return null;
			}
		}
		return urlKey;
	}
	
	public static int getNumerOfAerospikeInstances(String fileName)throws IOException
	{
		InputStream is = new BufferedInputStream(new FileInputStream(fileName));
	    try {
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = is.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	        }
	        return (count == 0 && !empty) ? 1 : (count/2);
	    } finally {
	        is.close();
	    }
	}
}
*/