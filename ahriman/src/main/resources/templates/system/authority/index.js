$(function (){
	var setting = {
		async: {
			enable: true,
			url:"authority/treedata",
			autoParam:["id"],
			dataFilter: function (treeId, parentNode, response) {
				if(response.code == 0){
					preProccesor(response.data);
					return response.data;
				} else {
					layer.msg(response.msg,{icon: 5});
					return null;
				}
			}
		},
		callback: {
			onAsyncError: function (){
				layer.msg('加载数据失败',{icon: 5});
			},
			onClick: function (event,treeId,treeNode,clickFlag){
				$.fn.zTree.getZTreeObj("main").expandNode(treeNode);
			},
			onRightClick: function (event,treeId,treeNode){
				if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
					zTree.cancelSelectedNode();
					showRMenu(3, event.clientX, event.clientY);
				} else if (treeNode) {
					zTree.selectNode(treeNode);
					showRMenu(treeNode.type, event.clientX, event.clientY);
				}
			}
		},
		view: {
			dblClickExpand: false
		}
	};

	window.zTree = $.fn.zTree.init($("#main"), setting);
	
	$('#addNode').click(addNode);
	$('#editNode').click(editNode);
	$('#deleteNode').click(deleteNode);
});

/**
 * 预处理树节点数组
 * @param children 树节点数组
 * @returns 无返回值
 */
function preProccesor(children){
	for(var node of children){
		node.open = true;
		com.syhd.utils.ztree.setNodeIcon(node);
		preProccesor(node.children);
	}
}
/**
 * 显示右键菜单
 * @param type 代表权限类型，0：菜单；1：页面；2：功能；3：根权限
 * @param x 显示位置的x轴坐标
 * @param y 显示位置的y轴坐标
 * @returns 无返回
 */
function showRMenu(type, x, y) {
	switch(type){
	case 0:
	case 1:
		$('#addNode').css('display','list-item');
		$('#editNode').css('display','list-item');
		$('#deleteNode').css('display','list-item');
		break;
	case 2:
		$('#addNode').css('display','none');
		$('#editNode').css('display','list-item');
		$('#deleteNode').css('display','list-item');
		break;
	case 3:
		$('#addNode').css('display','list-item');
		$('#editNode').css('display','none');
		$('#deleteNode').css('display','none');
		break;
	default:
		layer.mgs('无效的权限类型',{icon: 5});
		return;
	}

    y += document.body.scrollTop;
    x += document.body.scrollLeft;
    $('#contextMenu').css({"top":y+"px", "left":x+"px", "display":"block"});

	$("body").bind("mousedown", onBodyMouseDown);
}
function hideRMenu() {
	$('#contextMenu').css({"display" : "none"});
	$("body").unbind("mousedown", onBodyMouseDown);
}
function onBodyMouseDown(event){
	if (!(event.target.id == "contextMenu" || $(event.target).parents("#contextMenu").length>0)) {
		$('#contextMenu').css({"display" : "none"});
	}
}
function addNode() {
	hideRMenu();
	var selectedNode = zTree.getSelectedNodes()[0];
	var url = selectedNode? ('authority/insertPage?parentId='+selectedNode.id) : 'authority/insertPage';
	layer.open({
		type: 2,
		title: '添加权限',
		content: url,
		area: ['700px','500px']
	});
}
function editNode() {
	hideRMenu();
	layer.open({
		type: 2,
		title: '修改权限',
		content: 'authority/updatePage?id='+zTree.getSelectedNodes()[0].id,
		area: ['700px','500px']
	});
}
function deleteNode() {
	hideRMenu();
	layer.open({
		title: '提示',
		content: '确定删除该权限和下面的子权限？',
		btn: ['确定'],
		yes: function (){
			var target = zTree.getSelectedNodes()[0];
			var parentNode = target.getParentNode();
			$.ajax({
				url: 'authority/delete',
				method: 'post',
				data: {
					id: target.id,
					verNo: target.verNo
				},
				success: function (resp) {
					if(resp.code === 0){
						layer.msg('操作成功');
						zTree.reAsyncChildNodes(parentNode,'refresh');
					} else {
						layer.msg(resp.message,{icon: 5});
					}
				},
				error: function (){
					layer.msg('连接服务器失败',{icon: 5});
				}
			});
		}
	});
}