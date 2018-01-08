package com.demo.oauth2.client.controller;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.demo.oauth2.server.dto.OauthParam;

@Controller
public class AuthController {
	
	 private Logger logger = LoggerFactory.getLogger(RedirectController.class);
	
	@RequestMapping("/clientoauth")
	public ModelAndView	 client() {
		return new ModelAndView("clientoauth");
	}
	
	@RequestMapping("/requestAuth")
	public ModelAndView requestAuth(@ModelAttribute("oauthParams") OauthParam oauthParams) {
		try {
			 OAuthClientRequest request = OAuthClientRequest
		                .authorizationLocation(oauthParams.getAuthzEndpoint())
		                .setClientId(oauthParams.getClientId())
		                .setRedirectURI(oauthParams.getRedirectUri())
		                .setResponseType(ResponseType.CODE.toString())
		                .setScope(oauthParams.getScope())
		                .setState(oauthParams.getState())
		                .buildQueryMessage();

		     return new ModelAndView(new RedirectView(request.getLocationUri()));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return null;
		}
	}
}
