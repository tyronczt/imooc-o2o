package com.tyron.o2o.web.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tyron.o2o.dto.ShopExecution;
import com.tyron.o2o.entity.Area;
import com.tyron.o2o.entity.Shop;
import com.tyron.o2o.entity.ShopCategory;
import com.tyron.o2o.enums.OperationStatusEnum;
import com.tyron.o2o.service.AreaService;
import com.tyron.o2o.service.ShopCategoryService;
import com.tyron.o2o.service.ShopService;
import com.tyron.o2o.util.HttpServletRequestUtil;

/**
 * @Description: 店铺列表展示控制器
 *
 * @author tyronchen
 * @date 2018年12月1日
 */
@Controller
@RequestMapping("/front")
public class ShopListController {

	@Autowired
	private AreaService areaService;

	@Autowired
	private ShopService shopService;

	@Autowired
	private ShopCategoryService shopCategoryService;

	/**
	 * 返回商品列表里的一级或二级ShopCategory，以及区域信息列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "listshopspageinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShopsPageinfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 前端获取parentId
		long parentId = HttpServletRequestUtil.getLong(request, "parentId");
		List<ShopCategory> shopCategoryList = null;
		// parentId存在，表示查询二级店铺
		if (parentId != -1) {
			try {
				ShopCategory shopCategoryCondition = new ShopCategory();
				ShopCategory parentShopCategory = new ShopCategory();
				parentShopCategory.setShopCategoryId(parentId);
				shopCategoryCondition.setParent(parentShopCategory);
				shopCategoryList = shopCategoryService.getShopCategoryList(shopCategoryCondition);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		}
		// parentId不存在， 即“全部商店”列表
		else {
			try {
				shopCategoryList = shopCategoryService.getShopCategoryList(null);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		}
		modelMap.put("shopCategoryList", shopCategoryList);
		List<Area> areaList = null;
		try {
			areaList = areaService.getAreaList();
			modelMap.put("success", true);
			modelMap.put("areaList", areaList);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}

		return modelMap;
	}

	/**
	 * 获取指定查询条件下的店铺列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "listshops", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShops(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 获取页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		// 获取每页显示条数
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		// 页码非空判断
		if (pageIndex != -1 && pageSize != -1) {
			// 获取一级类别Id
			long parentId = HttpServletRequestUtil.getLong(request, "parentId");
			// 获取二级类别Id
			long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
			// 获取模糊查询的店铺名称
			String shopName = HttpServletRequestUtil.getString(request, "shopName");
			// 获取区域id
			int areaId = HttpServletRequestUtil.getInt(request, "areaId");
			// 获取组合后的查询条件
			Shop shopCondition = compactShopCondition4Search(parentId, shopCategoryId, areaId, shopName);
			// 查询
			ShopExecution shopExecution = shopService.getShopList(shopCondition, pageIndex, pageSize);
			modelMap.put("success", true);
			modelMap.put("shopList", shopExecution.getShopList());
			modelMap.put("count", shopExecution.getCount());
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", OperationStatusEnum.PAGIN_EMPTY.getStateInfo());
		}

		return modelMap;
	}

	/**
	 * 根据查询条件组合
	 * 
	 * @param parentId       一级类别Id
	 * @param shopCategoryId 二级类别Id
	 * @param areaId         区域id
	 * @param shopName       模糊查询的店铺名称
	 * @return
	 */
	private Shop compactShopCondition4Search(long parentId, long shopCategoryId, int areaId, String shopName) {
		Shop shopCondition = new Shop();
		// 查询某个一级shopCategory下的所有二级shopCateg的店铺列表
		if (parentId != -1) {
			ShopCategory childShopCategory = new ShopCategory();
			ShopCategory parentShopCategory = new ShopCategory();
			parentShopCategory.setShopCategoryId(parentId);
			childShopCategory.setParent(parentShopCategory);
			shopCondition.setShopCategory(childShopCategory);
		}
		// 查询某个二级shopCategory下的店铺列表
		if (shopCategoryId != -1) {
			ShopCategory shopCategory = new ShopCategory();
			shopCategory.setShopCategoryId(shopCategoryId);
			shopCondition.setShopCategory(shopCategory);
		}
		// 查询某个区域下的店铺列表
		if (areaId != -1) {
			Area area = new Area();
			area.setAreaId(areaId);
			shopCondition.setArea(area);
		}
		// 模糊查询店铺名称的列表
		if (shopName != null) {
			shopCondition.setShopName(shopName);
		}

		return shopCondition;
	}
}
