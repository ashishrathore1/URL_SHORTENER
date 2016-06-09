package com.urlshortener.dao;

import java.io.FileInputStream;
import java.util.Properties;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

import com.urlshortener.entity.ApiEntity;

public class HibernateUtil {
	private static SessionFactory sessionFactory;
	private static Configuration configuration;
	static {
		try
		{
			configuration = new AnnotationConfiguration();
			Properties properties = new Properties();
			FileInputStream fileInputStream = new FileInputStream(/*System.getenv("HOME") +*/ "/apps/url-shortner/current/conf/db.properties");
        	properties.load(fileInputStream);
			configuration = configuration.configure("hibernate.cfg.xml");
			configuration.addProperties(properties);
			sessionFactory = configuration.buildSessionFactory();
			sessionFactory.evict(ApiEntity.class);
			fileInputStream.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
