package com.tyron.o2o.enums;

/**
 * @Description: 用户信息操作枚举类
 *
 * @author tyronchen
 * @date 2019年6月5日
 */
public enum PersonInfoStateEnum {
	NULL_PERSON_INFO(-1001, "注册信息为空");

	private int state;

	private String stateInfo;

	private PersonInfoStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public static PersonInfoStateEnum stateOf(int index) {
		for (PersonInfoStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}

}
