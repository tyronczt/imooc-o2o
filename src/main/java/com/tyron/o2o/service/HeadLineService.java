package com.tyron.o2o.service;

import java.util.List;

import com.tyron.o2o.entity.HeadLine;

/**
 * @Description: 首页头条业务接口
 *
 * @author: tyron
 * @date: 2018年11月26日
 */

public interface HeadLineService {

	/**
	 * 根据条件查询头条列表
	 * 
	 * @param headLineCondition
	 * @return
	 * @throws Exception
	 */
	List<HeadLine> getHeadLineList(HeadLine headLineCondition);
	
}
