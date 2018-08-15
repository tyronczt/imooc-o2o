package com.tyron.o2o.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tyron.o2o.dao.ShopDao;
import com.tyron.o2o.dto.ShopExecution;
import com.tyron.o2o.entity.Shop;
import com.tyron.o2o.enums.EnableStatusEnum;
import com.tyron.o2o.enums.ShopStateEnum;
import com.tyron.o2o.exceptions.ShopOperationException;
import com.tyron.o2o.service.ShopService;
import com.tyron.o2o.util.ImageUtil;
import com.tyron.o2o.util.PathUtil;

/**
 * @Description: 店铺接口实现类
 *
 * @author tyronchen
 * @date 2018年4月13日
 */
@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopDao shopDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tyron.o2o.service.ShopService#addShop(com.tyron.o2o.entity.Shop,
	 * java.io.File)
	 */
	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, MultipartFile shopImg) {
		// 空置判断
		if (shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP_INFO);
		} else {
			try {
				// 初始化赋值
				shop.setCreateTime(new Date());
				shop.setLastEditTime(new Date());
				shop.setEnableStatus(EnableStatusEnum.CHECK.getState());
				// 添加店铺信息
				int effectedNum = shopDao.insertShop(shop);
				// 添加店铺失败
				if (effectedNum <= 0) {
					throw new ShopOperationException("添加店铺失败");
				} else {
					try {
						// 空值判断
						if (shopImg == null) {
							throw new ShopOperationException("图片不存在");
						} else {
							// 存储图片
							addImage(shop, shopImg);
							effectedNum = shopDao.updateShop(shop);
							if (effectedNum <= 0) {
								throw new ShopOperationException("创建图片地址失败");
							}
						}
					} catch (Exception e) {
						throw new ShopOperationException("addShopImg error" + e.getMessage());
					}
				}
			} catch (Exception e) {
				throw new ShopOperationException("addShop error" + e.getMessage());
			}
			return new ShopExecution(ShopStateEnum.CHECK, shop);
		}
	}

	@Override
	public Shop getByShopId(long shopId) {
		return shopDao.queryByShopId(shopId);
	}

	@Override
	public ShopExecution modifyShop(Shop shop, MultipartFile shopImg) {
		// 判断店铺是否存在
		if (shop == null || shop.getShopId() == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP_INFO);
		} else {
			try {
				// 判断是否要处理照片
				if (shopImg != null) {
					Shop tempShop = shopDao.queryByShopId(shop.getShopId());
					if (tempShop.getShopImg() != null) {
						// 删除原先图片
						ImageUtil.deleteFileOrPath(tempShop.getShopImg());
					}
					// 添加新照片
					addImage(shop, shopImg);
				}
				// 更新照片信息
				shop.setLastEditTime(new Date());
				int effectNum = shopDao.updateShop(shop);
				// 更新成功
				if (effectNum > 0) {
					shop = shopDao.queryByShopId(shop.getShopId());
					return new ShopExecution(ShopStateEnum.SUCCESS, shop);
				} else {
					return new ShopExecution(ShopStateEnum.INNER_ERROR);
				}
			} catch (Exception e) {
				throw new ShopOperationException("modifyShop error" + e.getMessage());
			}
		}
	}

	/**
	 * 存储图片
	 * 
	 * @param shop
	 * @param shopImg
	 */
	private void addImage(Shop shop, MultipartFile shopImg) {
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(shopImg, dest);
		// 将图片路径存储用于更新店铺信息
		shop.setShopImg(shopImgAddr);
	}

}
