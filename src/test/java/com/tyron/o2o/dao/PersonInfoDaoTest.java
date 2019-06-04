package com.tyron.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tyron.o2o.BaseTest;
import com.tyron.o2o.entity.PersonInfo;
import com.tyron.o2o.enums.PersonInfoStatusEnum;
import com.tyron.o2o.enums.PersonInfoTypeEnum;

/**
 * @Description: 用户信息数据层测试
 *
 * @author tyronchen
 * @date 2019年6月3日
 */
public class PersonInfoDaoTest extends BaseTest {

	@Autowired
	private PersonInfoDao personInfoDao;

	/**
	 * 测试插入用户
	 * 
	 * @throws Exception
	 */
	@Test
	@Ignore
	public void testInsertPersonInfo() throws Exception {
		// 新增一条用户
		PersonInfo personInfo = new PersonInfo();
		personInfo.setLocalAuthId(1L);
		// 注册一个有权限访问的顾客
		personInfo.setEnableStatus(PersonInfoStatusEnum.ALLOW.getState());
		personInfo.setUserType(PersonInfoTypeEnum.CUSTOMER.getState());
		personInfo.setCreateTime(new Date());
		personInfo.setGender("男");
		personInfo.setEmail("a@a.com");
		personInfo.setName("顾客a");
		int effectNum = personInfoDao.insertPersonInfo(personInfo);
		assertEquals(1, effectNum);
	}

	/**
	 * 根据用户Id查询本地账号信息
	 */
	@Test
	@Ignore
	public void testQueryInfoByUserId() {
		PersonInfo personInfo = personInfoDao.queryInfoByUserId(2L);
		assertEquals("顾客a", personInfo.getName());
	}

	/**
	 * 更新账号信息
	 */
	@Test
	public void testUpdatePersonInfo() {
		PersonInfo personInfo = new PersonInfo();
		personInfo.setLastEditTime(new Date());
		personInfo.setUserId(2L);
		personInfo.setName("顾客aa");
		int effectNum = personInfoDao.updatePersonInfo(personInfo);
		assertEquals(1, effectNum);
	}

}
