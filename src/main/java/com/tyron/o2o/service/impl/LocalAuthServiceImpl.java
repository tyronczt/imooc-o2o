package com.tyron.o2o.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tyron.o2o.dao.LocalAuthDao;
import com.tyron.o2o.dto.LocalAuthExecution;
import com.tyron.o2o.entity.LocalAuth;
import com.tyron.o2o.enums.LocalAuthStateEnum;
import com.tyron.o2o.enums.OperationStatusEnum;
import com.tyron.o2o.exceptions.LocalAuthOperationException;
import com.tyron.o2o.service.LocalAuthService;
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
	 * @see com.tyron.o2o.service.LocalAuthService#getLocalAuthByUsername(java.lang.
	 * String)
	 */
	@Override
	public LocalAuth getLocalAuthByUsername(String username) {
		return localAuthDao.queryLocalByUsername(username);
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
	public LocalAuthExecution modifyLocalAuth(String username, String password, String newPassword)
			throws LocalAuthOperationException {
		// 非空判断
		if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)
				&& StringUtils.isNotBlank(newPassword)) {
			try {
				int effectedNum = localAuthDao.updateLocalAuth(username, MD5.getMd5(password), MD5.getMd5(newPassword),
						new Date());
				if (effectedNum <= 0) {
					return new LocalAuthExecution(LocalAuthStateEnum.ERROR_UPDATE);
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
