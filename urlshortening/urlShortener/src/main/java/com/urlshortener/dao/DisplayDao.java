package com.urlshortener.dao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.urlshortener.entity.URLShortener;

public class DisplayDao {
	
	private static SessionFactory sessionFactory;
	private static final int NUMBER_OF_RECORDS_PAGE = Integer.parseInt(ApplicationSettingsDao.getInstance().getValueWithKeyName("noofrecordsperpage"));
	
	static {
		sessionFactory = HibernateUtil.getSessionFactory();
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<URLShortener> getRecordFromDB()
	{
		
		Session session = sessionFactory.openSession();
		ArrayList<URLShortener> entries = new ArrayList<URLShortener>();
		String hql = "from com.urlshortener.entity.URLShortener";
		Query query = session.createQuery(hql);
		try
		{
			entries = (ArrayList<URLShortener>) query.list();
			return entries;
		}
		catch(ClassCastException e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			if(session!=null)
				session.close();
		}
	}
	
	public static long getTotalRecordsFromDB()
	{
		Session session = null;
		try
		{
			session = sessionFactory.openSession();
			return ((Long) session.createQuery("select count(*) from com.urlshortener.entity.URLShortener").uniqueResult()).intValue();
		}
		finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
	}
	
	public static ArrayList<URLShortener> getRecordFromDB(int pageNumber)throws HibernateException
	{
		Session session = null;
		if(pageNumber == 0)
			pageNumber = 1;
		try
		{	
			session = sessionFactory.openSession();
			String sql = "from com.urlshortener.entity.URLShortener order by id DESC";
			Query query = session.createQuery(sql);
			query.setFirstResult((pageNumber-1) * NUMBER_OF_RECORDS_PAGE);
			query.setMaxResults(NUMBER_OF_RECORDS_PAGE);
			return (ArrayList<URLShortener>) query.list();
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
