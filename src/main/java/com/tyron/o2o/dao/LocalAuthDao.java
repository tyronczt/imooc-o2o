package com.tyron.o2o.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.tyron.o2o.entity.LocalAuth;

/**
 * @Description: 用户登录接口
 *
 * @author tyronchen
 * @date 2019年5月18日
 */
public interface LocalAuthDao {

	/**
	 * 根据账号密码查询用户信息
	 * 
	 * @param username 用户名
	 * @param password 密码
	 * @return 用户信息
	 */
	LocalAuth queryLocalByUsernameAndPwd(@Param("username") String username, @Param("password") String password);

	/**
	 * 根据账号查询用户信息
	 * 
	 * @param username 用户名
	 * @return 用户信息
	 */
	LocalAuth queryLocalByUsername(String username);

	/**
	 * 根据用户ID查询用户信息
	 * 
	 * @param localAuthId 用户ID
	 * @return 用户信息
	 */
	LocalAuth queryLocalByLocalAuthId(long localAuthId);

	/**
	 * 添加平台账号
	 * 
	 * @param localAuth 用户信息
	 * @return 添加成功条数
	 */
	int insertLocalAuth(LocalAuth localAuth);

	/**
	 * 通过username，password更改密码
	 * 
	 * @param username     用户名
	 * @param password     原密码
	 * @param newPassword  新密码
	 * @param lastEditTime 最后修改时间
	 * @return 修改成功数
	 */
	int updateLocalAuth(@Param("username") String username, @Param("password") String password,
			@Param("newPassword") String newPassword, @Param("lastEditTime") Date lastEditTime);

}
