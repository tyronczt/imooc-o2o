package com.tyron.o2o.dao;

import java.util.List;

import com.tyron.o2o.entity.ProductImg;

/**
 * @Description: 商品图片数据接口
 *
 * @author: tyron
 * @date: 2018年10月27日
 */

public interface ProductImgDao {

	/**
	 * 批量插入商品图片
	 * 
	 * @param productImgList 商品图片列表
	 * @return
	 */
	int batchInsertProductImg(List<ProductImg> productImgList);
}
