package com.tyron.o2o.service.impl;

import org.springframework.stereotype.Service;

import com.tyron.o2o.dto.ProductExecution;
import com.tyron.o2o.exceptions.ProductOperationException;
import com.tyron.o2o.service.ProductService;

/**
 * @Description: 商品业务接口实现
 *
 * @author: tyron
 * @date: 2018年10月27日
 */
@Service
public class ProductServiceImpl implements ProductService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tyron.o2o.service.ProductService#addProduct()
	 */
	@Override
	public ProductExecution addProduct() throws ProductOperationException {
		return null;
	}

}
