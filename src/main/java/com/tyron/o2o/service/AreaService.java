package com.tyron.o2o.service;

import java.util.List;

import com.tyron.o2o.entity.Area;

/**
 * @Description: 区域业务接口
 *
 * @author tyronchen
 * @date 2018年3月24日
 */
public interface AreaService {

	/**
	 * 获取区域列表
	 * 
	 * @return
	 */
	List<Area> getAreaList();

}
