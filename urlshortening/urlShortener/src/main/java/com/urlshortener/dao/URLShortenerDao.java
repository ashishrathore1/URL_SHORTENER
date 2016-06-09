package com.urlshortener.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.urlshortener.conversion.BaseConvertor;
import com.urlshortener.entity.URLShortener;
import com.urlshortener.exceptions.CustomSlugLimitExceededException;
import com.urlshortener.exceptions.DuplicateDefaultSlugGeneratedException;

public class URLShortenerDao {
	
	private static final Logger logger = LoggerFactory.getLogger(URLShortenerDao.class);
	private static final int MAX_LENGTH_CUSTOM_LENGTH = 14;
	
	private static SessionFactory sessionFactory;
	
	static{
		sessionFactory = HibernateUtil.getSessionFactory();
	}
	
	public URLShortenerDao() {
		
	}
	
	public static boolean addOrUpdateToDb(URLShortener entity)throws DuplicateDefaultSlugGeneratedException
	{
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(entity);
			transaction.commit();
			entity.setShortenedUrl(BaseConvertor.base10ToCustomBase36(Integer.toString(entity.getId())));
			transaction = session.beginTransaction();
			session.update(entity);
			transaction.commit();
			return true;
		}
		finally
		{
			if(session!=null)
				session.close();
		}
	}
	
	public static String getShortenedurl(int id)throws HibernateException
	{
		Session session =null;
		try
		{
			
			session = sessionFactory.openSession();
			URLShortener entity = (URLShortener) session.get(URLShortener.class, id);
			if(entity==null)
			{
				return null;
			}
			return entity.getShortenedUrl();
		}
		finally
		{
			if(session!= null)
			{
				session.close();
			}
		}

	}
	
	private static URLShortener getEntityFromUrl(String url)throws HibernateException
	{
		Session session=null;
		try
		{
			session = sessionFactory.openSession();	
			String hql ="from com.urlshortener.entity.URLShortener where url = :urlParam ";
			Query query =session.createQuery(hql);
			query.setParameter("urlParam", url);
			return ((URLShortener) query.uniqueResult());
		}
		finally
		{
			if(session!=null)
			{
				session.close();
			}
		}

	}
	
	public static URLShortener getEntityFromShortenedUrl(String shortenedUrl)throws HibernateException
	{
		Session session = null;
		try
		{
			session = sessionFactory.openSession();
			String hql = "from com.urlshortener.entity.URLShortener where shortenedUrl = :shortenedUrlParam";
			Query query = session.createQuery(hql);
			query.setParameter("shortenedUrlParam", shortenedUrl);
			return ((URLShortener) query.uniqueResult());
		}
		finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
	}
	
	public static boolean addCustomUrl(String url,String customUrl)throws HibernateException, CustomSlugLimitExceededException
	{
		if(customUrl.length()>=MAX_LENGTH_CUSTOM_LENGTH)
			throw new CustomSlugLimitExceededException();
		String hql = "from com.urlshortener.entity.URLShortener where shortenedUrl = :customUrlParam";
		Session session = null;
		try
		{
			session = sessionFactory.openSession();
			Query query = session.createQuery(hql);
			query.setParameter("customUrlParam", customUrl);
			URLShortener entity = (URLShortener) query.uniqueResult();
			if(entity==null)
			{
				entity = new URLShortener();
				entity.setShortenedUrl(customUrl);
				entity.setUrl(url);
				Transaction transaction = session.beginTransaction();
				session.save(entity);
				transaction.commit();
				return true;
			}
			if(entity.getUrl().equalsIgnoreCase(url))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
	}
	
	
	public static boolean addCustomUrl(String url,String customUrl, String userID)throws HibernateException, CustomSlugLimitExceededException
	{
		if(customUrl.length()>MAX_LENGTH_CUSTOM_LENGTH)
			throw new CustomSlugLimitExceededException();
		String hql = "from com.urlshortener.entity.URLShortener where shortenedUrl = :customUrlParam";
		Session session = null;
		try
		{
			session = sessionFactory.openSession();
			Query query = session.createQuery(hql);
			query.setParameter("customUrlParam", customUrl);
			URLShortener entity = (URLShortener) query.uniqueResult();
			if(entity==null)
			{
				entity = new URLShortener();
				entity.setShortenedUrl(customUrl);
				entity.setUrl(url);
				entity.setUserID(userID);
				Transaction transaction = session.beginTransaction();
				session.save(entity);
				transaction.commit();
				return true;
			}
			if(entity.getUrl().equalsIgnoreCase(url))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
	}
	
	public static boolean deleteEntityFromShortenedUrl(String shortenedUrl)throws HibernateException
	{
		String hql = "delete from com.urlshortener.entity.URLShortener where shortenedUrl = :shortenedUrlParam";
		Session session = null;
		try
		{
			session = sessionFactory.openSession();
			Query query = session.createQuery(hql);
			query.setParameter("shortenedUrlParam", shortenedUrl);
			query.executeUpdate();
			return true;
		}
		finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
	}
	
	public static String getUrlFromShortenedUrl(String shortenedUrl)throws HibernateException
	{
		Session session = null;
		String hql = "from com.urlshortener.entity.URLShortener where shortenedUrl = :shortenedUrlParam";
		try
		{
			session = sessionFactory.openSession();
			Query query = session.createQuery(hql);
			query.setParameter("shortenedUrlParam", shortenedUrl);
			URLShortener entity = (URLShortener) query.uniqueResult();
			if(entity==null)
			{
				return null;
			}
			return entity.getUrl();
		}
		finally
		{
			if(session!=null)
				session.close();
		}
		
	}
	
	public static String getShortenedUrlFromUrl(String url)throws HibernateException
	{
		Session session = null;
		String hql = "from com.urlshortener.entity.URLShortener where url = :urlParam";
		try
		{
			session = sessionFactory.openSession();
			Query query = session.createQuery(hql);
			query.setParameter("urlParam", url);
			URLShortener entity = (URLShortener) query.uniqueResult();
			if(entity==null)
			{
				return null;
			}
			return entity.getShortenedUrl();
		}
		finally
		{
			if(session!=null)
				session.close();
		}
		
	}
	

}
