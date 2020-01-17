<template>
	<view>
		<image :src="imgSrc" class="login-image" mode="widthFix"></image>
		<button class="btn-blue login-button" @click="login" open-type='getUserInfo' hover-class="btn-blue-active" :loading="clickF">点击微信登陆</button>
	</view>
</template>

<script>
	import fileService from '../../common/js/fileService.js'
	export default {
		data() {
			return {
				backUrl: null,
				backType: null,
				clickF: false,
				imgSrc: null
			}
		},
		onLoad(option) {
			this.backUrl = option.backUrl
			this.backType = option.backType

			uni.request({
				url: this.apiService + '/file/826132a7004047049dce9c32b26feeb4',
				method: 'GET',
				success: res => {
					let base64String = res.data.obj;
					base64String = uni.arrayBufferToBase64(uni.base64ToArrayBuffer(base64String))
					this.imgSrc = 'data:image/jpg;base64,' + base64String;
				},
				fail: () => {},
				complete: () => {}
			});
		},
		methods: {
			login: function() {
				let _this = this;
				this.clickF = true
				// #ifdef MP-WEIXIN
				uni.login({
					success: (res) => {
						let code = res.code
						if (code) {
							uni.request({
								url: this.apiService + '/user/getUserInfo',
								method: 'GET',
								data: {
									code: code
								},
								success: data => {
									console.log('第三方', data)
									_this.checkOrSaveUser(data.data.obj)
								},
								fail: () => {},
								complete: () => {
									this.clickF = false
								}
							});
						}
					}
				})
				// #endif
			},
			checkOrSaveUser: function(data) {
				let _this = this;
				data = JSON.parse(data)
				uni.getUserInfo({
					success: (res) => {
						let param = {
							openId: data.openid,
							userName: res.userInfo.nickName,
							userFace: res.userInfo.avatarUrl
						}
						uni.request({
							url: this.apiService + '/user/saveUser',
							method: 'POST',
							header: {
								'content-type': 'application/json'
							},
							data: JSON.stringify(param),
							success: res => {
								if (res.data.successful) {
									let rs = res.data.obj
									uni.setStorageSync("userId", rs.userId)
									uni.setStorageSync("openId", rs.openId)
									uni.setStorageSync("userName", rs.userName)
									uni.setStorageSync("userFace", rs.userFace)
									uni.redirectTo({
										url: _this.backUrl
									})
								}
								uni.showToast({
									title: res.data.msg
								})
								console.log('back', res)
							},
							fail: () => {},
							complete: () => {}
						});
					}
				})
			}
		}
	}
</script>

<style>
	.login-view {
		background-color: red;
	}

	.login-button {
		position: absolute;
		width: 50%;
		left: 25%;
		top: 60%;
	}

	.login-image {
		box-shadow: 5px 5px rgba(100, 255, 0, .6);
		width: 100%;
		height: 100%;
		background-color: #808080;
		border-radius: 10rpx;
		overflow: hidden;
	}

	page {
		text-align: center;
		cursor: default;
		-webkit-user-select: none;
		user-select: none;
		width: 100%;
		overflow-x: hidden;
		background-color: rgba(247, 56, 9, .5);
	}
</style>
