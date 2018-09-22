/**
 * 
 */
package com.tyron.o2o.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tyron.o2o.BaseTest;
import com.tyron.o2o.entity.ProductCategory;

/**
 * @Description: 店铺类别dao层测试
 *
 * @author: tyron
 * @date: 2018年9月21日
 */

public class ProductCategoryDaoTest extends BaseTest {

	@Autowired
	private ProductCategoryDao productCategoryDao;

	@Test
	@Ignore
	public void testQueryByShopId() throws Exception {
		long shopId = 1;
		List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
		System.out.println("该店铺的类别数：" + productCategoryList.size());
	}

	@Test
	public void testBatchInsertProductCategory() {
		ProductCategory pc1 = new ProductCategory();
		pc1.setCreateTime(new Date());
		pc1.setPriority(1);
		pc1.setShopId(1L);
		pc1.setProductCategoryName("商品类别1");
		ProductCategory pc2 = new ProductCategory();
		pc2.setCreateTime(new Date());
		pc2.setPriority(2);
		pc2.setShopId(1L);
		pc2.setProductCategoryName("商品类别2");
		List<ProductCategory> pcList = new ArrayList<>();
		pcList.add(pc1);
		pcList.add(pc2);
		int effectNum = productCategoryDao.batchInsertProductCategory(pcList);
		System.out.println(effectNum);
	}

}
