var lastSelected = null;
var defaultNodeId = 0;
$(function (){
	$('#tt').tree({
		url: 'menu/menutree',
		method: 'get',
		animate: true,
		onContextMenu: function(e,node){
			e.preventDefault();
			$('#mm').menu('show',{
				left: e.pageX,
				top: e.pageY
			});
		},
		loadFilter: function(data,parent){
			if(data.code == 0)
				return data.data;
			return data;
		},
		onDblClick: function(node){
			$(this).tree('beginEdit',node.target);
		},
		onClick: function(node){
			if(lastSelected == node) {
				$('#tt').tree('select', null);
				lastSelected = null;
			} else {
				$('#tt').tree('select', node.target);
				lastSelected = node;
			}
		},
		onAfterEdit: function(node){
			if(node.id == defaultNodeId){
				var tt = $('#tt');
				$.ajax({
					url: 'menu/add',
					method: 'POST',
					data: {
						parentId: node.parentId,
						text: node.text
					},
					success: function(data){
						if(data.code == 0){
							tt.tree('update',{
								target: node.target,
								id: data.data
							});
						} else if(data.code != undefined){
							alert(data.message);
							tt.tree('remove',node.target);
						} else{
							tt.tree('remove',node.target);
						}
					},
					error: function(){
						tt.tree('remove',node.target);
						alert('连接服务器出错');
					}
				});
			} else{
				var tt = $('#tt');
				$.ajax({
					url: 'menu/edit',
					method: 'POST',
					data: {
						id: node.id,
						text: node.text
					},
					success: function(data){
						if(data.code == 0){
							//nothing to do
						} else if(data.code != undefined){
							alert(data.message);
							tt.tree('cancelEdit',node.target);
						} else{
							tt.tree('cancelEdit',node.target);
						}
					},
					error: function(){
						alert('连接服务器出错');
					}
				});
			}
		}
	});
});

function append(){
	var t = $('#tt');
	var node = t.tree('getSelected');
	t.tree('append', {
		parent: node?node.target:null,
		data: [{
			id: defaultNodeId,
			text: node?node.text+'子菜单':'顶级菜单',
			parentId: node?node.id:null
		}]
	});
	node = t.tree('find',defaultNodeId);
    t.tree("select",node.target).tree('beginEdit',node.target);
}
function removeit(){
	var t = $('#tt');
	var node = t.tree('getSelected');
	$.ajax({
		url: 'menu/delete',
		method: 'POST',
		data: {
			menuId: node.id
		},
		success: function(data){
			if(data.code == 0){
				t.tree('remove', node.target);
			} else if(data.code != undefined){
				alert(data.message);
			} else{
				//nothing to do
			}
		},
		error: function(){
			alert('连接服务器出错');
		}
	});
}
function collapse(){
	var node = $('#tt').tree('getSelected');
	$('#tt').tree('collapse',node.target);
}
function expand(){
	var node = $('#tt').tree('getSelected');
	$('#tt').tree('expand',node.target);
}