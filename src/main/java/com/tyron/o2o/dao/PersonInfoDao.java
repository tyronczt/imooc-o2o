package com.tyron.o2o.dao;

import com.tyron.o2o.entity.PersonInfo;

/**
 * @Description: 用户信息接口
 *
 * @author tyronchen
 * @date 2019年6月3日
 */
public interface PersonInfoDao {

	/**
	 * 根据用户ID查询用户信息
	 * 
	 * @param userId 用户ID
	 * @return 用户信息
	 */
	PersonInfo queryInfoByUserId(long userId);

	/**
	 * 添加平台账号
	 * 
	 * @param user 用户信息
	 * @return 添加成功条数
	 */
	int insertPersonInfo(PersonInfo user);

	/**
	 * 修改用户信息
	 * 
	 * @param user 用户信息
	 * @return 修改成功数
	 */
	int updatePersonInfo(PersonInfo user);

}
