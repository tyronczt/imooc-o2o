/**
 * 基础类js
 */

/**
 * 点击更换验证码
 * 
 * @param img
 */
function changeVerifyCode(img) {
	img.src = "../Kaptcha?" + Math.floor(Math.random() * 100);
}

/**
 * 根据属性名获取QueryString中所带的属性值
 * 
 * @param name:属性名
 */
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
		return decodeURIComponent(r[2]);
	}
	return '';
}