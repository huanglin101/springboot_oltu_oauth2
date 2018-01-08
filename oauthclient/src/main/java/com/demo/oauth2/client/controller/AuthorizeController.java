package com.demo.oauth2.client.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.demo.oauth2.client.dto.OauthParam;

@Controller
public class AuthorizeController {

	private Logger logger=LoggerFactory.getLogger(AuthorizeController.class);
	
	public void authorize(@ModelAttribute("oauthParams") OauthParam oauthParam,
			HttpServletRequest request,
			HttpServletResponse response) {
		try {
			
		} catch (Exception e) {
			
		}
	}
}
