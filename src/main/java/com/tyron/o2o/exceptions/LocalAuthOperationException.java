package com.tyron.o2o.exceptions;

/**
 * @Description: 本地账号操作异常
 *
 * @author tyronchen
 * @date 2019年5月20日  
 */
public class LocalAuthOperationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LocalAuthOperationException(String msg) {
		super(msg);
	}
}