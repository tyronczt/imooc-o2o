/**
 * 
 */
package com.tyron.o2o.web.shopadmin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tyron.o2o.dto.ProductCategoryExecution;
import com.tyron.o2o.dto.Result;
import com.tyron.o2o.entity.ProductCategory;
import com.tyron.o2o.entity.Shop;
import com.tyron.o2o.enums.ProductCategoryStateEnum;
import com.tyron.o2o.exceptions.ProductCategoryOperationException;
import com.tyron.o2o.service.ProductCategoryService;

/**
 * @Description: 店铺类别控制层
 *
 * @author: tyron
 * @date: 2018年9月22日
 */
@RequestMapping(value = "/shopadmin")
@Controller
public class ProductCategoryController {

	@Autowired
	private ProductCategoryService productCategoryService;

	/**
	 * 根据ShopId获取productCategory
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request) {
		List<ProductCategory> productCategoryList;
		ProductCategoryStateEnum ps;
		// 在进入到
		// shop管理页面（即调用getShopManageInfo方法时）,如果shopId合法，便将该shop信息放在了session中，key为currentShop
		// 这里我们不依赖前端的传入，因为不安全。 我们在后端通过session来做
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");

		if (currentShop != null && currentShop.getShopId() != null) {
			try {
				productCategoryList = productCategoryService.getProductCategoryList(currentShop.getShopId());
				return new Result<List<ProductCategory>>(true, productCategoryList);
			} catch (Exception e) {
				e.printStackTrace();
				ps = ProductCategoryStateEnum.INNER_ERROR;
				return new Result<List<ProductCategory>>(false, ps.getState(), ps.getStateInfo());
			}
		} else {
			ps = ProductCategoryStateEnum.NULL_SHOP;
			return new Result<List<ProductCategory>>(false, ps.getState(), ps.getStateInfo());
		}
	}

	/**
	 * 添加商铺目录 ，使用@RequestBody接收前端传递过来的productCategoryList
	 * 
	 * @param productCategoryList
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addproductcategorys", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList,
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 列表不为空
		if (productCategoryList != null && !productCategoryList.isEmpty()) {
			// 从session中获取店铺信息，尽量减少对前端的依赖
			Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
			if (currentShop != null && currentShop.getShopId() != null) {
				for (ProductCategory productCategory : productCategoryList) {
					productCategory.setShopId(currentShop.getShopId());
					productCategory.setCreateTime(new Date());
				}
			}
			try {
				// 批量插入
				ProductCategoryExecution productCategoryExecution = productCategoryService
						.batchAddProductCategory(productCategoryList);
				if (productCategoryExecution.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
					// 同时也将新增成功的数量返回给前台
					modelMap.put("effectNum", productCategoryExecution.getCount());
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", productCategoryExecution.getStateInfo());
				}
			} catch (ProductCategoryOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "至少输入一个店铺目录信息");
		}

		return modelMap;
	}

}
