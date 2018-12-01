package com.tyron.o2o.enums;

/**
 * @Description: 操作状态枚举
 *
 * @author tyronchen
 * @date 2018年11月29日
 */
public enum OperationStatusEnum {

	SUCCESS(1, "操作成功"), ERROR(-1001, "操作失败"), VERIFYCODE_ERROR(-1002, "验证码输入有误，请重新输入"), PIC_EMPTY(-1003, "上传图片不能为空"),
	PIC_UPLOAD_ERROR(-1004, "创建图片失败"), PAGIN_EMPTY(-1005, "缺少分页参数");

	private int state;
	private String stateInfo;

	private OperationStatusEnum(int state, String stateInfo) {
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
	public static OperationStatusEnum stateOf(int index) {
		for (OperationStatusEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}
}
