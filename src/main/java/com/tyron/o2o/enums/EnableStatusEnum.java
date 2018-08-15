package com.tyron.o2o.enums;

/**
 * @Description: 使用状态枚举
 *
 * @author tyronchen
 * @date 2018年4月15日
 */
public enum EnableStatusEnum {
	CHECK(0, "审核中"), UNAVAILABLE(-1, "不可用"), AVAILABLE(1, "可用");
	private int state;

	private String stateInfo;

	private EnableStatusEnum(int state, String stateInfo) {
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
	public static EnableStatusEnum stateOf(int index) {
		for (EnableStatusEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}
}
