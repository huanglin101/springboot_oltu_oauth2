package com.demo.oauth2.server.server.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
public class CacheConfig {
	        	
	  @Autowired
		private RedisConfig redisConfig;
		
		@Bean
		JedisConnectionFactory jedisConnectionFactory() {
			JedisPoolConfig poolConfig = new JedisPoolConfig();
			poolConfig.setMaxTotal(redisConfig.getMaxActive());    
			poolConfig.setMaxIdle(redisConfig.getMaxIdle());    
	        poolConfig.setMaxWaitMillis(redisConfig.getMaxWait());    
	        poolConfig.setTestOnBorrow(true);   
			JedisConnectionFactory factory = new JedisConnectionFactory();
			factory.setHostName(redisConfig.getHost());
			factory.setPort(redisConfig.getPort());
			factory.setPassword(redisConfig.getPassword());
			factory.setUsePool(true);
			factory.setDatabase(redisConfig.getRedisIndex());
			return factory;
		}

		@Bean
		RedisTemplate<Object, Object> redisTemplate() {
			RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
			redisTemplate.setConnectionFactory(jedisConnectionFactory());
			StringRedisSerializer stringSer = new StringRedisSerializer();
			redisTemplate.setKeySerializer(stringSer);
			redisTemplate.setValueSerializer(stringSer);
			redisTemplate.setHashKeySerializer(stringSer);
			redisTemplate.setHashValueSerializer(stringSer);
			return redisTemplate;
		}

		@Bean
		public CacheManager cacheManager() {
			return new RedisCacheManager(redisTemplate());
			
		}
}
