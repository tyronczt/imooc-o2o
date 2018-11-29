package com.tyron.o2o.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tyron.o2o.dao.ShopDao;
import com.tyron.o2o.dto.ShopExecution;
import com.tyron.o2o.entity.Shop;
import com.tyron.o2o.enums.EnableStatusEnum;
import com.tyron.o2o.enums.OperationStatusEnum;
import com.tyron.o2o.enums.ShopStateEnum;
import com.tyron.o2o.exceptions.ShopOperationException;
import com.tyron.o2o.service.ShopService;
import com.tyron.o2o.util.ImageUtil;
import com.tyron.o2o.util.PageCalculator;
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

	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) throws ShopOperationException {
		// 前台页面插入的pageIndex（第几页）， 而dao层是使用 rowIndex （第几行） ，所以需要转换一下
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Shop> shopList = new ArrayList<Shop>();
		ShopExecution se = new ShopExecution();
		// 查询带有分页的shopList
		shopList = shopDao.selectShopList(shopCondition, rowIndex, pageSize);
		// 查询符合条件的shop总数
		int count = shopDao.selectShopCount(shopCondition);
		// 将shopList和 count设置到se中，返回给控制层
		if (shopList != null) {
			se.setShopList(shopList);
			se.setCount(count);
		} else {
			se.setState(ShopStateEnum.EDIT_ERROR.getState());
		}
		return se;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tyron.o2o.service.ShopService#addShop(com.tyron.o2o.entity.Shop,
	 * java.io.File)
	 */
	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, MultipartFile shopImg) {
		// 空值判断
		if (shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP_INFO);
		} else {
			try {
				// 初始化赋值
				shop.setCreateTime(new Date());
				shop.setEnableStatus(EnableStatusEnum.CHECK.getState());
				// 添加店铺信息
				int effectedNum = shopDao.insertShop(shop);
				// 添加店铺失败
				if (effectedNum <= 0) {
					throw new ShopOperationException(ShopStateEnum.EDIT_ERROR.getStateInfo());
				} else {
					try {
						// 空值判断
						if (shopImg == null) {
							throw new ShopOperationException(OperationStatusEnum.PIC_EMPTY.getStateInfo());
						} else {
							// 存储图片
							addImage(shop, shopImg);
							effectedNum = shopDao.updateShop(shop);
							if (effectedNum <= 0) {
								throw new ShopOperationException(OperationStatusEnum.PIC_UPLOAD_ERROR.getStateInfo());
							}
						}
					} catch (Exception e) {
						throw new ShopOperationException(ShopStateEnum.EDIT_ERROR.getStateInfo() + e.getMessage());
					}
				}
			} catch (Exception e) {
				throw new ShopOperationException(ShopStateEnum.EDIT_ERROR.getStateInfo() + e.getMessage());
			}
			return new ShopExecution(ShopStateEnum.CHECK, shop);
		}
	}

	@Override
	public Shop getByShopId(long shopId) {
		return shopDao.selectByShopId(shopId);
	}

	@Override
	@Transactional
	public ShopExecution modifyShop(Shop shop, MultipartFile shopImg) {
		// 判断店铺是否存在
		if (shop == null || shop.getShopId() == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP_INFO);
		} else {
			try {
				// 判断是否要处理照片
				if (shopImg != null) {
					Shop tempShop = shopDao.selectByShopId(shop.getShopId());
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
					shop = shopDao.selectByShopId(shop.getShopId());
					return new ShopExecution(OperationStatusEnum.SUCCESS, shop);
				} else {
					return new ShopExecution(ShopStateEnum.EDIT_ERROR);
				}
			} catch (Exception e) {
				throw new ShopOperationException(ShopStateEnum.EDIT_ERROR.getStateInfo() + e.getMessage());
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
