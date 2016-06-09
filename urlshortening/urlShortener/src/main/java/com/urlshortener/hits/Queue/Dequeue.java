package com.urlshortener.hits.Queue;

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

import com.urlshortener.hits.hashmap.HashMapDao;
import com.urlshortener.hits.timertask.DumpHashMapToDB;


public class Dequeue extends Thread implements ExceptionListener{
	private static String brokerUri = QueueUtil.getBrokerUri();
	private static String userName = QueueUtil.getUserName();
	private static String password = QueueUtil.getPassword();
	private static final Logger logger = LoggerFactory.getLogger(Dequeue.class);
	/*
	 *TODO
	 * ActiveMQ failure
	 * (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	
	
	public void run()
	{
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(userName, password, brokerUri);
		HashMapDao hashmap = new HashMapDao();
		
		Connection connection = null;
		Session session =null;
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			connection.setExceptionListener(this);
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue("HitsCountQueue");
			MessageConsumer consumer = session.createConsumer(destination);
			while(true)
			{	
				Message message = consumer.receive();
				if(message instanceof TextMessage)
				{
					TextMessage textMessage = (TextMessage) message;
					hashmap.addOrUpdateInHashMap(textMessage.getText());
					boolean hashMapSizeFlag = hashmap.checkHashMapSize(); 
					if(hashMapSizeFlag)
					hashmap.deleteAllEntriesInHashMapAndUpdateDB();
				}
				else
				{
					logger.info("Non text message");
				}	
			}
		} 
		catch (JMSException e) 
		{
			try 
			{
				Thread.sleep(100 * 1000);
			} 
			catch (InterruptedException e1) 
			{
				e1.printStackTrace();
			}
			Dequeue.startDequeueProcess();
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				if(session!=null)
					session.close();
				if(connection!=null)
					connection.close();
			} 
			catch (JMSException e)
			{
				logger.warn(e.toString());
				e.printStackTrace();
			}
		}
	}
	
	public void onException(JMSException arg0) {
		logger.info("JMSException has occured");
		System.out.println("JMSException occured");
	}
	public static void startDequeueProcess()
	{
		Dequeue dequeue = new Dequeue();
		dequeue.start();
		DumpHashMapToDB timerDumpDataToDB = new DumpHashMapToDB(15);
		timerDumpDataToDB.startDumpingData();
		
	}
}
