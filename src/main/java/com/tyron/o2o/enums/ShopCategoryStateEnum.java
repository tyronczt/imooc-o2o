package com.tyron.o2o.enums;

/**
 * @Description: 店铺类别状态枚举
 *
 * @author: tyron
 * @date: 2018年11月29日
 */

public enum ShopCategoryStateEnum {

	EDIT_ERROR(-2001, "店铺编辑失败"), NULL_SHOP_CATEGORY(-2002, "shopCategory信息为空");

	private int state;
	private String stateInfo;

	/**
	 * @Description:构造函数
	 * 
	 * @param state
	 * @param stateInfo
	 */
	private ShopCategoryStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	/**
	 * @Description: 通过state获取shopCategoryStateEnum, 从而可以调用ShopCategoryStateEnum
	 *               #getStateInfo()获取stateInfo
	 * 
	 * @param index
	 */
	public static ShopCategoryStateEnum stateOf(int index) {
		for (ShopCategoryStateEnum shopCategoryStateEnum : values()) {
			if (shopCategoryStateEnum.getState() == index) {
				return shopCategoryStateEnum;
			}
		}
		return null;
	}
}
