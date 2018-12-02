$(function() {
	var productId = getQueryString('productId');
	var productUrl = '/o2o/front/listproductdetailpageinfo?productId=' + productId;

	$.getJSON(
		productUrl,function(data) {
			if (data.success) {
				var product = data.product;
				$('#product-img').attr('src', product.imgAddr);
				$('#product-time').text(new Date(product.lastEditTime) .Format("yyyy-MM-dd"));
				$('#product-name').text(product.productName);
				$('#product-desc').text(product.productDesc);
				var imgListHtml = '';
				product.productImgList.map(function(item, index) {
					imgListHtml += '<div> <img src="'
							+ item.imgAddr + '"/></div>';
				});
				// 生成购买商品的二维码供商家扫描
				imgListHtml += '<div> <img src="/myo2o/front/generateqrcode4product?productId='
						+ product.productId + '"/></div>';
				$('#imgList').html(imgListHtml);
			}
		});
	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});
	$.init();
});
