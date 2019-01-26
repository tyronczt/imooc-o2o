package com.tyron.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tyron.o2o.entity.ShopCategory;

/**
 * @Description: 店铺类别数据接口
 *
 * @author tyronchen
 * @date 2018年5月27日
 */
public interface ShopCategoryDao {

	/**
	 * 根据查询条件获取分页列表 
	 * 需求：1、首页展示一级目录（即parent_id 为 null的店铺类别） 
	 *    2、点进去某个一级目录加载对应目录下的子目录
	 *    3、店铺只能挂在店铺二级类别下
	 *    4、在首页点击某个一级店铺目录 进入店铺展示页面的时候 需要加载对应目录下的子目录
	 * 
	 * @param shopCategoryCondition 查询条件
	 * @return
	 */
	List<ShopCategory> selectShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);

	/**
	 * 新增商品分类
	 * 
	 * @param shopCategory
	 * @return
	 */
	int insertShopCategory(ShopCategory shopCategory);

	/**
	 * 修改商品分类
	 * 
	 * @param shopCategory
	 * @return
	 */
	int updateShopCategory(ShopCategory shopCategory);

	/**
	 * 根据Id查询商品分类信息
	 * 
	 * @param shopCategoryId
	 * @return
	 */
	ShopCategory selectShopCategoryById(long shopCategoryId);

}
