package com.tyron.o2o.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tyron.o2o.dto.ShopCategoryExecution;
import com.tyron.o2o.entity.ShopCategory;

/**
 * @Description: 店鋪类别业务接口
 *
 * @author tyronchen
 * @date 2018年5月27日
 */
public interface ShopCategoryService {
	public final static String SC_LIST_KEY = "shopcategorylist";

	/**
	 * 条件获取店铺类别分页列表
	 * 
	 * @param shopCategoryCondition 查询条件
	 * @return
	 */
	List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);

	/**
	 * 新增商品分类
	 * 
	 * @param shopCategory
	 * @param shopCategoryImg
	 * @return
	 */
	ShopCategoryExecution addShopCategory(ShopCategory shopCategory, MultipartFile shopCategoryImg);

	/**
	 * 修改商品分类
	 * 
	 * @param shopCategory
	 * @return
	 */
	ShopCategoryExecution modifyShopCategory(ShopCategory shopCategory, MultipartFile shopCategoryImg);

	/**
	 * 根据Id查询商品分类信息
	 * 
	 * @param shopCategory
	 * @return
	 */
	ShopCategory getShopCategoryById(long shopCategoryId);

}
