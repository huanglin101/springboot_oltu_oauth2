package com.demo.oauth2.adapter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("authAdaper")
public class AuthController {

	@Autowired
	private Map<String, AuthAdapter> authAdapters;
	
	public void authRequest() {
		int authServerId=1;
		String AUTHtYPE="oauth2";
		//根据id和type找到对应的适配器
		
		AuthAdapter authAdapter=authAdapters.get("1");
		
		String redirctUrl=authAdapter.getAuthRequest();
	}
}
