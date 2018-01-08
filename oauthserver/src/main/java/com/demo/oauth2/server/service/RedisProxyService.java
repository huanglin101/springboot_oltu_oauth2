package com.demo.oauth2.server.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.SessionCallback;

public interface RedisProxyService {

	public void put(String key, String value);
	
	public void putWithoutExpireTime(String key, String value);

	public String get(String key);

	public <T> T get(String key, Class<T> type);	
	
	public void batchRpush(String key, List<String> col);
	
	public List<String> lrange(String key, int start, int end);

	void delete(Collection<String> keys);

	void delete(String key);

	List<Object> executePipelined(RedisCallback<?> action);

	List<Object> executePipelined(SessionCallback<?> session);

	Double increment(String key, double delta);

	Long increment(String key, long delta);
	
	Long incrementWithOutExpire(String key, long delta);

	List<String> multiGet(Collection<String> keys);

	void multiSet(Map<? extends String, ? extends String> map);

	Set<String> MembersOfSet(String key);
	
	Set<String> MembersOfSetWithoutExpire(String key);

	Boolean isMemberOfSet(String key, Object o);
	
	Boolean isMemberOfSetWithoutExpire(String key, Object o);
	
	Set<String> differenceWithoutExpire(String key, Collection<String> collection);
	
	Set<String> intersectWithoutExpire(String key, String otherKey);

	Long sizeOfSet(String key);

	Boolean moveToSet(String key, String value, String destKey);

	String popToSet(String key);

	Long addToSet(String key, String... values);
	
	Long addToSetWithExpire(long expireTime,TimeUnit tiemUnit, String key, String... values);
	
	Long addToSetWithOutExpirtTime(String key, String... values);

	String leftPopList(String key);

	Long removeOfList(String key, long count, Object value);

	Long rightPushAllList(String key, Collection<String> values);

	Long rightPushAllList(String key, String... values);

	List<String> rangeOfList(String key, long start, long end);
	
	Boolean hasKey(String key);

	void set(String key, int i, String value);

	BoundListOperations<String, String> boundListOps(String key);

	void set(String key,String value);
	/**
	 * 防止并发锁 特殊处理 
	 */
	void expire(String keys);

	Long sizeOfList(String key);

	void addVo(String key, String str);
	
	Set<String> MembersOfVo(String key);

    Set<String> keySet(String keyPattern);
	
	void batchDelete(String keyPattern);
	
	void set(String key, String string, int i, TimeUnit seconds);
	
	void leftPushAll(String string, List<String> originalTaskInfoJSONs);

	public void add(String key,String[] setToArr);

	void putWithDays(String key, String value, long days);
	
	void putWithMinites(String key, String value, long minites);

	Long removeOfSet(String key, String... values);
	
	public Long rightPushAllListForEver(String key, String... values);
	
	public Long rightPushAllListForEver(String key, Collection<String> values);
	
	void expire(String key, int expireTime, TimeUnit timeUnit);
	
	/**
	 * zSet有序插入
	 * @param key
	 * @param value
	 * @param score
	 */
	void zAdd(String key, String value, double score);
	
	void zAdd1(double score ,String key, String... values);

	/**
	 * 分页查询
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	Set<String> zRange(String key, long start, long end);
	
	/**
	 * 查询所有有序元素
	 * @param key
	 * @return
	 */
	Set<String> MembersOfZSet(String key);
	/**
	 * 移除指定有序元素
	 * @param key
	 * @param values
	 * @return
	 */
	Long removeOfZSet(String key, String... values);
	
	/**
	 * 递增key
	 * @param key
	 * @param delta
	 * @param expireTime
	 * @return
	 */
	public Long increment(String key, long delta, long expireTime);
	
}
