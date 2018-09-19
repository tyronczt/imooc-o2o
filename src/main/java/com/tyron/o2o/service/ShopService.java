package com.tyron.o2o.service;

import org.springframework.web.multipart.MultipartFile;

import com.tyron.o2o.dto.ShopExecution;
import com.tyron.o2o.entity.Shop;
import com.tyron.o2o.exceptions.ShopOperationException;

/**
 * @Description: 店铺业务接口
 *
 * @author tyronchen
 * @date 2018年4月13日
 */
public interface ShopService {

	/**
	 * 根据id查询店铺详情
	 * 
	 * @param shopId
	 * @return
	 */
	Shop getByShopId(long shopId);

	/**
	 * 添加店铺
	 * 
	 * @param shop
	 * @param shopImg
	 * @return
	 */
	ShopExecution addShop(Shop shop, MultipartFile shopImg) throws ShopOperationException;

	/**
	 * 修改店铺
	 * 
	 * @param shop
	 * @param shopImg
	 * @return
	 */
	ShopExecution modifyShop(Shop shop, MultipartFile shopImg) throws ShopOperationException;

	/**
	 * 
	 * @param shopCondition
	 * @param pageIndex：前端页面:第几页
	 *            定义为pageIndex
	 * @param pageSize：展示的行数
	 * @throws ShopOperationException
	 * 
	 */
	ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) throws ShopOperationException;
}
