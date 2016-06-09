package com.urlhits.Queue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.urlhits.hashmap.HashMapDao;

public class Dequeue extends Thread implements ExceptionListener{
	private static String brokerUri;
	private static String userName;
	private static String password;
	//private Logger logger = LoggerFactory.getLogger(Dequeue.class);
	
	static{
    	try {			
			BufferedReader inputFromFile = new BufferedReader(new FileReader(/*System.getenv("HOME") + */"/apps/url-shortner/current/conf/activemq.properties"));
			String brokeruriLine = inputFromFile.readLine();
			String userLine = inputFromFile.readLine();
			String passLine = inputFromFile.readLine();
			brokerUri = brokeruriLine.split("=")[1].trim();
			userName = userLine.split("=")[1].trim();
			password=passLine.split("=")[1].trim();
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
    }
	
	public Dequeue(String brokerUri, String userName, String password)
	{
		this.brokerUri = brokerUri;
		this.userName = userName;
		this.password = password;
	}

	public void run()
	{
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(userName, password, brokerUri);
		HashMapDao hashmap = new HashMapDao();
		
		Connection connection = null;
		while(true)
		{
			
			try {
				connection = connectionFactory.createConnection();
				connection.start();
				connection.setExceptionListener(this);
	
				Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				
				Destination destination = session.createQueue("HitsCountQueue");
				MessageConsumer consumer = session.createConsumer(destination);
				Message message = consumer.receive();
				if(message instanceof TextMessage)
				{
					TextMessage textMessage = (TextMessage) message;
					hashmap.addOrUpdateInHashMap(textMessage.getText());
					boolean hashMapSizeFlag = hashmap.checkHashMapSize(); 
					if(hashMapSizeFlag)
						hashmap.deleteAllEntriesInHashMap();
				}
				else
				{
					//logger.info("Non text message");
				}
				
				session.close();
				connection.close();
			} 
			catch (JMSException e) 
			{
				//logger.warn(e.toString());
			}
			finally
			{
				try {
					connection.close();
				} catch (JMSException e) {
					//logger.warn(e.toString());
				}
			}
		}
	}

	public int queueSize()
	{
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(userName, password, brokerUri);
		Connection connection;
		try 
		{
			connection = connectionFactory.createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			//QueueBrowser browser = session.createBrowser()
		}
		catch(JMSException e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	public void onException(JMSException arg0) {
		System.out.println("JMSException occured");
	}
	public static void main(String args[])
	{
		Dequeue dequeue = new Dequeue(brokerUri, userName, password);
		dequeue.start();
		
	}
}
