package com.tyron.o2o.web.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Description: 前端页面控制器
 *
 * @author: tyron
 * @date: 2018年11月28日
 */
@Controller
@RequestMapping("/front")
public class FrontController {

	/**
	 * 前端首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "front/index";
	}

	/**
	 * 店铺列表页路由
	 * 
	 * @return
	 */
	@RequestMapping(value = "/shoplist", method = RequestMethod.GET)
	public String shopList() {
		return "front/shoplist";
	}

	/**
	 * 店铺详情页路由
	 * 
	 * @return
	 */
	@RequestMapping(value = "/shopdetail", method = RequestMethod.GET)
	public String shopDetail() {
		return "front/shopdetail";
	}

	/**
	 * 商品详情页路由
	 * 
	 * @return
	 */
	@RequestMapping(value = "/productdetail", method = RequestMethod.GET)
	public String productDetail() {
		return "front/productdetail";
	}
}
