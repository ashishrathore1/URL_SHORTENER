package com.urlshortener.exceptions;

public class CustomSlugLimitExceededException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public String toString() {
		return "Custom Slug Limit Exceeded";
	}

}
