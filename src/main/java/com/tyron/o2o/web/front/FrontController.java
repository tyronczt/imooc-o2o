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
}
