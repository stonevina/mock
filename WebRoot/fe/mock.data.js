/**
 * 数据模拟部分
 */
define(function (require, exports, module) {
	
	//json格式
	$.mockjax({
		url: '/response-callback',
		response: function( settings ) {
			this.responseText = { currentTime: 'now: ' + new Date() };
		}
	});
	
	//jsonp格式
	$.mockjax({
		url: "http://some/fake/jsonp/endpoint",
		response: function() {
			this.responseText = [{ "data" : "JSONP is cool" }];
		}
	});
});