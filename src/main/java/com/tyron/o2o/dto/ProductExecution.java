package com.tyron.o2o.dto;

import java.util.List;

import com.tyron.o2o.entity.Product;
import com.tyron.o2o.enums.OperationStatusEnum;
import com.tyron.o2o.enums.ProductStateEnum;

/**
 * 
 * @Description: 商品返回结果信息
 *
 * @author: tyron
 * @date: 2018年10月27日
 */
public class ProductExecution {

	// 结果状态
	private int state;

	// 状态标识
	private String stateInfo;

	// 商品数量
	private int count;

	// 操作的Product（增删改商品的时候用）
	private Product Product;

	// 获取的Product列表(查询商品列表的时候用)
	private List<Product> ProductList;

	public ProductExecution() {
	}

	// 商品操作失败的时候使用的构造器
	public ProductExecution(ProductStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// 商品操作成功的时候使用的构造器
	public ProductExecution(OperationStatusEnum stateEnum, Product Product) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.Product = Product;
	}

	// 商品操作成功的时候使用的构造器
	public ProductExecution(OperationStatusEnum stateEnum, List<Product> ProductList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.ProductList = ProductList;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Product getProduct() {
		return Product;
	}

	public void setProduct(Product Product) {
		this.Product = Product;
	}

	public List<Product> getProductList() {
		return ProductList;
	}

	public void setProductList(List<Product> ProductList) {
		this.ProductList = ProductList;
	}

}
