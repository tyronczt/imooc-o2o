package com.tyron.o2o.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyron.o2o.cache.JedisUtil;
import com.tyron.o2o.dao.AreaDao;
import com.tyron.o2o.entity.Area;
import com.tyron.o2o.exceptions.AreaOperationException;
import com.tyron.o2o.service.AreaService;

/**
 * @Description: 区域业务接口实现
 *
 * @author tyronchen
 * @date 2018年3月24日
 */
@Service
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaDao areaDao;
	@Autowired
	private JedisUtil.Keys jedisKeys;
	@Autowired
	private JedisUtil.Strings jedisStrings;

	// Redis中区域信息的key
	private static String AREALISTKEY = "arealist";
	private static Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tyron.o2o.service.AreaService#getAreaList()
	 */
	@Override
	@Transactional
	public List<Area> getAreaList() {
		// key
		String key = AREALISTKEY;
		List<Area> areaList = null;
		ObjectMapper mapper = new ObjectMapper();
		// 如果Redis中未存在key
		if (!jedisKeys.exists(key)) {
			// 数据库中获取区域列表
			areaList = areaDao.selectArea();
			String jsonString = null;
			// 将list转换为String
			try {
				jsonString = mapper.writeValueAsString(areaList);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new AreaOperationException(e.getMessage());
			}
			jedisStrings.set(key, jsonString);
		}
		// Redis中存在key，则取出
		else {
			// 将String转换为List
			String jsonString = jedisStrings.get(key);
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
			try {
				mapper.readValue(jsonString, javaType);
			} catch (JsonParseException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new AreaOperationException(e.getMessage());
			} catch (JsonMappingException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new AreaOperationException(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new AreaOperationException(e.getMessage());
			}
		}

		return areaList;
	}

}
