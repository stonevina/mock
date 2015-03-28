//入口
seajs.config({
	base: './'
});

seajs.use(['fe/lib/util.template.js', 'fe/ui/dialog/Dialog.js'], function (template, Dialog) {
	
	var mock = {
		listTpl: $('#mockListTemplate').text(),
		init: function () {
			//显示列表
			this.showList();
			this.bindEvent();
		},
		showList: function () {
			var that = this;
			
			$.get('/mock/mockQueryAction.action', function (result) {
				$('#mockList tbody').remove();
				$('#mockList').append(template(that.listTpl, result));
			});
		},
		getParam: function () {
			var result = {};
			var rselectTextarea = /^(?:select|textarea)/i;
			var rinput = /^(?:color|date|datetime|datetime-local|email|hidden|month|number|password|range|search|tel|text|time|url|week)$/i;
			
			//同步编辑器和textarea的内容
//			this.editor.save();
			this.editor.getTextArea().value = this.editor.getValue().replace(/[\r|\n|\s*]/g, '');
			
			return $.param($('form').find(':input').filter(function(){
	            return this.name && !this.disabled &&
	                ( this.checked || rselectTextarea.test( this.nodeName ) ||
	                    rinput.test( this.type ) );
	        })
	        .map(function( i, elem ){
	            var val = filterXSS.escapeHtml($( this ).val());
	
	            return val == null ?
	                null :
	                { name: elem.name, value: val }
	        }).get());
	        
		},
		bindEvent: function () {
			var that = this;
			
			//创建
			$('#J-createMock').bind('click', function () {
				that.dialog = new Dialog({
					width: 500,
					height: 800,
					source: $('#newMockTemplate').html(), 
					type: 'html',
					title: '新建接口',
					complete: function () {
						$('#J-submit').unbind('click').bind('click', function () {
							that.createMockAction();
						})
						
						that.format();
					}
				});
			})
			
			//删除
			$('body').delegate('[deletemockid]', 'click', function () {
				var id = $(this).attr('deletemockid');
				that.deleteMockAction(id);
			})
			
			//编辑
			$('body').delegate('[editmockid]', 'click', function () {
				var id = $(this).attr('editmockid');

				that.dialog = new Dialog({
					width: 500,
					height: 700,
					source: $('#editMockTemplate').html(), 
					type: 'html',
					title: '编辑接口',
					complete: function () {
						that.queryAction(id, function (result) {
							$('.new-mock-form').replaceWith(template($('#editMockTemplate').html(), result));
							that.format();
						});
						
						
					}
				});
				
			})
			
			//编辑提交
			$('body').delegate('#J-edit-submit', 'click', function () {
				that.editMockAction();
			})
		},
		createMockAction: function () {
			var that = this;
			
			$.post('/mock/mockUpdateAction.action', this.getParam(), function (result) {
				if (result && result.success) {
					that.dialog.close();
					that.showList();
				}
			});
		},
		deleteMockAction: function (mockId) {
			var that = this;
			
			$.post('/mock/mockUpdateAction.action', {mockID: mockId}, function (result) {
				that.showList();
			});
		},
		editMockAction: function () {
			var that = this;
			
			$.post('/mock/mockUpdateAction.action', this.getParam(), function (result) {
				if (result && result.success) {
					that.dialog.close();
					that.showList();
				}
			});
		},
		queryAction: function (mockId, callback) {
			$.get('/mock/mockQueryAction.action', {mockID: mockId, action: 'edit'}, function (result) {
				callback && callback(result);
			});
		},
		//格式化代码
		format: function () {
			var editor = CodeMirror.fromTextArea(document.getElementById("code"), {
		      lineNumbers: true,
		      theme: "eclipse",
		      mode: {name: "javascript", json: true},
		      extraKeys: {
		        "F11": function(cm) {
		          cm.setOption("fullScreen", !cm.getOption("fullScreen"));
		        },
		        "Esc": function(cm) {
		          if (cm.getOption("fullScreen")) cm.setOption("fullScreen", false);
		        }
		      }
		    });
		    CodeMirror.commands["selectAll"](editor);
		    
			function getSelectedRange() {
			    return { from: editor.getCursor(true), to: editor.getCursor(false) };
			}
			  
			function autoFormatSelection() {
				var range = getSelectedRange();
				editor.autoFormatRange(range.from, range.to);
			}
			
			this.editor = editor;
			autoFormatSelection();
		}
	};
	
	mock.init();
});