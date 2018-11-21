package com.tyron.o2o.dao;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
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
public class ProductDaoTest extends BaseTest {

	@Autowired
	private ProductDao productDao;

	@Test
	@Ignore
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

	@Test
	@Ignore
	public void testQueryProductByProductId() throws Exception {
		Long productId = 1L;
		Product product = productDao.queryProductByProductId(productId);
		System.out.println("productImgSize：" + product.getProductImgList().size());
	}

	@Test
	@Ignore
	public void testUpdateProduct() throws Exception {
		Product product = new Product();
		product.setProductId(1L);
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryId(1L);
		Shop shop = new Shop();
		shop.setShopId(1L);
		product.setShop(shop);
		product.setProductCategory(productCategory);
		product.setProductName("测试修改商品");
		int effectNum = productDao.updateProduct(product);
		System.out.println("effectNum:" + effectNum);
	}

	@Test
	public void testQueryProductList() throws Exception {
		Product productCondition = new Product();
		// 分页查询
		List<Product> list = productDao.queryProductList(productCondition, 0, 5);
		System.out.println("list.size:" + list.size());
		// 查询总数
		int productCount = productDao.queryProductCount(productCondition);
		System.out.println("productCount:" + productCount);

		// 使用条件查询
		productCondition.setProductName("秋");
		// 条件分页查询
		List<Product> conditionList = productDao.queryProductList(productCondition, 0, 5);
		System.out.println("conditionList.size:" + conditionList.size());
		// 条件查询总数
		int conditionProductCount = productDao.queryProductCount(productCondition);
		System.out.println("conditionProductCount:" + conditionProductCount);
	}

}
