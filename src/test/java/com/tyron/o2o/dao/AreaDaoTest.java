package com.tyron.o2o.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tyron.o2o.BaseTest;
import com.tyron.o2o.entity.Area;

/**
 * @Description: 区域接口测试
 *
 * @author tyronchen
 * @date 2018年3月24日
 */

public class AreaDaoTest extends BaseTest {

	@Autowired
	private AreaDao areaDao;

	@Test
	public void testQueryArea() {
		List<Area> areaList = areaDao.queryArea();
		System.out.println("dao测试：" + areaList.toString());
	}

}
