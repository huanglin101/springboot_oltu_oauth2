package com.demo.oauth2.server.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class LoginController {

	/**
	 * 用户登陆
	 * @param username
	 * @param pword
	 * @return
	 */
	@RequestMapping("/login")	
	public Object login(String username,String pword) {
		Map<String, String> res=new HashMap<>();
		if(!("admin".equals(username) && "123456".equals(pword)))
		{			
			res.put("result", "false");
			res.put("msg", "密码或用户名错误");			
		}
		else{
			//生成token写入服务器并返回
			String token ="123";
			
			res.put("result", "true");
			res.put("msg", token);
		}
		return res;
	}
}
