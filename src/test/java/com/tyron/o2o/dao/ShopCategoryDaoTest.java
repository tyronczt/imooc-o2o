package com.tyron.o2o.dao;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.tyron.o2o.BaseTest;
import com.tyron.o2o.entity.ShopCategory;
import com.tyron.o2o.util.ImageUtil;
import com.tyron.o2o.util.PathUtil;

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
	public void testInsertShopCategory() throws Exception {
		ShopCategory shopCategory = new ShopCategory();
		shopCategory.setCreateTime(new Date());
		shopCategory.setPriority(6);
		shopCategory.setShopCategoryName("箱包");
		shopCategory.setShopCategoryDesc("冬尚潮包 暖心购");
		String filePath = "D:\\eclipse\\pic\\箱包.png";
		MultipartFile multipartFile = ImageUtil.path2MultipartFile(filePath);
		String dest = PathUtil.getHeadLineImagePath();
		String generateHeadImg = ImageUtil.generateShopCategoryImg(multipartFile, dest);
		shopCategory.setShopCategoryImg(generateHeadImg);
		shopCategory.setParent(null);
		int effectNum = shopCategoryDao.insertShopCategory(shopCategory);
		System.out.println("effectNum:" + effectNum);
	}

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
	@Ignore
	public void testQueryShopCategory2() {
		List<ShopCategory> shopCategoryList = shopCategoryDao.selectShopCategory(null);
		System.out.println("shopCategoryList.size():" + shopCategoryList.size());
	}
}
