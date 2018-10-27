package com.tyron.o2o.dao;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tyron.o2o.BaseTest;
import com.tyron.o2o.entity.Product;
import com.tyron.o2o.entity.ProductCategory;
import com.tyron.o2o.entity.Shop;

/**
 * @Description: 商品测试
 *
 * @author: tyron
 * @date: 2018年10月27日
 */
public class ProductTest extends BaseTest {

	@Autowired
	private ProductDao productDao;

	@Test
	public void testInsertProduct() {
		Shop shop = new Shop();
		shop.setShopId(1L);
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryId(1L);
		Product product = new Product();
		product.setCreateTime(new Date());
		product.setEnableStatus(1);
		product.setProductName("1");
		product.setProductDesc("11");
		product.setImgAddr("111");
		product.setNormalPrice("1111");
		product.setPromotionPrice("11111");
		product.setPriority(1);
		product.setProductCategory(productCategory);
		product.setShop(shop);
		int effectNum = productDao.insertProduct(product);
		System.out.println("effectNum:" + effectNum);
	}

}
