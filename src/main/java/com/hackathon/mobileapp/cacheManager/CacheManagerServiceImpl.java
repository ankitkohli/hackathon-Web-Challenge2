package com.hackathon.mobileapp.cacheManager;


	import java.util.Collection;
	import java.util.List;
	import java.util.Map;
	import java.util.Set;
	import java.util.concurrent.TimeUnit;

	import org.slf4j.LoggerFactory;
	import org.springframework.data.redis.connection.RedisConnection;
	import org.springframework.data.redis.core.ListOperations;
	import org.springframework.data.redis.core.RedisTemplate;
	import org.springframework.data.redis.core.ValueOperations;
	import org.springframework.data.redis.serializer.RedisSerializer;
	import org.springframework.data.redis.serializer.SerializationUtils;
	import org.springframework.util.CollectionUtils;



	public class CacheManagerServiceImpl implements ICacheManagerService {

		private ListOperations<Object, Object> listOpsTemplate;

		private ValueOperations<Object, Object> valueOpsTemplate;

		// private Jedis jedis;

		RedisSerializer keySerializer;

		RedisSerializer valueSerializer;

		RedisTemplate tmpl;
		private static org.slf4j.Logger logger = LoggerFactory
				.getLogger(CacheManagerServiceImpl.class);

		public void set_cacheTemplate(RedisTemplate<Object, Object> _cacheTemplate) {
			this.valueOpsTemplate = _cacheTemplate.opsForValue();
			this.listOpsTemplate = _cacheTemplate.opsForList();
			tmpl = _cacheTemplate;
			// /this.jedis = (Jedis)
			// _cacheTemplate.getConnectionFactory().getConnection().openPipeline();
			this.keySerializer = _cacheTemplate.getKeySerializer();
			this.valueSerializer = _cacheTemplate.getValueSerializer();
		}

		/*
		 * Store key-value in cache
		 * 
		 * @see
		 * com.mmt.engine.fph.flight.manager.ICacheManager#storeValue(java.lang.
		 * Object, java.lang.Object)
		 */
		public void storeValue(Object key, Object value) {
			valueOpsTemplate.set(key, value);
		}

		public void storeValueWithTTL(Object key, Object value, long timeout,
				TimeUnit unit) {
			valueOpsTemplate.set(key, value, timeout, unit);
		}

		public Object getValue(Object key) {
			return valueOpsTemplate.get(key);
		}

		public Object replaceValue(Object key, Object value) {
			return valueOpsTemplate.getAndSet(key, value);
		}

		/*
		 * Store multiple-key-value in cache
		 * 
		 * @see
		 * com.mmt.engine.fph.flight.manager.ICacheManager#storeValue(java.lang.
		 * Object, java.lang.Object)
		 */
		public List<Object> getMultipleValues(List<Object> keys) {
			return valueOpsTemplate.multiGet(keys);
		}

		public void setMultipleValues(Map<Object, Object> map) {
			valueOpsTemplate.multiSet(map);
		}

		/*
		 * List Operations
		 * 
		 * @see
		 * com.mmt.engine.fph.flight.manager.ICacheManager#storeValue(java.lang.
		 * Object, java.lang.Object)
		 */

		public void leftPush(Object key, Object value) {
			listOpsTemplate.leftPush(key, value);
		}

		public void rightPush(Object key, Object value) {
			listOpsTemplate.rightPush(key, value);
		}

		public Object leftPop(Object key, long timeout, TimeUnit unit) {
			return listOpsTemplate.leftPop(key, timeout, unit);
		}

		public Object rightPop(Object key, long timeout, TimeUnit unit) {
			return listOpsTemplate.rightPop(key, timeout, unit);
		}

		public void delKey(Object key) {

			valueOpsTemplate.getOperations().delete(key);
		}

		public void delKeys(Collection<Object> keys) {

			final byte[][] rawKeys = rawKeys(keys);
			RedisConnection conn = tmpl.getConnectionFactory().getConnection();
			try {
				conn.del(rawKeys);
			} catch (RuntimeException e) {
				logger.warn(e.getMessage() + "\n The keys creating error are:"
						+ keys);
				throw e;
			}
			conn.close();
		}

		// public List<Object> getUnCachedKeys(List<Object> keys,
		// List<Object> cachedValues) {
		// if (CollectionUtils.isEmpty(keys)) {
		// return null;
		// }
		// List<Object> values = getMultipleValues(keys);
		// List<Object> unCachedKeys = new ArrayList<Object>();
		// unCachedKeys.addAll(keys);
		// if (!CollectionUtils.isEmpty(values)) {
		// for (Object cachedValue : values) {
		// if (cachedValue != null) {
		// cachedValues.add(cachedValue);
		// unCachedKeys.remove(((PackageDetail) cachedValue).getId()
		// .toString());
		// }
		// }
		// }
		// return unCachedKeys;
		// }

		public void setMultipleValues(Map<Object, Object> map, long timeout,
				TimeUnit unit) {

			RedisConnection conn = tmpl.getConnectionFactory().getConnection();

			if (!CollectionUtils.isEmpty(map)) {

				conn.openPipeline();
				int time = (int) unit.toSeconds(timeout);
				for (Object key : map.keySet()) {
					conn.setEx(keySerializer.serialize(key), time,
							valueSerializer.serialize(map.get(key)));
				}

				conn.closePipeline();

				conn.close();

				// Pipeline pipeline = jedis.pipelined();
				// pipeline.multi();
				// int time = (int) unit.toSeconds(timeout);
				// for (Object key : map.keySet()) {
				// pipeline.setex(keySerializer.serialize(key), time,
				// valueSerializer.serialize(map.get(key)));
				// }
				// pipeline.exec();
			}
		}

		@SuppressWarnings("unchecked")
		public Set<Object> getKeysByPattern(String pattern) {
			// Transaction transaction = jedis.multi();

			// transaction.flushAll();
			// transaction.exec();

			final byte[] rawKey = rawKey(pattern);
			RedisConnection conn = tmpl.getConnectionFactory().getConnection();
			Set<byte[]> rawKeys = conn.keys(rawKey);
			conn.close();
			return SerializationUtils.deserialize(rawKeys, keySerializer);
		}

		@SuppressWarnings("unchecked")
		private byte[] rawKey(Object key) {
			if (key == null)
				return null;
			return keySerializer.serialize(key);
		}

		private byte[][] rawKeys(Collection<Object> keys) {
			final byte[][] rawKeys = new byte[keys.size()][];

			int i = 0;
			for (Object key : keys) {
				rawKeys[i++] = rawKey(key);
			}

			return rawKeys;
		}

		public void flushAll() {
			// Transaction transaction = jedis.multi();

			// transaction.flushAll();
			// transaction.exec();

			RedisConnection conn = tmpl.getConnectionFactory().getConnection();
			conn.flushAll();
			conn.close();

		}

	}

