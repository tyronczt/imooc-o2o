$(function() {
	var url = '/o2o/user/changepwd';
	$('#submit').click(function() {
		var username = $('#username').val();
		if (!username) {
			$.toast('请输入用户名！');
			return;
		}
		var password = $('#password').val();
		if (!password) {
			$.toast('请输入密码！');
			return;
		} else if(password.length < 6){
			$.toast('密码长度至少六位');
			return;
		}
		var newPassword = $('#newPassword').val();
		if (!newPassword) {
			$.toast('请输入密码！');
			return;
		} else if(newPassword.length < 6){
			$.toast('密码长度至少六位');
			return;
		}
		var renewpwd = $('#renewPassword').val();
		// 新密码和确认密码不相同
		if (newPassword != renewpwd) {
			$.toast('新密码和确认密码输入不相同，请重新输入');
			return;
		}
		// 新密码和原密码相同
		if (newPassword == password) {
			$.toast('新密码和原密码相同，请重新输入');
			return;
		}
		var formData = new FormData();
		formData.append('username', username);
		formData.append('password', password);
		formData.append('newPassword', newPassword);
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
		}
		formData.append("verifyCodeActual", verifyCodeActual);
		$.ajax({
			url : url,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					$.toast('密码修改成功！');
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
});
