package com.tyron.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tyron.o2o.entity.Product;

/**
 * @Description: 商品数据接口
 *
 * @author: tyron
 * @date: 2018年10月26日
 */

public interface ProductDao {

	/**
	 * 查询商品列表并分页，输入条件：商品名（模糊），商品状态，店铺Id，商品类别
	 * 
	 * @param productCondition 查询条件
	 * @param rowIndex        行数
	 * @param pageSize        每页数
	 * @return
	 */
	List<Product> queryProductList(@Param("productCondition") Product productCondition, @Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize);

	/**
	 * 根据条件查询商品总数
	 * 
	 * @param productCondition 查询条件
	 * @return
	 */
	int queryProductCount(@Param("productCondition") Product productCondition);

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

	/**
	 * 更新商品信息
	 * 
	 * @param product
	 * @return
	 */
	int updateProduct(Product product);

}
