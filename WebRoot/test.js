/**
 * 业务逻辑部分
 */

seajs.config({
	base: './'
});

//seajs.use(['fe/mock.data.js'], function (mockData) {
seajs.use([], function () {
	
	$.ajax({
		url: 'http://gift.jd.com/remind/remind/list',
		dataType: 'jsonp',
		error: function () {
			console.info('error');
		},
		success: function(json) {
			console.info(json);
		}
	});
	
	$.ajax({
		url: "http://some/fake/jsonp/endpoint",
		dataType: "jsonp",
		error: function () {
			console.info('error');
		},
		success: function(data) {
			console.info(data);
		}
	});
});