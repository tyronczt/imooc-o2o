package com.tyron.o2o.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * @Description: 继承PropertyPlaceholderConfigurer，重写convertProperty
 *
 * @author tyronchen
 * @date 2018年12月25日
 */
public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	// 需要加密的字段数组
	private String[] encryptPropNames = { "jdbc.username", "jdbc.password" };

	/**
	 * 对关键的属性进行转换
	 */
	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		// 判断是否加密
		if (isEncryptProp(propertyName)) {
			// 解密
			String decryptValue = DESUtil.getDecryptString(propertyValue);
			return decryptValue;
		} else {
			return propertyValue;
		}
	}

	/**
	 * 判断该属性是否加密
	 * 
	 * @param propertyName
	 * @return
	 */
	private boolean isEncryptProp(String propertyName) {
		for (String encryptpropertyName : encryptPropNames) {
			if (encryptpropertyName.equals(propertyName))
				return true;
		}
		return false;
	}
}