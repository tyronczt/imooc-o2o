package com.tyron.o2o.enums;

/**
 * @Description: 商品状态枚举
 *
 * @author: tyron
 * @date: 2018年10月27日
 */
public enum ProductStateEnum {

	SUCCESS(1, "操作成功"), INNER_ERROR(-1001, "操作失败"), NULL_PARAMETER(-1002, "缺少参数");

	private int state;
	private String stateInfo;

	/**
	 * @Description:私有构造函数,禁止外部初始化改变定义的常量
	 * 
	 * @param state
	 * @param stateInfo
	 */
	private ProductStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	/**
	 * @Description: 仅设置get方法,禁用set
	 */
	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	/**
	 * 
	 * @Description: 定义换成pulic static 暴漏给外部,通过state获取ShopStateEnum
	 *               values()获取全部的enum常量
	 * 
	 * @param state
	 * @return: ShopStateEnum
	 */
	public static ProductStateEnum stateOf(int state) {
		for (ProductStateEnum stateEnum : values()) {
			if (stateEnum.getState() == state) {
				return stateEnum;
			}
		}
		return null;
	}

}
