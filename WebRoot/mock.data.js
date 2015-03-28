//time: 2015/03/02 17:22:20
$.mockjax({
	url: '/response-callback',
	response: function () {
		this.responseText = {a:1,b:3};
	}
})