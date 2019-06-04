package com.tyron.o2o.enums;

/**
 * @Description: 用户可用状态信息
 *
 * @author tyronchen
 * @date 2019年6月2日
 */
public enum PersonInfoStatusEnum {

	ALLOW(1, "允许"), NOT_ALLOW(0, "不允许");
	private int state;
	private String stateInfo;

	private PersonInfoStatusEnum(int state, String stateInfo) {
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
	public static PersonInfoStatusEnum stateOf(int index) {
		for (PersonInfoStatusEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}
}
