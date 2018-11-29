package com.tyron.o2o.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tyron.o2o.dao.ShopCategoryDao;
import com.tyron.o2o.dto.ShopCategoryExecution;
import com.tyron.o2o.entity.ShopCategory;
import com.tyron.o2o.enums.OperationStatusEnum;
import com.tyron.o2o.enums.ShopCategoryStateEnum;
import com.tyron.o2o.exceptions.ShopCategoryOperationException;
import com.tyron.o2o.service.ShopCategoryService;
import com.tyron.o2o.util.ImageUtil;
import com.tyron.o2o.util.PageCalculator;
import com.tyron.o2o.util.PathUtil;

/**
 * @Description: 店鋪类别业务接口实现类
 *
 * @author tyronchen
 * @date 2018年5月27日
 */
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

	@Autowired
	private ShopCategoryDao shopCategoryDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tyron.o2o.service.ShopCategoryService#getShopCategoryList(com.tyron.o2o.
	 * entity.ShopCategory)
	 */
	@Override
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition, int pageIndex, int pageSize) {
		// 前台页面插入的pageIndex（第几页）， 而dao层是使用 rowIndex （第几行） ，所以需要转换一下
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		return shopCategoryDao.selectShopCategory(shopCategoryCondition, rowIndex, pageSize);
	}

	@Override
	public ShopCategoryExecution addShopCategory(ShopCategory shopCategory, MultipartFile shopCategoryImg) {
		// 空值判断
		if (shopCategory == null) {
			return new ShopCategoryExecution(ShopCategoryStateEnum.NULL_SHOP_CATEGORY);
		} else {
			// 初始化赋值
			shopCategory.setCreateTime(new Date());
			// 店铺图片不为空
			if (shopCategoryImg != null) {
				addShopCategoryImg(shopCategory, shopCategoryImg);
			}
			try {
				int effectNum = shopCategoryDao.insertShopCategory(shopCategory);
				if (effectNum <= 0) {
					throw new ShopCategoryOperationException(ShopCategoryStateEnum.EDIT_ERROR.getStateInfo());
				}
			} catch (Exception e) {
				throw new ShopCategoryOperationException(
						ShopCategoryStateEnum.EDIT_ERROR.getStateInfo() + e.getMessage());
			}
			return new ShopCategoryExecution(OperationStatusEnum.SUCCESS, shopCategory);
		}
	}

	@Override
	public ShopCategoryExecution modifyShopCategory(ShopCategory shopCategory, MultipartFile shopCategoryImg) {
		// 空值判断
		if (shopCategory != null && shopCategory.getShopCategoryId() != null) {
			// 设置默认更新时间
			shopCategory.setLastEditTime(new Date());
			// 若图不为空且原有图不为空，则先删除原有图并添加
			if (shopCategoryImg != null) {
				// 获取原来商品类别图片并删除
				ShopCategory origShopCategory = shopCategoryDao
						.selectShopCategoryById(shopCategory.getShopCategoryId());
				if (origShopCategory.getShopCategoryImg() != null) {
					ImageUtil.deleteFileOrPath(origShopCategory.getShopCategoryImg());
				}
				addShopCategoryImg(shopCategory, shopCategoryImg);
			}
			// 更新信息
			try {
				int effectNum = shopCategoryDao.updateShopCategory(shopCategory);
				if (effectNum <= 0) {
					throw new ShopCategoryOperationException(ShopCategoryStateEnum.EDIT_ERROR.getStateInfo());
				}
			} catch (Exception e) {
				throw new ShopCategoryOperationException(
						ShopCategoryStateEnum.EDIT_ERROR.getStateInfo() + e.getMessage());
			}
			return new ShopCategoryExecution(OperationStatusEnum.SUCCESS, shopCategory);
		} else {
			return new ShopCategoryExecution(ShopCategoryStateEnum.NULL_SHOP_CATEGORY);
		}
	}

	@Override
	public ShopCategory getShopCategoryById(long shopCategoryId) {
		return shopCategoryDao.selectShopCategoryById(shopCategoryId);
	}

	/**
	 * 添加店铺类别图片
	 * 
	 * @param shopCategory    店铺类别实体类
	 * @param shopCategoryImg 店铺类别图片
	 */
	private void addShopCategoryImg(ShopCategory shopCategory, MultipartFile shopCategoryImg) {
		// 获取图片存储路径，将图片放在相应店铺类别的文件夹下
		String dest = PathUtil.getShopCategoryImagePath();
		String generateShopCategoryImg = ImageUtil.generateShopCategoryImg(shopCategoryImg, dest);
		shopCategory.setShopCategoryImg(generateShopCategoryImg);
	}
}