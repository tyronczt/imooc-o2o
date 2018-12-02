$(function() {
	// 获取此店铺下的商品列表的url
	var listUrl = '/o2o/shopadmin/getproductlistbyshop?pageIndex=1&pageSize=999';
	// 商品上/下架
	var statusUrl = '/o2o/shopadmin/modifyproduct';

	/**
	 * 获取商品列表
	 */
	function getList() {
		$.getJSON(listUrl, function(data) {
			if (data.success) {
				var productList = data.productList;
				var tempHtml = '';
				// 遍历商品信息，并拼接
				productList.map(function(item, index) {
					var textOp = "下架";
					var contraryStatus = 0;
					// 状态值为0，表明商品已下架，操作变为“上架”
					if (item.enableStatus == 0) {
						textOp = "上架";
						contraryStatus = 1;
					} else {
						contraryStatus = 0;
					}
					tempHtml += '' + '<div class="row row-product">'
							+ '<div class="col-33">'
							+ item.productName
							+ '</div>'
							+ '<div class="col-20">'
							+ item.priority
							+ '</div>'
							+ '<div class="col-40">'
							+ '<a href="#" class="edit" data-id="'
							+ item.productId
							+ '" data-status="'
							+ item.enableStatus
							+ '">编辑</a>'
							+ '<a href="#" class="status" data-id="'
							+ item.productId
							+ '" data-status="'
							+ contraryStatus
							+ '">'
							+ textOp
							+ '</a>'
							+ '<a href="#" class="preview" data-id="'
							+ item.productId
							+ '" data-status="'
							+ item.enableStatus
							+ '">预览</a>'
							+ '</div>'
							+ '</div>';
				});
				$('.product-wrap').html(tempHtml);
			}
		});
	}

	getList();
	
	/**
	 * 商品列表绑定链接事件
	 */
	$('.product-wrap').on('click','a',function(e) {
		var target = $(e.currentTarget);
		// 点击“编辑”按钮
		if (target.hasClass('edit')) {
			window.location.href = '/o2o/shopadmin/productoperation?productId=' + e.currentTarget.dataset.id;
		} 
		// 点击“上/下架”按钮
		else if (target.hasClass('status')) {
			changeItemStatus(e.currentTarget.dataset.id, e.currentTarget.dataset.status);
		} 
		// 点击“预览按钮”
		else if (target.hasClass('preview')) {
			window.location.href = '/o2o/front/productdetail?productId=' + e.currentTarget.dataset.id;
		}
	});

	/**
	 * 商品上下架
	 */
	function changeItemStatus(id, enableStatus) {
		var product = {};
		product.productId = id;
		product.enableStatus = enableStatus;
		$.confirm('确定么?', function() {
			$.ajax({
				url : statusUrl,
				type : 'POST',
				data : {
					productStr : JSON.stringify(product),
					statusChange : true
				},
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$.toast('操作成功！');
						getList();
					} else {
						$.toast('操作失败！');
					}
				}
			});
		});
	}
});