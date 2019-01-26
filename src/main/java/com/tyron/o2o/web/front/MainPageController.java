package com.tyron.o2o.web.front;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tyron.o2o.entity.HeadLine;
import com.tyron.o2o.entity.ShopCategory;
import com.tyron.o2o.enums.EnableStatusEnum;
import com.tyron.o2o.service.HeadLineService;
import com.tyron.o2o.service.ShopCategoryService;

/**
 * @Description: 首页控制层
 *
 * @author: tyron
 * @date: 2018年11月26日
 */

@Controller
@RequestMapping("/front")
public class MainPageController {

	@Autowired
	private ShopCategoryService shopCategoryService;

	@Autowired
	private HeadLineService headLineService;

	/**
	 * 初始化前端展示系统的主页信息，包括获取一级店铺类别列表一级头条列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listmainpageinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> listMainPageInfo() {
		Map<String, Object> modelMap = new HashMap<>();
		List<ShopCategory> shopCategoryList = new ArrayList<>();
		try {
			// 获取一级店铺列表（即parentId为空的shopCategory）
			shopCategoryList = shopCategoryService.getShopCategoryList(null);
			modelMap.put("shopCategoryList", shopCategoryList);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}

		// 构建头条列表
		List<HeadLine> headLineList = new ArrayList<>();
		try {
			// 获取状态可用的头条列表
			HeadLine headLineCondition = new HeadLine();
			headLineCondition.setEnableStatus(EnableStatusEnum.AVAILABLE.getState());
			headLineList = headLineService.getHeadLineList(headLineCondition);
			modelMap.put("headLineList", headLineList);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		modelMap.put("success", true);
		return modelMap;
	}

}
