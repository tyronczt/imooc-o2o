package com.tyron.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Description: 店铺管理员控制器
 *
 * @author tyronchen
 * @date 2018年4月15日
 */
@Controller
@RequestMapping(value = "shopadmin", method = { RequestMethod.GET })
public class ShopAdminController {

	/**
	 * 店铺操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "shopoperation")
	public String shopOperation() {
		return "shop/shopoperation";
	}

	/**
	 * 店铺列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/shoplist", method = RequestMethod.GET)
	public String shopList() {
		return "shop/shoplist";
	}

	/**
	 * 店铺管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/shopmanagement", method = RequestMethod.GET)
	public String shopManagement() {
		return "shop/shopmanagement";
	}

	/**
	 * 商品类别管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/productcategorymanagement", method = RequestMethod.GET)
	public String productCategoryManagement() {
		return "shop/productcategorymanagement";
	}

	/**
	 * 商品操作页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/productoperation", method = RequestMethod.GET)
	public String productOperation() {
		return "shop/productoperation";
	}
}
