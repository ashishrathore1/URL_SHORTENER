package com.urlshortener.hits.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.urlshortener.dao.HibernateUtil;
import com.urlshortener.hits.entity.UrlHits;



public class HitsDao {

	private static SessionFactory sesisonFactory = HibernateUtil.getSessionFactory();
	public HitsDao()
	{
		/*System.out.println("1");
		System.out.println("2");*/
	}
	public void addOrUpdateInDB(UrlHits urlHitInstance)
	{
		Session session = null;
		Transaction transaction = null;
		try
		{
			System.out.println("3");
			session = sesisonFactory.openSession();
			System.out.println("4");
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
	
	public static int getHitsCountFromShortenedUrl(String shortenedurl)throws HibernateException
	{
		Session session = null;
		if(shortenedurl=="" || shortenedurl==null)
			return 0;
		try
		{
			session = sesisonFactory.openSession();
			UrlHits urlHitInstance = (UrlHits) session.get(UrlHits.class, shortenedurl);
			if(urlHitInstance == null)
				return 0;
			return urlHitInstance.getCount();
			
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
