package com.urlshortener.hits.Queue;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Enqueue extends Thread {
	
	private String shortenedUrl;
	private int number_of_retries;
	private static final int MAX_RETRIES = 3;
	private static String brokerUri = QueueUtil.getBrokerUri();
    private static String userName = QueueUtil.getUserName();
    private static String password = QueueUtil.getPassword();
    private static ActiveMQConnectionFactory connectionFactory;
    private static Connection connection;
    private static final Logger logger = LoggerFactory.getLogger(Enqueue.class);
   
    
    static
    {
    	try 
    	{
    		System.out.println("ActiiveMQ Credentials");
    		System.out.println(userName + "~~~~" + password + "~~~~" + brokerUri);
			connectionFactory = new ActiveMQConnectionFactory(userName, password, brokerUri);
			connection = connectionFactory.createConnection();
        	connection.start();
        	logger.info("Connection established with ActiveMQ");
		}
    	catch(NullPointerException e)
		{
			e.printStackTrace();
		}
    	catch (JMSException e) {
			System.out.println("Couldn't connect to ActiveMQ");
			e.printStackTrace();
		}
    }
    
	public Enqueue(String shortenedUrl) {
		this.shortenedUrl = shortenedUrl;
		this.number_of_retries = 0;
	}
	
	public Enqueue(String shortenedUrl, int number_of_retries) {
		this.shortenedUrl = shortenedUrl;
		this.number_of_retries = number_of_retries;
	}
	
	public void setShortenedUrl(String shortenedUrl) {
		this.shortenedUrl = shortenedUrl;
	}

	public void run()
	{
		System.out.println("Retrying connecting to ActiveMQ " + this.number_of_retries);
        Session session=null;
        try
        {
        	System.out.println("Hiits Count Queue About To Create");
        	logger.info("Hiits Count Queue About To Create");
        	session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        	Destination destination = session.createQueue("HitsCountQueue");
        	System.out.println("Hiits Count Queue Created");
        	logger.info("Hiits Count Queue Created");
        	MessageProducer messageProducer= session.createProducer(destination);
        	messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        	TextMessage message = session.createTextMessage(shortenedUrl);
        	messageProducer.send(message);
        }
        catch(JMSException e)
        {
        	try 
        	{
				Thread.sleep(100 * 1000);
			}
        	catch (InterruptedException e1) 
        	{
				e1.printStackTrace();
			}
        	if(this.number_of_retries<MAX_RETRIES)
        	{
        		try
        		{
        			connectionFactory = new ActiveMQConnectionFactory(userName, password, brokerUri);
        			connection = connectionFactory.createConnection();
        			connection.start();
        		}
        		catch(JMSException e1)
        		{
        			logger.info(e.toString());
        			e1.printStackTrace();
        		}
        		Enqueue enqueue = new Enqueue(shortenedUrl, ++this.number_of_retries);
        		enqueue.start();
        	}
        	
        }
        finally
        {
        	if(session!=null)
        	{
        		try 
        		{	
					session.close();
				} 
        		catch (JMSException e) 
        		{
        			logger.info(e.toString() + "ERROR IN CLOSING THEE EXCEPTION");
					e.printStackTrace();
				}
        	}
        }
	}
}
