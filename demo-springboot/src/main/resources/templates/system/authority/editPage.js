$(function (){
	$('#mainForm').submit(function (event){
		event.preventDefault();
		var url = $('#id')[0]?'authority/update':'authority/insert';
		$.ajax({
			url: url,
			method: 'post',
			data: $(this).serialize(),
			success: function (resp){
				if(resp.code == 0){
					layui.layer.open({
						title: '提示',
						content: '操作成功',
						btn: ['确定'],
						yes: function (){
							parent.zTree.reAsyncChildNodes(parent.zTree.getSelectedNodes()[0],'refresh');
							parent.layer.close(parent.layer.getFrameIndex(window.name));
						}
					});
				} else {
					layui.layer.msg(resp.message,{icon: 5});
				}
			},
			error: function (){
				layui.layer.msg('连接服务器失败',{icon: 5});
			}
		});
	});
});