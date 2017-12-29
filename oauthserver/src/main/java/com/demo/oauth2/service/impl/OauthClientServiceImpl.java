package com.demo.oauth2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.oauth2.entity.TOAuth2Client;
import com.demo.oauth2.entity.TOAuth2ClientCriteria;
import com.demo.oauth2.mapper.TOAuth2ClientMapper;
import com.demo.oauth2.service.OauthClientService;

@Service
public class OauthClientServiceImpl implements OauthClientService{

	@Autowired
	private TOAuth2ClientMapper oAuth2ClientMapper;
	
	@Override
	public boolean checkClientId(String clientId) {
		TOAuth2ClientCriteria example=new TOAuth2ClientCriteria();
		example.or().andClientIdEqualTo(clientId);
		TOAuth2Client client=oAuth2ClientMapper.selectByPrimaryKey(clientId);
		if(client!=null)
		{
			return true;
		}
		return false;
	}

}
