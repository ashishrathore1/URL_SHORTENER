package com.urlhits.dao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

public class HibernateUtil {
	private static SessionFactory sessionFactory;
	private static Configuration configuration;
	static {
		configuration = new AnnotationConfiguration();
		Properties properties = new Properties();
        try {
        	properties.load(new FileInputStream(/*System.getenv("HOME") + */"/apps/url-shortner/current/conf/db.properties"));
        	//properties.load(new FileInputStream("apps/url-shortener/releases/1.0.0/conf/db.properties"));
        	System.out.println("FILE LOADED");
        } catch (IOException e) {
			System.out.println("FILE NOT LOADED");
			e.printStackTrace();
		}
        configuration = configuration.configure();
        configuration.addProperties(properties);
        sessionFactory = configuration.buildSessionFactory();
        
	}
	public static SessionFactory getSessionFactory(){
		if(sessionFactory==null)
			sessionFactory=configuration.buildSessionFactory();
		return sessionFactory;
	}
}