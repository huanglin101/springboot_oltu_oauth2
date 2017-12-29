package com.demo.oauth2.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="spring.redis")
public class RedisConfig {
	private String host ;
	private int port ;
	private String password ;
    private int examRedisIndex;
	private int maxIdle;
	private int maxActive;
	private int maxWait;
	private boolean testOnBorrow;
	public int getExamRedisIndex() {
		return examRedisIndex;
	}
	public void setExamRedisIndex(int examRedisIndex) {
		this.examRedisIndex = examRedisIndex;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getMaxIdle() {
		return maxIdle;
	}
	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}
	public int getMaxActive() {
		return maxActive;
	}
	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}
	public int getMaxWait() {
		return maxWait;
	}
	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}
	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}
	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}
	
}
