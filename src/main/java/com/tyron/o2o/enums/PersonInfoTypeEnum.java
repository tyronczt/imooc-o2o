package com.tyron.o2o.enums;

/**
 * @Description: 用户身份状态信息
 *
 * @author tyronchen
 * @date 2019年6月2日
 */
public enum PersonInfoTypeEnum {

	CUSTOMER(1, "顾客"), OWNER(2, "店家"), ADMIN(3, "管理员");
	private int state;
	private String stateInfo;

	private PersonInfoTypeEnum(int state, String stateInfo) {
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
	public static PersonInfoTypeEnum stateOf(int index) {
		for (PersonInfoTypeEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}
}
