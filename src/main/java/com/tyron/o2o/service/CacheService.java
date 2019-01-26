package com.tyron.o2o.service;

/**
 * @Description: 缓存服务接口
 *
 * @author tyronchen
 * @date 2019年1月26日
 */
public interface CacheService {

	/**
	 * 依据key前缀匹配原则删除缓存数据
	 * 
	 * @param keyPrefix
	 */
	void removeFromCache(String keyPrefix);

}
