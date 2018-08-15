package com.tyron.o2o.service;

import java.util.List;

import com.tyron.o2o.entity.ShopCategory;

/**
 * @Description: 店鋪类别业务接口
 *
 * @author tyronchen
 * @date 2018年5月27日
 */
public interface ShopCategoryService {

	/**
	 * 条件获取店铺类别列表
	 * 
	 * @param shopCategoryCondition
	 * @return
	 */
	List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);

}
