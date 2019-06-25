package com.tyron.o2o.web.shopadmin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyron.o2o.dto.ShopExecution;
import com.tyron.o2o.entity.Area;
import com.tyron.o2o.entity.PersonInfo;
import com.tyron.o2o.entity.Shop;
import com.tyron.o2o.entity.ShopCategory;
import com.tyron.o2o.enums.OperationStatusEnum;
import com.tyron.o2o.enums.ShopStateEnum;
import com.tyron.o2o.service.AreaService;
import com.tyron.o2o.service.ShopCategoryService;
import com.tyron.o2o.service.ShopService;
import com.tyron.o2o.util.CodeUtil;
import com.tyron.o2o.util.HttpServletRequestUtil;

/**
 * @Description: 店铺操作控制器
 *
 * @author tyronchen
 * @date 2018年4月15日
 */
@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {

	@Autowired
	private ShopService shopService;

	@Autowired
	private ShopCategoryService shopCategoryService;

	@Autowired
	private AreaService areaService;

	/**
	 * 店铺信息初始化：店铺区域和店铺类别
	 * 
	 * @return
	 */
	@RequestMapping(value = "getshopinitinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getShopInitInfo() {
		Map<String, Object> modelMap = new HashMap<>();
		List<Area> areaList = new ArrayList<>();
		List<ShopCategory> shopCategoryList = new ArrayList<>();
		try {
			shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
			areaList = areaService.getAreaList();
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errorMsg", e.getMessage());
		}
		return modelMap;
	}

	/**
	 * 注册店铺
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/registershop", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> registerShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 校验验证码
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", OperationStatusEnum.VERIFYCODE_ERROR.getStateInfo());
			return modelMap;
		}

		// 1、接受并转化相应参数，包括店铺信息及图片信息
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		// 使用jackson-databind-->https://github.com/FasterXML/jackson-databind将json转换为pojo
		ObjectMapper mapper = new ObjectMapper(); // create once, reuse（创建一次，可重用）
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}

		// 获取图片文件流
		MultipartHttpServletRequest multipartRequest = null;
		MultipartFile shopImg = null;
		MultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			multipartRequest = (MultipartHttpServletRequest) request;
			shopImg = (MultipartFile) multipartRequest.getFile("shopImg");
		}
		if (shopImg == null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", OperationStatusEnum.PIC_EMPTY.getStateInfo());
			return modelMap;
		}

		// 2、注册店铺，尽量不要依靠前端信息
		if (shop != null) {
			PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
			shop.setOwner(owner);
			ShopExecution se = shopService.addShop(shop, shopImg);
			if (se.getState() == ShopStateEnum.CHECK.getState()) {
				modelMap.put("success", true);

				// 注册成功，将店铺列表存入到session中
				@SuppressWarnings("unchecked")
				List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
				if (shopList == null || shopList.isEmpty()) {
					shopList = new ArrayList<>();
				}
				shopList.add(shop);
				request.getSession().setAttribute("shopList", shopList);

			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", se.getStateInfo());
			}
			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", ShopStateEnum.NULL_SHOP_INFO.getStateInfo());
			return modelMap;
		}
	}

	/**
	 * 根据id获取店铺信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getShopById(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 获取shopid
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if (shopId > -1) {
			try {
				Shop shop = shopService.getByShopId(shopId);
				modelMap.put("shop", shop);
				// 获取区域列表
				List<Area> areaList = areaService.getAreaList();
				modelMap.put("areaList", areaList);
				modelMap.put("success", true);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", ShopStateEnum.NULL_SHOPID.getStateInfo());
		}
		return modelMap;
	}

	/**
	 * 修改店铺
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modifyShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 校验验证码
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", OperationStatusEnum.VERIFYCODE_ERROR.getStateInfo());
			return modelMap;
		}

		// 1、接收并转化相应参数，包括店铺信息及图片信息
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper(); // create once, reuse（创建一次，可重用）
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}

		// 获取图片文件流
		MultipartHttpServletRequest multipartRequest = null;
		MultipartFile shopImg = null;
		MultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			multipartRequest = (MultipartHttpServletRequest) request;
			shopImg = (MultipartFile) multipartRequest.getFile("shopImg");
		}

		// 2、修改店铺，尽量不要依靠前端信息
		if (shop != null && shop.getShopId() > 0) {
			ShopExecution se = shopService.modifyShop(shop, shopImg);
			if (se.getState() == OperationStatusEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", se.getStateInfo());
			}
			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", ShopStateEnum.NULL_SHOPID.getStateInfo());
			return modelMap;
		}
	}

	/**
	 * 获取店铺管理信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getshopmanageInfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getShopManagementInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		// 如果shopId不存在
		if (shopId <= 0) {
			// 从session中获取店铺信息
			Object currentShopObj = request.getSession().getAttribute("currentShop");
			if (currentShopObj == null) {
				// 如果session中没有店铺信息，重定向回店铺列表页面
				modelMap.put("redirect", true);
				modelMap.put("url", "/o2o/shopadmin/getshoplist");
			} else {
				Shop currentShop = (Shop) currentShopObj;
				modelMap.put("redirect", false);
				modelMap.put("shopId", currentShop.getShopId());
			}
		} else {
			Shop currentShop = new Shop();
			currentShop.setShopId(shopId);
			request.getSession().setAttribute("currentShop", currentShop);
			modelMap.put("redirect", false);
		}
		return modelMap;
	}

	/**
	 * 获取店铺列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getShopList(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
		try {
			Shop shopCondition = new Shop();
			shopCondition.setOwner(user);
			ShopExecution shopExecution = shopService.getShopList(shopCondition, 1, 100);
			modelMap.put("shopList", shopExecution.getShopList());
			// 列出店铺成功之后，将店铺放入session中作为权限验证依据，即该帐号只能操作它自己的店铺
			request.getSession().setAttribute("shopList", shopExecution.getShopList());
			modelMap.put("user", user);
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}

	/**
	 * 视频教程中的私有方法
	 */
	public static void inputStreamToFile(InputStream ins, File file) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			while ((bytesRead = ins.read(buffer)) != -1) {
				fos.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			throw new RuntimeException("调用inputStreamToFile产生异常：" + e.getMessage());
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
				if (ins != null) {
					ins.close();
				}
			} catch (IOException e) {
				throw new RuntimeException("inputStreamToFile关闭io产生异常：" + e.getMessage());
			}
		}
	}

}
