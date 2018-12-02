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
				// 获取商品详情图片列表
				var productDetailImgList = product.productImgList;
				var swiperHtml = '';
				productDetailImgList.map(function(item, index) {
					swiperHtml += ''
                        + '<div class="swiper-slide img-wrap">'
                        +      '<img class="banner-img" src="'+ item.piImgAddr +'" alt="'+ item.piImgDesc +'">'
                        + '</div>';
				});
				// 生成购买商品的二维码供商家扫描
//				imgListHtml += '<div> <img src="/o2o/front/generateqrcode4product?productId=' + product.productId + '"/></div>';
				$('.swiper-wrapper').html(swiperHtml);
				 // 设置轮播图轮换时间为1秒
	            $(".swiper-container").swiper({
	                autoplay: 1000,
	                // 用户对轮播图进行操作时，是否自动停止autoplay
	                autoplayDisableOnInteraction: true
	            });
			}
		});
	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});
	$.init();
});
