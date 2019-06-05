package com.tyron.o2o.exceptions;

/**
 * @Description: 用户信息操作异常
 *
 * @author tyronchen
 * @date 2019年6月5日  
 */
public class PersonInfoOperationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PersonInfoOperationException(String msg) {
		super(msg);
	}
}