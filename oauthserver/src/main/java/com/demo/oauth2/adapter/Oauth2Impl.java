package com.demo.oauth2.adapter;

import org.springframework.stereotype.Service;

@Service("1")
public class Oauth2Impl implements AuthAdapter {

	
	@Override
	public String getAuthRequest() {
		// TODO Auto-generated method stub
		//获取配置信息
		//按oauth2格式生成认证请求//可以使用第三方客户端
		//发送认证请求
		return "";
	}

	@Override
	public String getConfig() {
		// TODO Auto-generated method stub
		
		return "";
		
	}

	@Override
	public String getUserInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String redirect() {
		// TODO Auto-generated method stub
		return null;
	}

}
