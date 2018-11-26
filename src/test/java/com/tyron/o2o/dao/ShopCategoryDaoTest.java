package com.tyron.o2o.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tyron.o2o.BaseTest;
import com.tyron.o2o.entity.ShopCategory;

/**
 * @Description: 店铺类别测试
 *
 * @author tyronchen
 * @date 2018年5月27日
 */
public class ShopCategoryDaoTest extends BaseTest {

	@Autowired
	private ShopCategoryDao shopCategoryDao;

	@Test
	@Ignore
	public void testQueryShopCategory() {
		List<ShopCategory> shopCategoryList = shopCategoryDao.selectShopCategory(new ShopCategory());
		System.out.println(shopCategoryList.size());
		ShopCategory testCategory = new ShopCategory();
		ShopCategory parentCategory = new ShopCategory();
		parentCategory.setShopCategoryId(1L);
		testCategory.setParent(parentCategory);
		shopCategoryList = shopCategoryDao.selectShopCategory(testCategory);
		System.out.println(shopCategoryList.get(0).getShopCategoryName());
	}

	@Test
	public void testQueryShopCategory2() {
		List<ShopCategory> shopCategoryList = shopCategoryDao.selectShopCategory(null);
		System.out.println("shopCategoryList.size():"+ shopCategoryList.size());
	}
}
