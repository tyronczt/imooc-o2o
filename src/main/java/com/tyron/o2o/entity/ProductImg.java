package com.tyron.o2o.entity;

import java.util.Date;

/**
 * @Description: 商品图片实体类
 *
 * @author tyronchen
 * @date 2018年3月24日
 */
public class ProductImg {
	private Long productImgId;
	private String imgAddr;
	private String imgDesc;
	private Integer priority;
	private Date createTime;
	private Long productId;

	// 与product联合查询字段
	private String piImgAddr;
	private String piImgDesc;

	public Long getProductImgId() {
		return productImgId;
	}

	public void setProductImgId(Long productImgId) {
		this.productImgId = productImgId;
	}

	public String getImgAddr() {
		return imgAddr;
	}

	public void setImgAddr(String imgAddr) {
		this.imgAddr = imgAddr;
	}

	public String getImgDesc() {
		return imgDesc;
	}

	public void setImgDesc(String imgDesc) {
		this.imgDesc = imgDesc;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getPiImgAddr() {
		return piImgAddr;
	}

	public void setPiImgAddr(String piImgAddr) {
		this.piImgAddr = piImgAddr;
	}

	public String getPiImgDesc() {
		return piImgDesc;
	}

	public void setPiImgDesc(String piImgDesc) {
		this.piImgDesc = piImgDesc;
	}

}
