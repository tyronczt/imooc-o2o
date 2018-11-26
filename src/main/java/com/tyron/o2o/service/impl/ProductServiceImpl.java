package com.tyron.o2o.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tyron.o2o.dao.ProductDao;
import com.tyron.o2o.dao.ProductImgDao;
import com.tyron.o2o.dto.ProductExecution;
import com.tyron.o2o.entity.Product;
import com.tyron.o2o.entity.ProductImg;
import com.tyron.o2o.enums.ProductStateEnum;
import com.tyron.o2o.exceptions.ProductOperationException;
import com.tyron.o2o.service.ProductService;
import com.tyron.o2o.util.ImageUtil;
import com.tyron.o2o.util.PageCalculator;
import com.tyron.o2o.util.PathUtil;
import com.tyron.o2o.util.SystemEnumUtil;

/**
 * @Description: 商品业务接口实现
 *
 * @author: tyron
 * @date: 2018年10月27日
 */
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;

	@Autowired
	private ProductImgDao productImgDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tyron.o2o.service.ProductService#addProduct(com.tyron.o2o.entity.Product,
	 * org.springframework.web.multipart.MultipartFile, java.util.List)
	 */
	@Override
	@Transactional
	// 1、处理缩略图，获取缩略图相对路径并赋值给product
	// 2、往tb_product写入商品信息，获取productId
	// 3、结合productId批量处理商品详细图
	// 4、将商品详情图列表批量插入tb_product_img中
	public ProductExecution addProduct(Product product, MultipartFile productImg, List<MultipartFile> productImgList)
			throws ProductOperationException {
		// 空值判断
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			// 给商品设置默认属性
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			// 默认上架状态
			product.setEnableStatus(SystemEnumUtil.ENABLE_STATUS.USABLE.getValue());
			// 若商品缩略图不为空则添加
			if (productImg != null) {
				addProductImg(product, productImg);
			}
			// 创建商品信息
			try {
				int effectNum = productDao.insertProduct(product);
				if (effectNum <= 0) {
					throw new ProductOperationException("创建商品失败");
				}
			} catch (Exception e) {
				throw new ProductOperationException("创建商品失败" + e.toString());
			}
			// 若商品详情图列表不为空则添加
			if (productImgList != null && !productImgList.isEmpty()) {
				addProductImgList(product, productImgList);
			}
			return new ProductExecution(ProductStateEnum.SUCCESS, product);
		} else {
			// 参数为空则返回空值错误信息
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tyron.o2o.service.ProductService#modifyProduct(com.tyron.o2o.entity.
	 * Product, org.springframework.web.multipart.MultipartFile, java.util.List)
	 */
	// ①若缩略图参数有值，则先处理缩略图，先删除原有缩略图再添加
	// ②若商品详情图参数有值，先删除后添加
	// ③更新tb_product的信息
	@Override
	@Transactional
	public ProductExecution modifyProduct(Product product, MultipartFile productImg, List<MultipartFile> productImgList)
			throws ProductOperationException {
		// 空值判断
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			// 设置默认更新时间
			product.setLastEditTime(new Date());
			// 若商品缩略图不为空且原有缩略图不为空，则先删除原有缩略图并添加
			if (productImg != null) {
				// 先获取原有信息，得到原有图片地址
				Product origProduct = productDao.selectProductByProductId(product.getProductId());
				if (origProduct.getImgAddr() != null) {
					ImageUtil.deleteFileOrPath(origProduct.getImgAddr());
				}
				addProductImg(product, productImg);
			}

			// 若存在新的商品详情图且原详情图不为空，则先删除原有详情图并添加
			if (productImgList != null && !productImgList.isEmpty()) {
				deleteProductImgList(product.getProductId());
				addProductImgList(product, productImgList);
			}

			// 更新商品信息
			try {
				int effectNum = productDao.updateProduct(product);
				if (effectNum <= 0) {
					throw new ProductOperationException("更新商品信息失败");
				}
				return new ProductExecution(ProductStateEnum.SUCCESS, product);
			} catch (ProductOperationException e) {
				throw new ProductOperationException("更新商品信息失败" + e.getMessage());
			}
		} else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

	@Override
	public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
		// 将页码转换为数据库的行数
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		// 获取商品列表分页信息
		List<Product> productList = productDao.selectProductList(productCondition, rowIndex, pageSize);
		// 获取商品总数
		int productCount = productDao.selectProductCount(productCondition);
		// 构建返回对象,并设值
		ProductExecution productExecution = new ProductExecution();
		productExecution.setCount(productCount);
		productExecution.setProductList(productList);
		return productExecution;
	}

	@Override
	public Product getProductById(long productId) {
		return productDao.selectProductByProductId(productId);
	}

	/**
	 * 添加商品缩略图
	 * 
	 * @param product    商品
	 * @param productImg 商品缩略图
	 */
	private void addProductImg(Product product, MultipartFile productImg) {
		// 获取图片存储路径，将图片放在相应店铺的文件夹下
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		String productImgAddr = ImageUtil.generateThumbnail(productImg, dest);
		product.setImgAddr(productImgAddr);
	}

	/**
	 * 添加商品详情图
	 * 
	 * @param product        商品
	 * @param productImgList 商品详情图列表
	 */
	private void addProductImgList(Product product, List<MultipartFile> productImgList) {
		// 获取图片存储路径，将图片放在相应店铺的文件夹下
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		List<ProductImg> productImgs = new ArrayList<>();
		// 遍历商品详情图，并添加到productImg中
		for (MultipartFile multipartFile : productImgList) {
			String imgAddr = ImageUtil.generateNormalImg(multipartFile, dest);
			ProductImg productImg = new ProductImg();
			productImg.setProductId(product.getProductId());
			productImg.setImgAddr(imgAddr);
			productImg.setCreateTime(new Date());
			productImgs.add(productImg);
		}

		// 存入有图片，就执行批量添加操作
		if (productImgs.size() > 0) {
			try {
				int effectNum = productImgDao.batchInsertProductImg(productImgs);
				if (effectNum <= 0) {
					throw new ProductOperationException("创建商品详情图片失败");
				}
			} catch (Exception e) {
				throw new ProductOperationException("创建商品详情图片失败" + e.toString());
			}
		}
	}

	/**
	 * 删除某个商品下的详情图
	 * 
	 * @param productId
	 */
	private void deleteProductImgList(long productId) {
		// 根据productId获取原有的图片
		List<ProductImg> productImgList = productImgDao.selectProductImgListByProductId(productId);
		if (productImgList != null && !productImgList.isEmpty()) {
			for (ProductImg productImg : productImgList) {
				// 删除存的图片
				ImageUtil.deleteFileOrPath(productImg.getImgAddr());
			}
			// 删除数据库中图片
			productImgDao.deleteProductImgByProductId(productId);
		}
	}
}
