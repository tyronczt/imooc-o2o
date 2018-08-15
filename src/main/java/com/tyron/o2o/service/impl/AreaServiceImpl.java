package com.tyron.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyron.o2o.dao.AreaDao;
import com.tyron.o2o.entity.Area;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tyron.o2o.service.AreaService#getAreaList()
	 */
	@Override
	public List<Area> getAreaList() {
		List<Area> areaList = areaDao.queryArea();
		return areaList;
	}

}
