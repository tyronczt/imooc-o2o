package com.tyron.o2o.dao;

import com.tyron.o2o.entity.Product;

/**
 * @Description: 商品数据接口
 *
 * @author: tyron
 * @date: 2018年10月26日
 */

public interface ProductDao {

	/**
	 * 插入商品
	 * 
	 * @param product
	 * @return
	 */
	int insertProduct(Product product);

	/**
	 * 根据商品Id查询商品详情
	 * 
	 * @param productId
	 * @return
	 */
	Product queryProductByProductId(long productId);

}
