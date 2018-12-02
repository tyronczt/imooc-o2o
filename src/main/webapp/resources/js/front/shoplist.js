$(function() {
	var loading = false;
	// 分页允许返回的最大条数，超过此数值，禁止访问后台
	var maxItems = 999;
	// 一页返回的最大条数
	var pageSize = 3;
	var listUrl = '/o2o/front/listshops';
	var searchDivUrl = '/o2o/front/listshopspageinfo';
	// 页码
	var pageNum = 1;
	var parentId = getQueryString('parentId');
	var areaId = '';
	var shopCategoryId = '';
	var shopName = '';
	
	// 加载店铺列表以及区域列表
	getSearchDivData();
	// 预先加载pageSize *pageNum 条
	addItems(pageSize, pageNum);
	
	function getSearchDivData() {
		var url = searchDivUrl + '?parentId=' + parentId;
		$.getJSON(url,
				function(data) {
					if (data.success) {
						var shopCategoryList = data.shopCategoryList;
						var html = '';
						html += '<a href="#" class="button" data-category-id=""> 全部类别  </a>';
						// 店铺列表
						shopCategoryList
								.map(function(item, index) {
									html += '<a href="#" class="button" data-category-id='
											+ item.shopCategoryId
											+ '>'
											+ item.shopCategoryName
											+ '</a>';
								});
						$('#shoplist-search-div').html(html);
						var selectOptions = '<option value="">全部区域</option>';
						var areaList = data.areaList;
						// 区域列表
						areaList.map(function(item, index) {
							selectOptions += '<option value="'
									+ item.areaId + '">'
									+ item.areaName + '</option>';
						});
						$('#area-search').html(selectOptions);
					}
				});
	}
	
	/**
	 * 获取分页店铺列表信息
	 */
	function addItems(pageSize, pageIndex) {
		// 生成新条目的HTML
		var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
				+ pageSize + '&parentId=' + parentId + '&areaId=' + areaId
				+ '&shopCategoryId=' + shopCategoryId + '&shopName=' + shopName;
		loading = true;
		$.getJSON(url, function(data) {
			if (data.success) {
				maxItems = data.count;
				var html = '';
				data.shopList.map(function(item, index) {
					html += '' + '<div class="card" data-shop-id="'
							+ item.shopId + '">' + '<div class="card-header">'
							+ item.shopName + '</div>'
							+ '<div class="card-content">'
							+ '<div class="list-block media-list">' + '<ul>'
							+ '<li class="item-content">'
							+ '<div class="item-media">' + '<img src="'
							+ item.shopImg + '" width="44">' + '</div>'
							+ '<div class="item-inner">'
							+ '<div class="item-subtitle">' + item.shopDesc
							+ '</div>' + '</div>' + '</li>' + '</ul>'
							+ '</div>' + '</div>' + '<div class="card-footer">'
							+ '<p class="color-gray">'
							+ new Date(item.lastEditTime).Format("yyyy-MM-dd")
							+ '更新</p>' + '<span>点击查看</span>' + '</div>'
							+ '</div>';
				});
				$('.list-div').append(html);
				// 目前显示的卡片总数
				var total = $('.list-div .card').length;
				if (total >= maxItems) {
					// 异常加载提示符
					$('.infinite-scroll-preloader').hide();
				}else{
					$('.infinite-scroll-preloader').show();
				}
				pageNum += 1;
				loading = false;
				// 刷新页面,显示新加载的店铺
				$.refreshScroller();
			}
		});
	}
	
	// 下滑屏幕 自动进行分页搜索
	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading)
			return;
		addItems(pageSize, pageNum);
	});

	// 店铺详情页面
	$('.shop-list').on('click', '.card', function(e) {
		var shopId = e.currentTarget.dataset.shopId;
		window.location.href = '/o2o/front/shopdetail?shopId=' + shopId;
	});

	$('#shoplist-search-div').on(
			'click',
			'.button',
			function(e) {
				if (parentId) {// 如果传递过来的是一个父类下的子类
					shopCategoryId = e.target.dataset.categoryId;
					if ($(e.target).hasClass('button-fill')) {
						$(e.target).removeClass('button-fill');
						shopCategoryId = '';
					} else {
						$(e.target).addClass('button-fill').siblings()
								.removeClass('button-fill');
					}
					$('.list-div').empty();
					pageNum = 1;
					addItems(pageSize, pageNum);
				} else {// 如果传递过来的父类为空，则按照父类查询
					parentId = e.target.dataset.categoryId;
					if ($(e.target).hasClass('button-fill')) {
						$(e.target).removeClass('button-fill');
						parentId = '';
					} else {
						$(e.target).addClass('button-fill').siblings()
								.removeClass('button-fill');
					}
					$('.list-div').empty();
					pageNum = 1;
					addItems(pageSize, pageNum);
					parentId = '';
				}

			});

	$('#search').on('change', function(e) {
		shopName = e.target.value;
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});

	$('#area-search').on('change', function() {
		areaId = $('#area-search').val();
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});

	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});

	$.init();
});
