package com.urlshortener.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.urlshortener.entity.ApiEntity;

public class ApiEntityDao {
	
	private static SessionFactory sessionFactory;
	
	static 
	{
		sessionFactory = HibernateUtil.getSessionFactory();
	}

	public static void addApiEntity(ApiEntityDao apiEntity)throws HibernateException
	{
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(apiEntity);
			transaction.commit();
		}
		finally
		{
			if(session!=null)
				session.close();
		}
	}
	
	public static ApiEntity getApiEntityFromKey(String key)throws HibernateException
	{
		Session session = null;
		try
		{
			session = sessionFactory.openSession();
			ApiEntity entity = (ApiEntity) session.get(ApiEntity.class, key);
			return entity;
		}
		finally
		{
			if(session!=null)
				session.close();
		}
	}
}
