package com.urlshortener.BL;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.urlshortener.dao.ApplicationSettingsDao;
import com.urlshortener.exceptions.CustomSlugLimitExceededException;
import com.urlshortener.exceptions.DuplicateCustomUrlException;

public class FileProcess {

	private static String ROOTURL = ApplicationSettingsDao.getInstance().getValueWithKeyName("rooturl");
	private static final Logger logger = LoggerFactory.getLogger(FileProcess.class);
	
	public static File processCSVFile(File csvFile, String userID)
	{
		try
		{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile));
			CSVParser csvParser = new CSVParser(bufferedReader, CSVFormat.DEFAULT);
			File outFile = new File((csvFile.getPath()).substring(0, csvFile.getPath().length()-4)+"-out.csv");
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFile));
			bufferedWriter.write("Url,ShortenUrl" + "\n");
			for(CSVRecord record : csvParser)
			{
				String url = record.get(0).trim();
				if(url.equalsIgnoreCase("url"))
				{
					continue;
				}
				try
				{
					if (!url.contains("://")) {
						url = "http://" + url;
					}
				}
				catch(NullPointerException | IndexOutOfBoundsException e)
				{
					bufferedWriter.write(url+","+"not valid\n");
					e.printStackTrace();
					continue;
				}
				bufferedWriter.write(url+",");
				if(CheckUrl.isUrlValid(url))
				{
					try
					{
						String customUrl = record.get(1).trim();
						System.out.println("CustomURL"+customUrl+"a");
						if(customUrl=="" || (customUrl.length()==0) || customUrl==null)
						{
							System.out.println("here1");
							bufferedWriter.write(DeleteAddUrl.addDefaultUrl(url, userID)+ "\n");
							continue;
						}
						if ((!CheckUrl.isCustomSlugValid(customUrl)))
						{
							System.out.println("here2");
							bufferedWriter.write("CustomSlug Not Valid"+ "\n");
							continue;
						}
						else
						{
							System.out.println("here3");
							System.out.println("CustomURL" + customUrl + "ASCII"+(char)customUrl.charAt(0));
							String s = DeleteAddUrl.addCustomUrl(url, customUrl, userID)+ "\n";
							bufferedWriter.write(s);
							continue;
						}
						
					}
					catch(CustomSlugLimitExceededException e)
					{
						System.out.println("here4");
						bufferedWriter.write("Custom Slug cannot be greater than 14 characters"+ "\n");
						e.printStackTrace();
						continue;
					}
					catch(ArrayIndexOutOfBoundsException e)
					{
						System.out.println("here5");
						String s = DeleteAddUrl.addDefaultUrl(url, userID)+ "\n";
						bufferedWriter.write(s);
						e.printStackTrace();
						continue;
					} 
					catch(IndexOutOfBoundsException e)
					{
						System.out.println("here6");
						bufferedWriter.write(DeleteAddUrl.addDefaultUrl(url, userID)+ "\n");
						e.printStackTrace();
						continue;
					}
					catch (DuplicateCustomUrlException e) 
					{
						System.out.println("here7");
						bufferedWriter.write("Custom Slug Already Present"+ "\n");
						e.printStackTrace();
						continue;
					}
				}
				else
				{
					bufferedWriter.write("url not valid"+ "\n");
				}
				/*bufferedWriter.write("\n");*/
			}
			bufferedReader.close();
			bufferedWriter.close();
			csvParser.close();
			return outFile;
		}
		catch(FileNotFoundException e)
		{
			logger.warn(e.toString());
			logger.warn(e.getMessage());
			e.printStackTrace();
			return null;
		}
		catch(IndexOutOfBoundsException e)
		{
			logger.warn(e.toString());
			logger.warn(e.getMessage());
			e.printStackTrace();
			return null;
		}
		catch (IOException e) {
			logger.warn(e.toString());
			logger.warn(e.getMessage());
			System.out.println("IOException handled");
			e.printStackTrace();
			return null;
		}
		catch(Exception e)
		{
			System.out.println("Exception handled");
			e.printStackTrace();
			return null;
		}
	}

}
