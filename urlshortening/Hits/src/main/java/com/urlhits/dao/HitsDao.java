package com.urlhits.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.urlhits.entity.UrlHits;

public class HitsDao {

	private SessionFactory sesisonFactory = null;
	public HitsDao()
	{
		sesisonFactory = HibernateUtil.getSessionFactory();
	}
	public void addOrUpdateInDB(UrlHits urlHitInstance)
	{
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = sesisonFactory.openSession();
			transaction = session.beginTransaction();
			session.save(urlHitInstance);
			transaction.commit();
		}
		catch(HibernateException e)
		{
			if(transaction!=null)
			{
				transaction.rollback();
			}
			transaction = session.beginTransaction();
			String sql = "UPDATE urlhitcount set count = count + :countParam WHERE shortenedurl = :shortenedurlParam";
			Query query = session.createSQLQuery(sql);
			query.setInteger("countParam", urlHitInstance.getCount());
			query.setParameter("shortenedurlParam", urlHitInstance.getShortenedurl());
			query.executeUpdate();
			transaction.commit();
		}
		finally
		{
			if(session!=null)
				session.close();
		}
		
	}
}
