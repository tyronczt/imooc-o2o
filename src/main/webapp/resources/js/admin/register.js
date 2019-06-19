$(function() {
	var registerUrl = '/o2o/user/register';
	var checkUsernameUrl = '/o2o/user/checkusername';
	$('#submit').click(function() {
		var username = $('#username').val();
		if (!username) {
			$.toast('请输入用户名！');
			return;
		}
		var pwd = $('#password').val();
		if (!pwd) {
			$.toast('请输入密码！');
			return;
		} else if(pwd.length < 6){
			$.toast('密码长度至少六位');
			return;
		}
		var repwd = $('#repassword').val();
		// 密码不相同
		if (pwd != repwd) {
			$.toast('两次密码输入不相同，请重新输入');
			return;
		}
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
						window.location.href = '/o2o/admin/login?userType=' + getQueryString('userType');
					}, 2000);
				} else {
					$.toast(data.errMsg);
					$('#captcha_img').click();
				}
			}
		});
	});

	$('#back').click(function() {
		window.location.href = '/o2o/admin/login?userType=' + getQueryString('userType');
	});

	// 异步检查用户名是否已存在
	$("#username").blur(function checkUsername() {
		var username = $('#username').val();
		// 用户名存在
		if (username) {
			$.ajax({
				url : checkUsernameUrl,
				type : "post",
				dataType : 'json',
				data : {
					username : username
				},
				async : false,
				cache : false,
				success : function(data) {
					if (!data.success) {
						$.toast(data.errMsg);
						// 提示重名后清空
						$("#username").val("");
					}
				}
			});
		}
	});
});
