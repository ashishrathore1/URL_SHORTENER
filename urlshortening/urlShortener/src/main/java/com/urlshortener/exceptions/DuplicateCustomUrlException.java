package com.urlshortener.exceptions;

public class DuplicateCustomUrlException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public String toString() {
		return "Custom Url Exists";
	}

}
