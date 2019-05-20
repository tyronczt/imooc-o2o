package com.tyron.o2o.service;

import java.util.Date;

import com.tyron.o2o.dto.LocalAuthExecution;
import com.tyron.o2o.entity.LocalAuth;
import com.tyron.o2o.exceptions.LocalAuthOperationException;

/**
 * @Description: 本地用户信息服务层接口
 *
 * @author tyronchen
 * @date 2019年5月20日
 */
public interface LocalAuthService {

	/**
	 * 根据用户名、密码查询本地用户信息
	 * 
	 * @param username 用户名
	 * @param password 密码
	 * @return 用户信息
	 */
	LocalAuth getLocalAuthByUsernameAndPwd(String username, String password);

	/**
	 * 根据用户名查询本地用户信息
	 * 
	 * @param userId 用户ID
	 * @return 用户信息
	 */
	LocalAuth getLocalAuthByUserId(long userId);

	/**
	 * 保存本地账号信息
	 * 
	 * @param localAuth 本地用户
	 * @return 操作返回信息
	 * @throws LocalAuthOperationException 继承runtimeException结合事务使用
	 */
	LocalAuthExecution saveLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException;

	/**
	 * 修改本地账号登录密码
	 * 
	 * @param username     用户名
	 * @param password     原密码
	 * @param newPassword  新密码
	 * @param lastEditTime 最后修改时间
	 * @return 操作返回信息
	 * @throws LocalAuthOperationException 继承runtimeException结合事务使用
	 */
	LocalAuthExecution modifyLocalAuth(String username, String password, String newPassword, Date lastEditTime)
			throws LocalAuthOperationException;
}
