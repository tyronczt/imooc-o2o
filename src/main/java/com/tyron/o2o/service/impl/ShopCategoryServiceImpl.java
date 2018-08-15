package com.tyron.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyron.o2o.dao.ShopCategoryDao;
import com.tyron.o2o.entity.ShopCategory;
import com.tyron.o2o.service.ShopCategoryService;

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
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
		return shopCategoryDao.queryShopCategory(shopCategoryCondition);
	}

}
