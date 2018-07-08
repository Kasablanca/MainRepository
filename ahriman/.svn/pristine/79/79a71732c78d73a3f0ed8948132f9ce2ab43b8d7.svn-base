$(function (){
	var form = layui.form;

	form.verify({
		name: [/^\S{2,50}$/, '权限名必须2到50位'],
		url: [/^\S{2,100}$/, '权限路径必须2到100位']
	});

	//监听提交
	form.on('submit(insert)', function(data){
		$.ajax({
			url: 'authority/insert',
			method: 'post',
			data: data.field,
			success: function (data){
				if(data.code == 0){
					layer.open({
						content: '操作成功',
						btn: ['确定'],
						yes: function (){
							parent.layer.close(parent.layer.getFrameIndex(window.name));
						}
					});
					parent.zTree.reAsyncChildNodes(parent.zTree.getSelectedNodes()[0],'refresh');
				} else {
					layer.msg(data.message,{icon:5});
				}
			},
			error: function (data){
				layer.msg('连接服务器失败',{icon: 5});
			}
		});
		return false;
	});
});