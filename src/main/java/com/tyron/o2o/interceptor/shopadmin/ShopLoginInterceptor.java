package com.tyron.o2o.interceptor.shopadmin;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tyron.o2o.entity.PersonInfo;
import com.tyron.o2o.enums.EnableStatusEnum;

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
		// 从session中获取用户信息
		Object userObj = request.getSession().getAttribute("user");
		// 如果用户信息存在
		if (userObj != null) {
			// 将session中的用户信息转换为PersonInfo实体类对象
			PersonInfo user = (PersonInfo) userObj;
			// 用户存在且可用，并且用户类型为店家
			if (user != null && user.getUserId() != null && user.getUserId() > 0
					&& user.getEnableStatus().equals(EnableStatusEnum.AVAILABLE.getState())) {
				// 如果通过验证，则返回true，用户正常执行后续操作
				return true;
			}
		}

		// 不满足登录条件，则直接跳转用户登录页面
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<script>");
		out.println("window.open('" + request.getContextPath() + "/local/login?usertype=2','_self)");
		out.println("</script>");
		out.println("</html>");
		return false;
	}

}
