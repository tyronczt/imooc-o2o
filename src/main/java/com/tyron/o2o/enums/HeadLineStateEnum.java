package com.tyron.o2o.enums;

/**
 * @Description: 头条状态枚举类
 *
 * @author tyronchen
 * @date 2019年1月23日
 */
public enum HeadLineStateEnum {

	EMPTY(-2001, "头条信息为空"), EDIT_ERROR(-2002, "编辑头条信息失败"), DELETE_ERROR(-2003, "删除头条信息失败");

	private int state;

	private String stateInfo;

	private HeadLineStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public static HeadLineStateEnum stateOf(int index) {
		for (HeadLineStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}

}
