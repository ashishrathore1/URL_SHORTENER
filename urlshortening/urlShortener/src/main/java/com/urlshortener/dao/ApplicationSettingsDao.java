package com.urlshortener.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.cache.annotation.Cacheable;

import com.urlshortener.entity.ApplicationSettingsEntity;

public class ApplicationSettingsDao {
	
	private static SessionFactory sessionFactory;
	private static ApplicationSettingsDao obj = new ApplicationSettingsDao();
	private static final String ROOTURLkeyname = "rooturl";

	static{
		sessionFactory = HibernateUtil.getSessionFactory();
	}
	
	private ApplicationSettingsDao() {
		
	}

	public static ApplicationSettingsDao getInstance()
	{
		return obj;
	}
	
	@Cacheable
	public String getValueWithKeyName(String keyname) throws HibernateException
	{
		ApplicationSettingsEntity settingsEntity = new ApplicationSettingsEntity();
		Session session = null;
		try
		{
			session = sessionFactory.openSession();
			settingsEntity = (ApplicationSettingsEntity) session.get(ApplicationSettingsEntity.class,keyname);
			return settingsEntity.getValue();
		}
		finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
	}
	
	public void getROOTURL()throws HibernateException
	{
		Session session = null;
		try
		{
			session = sessionFactory.openSession();
			session.get(ApplicationSettingsEntity.class, ROOTURLkeyname);
		}
		finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
	}
}
