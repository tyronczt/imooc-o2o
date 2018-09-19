package com.tyron.o2o.util;

/**
 * @Description: 分页计算工具类
 *
 * @author tyronchen
 * @date 2018年4月15日
 */
public class PageCalculator {

	public static int calculateRowIndex(int pageIndex, int pageSize) {
		return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
	}
}
