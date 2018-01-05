package com.demo.oauth2.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.oauth2.server.entity.TOAuth2Client;
import com.demo.oauth2.server.entity.TOAuth2ClientCriteria;
import com.demo.oauth2.server.mapper.TOAuth2ClientMapper;
import com.demo.oauth2.server.service.OauthClientService;
import com.demo.oauth2.server.service.RedisProxyService;

@Service
public class OauthClientServiceImpl implements OauthClientService{

	@Autowired
	private TOAuth2ClientMapper oAuth2ClientMapper;
	
	@Autowired
	private RedisProxyService redisService;
	
	@Override
	public boolean checkClient(String clientId) {
		boolean res=false;
		
		TOAuth2Client client=oAuth2ClientMapper.selectByPrimaryKey(clientId);
		if(client!=null&&client.getClientId().equals(clientId))
		{
			res= true;
		}
		return res;
	}

	@Override
	public boolean checkClient(String clientId, String clientKey) {
		boolean res=false;
		TOAuth2ClientCriteria example=new TOAuth2ClientCriteria();
		
		example.or().andClientIdEqualTo(clientId).andClientSecretEqualTo(clientKey);
		TOAuth2Client client= oAuth2ClientMapper.selectByExample(example).get(0);
		if(client!=null&&clientId.equals(client.getClientId()))
		{
			res=true;
		}
		return res;
	}

	@Override
	public void saveCode(String clientId,String code) {		
		redisService.set(clientId, code);
	}

	@Override
	public String getCode(String clientId) {
	
		return redisService.get(clientId);
	}

	@Override
	public void saveAccessToken(String token, String userInfo) {
		redisService.set(token, userInfo);
		
	}

}
