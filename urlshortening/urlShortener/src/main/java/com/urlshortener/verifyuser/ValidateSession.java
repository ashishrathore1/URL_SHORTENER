package com.urlshortener.verifyuser;

import javax.servlet.http.HttpServletRequest;

public class ValidateSession {
	
	public static boolean getUserLoginSessionStatus(HttpServletRequest request)
	{
		try
		{
			/*System.out.println("Checking validate " + (boolean)request.getAttribute("userLoginStatus"));*/
			if((boolean) request.getSession().getAttribute("userLoginStatus"))
				return true;
			return false;
		}
		catch(NullPointerException e )
		{
			return false;
		}
		
	}
	
	public static void deleteUserLoginSessionStatus(HttpServletRequest request)throws NullPointerException
	{
			request.getSession().removeAttribute("userLoginStatus");
	}

}
