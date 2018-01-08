package com.demo.oauth2.server.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.demo.oauth2.server.service.RedisProxyService;

@Service
public class RedisProxyServiceImpl implements RedisProxyService{
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Value("${spring.redis.defaultExpireDay}")
	private long defaultExpireTime;

	@Override
	public void put(String key, String value) {
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		opsForValue.set(key, value);
		opsForValue.getOperations().expire(key, defaultExpireTime, TimeUnit.DAYS);
	}

	@Override
	public void putWithoutExpireTime(String key, String value) {
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		opsForValue.set(key, value);
	}

	@Override
	public void putWithDays(String key, String value, long days) {
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		opsForValue.set(key, value);
		opsForValue.getOperations().expire(key, days, TimeUnit.DAYS);
	}

	@Override
	public void putWithMinites(String key, String value, long minites) {
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		opsForValue.set(key, value);
		opsForValue.getOperations().expire(key, minites, TimeUnit.MINUTES);
	}

	@Override
	public String get(String key) {
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		return opsForValue.get(key);
	}	
	
	@Override
	public <T> T get(String key, Class<T> type) {
		T result = null;
		String jsonValue = this.get(key);
		if (StringUtils.isNotEmpty(jsonValue)) {
			result = JSON.parseObject(jsonValue, type);
		}
		return result;
	}

	@Override
	public List<String> lrange(String key, int start, int end) {
		ListOperations<String, String> opsList = redisTemplate.opsForList();
		return opsList.range(key, start, end);
	}

	@Override
	public void batchRpush(String key, List<String> col) {
		ListOperations<String, String> opsList = redisTemplate.opsForList();
		opsList.rightPushAll(key, col);
		opsList.getOperations().expire(key, defaultExpireTime, TimeUnit.DAYS);
	}

	@Override
	public void addVo(String key, String str) {
		BoundSetOperations<String, String> opsme = this.redisTemplate.boundSetOps(key);
		opsme.add(str);
		opsme.getOperations().expire(key, defaultExpireTime, TimeUnit.DAYS);

	}

	@Override
	public void multiSet(Map<? extends String, ? extends String> map) {
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		opsForValue.multiSet(map);
		opsForValue.getOperations().executePipelined(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				for (String key : map.keySet()) {
					opsForValue.getOperations().expire(key, defaultExpireTime, TimeUnit.DAYS);
				}
				return null;
			}
		});

	}

	@Override
	public List<String> multiGet(Collection<String> keys) {
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		return opsForValue.multiGet(keys);
	}

	@Override
	public Long increment(String key, long delta) {
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		Long increment = opsForValue.increment(key, delta);
		opsForValue.getOperations().expire(key, defaultExpireTime, TimeUnit.DAYS);
		return increment;
	}

	@Override
	public Long incrementWithOutExpire(String key, long delta) {
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		return opsForValue.increment(key, delta);
	}

	@Override
	public Double increment(String key, double delta) {
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		return opsForValue.increment(key, delta);
	}
	
	@Override
	public Long increment(String key, long delta, long expireTime) {
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		Long increment = opsForValue.increment(key, delta);
		opsForValue.getOperations().expire(key, expireTime, TimeUnit.MILLISECONDS);
		return increment;
	}

	@Override
	public List<Object> executePipelined(SessionCallback<?> session) {
		return redisTemplate.executePipelined(session);
	}

	@Override
	public List<Object> executePipelined(RedisCallback<?> action) {
		return redisTemplate.executePipelined(action);
	}

	@Override
	public void delete(String key) {
		redisTemplate.delete(key);
	}

	@Override
	public void delete(Collection<String> keys) {
		redisTemplate.delete(keys);
	}

	@Override
	public List<String> rangeOfList(String key, long start, long end) {
		ListOperations<String, String> opsForList = redisTemplate.opsForList();
		return opsForList.range(key, start, end);
	}

	@Override
	public Long rightPushAllList(String key, String... values) {
		ListOperations<String, String> opsForList = redisTemplate.opsForList();
		opsForList.getOperations().expire(key, defaultExpireTime, TimeUnit.DAYS);
		return opsForList.rightPushAll(key, values);
	}

	@Override
	public Long rightPushAllListForEver(String key, String... values) {
		ListOperations<String, String> opsForList = redisTemplate.opsForList();
		return opsForList.rightPushAll(key, values);
	}

	@Override
	public Long rightPushAllList(String key, Collection<String> values) {
		ListOperations<String, String> opsForList = redisTemplate.opsForList();
		opsForList.getOperations().expire(key, defaultExpireTime, TimeUnit.DAYS);
		return opsForList.rightPushAll(key, values);
	}
	
	@Override
	public Long rightPushAllListForEver(String key, Collection<String> values) {
		ListOperations<String, String> opsForList = redisTemplate.opsForList();
		return opsForList.rightPushAll(key, values);
	}

	@Override
	public Long removeOfList(String key, long count, Object value) {
		ListOperations<String, String> opsForList = redisTemplate.opsForList();
		return opsForList.remove(key, count, value);
	}
	

	@Override
	public String leftPopList(String key) {
		ListOperations<String, String> opsForList = redisTemplate.opsForList();
		opsForList.getOperations().expire(key, defaultExpireTime, TimeUnit.DAYS);
		return opsForList.leftPop(key);
	}

	@Override
	public Long addToSet(String key, String... values) {
		SetOperations<String, String> opsForSet = redisTemplate.opsForSet();
		Long add = opsForSet.add(key, values);
		opsForSet.getOperations().expire(key, defaultExpireTime, TimeUnit.DAYS);
		return add;
	}
	
	@Override
	public Long addToSetWithExpire(long expireTime,TimeUnit tiemUnit, String key, String... values) {
		SetOperations<String, String> opsForSet = redisTemplate.opsForSet();
		Long add = opsForSet.add(key, values);
		opsForSet.getOperations().expire(key, expireTime, tiemUnit);
		return add;
	}

	@Override
	public Long addToSetWithOutExpirtTime(String key, String... values) {
		SetOperations<String, String> opsForSet = redisTemplate.opsForSet();
		return opsForSet.add(key, values);
	}

	@Override
	public Long removeOfSet(String key, String... values) {
		SetOperations<String, String> opsForSet = redisTemplate.opsForSet();
		return opsForSet.remove(key, values);
	}

	@Override
	public String popToSet(String key) {
		SetOperations<String, String> opsForSet = redisTemplate.opsForSet();
		return opsForSet.pop(key);
	}

	@Override
	public Boolean moveToSet(String key, String value, String destKey) {
		SetOperations<String, String> opsForSet = redisTemplate.opsForSet();
		return opsForSet.move(key, value, destKey);
	}

	@Override
	public Long sizeOfSet(String key) {
		SetOperations<String, String> opsForSet = redisTemplate.opsForSet();
		return opsForSet.size(key);
	}

	@Override
	public Long sizeOfList(String key) {
		ListOperations<String, String> opsForList = redisTemplate.opsForList();
		return opsForList.size(key);
	}

	@Override
	public Boolean isMemberOfSet(String key, Object o) {
		SetOperations<String, String> opsForSet = redisTemplate.opsForSet();
		redisTemplate.opsForList().getOperations().expire(key, defaultExpireTime, TimeUnit.DAYS);
		return opsForSet.isMember(key, o);
	}
	
	@Override
	public Boolean isMemberOfSetWithoutExpire(String key, Object o) {
		SetOperations<String, String> opsForSet = redisTemplate.opsForSet();
		return opsForSet.isMember(key, o);
	}
	
	@Override
	public Set<String> differenceWithoutExpire(String key, Collection<String> collection) {
		SetOperations<String, String> opsForSet = redisTemplate.opsForSet();
		return opsForSet.difference(key, collection);
	}
	
	@Override
	public Set<String> intersectWithoutExpire(String key, String otherKey) {
		SetOperations<String, String> opsForSet = redisTemplate.opsForSet();
		return opsForSet.intersect(key, otherKey);
	}

	@Override
	public Set<String> MembersOfSet(String key) {
		SetOperations<String, String> opsForSet = redisTemplate.opsForSet();
		redisTemplate.opsForList().getOperations().expire(key, defaultExpireTime, TimeUnit.DAYS);
		return opsForSet.members(key);
	}
	
	@Override
	public Set<String> MembersOfSetWithoutExpire(String key) {
		SetOperations<String, String> opsForSet = redisTemplate.opsForSet();
		return opsForSet.members(key);
	}

	@Override
	public Set<String> MembersOfVo(String key) {
		BoundSetOperations<String, String> opsme = this.redisTemplate.boundSetOps(key);
		return opsme.members();

	}

	@Override
	public Boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}

	@Override
	public BoundListOperations<String, String> boundListOps(String key) {
		return redisTemplate.boundListOps(key);

	}

	public void set(String key, int i, String value) {
		redisTemplate.opsForList().getOperations().expire(key, defaultExpireTime, TimeUnit.DAYS);
		redisTemplate.boundListOps(key).set(i, value);
	}

	/**
	 * 特殊处理 防止并发处理其他接口不调用该接口 wanglei
	 */
	@Override
	public void expire(String keys) {
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		opsForValue.getOperations().expire(keys, 1, TimeUnit.MINUTES);

	}

	@Override
	public void set(String key, String value) {
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		opsForValue.set(key, value);
		opsForValue.getOperations().expire(key, defaultExpireTime, TimeUnit.DAYS);

	}

	@Override
	@Deprecated
	public void batchDelete(String keyPattern) {
		// 禁止使用keys
		Set<String> keySet = redisTemplate.keys(keyPattern + "*");
		if (!CollectionUtils.isEmpty(keySet)) {
			redisTemplate.delete(keySet);
		}
	}

	@Override
	@Deprecated
	public Set<String> keySet(String keyPattern) {
		// 禁止使用keys
		return redisTemplate.keys(keyPattern + "*");
	}

	@Override
	public void set(String key, String string, int i, TimeUnit seconds) {
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		opsForValue.getOperations().expire(key, defaultExpireTime, TimeUnit.DAYS);

		opsForValue.set(key, string, i, seconds);
	}

	@Override
	public void leftPushAll(String string, List<String> originalTaskInfoJSONs) {
		ListOperations<String, String> opsForList = redisTemplate.opsForList();
		// 保存原始任务
		opsForList.getOperations().expire(string, defaultExpireTime, TimeUnit.DAYS);
		opsForList.leftPushAll(string, originalTaskInfoJSONs);
	}

	@Override
	public void add(String key, String[] setToArr) {
		BoundSetOperations<String, String> boundSetOps = redisTemplate.boundSetOps(key);
		boundSetOps.add(setToArr);
		boundSetOps.getOperations().expire(key, defaultExpireTime, TimeUnit.DAYS);

	}

	@Override
	public void zAdd(String key, String value, double score) {
		ZSetOperations<String, String> opsForZSet = redisTemplate.opsForZSet();
		opsForZSet.add(key, value, score);
		opsForZSet.getOperations().expire(key, defaultExpireTime, TimeUnit.DAYS);

	}
	
	@Override
	public void zAdd1( double score ,String key, String... values) {
		ZSetOperations<String, String> opsForZSet = redisTemplate.opsForZSet();
		opsForZSet.getOperations().expire(key, defaultExpireTime, TimeUnit.DAYS);

	}

	@Override
	public Set<String> zRange(String key, long start, long end) {
		ZSetOperations<String, String> opsForZSet = redisTemplate.opsForZSet();
		return opsForZSet.range(key, start, end);

	}

	@Override
	public void expire(String key, int expireTime, TimeUnit timeUnit) {
		redisTemplate.expire(key, expireTime, timeUnit);
	}

	@Override
	public Set<String> MembersOfZSet(String key) {
		ZSetOperations<String, String> opsForZSet = redisTemplate.opsForZSet();
		return opsForZSet.range(key, 0, -1);
	}

	@Override
	public Long removeOfZSet(String key, String... values) {
		ZSetOperations<String, String> opsForZSet = redisTemplate.opsForZSet();
		return opsForZSet.remove(key, values);
	}
}
