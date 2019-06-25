$(function() {
	var userType = getQueryString('userType');
	var loginUrl = '/o2o/user/login';
	var loginCount = 0;

	$('#submit').click(function() {
		var username = $('#username').val();
		var password = $('#psw').val();
		var verifyCodeActual = $('#j_captcha').val();
		var needVerify = false;
		if (loginCount >= 3) {
			if (!verifyCodeActual) {
				$.toast('请输入验证码！');
				return;
			} else {
				needVerify = true;
			}
		}
		$.ajax({
			url : loginUrl,
			async : false,
			cache : false,
			type : "post",
			dataType : 'json',
			data : {
				username : username,
				password : password,
				verifyCodeActual : verifyCodeActual,
				needVerify : needVerify,
				userType : userType
			},
			success : function(data) {
				if (data.success) {
					// 延时1秒
					setTimeout(function() {
						$.toast('登录成功！');
					}, 1000);

					// 没有后台登录权限
					if (data.errMsg) {
						$.toast(data.errMsg);
						// 延时3秒
						setTimeout(function() {
							window.location.href = '/o2o/front/index';
						}, 3000);
					}
					// 仅当有权限登录成功且链接中带有back属性时跳转后台
					else if (userType == "back") {
						// 延时3秒
						setTimeout(function() {
							window.location.href = '/o2o/shopadmin/shoplist';
						}, 3000);
					}
					// 登录成功的其他情况都跳转前台
					else {
						// 延时3秒
						setTimeout(function() {
							window.location.href = '/o2o/front/index';
						}, 3000);
					}
				} else {
					$.toast(data.errMsg);
					loginCount++;
					if (loginCount >= 3) {
						$('#verifyPart').show();
					}
				}
			}
		});
	});

	$('#register').click(function() {
		window.location.href = '/o2o/admin/register?userType=back';
	});
});