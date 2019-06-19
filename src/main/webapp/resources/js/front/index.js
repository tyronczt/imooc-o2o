$(function() {
	// 定义访问后台获取头条列表以及一级商铺类别列表的URL
    var url = '/o2o/front/listmainpageinfo';
    
    // 访问后台获取头条列表以及一级商铺类别
    $.getJSON(url, function (data) {
        if (data.success) {
        	// 定义变量，接收后台传递过来的头条列表数据
            var headLineList = data.headLineList;
            var swiperHtml = '';
            // 遍历头条列表，并拼接出轮播图组
            headLineList.map(function (item, index) {
                swiperHtml += ''
                            + '<div class="swiper-slide img-wrap">'
                            +      '<img class="banner-img" src="'+ item.lineImg +'" alt="'+ item.lineName +'">'
                            + '</div>';
            });
            // 将轮播图组赋值给前端HTML空间
            $('.swiper-wrapper').html(swiperHtml);
            // 设置轮播图轮换时间为1秒
            $(".swiper-container").swiper({
                autoplay: 1000,
                // 用户对轮播图进行操作时，是否自动停止autoplay
                autoplayDisableOnInteraction: false
            });
            // 获取后台传递过来的一级商铺类别列表
            var shopCategoryList = data.shopCategoryList;
            var categoryHtml = '';
            // 遍历台传递过来的一级商铺类别列表 拼接col-50 两两一行的类别
            shopCategoryList.map(function (item, index) {
                categoryHtml += ''
                             +  '<div class="col-50 shop-classify" data-category='+ item.shopCategoryId +'>'
                             +      '<div class="word">'
                             +          '<p class="shop-title">'+ item.shopCategoryName +'</p>'
                             +          '<p class="shop-desc">'+ item.shopCategoryDesc +'</p>'
                             +      '</div>'
                             +      '<div class="shop-classify-img-warp">'
                             +          '<img class="shop-img" src="'+ item.shopCategoryImg +'">'
                             +      '</div>'
                             +  '</div>';
            });
            $('.row').html(categoryHtml);
        } else {
        	alert(data.errMsg);
        }
    });
    
    // 我的  
    $('#me').click(function () {
        $.openPanel('#panel-left-demo');
    });
    
    // 点击特定的分类
    $('.row').on('click', '.shop-classify', function (e) {
        var shopCategoryId = e.currentTarget.dataset.category;
        var newUrl = '/o2o/front/shoplist?parentId=' + shopCategoryId;
        window.location.href = newUrl;
    });

    // 用户登录
    $('#login').click(function() {
		window.location.href = '/o2o/admin/login';
	});

	// 修改密码
	$('#change-pwd').click(function() {
		window.location.href = '/o2o/admin/changepwd';
	});

	// 退出登录
	$('#log-out').click(function () {
		$.ajax({
			url : "/o2o/user/logout",
			type : "post",
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					window.location.href = '/o2o/admin/login';
				}
			},
			error : function(data, error) {
				alert(error);
			}
		});
	});

});
