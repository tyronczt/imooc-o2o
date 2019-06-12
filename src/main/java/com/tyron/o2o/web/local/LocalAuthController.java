package com.tyron.o2o.web.local;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tyron.o2o.dto.LocalAuthExecution;
import com.tyron.o2o.entity.LocalAuth;
import com.tyron.o2o.entity.PersonInfo;
import com.tyron.o2o.enums.OperationStatusEnum;
import com.tyron.o2o.enums.PersonInfoStatusEnum;
import com.tyron.o2o.enums.PersonInfoTypeEnum;
import com.tyron.o2o.service.LocalAuthService;
import com.tyron.o2o.service.PersonInfoService;
import com.tyron.o2o.util.CodeUtil;
import com.tyron.o2o.util.HttpServletRequestUtil;

/**
 * @Description: 本地用户信息
 *
 * @author tyronchen
 * @date 2019年5月22日
 */
@Controller
@RequestMapping("/user")
public class LocalAuthController {

	@Autowired
	private LocalAuthService localAuthService;
	@Autowired
	private PersonInfoService personInfoService;

	/**
	 * 注册账号
	 */
	@PostMapping(value = "/register")
	@ResponseBody
	public Map<String, Object> register(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 1、验证码校验
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", OperationStatusEnum.VERIFYCODE_ERROR.getStateInfo());
			return modelMap;
		}
		// 1、接收并转化相应参数
		String username = HttpServletRequestUtil.getString(request, "username");
		String password = HttpServletRequestUtil.getString(request, "password");
		// 判断
		if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
			// 2、构建账号对象
			LocalAuth localAuth = new LocalAuth();
			localAuth.setUsername(username);
			localAuth.setPassword(password);
			LocalAuthExecution e = localAuthService.saveLocalAuth(localAuth);
			// 3、操作成功，返回结果
			if (e.getState() == OperationStatusEnum.SUCCESS.getState()) {
				// 同时创建用户信息
				PersonInfo personInfo = new PersonInfo();
				personInfo.setLocalAuthId(localAuth.getLocalAuthId());
				personInfo.setName(username);
				personInfo.setUserType(PersonInfoTypeEnum.CUSTOMER.getState());
				personInfo.setEnableStatus(PersonInfoStatusEnum.ALLOW.getState());
				personInfo.setCreateTime(new Date());
				personInfoService.insertPersonInfo(personInfo);
				modelMap.put("success", true);
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名和密码均不能为空");
			return modelMap;
		}
		return modelMap;
	}

	/**
	 * 检查注册的用户名是否已注册
	 */
	@PostMapping(value = "/checkusername")
	@ResponseBody
	public Map<String, Object> checkUsername(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		String username = HttpServletRequestUtil.getString(request, "username");
		if (StringUtils.isNotBlank(username)) {
			LocalAuth localAuth = localAuthService.getLocalAuthByUsername(username);
			// 当前用户名的用户已存在
			if (localAuth != null) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "用户名已存在，请重新输入！");
			} else {
				modelMap.put("success", true);
			}
		}
		return modelMap;
	}

	/**
	 * 登录
	 */
	@PostMapping(value = "login")
	@ResponseBody
	public Map<String, Object> login(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 1、验证码，前端判断3次输入后需要验证码
		boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");
		if (needVerify && !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", OperationStatusEnum.VERIFYCODE_ERROR.getStateInfo());
			return modelMap;
		}

		// 2、获取参数
		String username = HttpServletRequestUtil.getString(request, "username");
		String password = HttpServletRequestUtil.getString(request, "password");
		if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
			LocalAuth localAuth = localAuthService.getLocalAuthByUsernameAndPwd(username, password);
			// 账号信息正确
			if (localAuth != null) {
				// 账号允许登录
				if (localAuth.getPersonInfo().getEnableStatus().equals(PersonInfoStatusEnum.ALLOW.getState())) {
					// 店家或管理员允许登录
					if (localAuth.getPersonInfo().getUserType().equals(PersonInfoTypeEnum.OWNER.getState())
							|| localAuth.getPersonInfo().getUserType().equals(PersonInfoTypeEnum.ADMIN.getState())) {
						modelMap.put("success", true);
						request.getSession().setAttribute("user", localAuth.getPersonInfo());
					} else {
						modelMap.put("success", false);
						modelMap.put("errMsg", "非店家或管理员没有权限访问");
					}
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", "当前用户禁止登录商城，请联系管理员");
				}
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "用户名或密码错误");
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名和密码均不能为空");
		}
		return modelMap;
	}

	/**
	 * 修改密码
	 */
	@PostMapping(value = "changepwd")
	@ResponseBody
	public Map<String, Object> changePwd(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 1、验证码
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", OperationStatusEnum.VERIFYCODE_ERROR.getStateInfo());
			return modelMap;
		}

		// 2、获取参数
		String username = HttpServletRequestUtil.getString(request, "username");
		String password = HttpServletRequestUtil.getString(request, "password");
		String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
		// 空值验证
		if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)
				&& StringUtils.isNotBlank(newPassword)) {
			// 修改密码
			LocalAuthExecution lae = localAuthService.modifyLocalAuth(username, password, newPassword);
			// 操作成功
			if (lae.getState() == OperationStatusEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", lae.getStateInfo());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入有效信息");
		}
		return modelMap;
	}

	/**
	 * 退出登录
	 */
	@PostMapping(value = "logout")
	@ResponseBody
	public Map<String, Object> logout(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		request.getSession().setAttribute("user", null);
		request.getSession().setAttribute("shopList", null);
		request.getSession().setAttribute("currentShop", null);
		modelMap.put("success", true);
		return modelMap;
	}
}
