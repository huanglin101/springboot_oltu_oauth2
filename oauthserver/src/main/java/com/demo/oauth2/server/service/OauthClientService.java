package com.demo.oauth2.server.service;

/**
 * 
 * @author hlin
 *
 */
public interface OauthClientService {

	/**
	 * 
	 * @param clientId
	 * @return
	 */
	boolean checkClient(String clientId);
	
	boolean checkClient(String clientId,String clientKey);
	
	void saveCode(String clientId,String code);
	
	String getCode(String clientId);
	
	void saveAccessToken(String token,String userInfo);
	
	
}
