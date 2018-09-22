/**
 * 
 */
package com.tyron.o2o.dao;

import java.util.List;

import com.tyron.o2o.entity.ProductCategory;

/**
 * @Description: 产品类别数据接口
 *
 * @author: tyron
 * @date: 2018年9月21日
 */
public interface ProductCategoryDao {

	/**
	 * 根据店铺id查询店铺商品列表
	 * 
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> queryProductCategoryList(long shopId);

	/**
	 * 批量添加商品类别
	 * 
	 * @param productCategoryList 商品类别列表
	 * @return
	 */
	int batchInsertProductCategory(List<ProductCategory> productCategoryList);
}
