package com.tyron.o2o.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tyron.o2o.dao.LocalAuthDao;
import com.tyron.o2o.dto.LocalAuthExecution;
import com.tyron.o2o.entity.LocalAuth;
import com.tyron.o2o.entity.PersonInfo;
import com.tyron.o2o.enums.LocalAuthStateEnum;
import com.tyron.o2o.enums.OperationStatusEnum;
import com.tyron.o2o.enums.PersonInfoStatusEnum;
import com.tyron.o2o.enums.PersonInfoTypeEnum;
import com.tyron.o2o.exceptions.LocalAuthOperationException;
import com.tyron.o2o.service.LocalAuthService;
import com.tyron.o2o.service.PersonInfoService;
import com.tyron.o2o.util.MD5;

/**
 * @Description: 本地用户信息服务层接口实现
 *
 * @author tyronchen
 * @date 2019年5月20日
 */
@Service
public class LocalAuthServiceImpl implements LocalAuthService {

	@Autowired
	private LocalAuthDao localAuthDao;
	@Autowired
	private PersonInfoService personInfoService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tyron.o2o.service.LocalAuthService#getLocalAuthByUsernameAndPwd(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public LocalAuth getLocalAuthByUsernameAndPwd(String username, String password) {
		return localAuthDao.queryLocalByUsernameAndPwd(username, MD5.getMd5(password));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tyron.o2o.service.LocalAuthService#getLocalAuthByUserId(long)
	 */
	@Override
	public LocalAuth queryLocalByLocalAuthId(long localAuthId) {
		return localAuthDao.queryLocalByLocalAuthId(localAuthId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tyron.o2o.service.LocalAuthService#saveLocalAuth(com.tyron.o2o.entity.
	 * LocalAuth)
	 */
	@Override
	@Transactional
	public LocalAuthExecution saveLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException {
		// 空值判断
		if (localAuth == null || localAuth.getUsername() == null || localAuth.getPassword() == null) {
			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}
		// 保存数据
		localAuth.setCreateTime(new Date());
		localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
		try {
			int effectedNum = localAuthDao.insertLocalAuth(localAuth);
			if (effectedNum <= 0) {
				throw new LocalAuthOperationException("用户账号新增失败");
			} else {
				// 构建用户信息
				PersonInfo user = new PersonInfo();
				user.setLocalAuthId(localAuth.getLocalAuthId());
				user.setName(localAuth.getUsername());
				// 默认注册一个有权限访问的顾客
				user.setEnableStatus(PersonInfoStatusEnum.ALLOW.getState());
				user.setUserType(PersonInfoTypeEnum.CUSTOMER.getState());
				personInfoService.insertPersonInfo(user);
				return new LocalAuthExecution(OperationStatusEnum.SUCCESS, localAuth);
			}
		} catch (Exception e) {
			throw new LocalAuthOperationException("insertLocalAuth error:" + e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tyron.o2o.service.LocalAuthService#modifyLocalAuth(
	 * java.lang.String, java.lang.String, java.lang.String, java.util.Date)
	 */
	@Override
	public LocalAuthExecution modifyLocalAuth(String username, String password, String newPassword, Date lastEditTime)
			throws LocalAuthOperationException {
		// 非空判断
		if (username != null && password != null && password != null && newPassword != null
				&& !password.equals(newPassword)) {
			try {
				int effectedNum = localAuthDao.updateLocalAuth(username, password, MD5.getMd5(newPassword), new Date());
				if (effectedNum <= 0) {
					throw new LocalAuthOperationException("用户账号更新失败");
				} else {
					return new LocalAuthExecution(OperationStatusEnum.SUCCESS);
				}
			} catch (Exception e) {
				throw new LocalAuthOperationException("updateLocalAuth error:" + e.getMessage());
			}
		} else {
			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}
	}
}
