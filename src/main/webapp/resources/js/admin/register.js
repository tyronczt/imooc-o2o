$(function() {
	var registerUrl = '/o2o/user/register';
	$('#submit').click(function() {
		var pwd = $('#password').val();
		var repwd = $('#repassword').val();
		// 密码不相同
		if (pwd != repwd) {
			$.toast('两次密码输入不相同，请重新输入');
			return;
		}
		var username = $('#username').val();
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
		}
		$.ajax({
			url : registerUrl,
			type : "post",
			dataType : 'json',
			data : {
				username : username,
				password : pwd,
				verifyCodeActual : verifyCodeActual
			},
			async : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					$.toast('注册成功！');
					// 延时2秒
					setTimeout(function () {
						window.location.href = '/o2o/admin/login';
					}, 2000);
				} else {
					$.toast(data.errMsg);
					$('#captcha_img').click();
				}
			}
		});
	});

	$('#back').click(function() {
		window.location.href = '/o2o/admin/login';
	});
});
