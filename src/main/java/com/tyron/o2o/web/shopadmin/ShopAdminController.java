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
@RequestMapping(value="shopadmin",method= {RequestMethod.GET})
public class ShopAdminController {

	@RequestMapping(value="shopoperation")
	public String shopOperation() {
		return "shop/shopoperation";
	}
}
