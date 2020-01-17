import Vue from 'vue'
import App from './App'
import './static/css/button.css'

Vue.config.productionTip = false

App.mpType = 'app'

const app = new Vue({
	...App
})
Vue.prototype.apiService = 'http://192.168.1.108:8080'
// Vue.prototype.apiService = 'http://localhost:8080'
Vue.prototype.checkLogin = function(url, type) {
	let userId = uni.getStorageSync("userId")
	let userCode = uni.getStorageSync("openId")
	let userName = uni.getStorageSync("userName")
	let userFace = uni.getStorageInfoSync("userFace")
	console.log('checkLogin')
	if (!userId || !userCode || !userName || !userFace) {
		uni.redirectTo({
			url: '/pages/login/login?backUrl=' + url + "&backType=" + type,
		});
		return false;
	}
	return [userId, userCode, userName, userFace]
}
app.$mount()
