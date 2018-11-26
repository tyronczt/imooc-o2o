package com.tyron.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyron.o2o.dao.HeadLineDao;
import com.tyron.o2o.entity.HeadLine;
import com.tyron.o2o.service.HeadLineService;

/**
 * @Description: 首页头条业务接口实现
 *
 * @author: tyron
 * @date: 2018年11月26日
 */
@Service
public class HeadLineServiveImpl implements HeadLineService {

	@Autowired
	private HeadLineDao headLineDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tyron.o2o.service.HeadLineService#getHeadLineList(com.tyron.o2o.entity.
	 * HeadLine)
	 */
	@Override
	public List<HeadLine> getHeadLineList(HeadLine headLineCondition) {
		return headLineDao.selectHeadLineList(headLineCondition);
	}

}
