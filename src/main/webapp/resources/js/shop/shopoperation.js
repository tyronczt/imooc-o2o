/**
 * 店铺操作：注册和修改
 */
$(function() {
	// 从selectstring中获取店铺id
	var shopId = getQueryString("shopId");
	// 如果shopId不为空，则为修改
	var isEdit = shopId ? true : false;
	// 初始化店铺信息：店铺分类和区域信息列表，用于注册店铺
	var initUrl = "/o2o/shopadmin/getshopinitinfo";
	// 注册店铺
	var registerShopUrl = "/o2o/shopadmin/registershop";
	// 根据shopid获取店铺详情，用于修改店铺信息
	var shopInfoUrl = "/o2o/shopadmin/getshopbyid?shopId=" + shopId;
	// 修改店铺
	var modifyShopUrl = "/o2o/shopadmin/modifyshop";

	// 初始化
	if (isEdit) {
		getShopInfo(shopId);
	} else {
		getShopInitInfo();
	}

	/*
	 * 根据店铺ID获取店铺信息：店铺分类和区域信息列表
	 */
	function getShopInfo(shopId) {
		$.getJSON(shopInfoUrl, function(data) {
			// 数据存在
			if (data.success) {
				var shop = data.shop;
				// 赋值 要和shop实体类中的属性名保持一致
				$('#shop-name').val(shop.shopName);
				// 店铺名称不能修改
//				$('#shop-name').attr('disabled', 'disabled');
				$('#shop-addr').val(shop.shopAddr);
				$('#shop-phone').val(shop.phone);
				$('#shop-desc').val(shop.shopDesc);
				// 商品目录进行赋值 商品目录仅仅加载对应的目录，且不可编辑
				var shopCategory = '<option data-id="'
						+ shop.shopCategory.shopCategoryId + '" selected>'
						+ shop.shopCategory.shopCategoryName + '</option>';
				$('#shop-category').html(shopCategory);
				// 设置为不可编辑
				$('#shop-category').attr('disabled', 'disabled');

				// 区域进行赋值 区域可以进行编辑，并且初始设置为后台对应的区域
				var tempShopAreaHtml = '';
				data.areaList.map(function(item, index) {
					tempShopAreaHtml += '<option data-id="' + item.areaId
							+ '">' + item.areaName + '</option>';
				});
				$('#area').html(tempShopAreaHtml);
				// 初始设置为后台对应的区域
				$("#area option[data-id='" + shop.area.areaId + "']")
						.attr("selected", "selected");
			} else {
				$.toast(data.errMsg);
			}
		})
	}

	/*
	 * 获取店铺初始化信息：店铺分类和区域信息列表
	 */
	function getShopInitInfo() {
		$.getJSON(initUrl, function(data) {
			// 数据存在
			if (data.success) {
				var tempHtml = "";
				var tempAreaHtml = "";
				// 迭代店铺分类列表
				data.shopCategoryList.map(function(item, index) {
					tempHtml += '<option data-id="' + item.shopCategoryId
							+ '">' + item.shopCategoryName + '</option>';
				});
				// 迭代区域信息
				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
							+ item.areaName + '</option>';
				});
				$('#shop-category').html(tempHtml);
				$('#area').html(tempAreaHtml);
			}
		})
	}

	/*
	 * 点击提交事件
	 */
	$("#submit").click(function() {
		var shop = {};
		// 如果是编辑，需要传入shopId
		if (isEdit) {
			shop.shopId = shopId;
		}
		shop.shopName = $("#shop-name").val();
		shop.shopAddr = $("#shop-addr").val();
		shop.phone = $("#shop-phone").val();
		shop.shopDesc = $("#shop-desc").val();
		// 选择id,双重否定=肯定
		shop.shopCategory = {
			// 这里定义的变量要和ShopCategory.shopCategoryId保持一致，否则使用databind转换会抛出异常
			shopCategoryId : $('#shop-category').find('option').not(function() {
				return !this.selected;
			}).data('id')
		};
		shop.area = {
			// 这里定义的变量要和Area.areaId属性名称保持一致，否则使用databind转换会抛出异常
			areaId : $('#area').find('option').not(function() {
				return !this.selected;
			}).data('id')
		};
		// 获取图片文件流
		var shopImg = $('#shop-img')[0].files[0];
		var formData = new FormData();
		formData.append('shopImg', shopImg);
		formData.append('shopStr', JSON.stringify(shop));
		var verifyCodeActual = $('#j_kaptcha').val();
		if (!verifyCodeActual) {
			$.toast("请输入验证码！");
			return;
		} else {
			formData.append('verifyCodeActual', verifyCodeActual);
		}
		$.ajax({
			url:isEdit ? modifyShopUrl:registerShopUrl,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					$.toast("提交成功！");
				} else {
					$.toast("提交失败！" + data.errMsg);
				}
				// 更换验证码
				$('#kaptcha_img').click();
			}
		});
	});
});