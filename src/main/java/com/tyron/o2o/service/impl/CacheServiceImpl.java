package com.tyron.o2o.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyron.o2o.cache.JedisUtil;
import com.tyron.o2o.service.CacheService;

/**
 * @Description: 缓存服务接口实现
 *
 * @author tyronchen
 * @date 2019年1月26日
 */
@Service
public class CacheServiceImpl implements CacheService {

	@Autowired
	private JedisUtil.Keys jedisKeys;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tyron.o2o.service.CacheService#removeFromCache(java.lang.String)
	 */
	@Override
	public void removeFromCache(String keyPrefix) {
		Set<String> keySet = jedisKeys.keys(keyPrefix + "*");
		for (String key : keySet) {
			jedisKeys.del(key);
		}
	}

}
