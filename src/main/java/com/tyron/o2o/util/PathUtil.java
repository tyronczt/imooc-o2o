package com.tyron.o2o.util;

/**
 * @Description: 路径工具类
 *
 * @author tyronchen
 * @date 2018年4月11日
 */
public class PathUtil {

	// 获取操作系统的分隔符
	private static String separator = System.getProperty("file.separator");

	/**
	 * 获取存放图片路径
	 */
	public static String getImgBasePath() {
		// 获取操作系统的信息
		String os = System.getProperty("os.name");
		String basePath = "";
		// 如果是window操作系统
		if (os.toLowerCase().startsWith("win")) {
			basePath = "D:/eclipse/tyron/image"; // Windows系统
		} else {
			basePath = "/home/tyron/image"; // 除了Windows系统
		}

		// 更换分隔符
		basePath = basePath.replace("/", separator);
		return basePath;
	}

	/**
	 * 获取店铺照片路径
	 */
	public static String getShopImagePath(long shopId) {
		String imagePath = "/upload/item/shop" + shopId + "/";
		return imagePath.replace("/", separator);
	}

	/**
	 * 获取首页头图路径
	 */
	public static String getHeadLineImagePath() {
		String imagePath = "/upload/item/headLine/";
		return imagePath.replace("/", separator);
	}

	/**
	 * 获取店铺类别路径
	 */
	public static String getShopCategoryImagePath() {
		String imagePath = "/upload/item/shopcategory/";
		return imagePath.replace("/", separator);
	}
}
