package com.demo.oauth2.adapter;



public interface AuthAdapter {
	
	String getAuthRequest();	
	
	String getConfig();
	
	String getUserInfo();
	
	
	String redirect();
	
}
