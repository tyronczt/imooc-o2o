package com.tyron.o2o.dao;

import java.util.List;

import com.tyron.o2o.entity.Area;

/**
 * @Description: 区域数据接口
 *
 * @author tyronchen
 * @date 2018年3月24日
 */
public interface AreaDao {

	/**
	 * 查询区域信息
	 * 
	 * @return
	 */
	List<Area> selectArea();

}
