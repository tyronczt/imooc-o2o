package com.tyron.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tyron.o2o.entity.ShopCategory;

/**
 * @Description: 店铺类别数据接口
 *
 * @author tyronchen
 * @date 2018年5月27日
 */
public interface ShopCategoryDao {

	/**
	 * 根据查询条件获取列表
	 * 
	 * @param shopCategoryCondition
	 * @return
	 */
	List<ShopCategory> selectShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);

}
