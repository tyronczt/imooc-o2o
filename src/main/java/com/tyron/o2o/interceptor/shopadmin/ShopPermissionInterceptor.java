package com.tyron.o2o.interceptor.shopadmin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tyron.o2o.entity.Shop;

/**
 * @Description: 店铺操作权限拦截器
 *
 * @author tyronchen
 * @date 2019年6月24日
 */
public class ShopPermissionInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println(">>>ShopPermissionInterceptor>>>>>>>在请求处理之前进行调用（Controller方法调用之前）");
		// 从session中获取当前选择的店铺
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		@SuppressWarnings("unchecked")
		// 获取session中当前用户可操作的店铺列表
		List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
		// 非空判断
		if (currentShop != null && shopList != null) {
			// 遍历
			for (Shop shop : shopList) {
				// 如果当前店铺在可操作的列表则返回true，进行后续操作
				if (shop.getShopId() == currentShop.getShopId()) {
					return true;
				}
			}
		}
		return false;
	}
}
