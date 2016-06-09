package com.urlshortener.exceptions;

public class DuplicateDefaultSlugGeneratedException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public String toString() {
		return "Custom Url Exists";
	}

}
