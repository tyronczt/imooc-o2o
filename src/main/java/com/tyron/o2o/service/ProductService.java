package com.tyron.o2o.service;

import com.tyron.o2o.dto.ProductExecution;
import com.tyron.o2o.exceptions.ProductOperationException;

/**
 * @Description: 商品业务接口
 *
 * @author: tyron
 * @date: 2018年10月27日
 */

public interface ProductService {

	/**
	 *  添加商品信息
	 * 
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution addProduct() throws ProductOperationException;

}
