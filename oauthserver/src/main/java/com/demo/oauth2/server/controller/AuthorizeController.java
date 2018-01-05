package com.demo.oauth2.server.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.demo.oauth2.server.constant.ErrorConstants;
import com.demo.oauth2.server.service.OauthClientService;
import com.demo.oauth2.server.untils.ControllerHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api("认证服务")
@RequestMapping("/Oauth")
@ResponseBody
public class AuthorizeController {

	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OauthClientService oauthClientService;
	
	@RequestMapping("/authorize")
	@ApiOperation("第三方客户端授权接口")
	public Object authorize(HttpServletRequest request) {
		try {
			OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);		
			String clientId=oauthRequest.getClientId();
		
			//跳转去前端检查是否已经登录，登录则调研本接口，否则显示登录界面，然用户进行登录
			Map<String, String> model =new HashMap<>();
			model.put("clientId",clientId);
			model.put("redirectRUI", oauthRequest.getRedirectURI());
			model.put("responseType", oauthRequest.getResponseType());
			return new ModelAndView("login",model);				
		} catch (Exception e) {
			logger.error(e.getCause().getMessage(),e);
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getCode")
	@ApiOperation("获取授权码")
	public Object getCode(HttpServletRequest request) {
		try {
			OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
			OAuthResponse oAuthResponse;
			String clientId=oauthRequest.getClientId();
			//校验client信息
			if(!oauthClientService.checkClient(clientId))
			{
				return ControllerHelper.getResponseEntity(HttpServletResponse.SC_BAD_REQUEST, OAuthError.TokenResponse.INVALID_CLIENT, ErrorConstants.ERROR_CLIENT_MSG);				
			}
			//获取登陆信息
			//已经登录校验内部token信息,没有登陆，校验登陆信息
			String token=request.getParameter("token");
			if(StringUtils.isEmpty(token))//token不存在及用户没有登陆，非法访问
			{				
				return ControllerHelper.getResponseEntity(HttpServletResponse.SC_BAD_REQUEST, OAuthError.CodeResponse.ACCESS_DENIED, ErrorConstants.ERROR_CLIENT_LOGIN);
 			}
			else {//校验token 服务器端对应的token是否存在，及获取用户信息等
//				checktoken()
			}
			//生成授权码
			String authcode=null;
			String responseType=oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
			if(responseType.equals(ResponseType.CODE.toString()))
			{
				OAuthIssuerImpl oAuthIssuerImpl=new OAuthIssuerImpl(new MD5Generator());
				authcode=oAuthIssuerImpl.authorizationCode();
				//保存授权码
				oauthClientService.saveCode(clientId, authcode);				
			}
			//Oauth 响应
			OAuthASResponse.OAuthAuthorizationResponseBuilder builder=OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);
			//设置授权码
			builder.setCode(authcode);
			String redirectURI=oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
			
			oAuthResponse=builder.location(redirectURI).buildQueryMessage();
			 //根据OAuthResponse返回ResponseEntity响应
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(new URI(oAuthResponse.getLocationUri()));
            return new ResponseEntity(headers, HttpStatus.valueOf(oAuthResponse.getResponseStatus()));
			
			
		} catch (Exception e) {
			logger.error(e.getCause().getMessage(),e);
		}
		return null;
	}
	
	
	
	
}
