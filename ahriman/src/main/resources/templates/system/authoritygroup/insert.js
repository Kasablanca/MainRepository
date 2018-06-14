$(function (){
	var form = layui.form;

	form.verify({
		groupName: [/^\S{2,15}$/, '角色名必须2到15位']
	});

	//监听提交
	form.on('submit(insert)', function(data){
		var checkedNodes = zTree.getCheckedNodes(true);
		var ids = [];
		for(var item of checkedNodes){
			ids.push(item.id);
		}
		if(ids.length > 0){
			data.field.authorityIds = JSON.stringify(ids);
		}
		$.ajax({
			url: 'authorityGroup/insert',
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
					parent.layui.table.reload('main');
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
	
	var setting = {
			callback: {
				onAsyncError: function (){
					layer.msg('加载数据失败',{icon: 5});
				},
				onClick: function (event,treeId,treeNode,clickFlag){
					$.fn.zTree.getZTreeObj("main").expandNode(treeNode);
				}
			},
			view: {
				dblClickExpand: false
			},
			check: {
				enable: true
			}
	};

	var nodes = JSON.parse(authorityTree);
	preProccesor(nodes);
	window.zTree = $.fn.zTree.init($("#main"), setting, nodes);
});

/**
 * 预处理树节点数组
 * @param children 树节点数组
 * @returns 无返回值
 */
function preProccesor(nodes){
	for(var node of nodes){
		node.open = false;
		com.syhd.utils.ztree.setNodeIcon(node);
		preProccesor(node.children);
	}
}