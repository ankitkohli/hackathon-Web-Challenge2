package com.hackathon.mobileapp.cacheManager;


import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface ICacheManagerService {

	/* Value Operations */
	void storeValue(Object key, Object value);

	public void storeValueWithTTL(Object key, Object value, long timeout,
			TimeUnit unit);

	Object getValue(Object key);

	Object replaceValue(Object key, Object value);

	/* Multi-Value Operations */
	List<Object> getMultipleValues(List<Object> keys);

	void setMultipleValues(Map<Object, Object> map);

	void setMultipleValues(Map<Object, Object> map, long timeout, TimeUnit unit);

	void delKey(Object key);

	/* List Based Operations */
	void leftPush(Object key, Object value);

	void rightPush(Object key, Object value);

	Object leftPop(Object key, long timeout, TimeUnit unit);

	Object rightPop(Object key, long timeout, TimeUnit unit);

	// List<Object> getUnCachedKeys(List<Object> keys, List<Object>
	// cachedValues);

	void flushAll();

	public void delKeys(Collection<Object> keys);

	public Set<Object> getKeysByPattern(String pattern);
}

