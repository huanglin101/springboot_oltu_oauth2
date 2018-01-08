package com.demo.oauth2.server.untils;

import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ControllerHelper {

	private ControllerHelper() {
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ResponseEntity getResponseEntity(int htttResponseState,String errorCode,String errorMsg) throws OAuthSystemException
	{							 
		 OAuthResponse oAuthResponse = OAuthASResponse.errorResponse(htttResponseState)
				.setError(errorCode)
				.setErrorDescription(errorMsg)
				.buildJSONMessage();
		return new  ResponseEntity(oAuthResponse.getBody(),HttpStatus.valueOf(oAuthResponse.getResponseStatus()));
	
	}
}
