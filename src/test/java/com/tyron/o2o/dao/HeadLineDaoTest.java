package com.tyron.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tyron.o2o.BaseTest;
import com.tyron.o2o.entity.HeadLine;
import com.tyron.o2o.util.SystemEnumUtil;

/**
 * @Description: 首页头条数据接口测试
 *
 * @author: tyron
 * @date: 2018年11月25日
 */

public class HeadLineDaoTest extends BaseTest {

	@Autowired
	private HeadLineDao headLineDao;

	@Test
	@Ignore
	public void testInsertHeadLine() throws Exception {
		HeadLine headLine = new HeadLine();
		headLine.setCreateTime(new Date());
		headLine.setEnableStatus(SystemEnumUtil.ENABLE_STATUS.USABLE.getValue());
		headLine.setLineImg("test");
		headLine.setLineLink("testLink");
		headLine.setLineName("测试");
		headLine.setPriority(1);
		int effectNum = headLineDao.insertHeadLine(headLine);
		System.out.println("insertNum:" + effectNum);
	}

	@Test
	@Ignore
	public void testUpdateHeadLine() throws Exception {
		HeadLine headLine = new HeadLine();
		headLine.setLineId(1L);
		headLine.setEnableStatus(SystemEnumUtil.ENABLE_STATUS.UNUSABLE.getValue());
		headLine.setLastEditTime(new Date());
		int effectNum = headLineDao.updateHeadLine(headLine);
		System.out.println("insertNum:" + effectNum);
	}

	@Test
	public void testSelectHeadLine() throws Exception {
		List<HeadLine> headLineList = headLineDao.selectHeadLineList(new HeadLine());
		assertEquals(1, headLineList.size());
	}

}
