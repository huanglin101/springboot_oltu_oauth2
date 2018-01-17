package com.demo.oauth2.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.jwt.JWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.oauth2.server.constant.ErrorConstants;
import com.demo.oauth2.server.service.OauthClientService;
import com.demo.oauth2.server.untils.ControllerHelper;

@Controller
@RequestMapping("/Oauth")
public class AccessTokenController {

	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OauthClientService oauthClientService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/accesstoken")
	public Object getToken(HttpServletRequest request) throws OAuthSystemException {
		try {
			OAuthTokenRequest oAuthTokenRequest= new OAuthTokenRequest(request);
			String clientId=oAuthTokenRequest.getClientId();
			String clientKey= oAuthTokenRequest.getClientSecret();
			
			if(!oauthClientService.checkClient(clientId,clientKey))
			{
				return ControllerHelper.getResponseEntity(HttpServletResponse.SC_BAD_REQUEST, OAuthError.TokenResponse.INVALID_CLIENT, ErrorConstants.ERROR_CLIENT_MSG);
			}
			OAuthResponse oAuthResponse;
			
			String authcode= oAuthTokenRequest.getCode();
			String grantType= oAuthTokenRequest.getGrantType();
			if(GrantType.AUTHORIZATION_CODE.toString().equals(grantType) && authcode.equals(oauthClientService.getCode(clientId)))
			{
				//生成token				
	            OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
	            final String accessToken = oauthIssuerImpl.accessToken();
	            oauthClientService.saveAccessToken(accessToken, "");


	            //生成OAuth响应
	            oAuthResponse = OAuthASResponse
	                    .tokenResponse(HttpServletResponse.SC_OK)
	                    .setAccessToken(accessToken)	                   
	                    .setExpiresIn(String.valueOf( 3600L))
	                    .buildJSONMessage();

	            //根据OAuthResponse生成ResponseEntity
	            return new ResponseEntity(oAuthResponse.getBody(), HttpStatus.valueOf(oAuthResponse.getResponseStatus()));
				
			}
			else{
				return ControllerHelper.getResponseEntity(HttpServletResponse.SC_BAD_REQUEST, OAuthError.TokenResponse.INVALID_GRANT, ErrorConstants.ERROR_AUTH_CODE);
			}
			
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return ControllerHelper.getResponseEntity(HttpServletResponse.SC_BAD_REQUEST, ErrorConstants.ERROR_UNKNOW, e.getCause().getMessage());
		}		
	}
}
