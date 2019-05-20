package com.tyron.o2o.enums;

/**
 * @Description: 本地账号信息操作枚举类
 *
 * @author tyronchen
 * @date 2019年5月20日
 */
public enum LocalAuthStateEnum {
	LOGINFAIL(-1, "密码或帐号输入有误"), NULL_AUTH_INFO(-1006, "注册信息为空");

	private int state;

	private String stateInfo;

	private LocalAuthStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public static LocalAuthStateEnum stateOf(int index) {
		for (LocalAuthStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}

}
