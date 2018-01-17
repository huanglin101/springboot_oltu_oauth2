package com.demo.oauth2.client.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthAuthzResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.demo.oauth2.server.dto.OauthParam;
import com.demo.oauth2.server.untils.Utils;

@Controller
public class RedirectController {

	 private Logger logger = LoggerFactory.getLogger(RedirectController.class);

	 @Autowired
	    private Utils utils;
	 
	@RequestMapping(value = "/redirect", method = RequestMethod.GET)
	public ModelAndView gettoken(@ModelAttribute("oauthParams") OauthParam oauthParams,
            HttpServletRequest request,
            HttpServletResponse response) {
		try {
			 	request.getParameter("clientId");
			  	String clientId = Utils.findCookieValue(request, "clientId");
	            String clientSecret = Utils.findCookieValue(request, "clientSecret");
	            String authzEndpoint = Utils.findCookieValue(request, "authzEndpoint");
	            String tokenEndpoint = Utils.findCookieValue(request, "tokenEndpoint");
	            String redirectUri = Utils.findCookieValue(request, "redirectUri");
	            String scope = Utils.findCookieValue(request, "scope");
	            String state = Utils.findCookieValue(request, "state");

	            oauthParams.setClientId(clientId);
	            oauthParams.setClientSecret(clientSecret);
	            oauthParams.setAuthzEndpoint(authzEndpoint);
	            oauthParams.setTokenEndpoint(tokenEndpoint);
	            oauthParams.setRedirectUri(redirectUri);
	            oauthParams.setScope(Utils.isIssued(scope));
	            oauthParams.setState(Utils.isIssued(state));
	            
	            // Create the response wrapper
	            OAuthAuthzResponse oar = null;
	            oar = OAuthAuthzResponse.oauthCodeAuthzResponse(request);

	            // Get Authorization Code
	            String code = oar.getCode();
	            oauthParams.setAuthzCode(code);

	            String app = Utils.findCookieValue(request, "app");
	            response.addCookie(new Cookie("app", app));

	            oauthParams.setApplication(app);
	            	
	            Map<String, String> model =new HashMap<>();
				model.put("authzCode",code);
			
	           return new ModelAndView("client",model);

		} catch (Exception e) {
			 logger.error("failed to create OAuth authorization response wrapper", e);	          	         
	         oauthParams.setErrorMessage(e.toString());	          
	         return new ModelAndView("client");
		}
	}
	
	@RequestMapping(value = "/gettoken")
	public Object getAccessToken(@ModelAttribute("oauthParams") OauthParam oauthParams) {
		  try {
//			  utils.validateTokenParams(oauthParams);
	           OAuthClientRequest request2 =OAuthClientRequest
	           		.tokenLocation(oauthParams.getTokenEndpoint())
	           		.setClientId(oauthParams.getClientId())
	           		.setClientSecret(oauthParams.getClientSecret())
	           		.setRedirectURI(oauthParams.getRedirectUri())
	           		.setCode(oauthParams.getAuthzCode())
	           		.setGrantType(GrantType.AUTHORIZATION_CODE)
	           		.buildBodyMessage();
	           
	           OAuthClient client=new OAuthClient(new URLConnectionClient());
	           
	           Class<? extends OAuthAccessTokenResponse> cl = OAuthJSONAccessTokenResponse.class;
	           //请求token
	           OAuthAccessTokenResponse oauthResponse=client.accessToken(request2,cl);
	           oauthParams.setAccessToken(oauthResponse.getAccessToken());
	           oauthParams.setExpiresIn(oauthResponse.getExpiresIn());
	           oauthParams.setRefreshToken(Utils.isIssued(oauthResponse.getRefreshToken()));
	           Map<String, String> model =new HashMap<>();
	           model.put("token",oauthParams.getAccessToken());
	           return new ModelAndView("test",model);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return null;
		}
	}
	
	@RequestMapping("/test")
	public String test() {
		return "test";
	}
}
