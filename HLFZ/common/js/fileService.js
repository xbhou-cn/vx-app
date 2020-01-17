function downloadFile(fileId) {
	debugger
	uni.request({
		url: this.apiService + '/file/' + saveUser,
		method: 'GET', 
		success: res => {
			debugger
			console.log('back', res)
		},
		fail: () => {},
		complete: () => {}
	});
}
