package com.tyron.o2o.dto;

import java.util.List;

import com.tyron.o2o.entity.ShopCategory;
import com.tyron.o2o.enums.OperationStatusEnum;
import com.tyron.o2o.enums.ShopCategoryStateEnum;

/**
 * @Description: 店铺类别类别返回结果信息
 *
 * @author tyronchen
 * @date 2018年11月29日
 */
public class ShopCategoryExecution {

	// 结果状态
	private int state;

	// 状态标识
	private String stateInfo;

	// 操作的shopCategory（增删改店铺类别的时候用）
	private ShopCategory shopCategory;

	// 获取的shopCategory列表(查询店铺类别列表的时候用)
	private List<ShopCategory> shopCategoryList;

	public ShopCategoryExecution() {
	}

	// 店铺类别操作失败的时候使用的构造器
	public ShopCategoryExecution(ShopCategoryStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// 店铺类别操作成功的时候使用的构造器,基本操作
	public ShopCategoryExecution(OperationStatusEnum stateEnum, ShopCategory shopCategory) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shopCategory = shopCategory;
	}

	// 店铺类别操作成功的时候使用的构造器
	public ShopCategoryExecution(OperationStatusEnum stateEnum, List<ShopCategory> shopCategoryList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shopCategoryList = shopCategoryList;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public ShopCategory getShopCategory() {
		return shopCategory;
	}

	public void setShopCategory(ShopCategory shopCategory) {
		this.shopCategory = shopCategory;
	}

	public List<ShopCategory> getShopCategoryList() {
		return shopCategoryList;
	}

	public void setShopCategoryList(List<ShopCategory> shopCategoryList) {
		this.shopCategoryList = shopCategoryList;
	}

}