package com.urlshortener.BL;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import com.urlshortener.dao.ApplicationSettingsDao;
import com.urlshortener.dao.DisplayDao;
import com.urlshortener.entity.URLShortener;
import com.urlshortener.hits.dao.HitsDao;

public class Display {
	
	public static final String ROOTURL = ApplicationSettingsDao.getInstance().getValueWithKeyName("rooturl");
	private static final Logger logger = LoggerFactory.getLogger(Display.class);
	private static final int NUMBER_OF_RECORDS_PAGE = Integer.parseInt(ApplicationSettingsDao.getInstance().getValueWithKeyName("noofrecordsperpage"));
	
	public static ArrayList<URLShortener> getListOfUrls(int pageNumber)
	{
		ArrayList<URLShortener> entries = DisplayDao.getRecordFromDB(pageNumber);
		/*System.out.println(entries);*/
		try
		{
			for(int i=0;i<entries.size();i++)
			{
				entries.get(i).setShortenedUrl(ROOTURL+entries.get(i).getShortenedUrl());
			}
		}
		catch(IndexOutOfBoundsException e)
		{
			logger.warn("No entries in database");
		}
		return entries;
		
	}
	
	public static void setListOfUrlsAndHitCountInModel(int pageNumber, Model model)
	{
		ArrayList<URLShortener> urlList = DisplayDao.getRecordFromDB(pageNumber);
		ArrayList<Integer> hitCountList = new ArrayList<Integer>();
		/*System.out.println(urlList);*/
		try
		{
			for(int i=0;i<urlList.size();i++)
			{
				hitCountList.add(HitsDao.getHitsCountFromShortenedUrl(urlList.get(i).getShortenedUrl()));
				urlList.get(i).setShortenedUrl(ROOTURL+urlList.get(i).getShortenedUrl());
				
			}
			model.addAttribute("urlList", urlList);
			model.addAttribute("urlHitList", hitCountList);
			/*System.out.println("URLLIST    " + urlList);
			System.out.println("HIT COUNT LIST    " + hitCountList);*/
		}
		catch(IndexOutOfBoundsException e)
		{
			logger.warn("No entries in database");
			e.printStackTrace();
		}
	}
	
	public static void updatePaginationArrayList(ArrayList<Integer> paginationList, long totalRecords)
	{
		/*System.out.println("Total Records " + totalRecords + "No. Of Records Per Page " + NUMBER_OF_RECORDS_PAGE);*/
		long iter = 0l;
		if(totalRecords%NUMBER_OF_RECORDS_PAGE == 0)
			iter = totalRecords/NUMBER_OF_RECORDS_PAGE;
		else
			iter = (totalRecords/NUMBER_OF_RECORDS_PAGE) + 1;
		for(int i = 1; i <= iter ; i++)
		{
			paginationList.add(i);
		}
	}

}
