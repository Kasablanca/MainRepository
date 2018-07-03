$(function (){
	var table = layui.table;
	
	$('#searchBtn').click(function (){
		table.reload('main', {
			url: 'user/list?'+$.param($('#searchForm').serializeArray()),
		});
	});
	
	$('#insertBtn').click(function (){
		layer.open({
			type: 2,
			content: 'user/insertPage',
			id: 'user-insertPage',
			area: ['800px','600px'],
			title: '新增用户'
		});
	});
	
	table.render({
		elem: '#main'
		,height: 400
		,url: 'user/list'
		,page: true
		,limit: 30
		,loading: true
		,text: {
			none: '暂无相关数据'
		}
		,where: {
			field: "accName",
			sord: "asc"
		}
		,cols: [[
			{field: 'accName', title: '账号', sort: true},
			{field: 'username', title: '用户名', sort: true},
			{field: 'lastLoginTime', title: '上次登录时间', sort: true},
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
				content: 'user/updatePage?id='+data.id,
				id: 'user-updatePage',
				area: ['800px','600px'],
				title: '修改用户'
			});
		} else if(obj.event === 'delete'){
			layer.confirm('确定删除？', function(index){
				$.ajax({
					url: 'user/delete',
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