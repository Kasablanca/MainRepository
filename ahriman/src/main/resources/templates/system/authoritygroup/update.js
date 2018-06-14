$(function (){
	var form = layui.form;

	form.verify({
		groupName: [/^\S{2,15}$/, '角色名必须2到15位']
	});

	//监听提交
	form.on('submit(update)', function(data){
		var checkedNodes = zTree.getCheckedNodes(true);
		var ids = [];
		for(var item of checkedNodes){
			ids.push(item.id);
		}
		if(ids.length > 0){
			data.field.authorityIds = JSON.stringify(ids);
		}
		$.ajax({
			url: 'authorityGroup/update',
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
	
	if(authorityIds){
		authorityIds = JSON.parse(authorityIds);
	}
	
	if(authorityTree){
		authorityTree = JSON.parse(authorityTree);
	} else {
		layer.msg('获取数据失败',{icon: 5});
		return;
	}
	
	preProccesor(authorityTree,authorityIds);
	window.zTree = $.fn.zTree.init($("#main"), setting, authorityTree);
});

/**
 * 预处理树节点数组
 * @param children 树节点数组
 * @returns 无返回值
 */
function preProccesor(children,authorityIds){
	if(authorityIds){
		for(var node of children){
			com.syhd.utils.ztree.setNodeIcon(node);
			
			node.open = false;
			
			if(contains(authorityIds, node.id))
				node.checked = true;
			
			preProccesor(node.children,authorityIds);
		}
	} else {
		for(var node of children){
			node.open = false;
			com.syhd.utils.ztree.setNodeIcon(node);
			preProccesor(node.children,authorityIds);
		}
	}
}

function contains(collection,target){
	for(var item of collection){
		if(item == target)
			return true;
	}
	return false;
}