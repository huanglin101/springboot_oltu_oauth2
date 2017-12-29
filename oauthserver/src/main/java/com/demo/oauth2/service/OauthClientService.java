package com.demo.oauth2.service;

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
	boolean checkClientId(String clientId);
}
