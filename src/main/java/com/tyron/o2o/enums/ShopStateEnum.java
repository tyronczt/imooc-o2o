package com.tyron.o2o.enums;

/**
 * @Description: 店铺状态枚举
 *
 * @author tyronchen
 * @date 2018年4月10日
 */
public enum ShopStateEnum {
	CHECK(0, "审核中"), PASS(1, "通过认证"), OFFLINE(-2001, "非法商铺"), EDIT_ERROR(-2002, "店铺操作失败"),
	NULL_SHOPID(-2003, "ShopId为空"), NULL_SHOP_INFO(-2004, "店铺信息为空");

	private int state;
	private String stateInfo;

	private ShopStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	// 根据传入的state值返回相应的状态值
	public static ShopStateEnum stateOf(int index) {
		for (ShopStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}
}
