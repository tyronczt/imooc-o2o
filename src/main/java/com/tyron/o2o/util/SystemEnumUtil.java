package com.tyron.o2o.util;

/**
 * @Description: 系统枚举类
 *
 * @author: tyron
 * @date: 2018年11月26日
 */

public class SystemEnumUtil {

	/**
	 * @Description: 使用状态
	 *
	 * @author: tyron
	 * @date: 2018年11月26日
	 */
	public enum ENABLE_STATUS {
	USABLE(0, "不可用"), UNUSABLE(1, "可用"), CHECKED(2, "审核中");
		private Integer value;
		private String label;

		ENABLE_STATUS(Integer value, String label) {
			this.value = value;
			this.label = label;
		}

		public Integer getValue() {
			return value;
		}

		public String getLabel() {
			return label;
		}

		public static ENABLE_STATUS parse(Integer value) {
			for (ENABLE_STATUS type : ENABLE_STATUS.values()) {
				if (type.value.equals(value)) {
					return type;
				}
			}
			return null;
		}
	}
}
