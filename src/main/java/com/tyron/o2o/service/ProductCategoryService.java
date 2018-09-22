/**
 * 
 */
package com.tyron.o2o.service;

import java.util.List;

import com.tyron.o2o.entity.ProductCategory;

/**
 * @Description: 店铺的商品类别业务接口
 *
 * @author: tyron
 * @date: 2018年9月21日
 */

public interface ProductCategoryService {

	/**
	 * 查询指定某个店铺下的所有商品列表信息
	 * 
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> getProductCategoryList(Long shopId);
}
