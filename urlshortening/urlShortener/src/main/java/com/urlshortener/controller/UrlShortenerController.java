package com.urlshortener.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.urlshortener.BL.CheckUrl;
import com.urlshortener.BL.DeleteAddUrl;
import com.urlshortener.BL.Display;
import com.urlshortener.BL.FileProcess;
import com.urlshortener.BL.RedirectURL;
import com.urlshortener.dao.ApplicationSettingsDao;
import com.urlshortener.dao.DisplayDao;
import com.urlshortener.exceptions.CustomSlugLimitExceededException;
import com.urlshortener.exceptions.DuplicateCustomUrlException;
import com.urlshortener.hits.Queue.Enqueue;
import com.urlshortener.verifyuser.ValidateSession;
import com.urlshortener.verifyuser.ValidateUserLoginThroughIMS;

@Controller
public class UrlShortenerController {
	private static String PAGENOTFOUNDURL = ApplicationSettingsDao
			.getInstance().getValueWithKeyName("pagenotfoundurl");

	private static final Logger logger = LoggerFactory
			.getLogger(UrlShortenerController.class);
	

	@RequestMapping(value = "*")
	public ModelAndView urlRedirect(HttpServletRequest request) {
		RedirectView redirectView = new RedirectView();
		ModelAndView modelAndView = new ModelAndView();
		String uri = request.getRequestURI();
		String path="";
		String redirectUrl ="";
		Enqueue enqueue =null;
		try
		{
			int index = uri.lastIndexOf("/");
			path = uri.substring(index + 1);
			enqueue = new Enqueue(path);
			redirectUrl = RedirectURL.getRedirectURL(path);
			if (redirectUrl == null) 
			{
				System.out.println("No redirect url found");
				redirectView.setUrl(PAGENOTFOUNDURL);
				modelAndView.setView(redirectView);
				return modelAndView;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();//This is a safety check.
			redirectView.setUrl(PAGENOTFOUNDURL);
			modelAndView.setView(redirectView);
			return modelAndView;
		}
		enqueue.start();
		redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		redirectView.setUrl(redirectUrl);
		modelAndView.setView(redirectView);
		return modelAndView;
	}

	
	
	
	@RequestMapping(value = "urlshortener/addurl", method=RequestMethod.POST)
	public String addNewUrl(HttpServletRequest request, Model model) {
		if(!ValidateSession.getUserLoginSessionStatus(request))
			return "/WEB-INF/login.jsp";
		String url = request.getParameter("url");
		String customUrl = request.getParameter("customUrl");
		String generatedUrl = "";
		String message = "";
		try
		{
			if (!url.contains("://")) {
				url = "http://" + url;
			}
			if (((!CheckUrl.isUrlValid(url)) || (!CheckUrl.isCustomSlugValid(customUrl)))) {
				message = "Please enter valid data";
				System.out.println(message + "URL OR CUSTOM SLUG NOT  VALID");
			} 
			else 
			{
				if ((!(customUrl == "" || customUrl == null)) && (!(request.getParameter("url") == "" || request.getParameter("url") == null))) 
				{
					generatedUrl = DeleteAddUrl.addCustomUrl(url, customUrl, (String) request.getSession().getAttribute("userid"));
				}
				else if (!(request.getParameter("url") == null || request.getParameter("url") == "")) 
				{
					generatedUrl = DeleteAddUrl.addDefaultUrl(url, (String) request.getSession().getAttribute("userid"));
				}
			}
		}
		catch(CustomSlugLimitExceededException e)
		{
			message = "Custom Slug cannot be greater than 14 characters";
			e.printStackTrace();
		}
		catch(NullPointerException e)
		{
			message = "no url added";
			logger.warn(e.getMessage());
			e.printStackTrace();
		}
		catch(DuplicateCustomUrlException e)
		{
			message = "Custom slug already exists for " + customUrl;
			e.printStackTrace();
		}
		catch(IndexOutOfBoundsException e)
		{
			message = "url invalid";
			logger.warn(e.getMessage());
		}
		catch(Exception e)
		{
			message = "Something broke. Please Contact Admin";
			logger.warn(e.getMessage());
			e.printStackTrace();
		}
		long totalRecords = DisplayDao.getTotalRecordsFromDB();
		ArrayList<Integer> paginationList = new ArrayList<Integer>();
		Display.updatePaginationArrayList(paginationList, totalRecords);
		model.addAttribute("message", message);
		model.addAttribute("paginationList", paginationList);
		model.addAttribute("totalRecords", totalRecords);
		if(generatedUrl!= "" )
		{
			System.out.println("GENERATED URL " + generatedUrl);
			model.addAttribute("generatedUrl", generatedUrl);
			model.addAttribute("enableTestLink", true);
		}
		Display.setListOfUrlsAndHitCountInModel(1, model);
		return "/WEB-INF/home.jsp";
	}
	
	
	
	
	@RequestMapping(value = "urlshortener/addurl",method=RequestMethod.GET)
	public String addNewUrlGetRequest(HttpServletRequest request,Model model)
	{
		if(!ValidateSession.getUserLoginSessionStatus(request))
			return "/WEB-INF/login.jsp";
		System.out.println("INSIDE ADD URL GET REQUEST");
		long totalRecords = DisplayDao.getTotalRecordsFromDB();
		ArrayList<Integer> paginationList = new ArrayList<Integer>();
		model.addAttribute("paginationList", paginationList);
		model.addAttribute("totalRecords", totalRecords);
		Display.setListOfUrlsAndHitCountInModel(1, model);
		return "/WEB-INF/home.jsp";
	}
	
	
	

	@RequestMapping(value = "urlshortener/display", method = RequestMethod.GET)
	public String displayListOfUrls(HttpServletRequest request, Model model) {
		System.out.println("INSIDE DISPLAY");
		System.out.println(ValidateSession.getUserLoginSessionStatus(request) + " " + request.getSession().getAttribute("userLoginStatus"));
		if(!ValidateSession.getUserLoginSessionStatus(request))
			return "/WEB-INF/login.jsp";
		int pageNumber ;
		long totalRecords = DisplayDao.getTotalRecordsFromDB();
		ArrayList<Integer> paginationList = new ArrayList<Integer>();
		Display.updatePaginationArrayList(paginationList, totalRecords);
		try
		{
			pageNumber = Integer.parseInt(request.getParameter("page"));
		}
		catch(Exception e)
		{
			pageNumber = 0;
		}
		model.addAttribute("paginationList", paginationList);
		model.addAttribute("totalRecords", totalRecords);
		System.out.println(pageNumber+"~~~~~"+totalRecords);
		Display.setListOfUrlsAndHitCountInModel(pageNumber, model);
		return "/WEB-INF/home.jsp";
	}

	
	@RequestMapping(value = "urlshortener/file",method=RequestMethod.GET)
	public String fileGetRequest(HttpServletRequest request,Model model)
	{
		if(!ValidateSession.getUserLoginSessionStatus(request))
			return "/WEB-INF/login.jsp";
		long totalRecords = DisplayDao.getTotalRecordsFromDB();
		ArrayList<Integer> paginationList = new ArrayList<Integer>();
		model.addAttribute("paginationList", paginationList);
		model.addAttribute("totalRecords", totalRecords);
		Display.setListOfUrlsAndHitCountInModel(1, model);
		return "/WEB-INF/home.jsp";
	}	
	
	
	
	/*@RequestMapping(value = "urlshortener/delete", method=RequestMethod.POST)
	public String deleteRecord(Model model, HttpServletRequest request) {
		if(!ValidateSession.getUserLoginSessionStatus(request))
			return "/WEB-INF/login.jsp";
		String message = DeleteAddUrl.deleteEntityFromShortenedUrl(request.getParameter("url"));
		model.addAttribute("message", message);
		return "/WEB-INF/home.jsp";
	}*/

	@RequestMapping(value = "urlshortener/file", method=RequestMethod.POST)
	public String filePostRequest(
			@RequestParam("filedata") MultipartFile multiPartFile,
			HttpServletResponse response, Model model,
			HttpServletRequest request) {
		
		if(!ValidateSession.getUserLoginSessionStatus(request))
			return "/WEB-INF/login.jsp";
		String message = "";
		File fileDir = new File(System.getProperty("user.dir") + "/csvfiles");
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		String uploadedFileName = multiPartFile.getOriginalFilename();
		int nameLength = uploadedFileName.length();
		if (nameLength >= 5) {
			if (uploadedFileName.substring(nameLength - 4).equalsIgnoreCase(
					".csv")) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"ddMMyy-hhmmss");
				String fileNameToSave = uploadedFileName.substring(0,
						uploadedFileName.lastIndexOf("."))
						+ dateFormat.format(new Date()) + ".csv";
				File uploadedFile = new File(fileDir, fileNameToSave);
				try {
					multiPartFile.transferTo(uploadedFile);
					File outFile = FileProcess.processCSVFile(uploadedFile, (String) request.getSession().getAttribute("userid"));
					if(outFile == null)
					{
						System.out.println("OUTPUT FILE NULL");
						throw new IllegalArgumentException();
					}
					System.out.println("Downloding file");
					response.setContentType("text/csv");
					response.setHeader("Content-Disposition",
							"attachment; filename=out-" + uploadedFileName);
					InputStream outputFileStream = new FileInputStream(outFile);
					IOUtils.copy(outputFileStream, response.getOutputStream());
					response.flushBuffer();
					message = "file downloaded as out-" + uploadedFileName;

				} catch (IllegalStateException e) {
					message = "cannot upload file";
				} catch (IOException e) {
					message = "cannot upload file";
				} catch (IllegalArgumentException e) {
					message = "Unable to Parse the file";
				}	
			} else {
				message = "file must be in csv format";
			}
		} else {
			message = "invalid file or file not uploaded";
		}
		long totalRecords = DisplayDao.getTotalRecordsFromDB();
		ArrayList<Integer> paginationList = new ArrayList<Integer>();
		model.addAttribute("paginationList", paginationList);
		model.addAttribute("totalRecords", totalRecords);
		model.addAttribute("message", message);
		Display.setListOfUrlsAndHitCountInModel(1, model);
		return "/WEB-INF/home.jsp";
	}
	
	@RequestMapping(value = "urlshortener/login", method=RequestMethod.POST)
	public String redirectToLogin(HttpServletRequest request, Model model)
	{
		ValidateUserLoginThroughIMS validateRequest = new ValidateUserLoginThroughIMS();
		UrlShortenerController urlShortenerController = new UrlShortenerController();
		boolean isUserValid = validateRequest.verifyUser(request.getParameter("username"), request.getParameter("password"), request);
		System.out.println(isUserValid);
		if(isUserValid)
		{
			System.out.println("Valid");
			request.getSession().setAttribute("userLoginStatus", true);
			System.out.println("LOGIN CONTROLLER " + request.getSession().getAttribute("userLoginStatus"));
			return urlShortenerController.displayListOfUrls(request, model);
		}
		else
		{
			System.out.println("INVALID");
			model.addAttribute("invalidCredentials", "Invalid User Credentials");
			return "/WEB-INF/login.jsp";
		}
		
	}
	
	@RequestMapping(value = "urlshortener/login", method=RequestMethod.GET)
	public String redirectToLoginGetRequest(HttpServletRequest request, Model model)
	{
		UrlShortenerController urlShortenerController = new UrlShortenerController();
		try
		{
			if((boolean) request.getSession().getAttribute("userLoginStatus"))
			{
				return urlShortenerController.displayListOfUrls(request, model);
			}
		}
		catch(NullPointerException e)
		{
			return "/WEB-INF/login.jsp";
		}
		return "/WEB-INF/login.jsp";
	}
	
	@RequestMapping(value="urlshortener/logout", method=RequestMethod.GET)
	public String invalidateLoginSession(HttpServletRequest request, Model model)
	{
		try
		{
			if((boolean) request.getSession().getAttribute("userLoginStatus"))
			{
				request.getSession().removeAttribute("userLoginStatus");
				request.getSession().removeAttribute("userid");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "/WEB-INF/login.jsp";
	}
}
