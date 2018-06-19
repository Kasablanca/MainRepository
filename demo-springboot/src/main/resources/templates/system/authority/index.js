$(function (){
	window.zTree;
	$.fn.zTree.init($("#main"), {
		async: {
			enable: true,
			url: "authority/getDescendant",
			autoParam :["id"],
			dataFilter: function (treeId, parentNode, response){
				if(response.code === 0){
					return response.data;
				} else {
					layui.layer.msg(response.message,{icon: 5});
				}
			}
		},
		callback: {
			onClick: function (event,treeId,treeNode,type){
				zTree.expandNode(treeNode);
			},
			onRightClick: function (event,treeId,treeNode){
				if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
					zTree.cancelSelectedNode();
					showContextMenu("root", event.clientX, event.clientY);
				} else if (treeNode) {
					var type;
					if(treeNode.type==0){
						type = 'menu';
					} else if(treeNode.type==1){
						type = 'page';
					} else if(treeNode.type==2){
						type = 'function';
					} else {
						layui.layer.msg('无效的权限类型',{icon: 5});
						return;
					}
					zTree.selectNode(treeNode);
					showContextMenu(type, event.clientX, event.clientY);
				}
			}
		},
		view: {
			dblClickExpand: false
		}
	});
	zTree = $.fn.zTree.getZTreeObj("main");
	
	$('#addNode').click(addTreeNode);
	$('#deleteNode').click(removeTreeNode);
	$('#editNode').click(editTreeNode);
});

function showContextMenu(type, x, y) {
	if(type == 'root'){
		$('#addNode').css('display','list-item');
		$('#deleteNode').css('display','none');
		$('#editNode').css('display','none');
	} else if(type == 'menu'){
		$('#addNode').css('display','list-item');
		$('#deleteNode').css('display','list-item');
		$('#editNode').css('display','list-item');
	} else if(type == 'page'){
		$('#addNode').css('display','list-item');
		$('#deleteNode').css('display','list-item');
		$('#editNode').css('display','list-item');
	} else if(type == 'function'){
		$('#addNode').css('display','list-item');
		$('#deleteNode').css('display','none');
		$('#editNode').css('display','list-item');
	}

    y += document.body.scrollTop;
    x += document.body.scrollLeft;
    $('#contextMenu').css({"top":y+"px", "left":x+"px", "display": "block"});

	$("body").bind("mousedown", onBodyMouseDown);
}
function hideRMenu() {
	$("#contextMenu").css({"display": "none"});
	$("body").unbind("mousedown", onBodyMouseDown);
}
function onBodyMouseDown(event){
	console.log(event);
	if(!(event.target.id == "contextMenu" || $(event.target).parents("#contextMenu").length>0)) {
		$("#contextMenu").css({"display" : "none"});
	}
}

function addTreeNode() {
	hideRMenu();
	layui.layer.open({
		type: 2,
		title: '新增权限',
		content: 'authority/editPage',
		area: ['700px','400px']
	});
}
function removeTreeNode() {
	hideRMenu();
	layui.layer.confirm('确定删除该权限和下面的子权限？',{
		icon: 3,
		title: '提示'
	},function (index){
		var target = zTree.getSelectedNodes()[0];
		$.ajax({
			url: 'authority/delete',
			method: 'post',
			data: {
				id: target.id,
				verNo: target.verNo
			},
			success: function (response){
				if(response.code == 0){
					layui.layer.msg('操作成功');
				} else {
					layui.layer.msg(reponse.message,{icon: 5});
				}
				ztree.reAsyncChildNodes();
			},
			error: function (){
				layui.layer.msg('连接服务器失败',{icon: 5});
			}
		});
		layui.layer.close(index);
	});
}
function editTreeNode() {
	hideRMenu();
	var target = zTree.getSelectedNodes()[0];
	layui.layer.open({
		type: 2,
		title: '编辑权限',
		content: 'authority/editPage?id='+target.id,
		area: ['700px','400px']
	});
}