$(function (){
	var table = layui.table;
	
	$('#searchBtn').click(function (){
		table.reload('main', {
			url: 'authorityGroup/list?'+$.param($('#searchForm').serializeArray()),
		});
	});
	
	$('#insertBtn').click(function (){
		layer.open({
			type: 2,
			content: 'authorityGroup/insertPage',
			id: 'authorityGroup-insertPage',
			area: ['700px','700px'],
			title: '添加角色'
		});
	});
	
	table.render({
		elem: '#main'
		,height: 400
		,url: 'authorityGroup/list'
		,page: true
		,limit: 30
		,loading: true
		,text: {
			none: '暂无相关数据'
		}
		,where: {
			field: "groupName",
			sord: "asc"
		}
		,cols: [[
			{field: 'groupName', title: '角色名', sort: true},
			{title: '操作', align:'center', toolbar: '#actionBar'}
		]]
	});

	table.on('sort(main)', function(obj){
		table.reload('main', {
			initSort: obj
			,where: {
				field: obj.field
				,sord: obj.type
			}
		});
	});
	
	table.on('tool(main)', function(obj){
		var data = obj.data;
		if(obj.event === 'edit'){
			layer.open({
				type: 2,
				content: 'authorityGroup/updatePage?id='+data.id,
				id: 'authorityGroup-updatePage',
				area: ['700px','700px'],
				title: '修改角色'
			});
		} else if(obj.event === 'delete'){
			layer.confirm('确定删除？', function(index){
				$.ajax({
					url: 'authorityGroup/delete',
					method: 'post',
					data: {
						id: data.id,
						verNo: data.verNo
					},
					success: function(data) {
						if(data.code === 0){
							layer.msg('操作成功');
							table.reload('main');
						} else {
							layer.msg(data.msg,{icon: 5});
						}
					},
					error: function() {
						layer.msg('连接服务器失败',{icon: 5});
					}
				});
				layer.close(index);
			});
		}
	});
});