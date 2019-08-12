package com.bridgelabz.microservices.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisUtil {

	private RedisTemplate<String, String> redisTemplate;
	private HashOperations<String, Object, String> hashOperation;

	@Autowired
	RedisUtil(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.hashOperation = redisTemplate.opsForHash();

	}

	public void putMap(String redisKey, Object key, String data) {
		hashOperation.put(redisKey, key, data);
	}

	public String getMap(String redisKey, Object key) {
		return hashOperation.get(redisKey, key);

	}
}
