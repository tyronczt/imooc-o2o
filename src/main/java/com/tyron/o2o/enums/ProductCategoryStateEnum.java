/**
 * 
 */
package com.tyron.o2o.enums;

/**
 * @Description: 店铺类别状态枚举
 *
 * @author: tyron
 * @date: 2018年9月22日
 */

public enum ProductCategoryStateEnum {

	SUCCESS(1, "操作成功"), INNER_ERROR(-1001, "操作失败"), NULL_SHOP(-1002, "Shop信息为空"), EMPETY_LIST(-1003, "请输入商品目录信息");

	private int state;
	private String stateInfo;

	/**
	 * 
	 * 
	 * @Title:ProductCategoryStateEnum
	 * 
	 * @Description:构造函数
	 * 
	 * @param state
	 * @param stateInfo
	 */
	private ProductCategoryStateEnum(int state, String stateInfo) {
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
	 * 
	 * 
	 * @Title: stateOf
	 * 
	 * @Description: 通过state获取productCategoryStateEnum,从而可以调用ProductCategoryStateEnum
	 *               #getStateInfo()获取stateInfo
	 * 
	 * @param index
	 * @return
	 * 
	 * @return: ProductCategoryStateEnum
	 */
	public static ProductCategoryStateEnum stateOf(int index) {
		for (ProductCategoryStateEnum productCategoryStateEnum : values()) {
			if (productCategoryStateEnum.getState() == index) {
				return productCategoryStateEnum;
			}
		}
		return null;
	}

}
