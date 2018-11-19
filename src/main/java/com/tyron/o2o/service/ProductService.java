package com.tyron.o2o.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tyron.o2o.dto.ProductExecution;
import com.tyron.o2o.entity.Product;
import com.tyron.o2o.exceptions.ProductOperationException;

/**
 * @Description: 商品业务接口
 *
 * @author: tyron
 * @date: 2018年10月27日
 */

public interface ProductService {

	/**
	 * 添加商品信息以及图片处理
	 * 
	 * @param product        商品信息
	 * @param productImg     商品缩略图
	 * @param productImgList 商品图片列表
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution addProduct(Product product, MultipartFile productImg, List<MultipartFile> productImgList)
			throws ProductOperationException;

	/**
	 * 查询商品列表并分页，可输入的条件有：商品名（模糊），商品状态，店铺Id，商品类别
	 * 
	 * @param productCondition 查询条件
	 * @param pageIndex        页码
	 * @param pageSize         每页条数
	 * @return
	 */
	ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

	/**
	 * 根据商品Id查询商品详情
	 * 
	 * @param productId 商品ID
	 * @return
	 */
	Product getProductById(long productId);

	/**
	 * 修改商品信息以及图片处理
	 * 
	 * @param product        商品信息
	 * @param productImg     商品缩略图
	 * @param productImgList 商品图片列表
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution modifyProduct(Product product, MultipartFile productImg, List<MultipartFile> productImgList)
			throws ProductOperationException;

}
