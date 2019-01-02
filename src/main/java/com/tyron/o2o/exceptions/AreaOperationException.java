package com.tyron.o2o.exceptions;

/**
 * @Description: 区域操作异常
 *
 * @author tyronchen
 * @date 2018年12月15日
 */
public class AreaOperationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AreaOperationException(String msg) {
		super(msg);
	}
}
