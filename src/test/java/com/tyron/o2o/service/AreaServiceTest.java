package com.tyron.o2o.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tyron.o2o.BaseTest;
import com.tyron.o2o.entity.Area;

/**
 * @Description: 区域业务测试
 *
 * @author tyronchen
 * @date 2018年3月24日
 */
public class AreaServiceTest extends BaseTest {

	@Autowired
	private AreaService areaService;

	@Test
	public void testQueryArea() {
		List<Area> areaList = areaService.getAreaList();
		System.out.println("service测试：" + areaList.toString());
	}

}
