package com.urlshortener.verifyuser;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.EmailValidator;

import com.snapdeal.ims.client.impl.LoginUserServiceClientImpl;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.LoginUserRequest;
import com.snapdeal.ims.response.LoginUserResponse;
import com.snapdeal.ims.utils.ClientDetails;
import com.urlshortener.dao.ApplicationSettingsDao;


@SuppressWarnings("deprecation")
public class ValidateUserLoginThroughIMS {

	private static final String imsIP = ApplicationSettingsDao.getInstance().getValueWithKeyName("imsip");
	private static final String imsPort = ApplicationSettingsDao.getInstance().getValueWithKeyName("imsport");
	private static final String clientKey = ApplicationSettingsDao.getInstance().getValueWithKeyName("imsclientkey");
	private static final String clientId = ApplicationSettingsDao.getInstance().getValueWithKeyName("imsclientid");
	private static final String imsTimeout = ApplicationSettingsDao.getInstance().getValueWithKeyName("imstimeout");
	private static final String imsUserMachineIdentifier = ApplicationSettingsDao.getInstance().getValueWithKeyName("imsUserMachineIdentifier");
	private static final String imsIUserAgent = ApplicationSettingsDao.getInstance().getValueWithKeyName("imsuseragent");
	private LoginUserServiceClientImpl loginservice;
	private LoginUserRequest loginrequest;
	private LoginUserResponse loginresponse;
	
	static
	{
		try {
			ClientDetails.init(imsIP, imsPort, clientKey, clientId, Integer.parseInt(imsTimeout));
		} catch (Exception e) {
			throw new RuntimeException("Could not initialize IMS Client", e);
		}
	}
	
	public boolean verifyUser(String userName, String password)
	{
		loginservice = new LoginUserServiceClientImpl();
		loginrequest = new LoginUserRequest();
		if(EmailValidator.getInstance().isValid(userName))
		{
			loginrequest.setEmailId(userName);
		}
		else
		{
			loginrequest.setMobileNumber(userName);
		}
		loginrequest.setPassword(password);
		loginrequest.setUserMachineIdentifier(imsUserMachineIdentifier);
		loginrequest.setUserAgent(imsIUserAgent);
		try 
		{
			loginresponse = loginservice.loginUserWithPassword(loginrequest);
			System.out.println("LOGIN SUCCESFUL");
		} 
		catch (ServiceException e) 
		{
			System.out.println("LOGIN FAILURE");
			e.printStackTrace();
			return false;
		}
		System.out.println("LOGIN RESPONSE " + loginresponse);
		/*System.out.println(loginresponse.toString());*/
		return (loginresponse!=null)? true : false;
		
	}
	
	public boolean verifyUser(String userName, String password, HttpServletRequest request)
	{
		loginservice = new LoginUserServiceClientImpl();
		loginrequest = new LoginUserRequest();
		if(EmailValidator.getInstance().isValid(userName))
		{
			loginrequest.setEmailId(userName);
		}
		else
		{
			loginrequest.setMobileNumber(userName);
		}
		loginrequest.setPassword(password);
		loginrequest.setUserMachineIdentifier(imsUserMachineIdentifier);
		loginrequest.setUserAgent(imsIUserAgent);
		try 
		{
			loginresponse = loginservice.loginUserWithPassword(loginrequest);
			System.out.println("LOGIN SUCCESFUL");
		} 
		catch (ServiceException e) 
		{
			System.out.println("LOGIN FAILURE");
			e.printStackTrace();
			/*
			 * REMOVE
			 * 
			 */
			//request.getSession().setAttribute("userid", "achi");
			return false;
		}
		System.out.println("LOGIN RESPONSE " + loginresponse);
		/*System.out.println(loginresponse.toString());*/
		if(loginresponse!=null)
		{
			request.getSession().setAttribute("userid", loginresponse.getUserDetails().getEmailId());
			return true;
		}
		return false;
		
	}
}
