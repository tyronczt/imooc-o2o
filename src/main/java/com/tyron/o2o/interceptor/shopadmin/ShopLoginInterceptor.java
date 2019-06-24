package com.tyron.o2o.interceptor.shopadmin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tyron.o2o.entity.PersonInfo;
import com.tyron.o2o.enums.EnableStatusEnum;
import com.tyron.o2o.enums.PersonInfoTypeEnum;

/**
 * @Description: 店家管理系统登录拦截器
 *
 * @author tyronchen
 * @date 2019年5月17日
 */
public class ShopLoginInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 主要做事前拦截，即用户操作前执行，改写preHandler里的逻辑，进行用户操作权限的拦截
	 * 
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println(">>>ShopLoginInterceptor>>>>>>>在请求处理之前进行调用（Controller方法调用之前）");
		// 从session中获取用户信息
		Object userObj = request.getSession().getAttribute("user");
		// 如果用户信息存在
		if (userObj != null) {
			// 将session中的用户信息转换为PersonInfo实体类对象
			PersonInfo user = (PersonInfo) userObj;
			// 用户存在且可用，并且用户类型为店家或者管理员
			if (user != null && user.getUserId() != null && user.getUserId() > 0
					&& user.getEnableStatus().equals(EnableStatusEnum.AVAILABLE.getState())
					&& (user.getUserType().equals(PersonInfoTypeEnum.OWNER.getState())
							|| user.getUserType().equals(PersonInfoTypeEnum.ADMIN.getState()))) {
				// 如果通过验证，则返回true，用户正常执行后续操作
				return true;
			}
		}
		// 不满足登录条件，则直接跳转后台用户登录页面
		String loginUrl = request.getContextPath() + "/admin/login?userType=back";
		response.sendRedirect(loginUrl);
		return false;
	}

}
