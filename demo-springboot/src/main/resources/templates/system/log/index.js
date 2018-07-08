$(function (){
	var table = layui.table;
	
	$('#searchBtn').click(function (){
		table.reload('main', {
			url: 'log/eventList?'+$.param($('#searchForm').serializeArray()),
		});
	});
	
	table.render({
		elem: '#main'
		//,height: 400
		,url: 'log/eventList'
		,page: true
		,limit: 30
		,loading: true
		,text: {
			none: '暂无相关数据'
		}
		,where: {
			field: "timestmp",
			sord: "desc"
		}
		,size: 'sm'
		//,skin: 'line'
		,even: true
		,cols: [[
			{field: 'eventId', title: '事件ID', sort: true, width: 80},
			{field: 'timestmp', title: '时间', sort: true, templet: function (row){
				return new Date(row.timestmp).format('yyyy-MM-dd hh:mm:ss');
			}, width: 150},
			{field: 'loggerName', title: '记录器名', sort: true},
			{field: 'levelString', title: '日志级别', sort: true, width: 85},
			{field: 'threadName', title: '线程名', sort: true},
			{field: 'arg0', title: '参数0', sort: true},
			{field: 'arg1', title: '参数1', sort: true},
			{field: 'arg2', title: '参数2', sort: true},
			{field: 'arg3', title: '参数3', sort: true},
			{field: 'callerFilename', title: '调用者文件名', sort: true},
			{field: 'callerClass', title: '调用者类名', sort: true},
			{field: 'callerMethod', title: '调用者方法', sort: true},
			{field: 'callerLine', title: '调用者行', sort: true},
			{field: 'formattedMessage', title: '日志内容', sort: false},
			{title: '操作', align:'center', toolbar: '#actionBar', width: 160}
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
		if(obj.event === 'mdc' && (obj.data.referenceFlag == 1 || obj.data.referenceFlag == 3)){
			layer.open({
				type: 2,
				content: 'log/eventPropertyPage?eventId='+data.eventId,
				id: 'eventPropertyPage',
				area: ['800px','600px'],
				title: 'MDC列表'
			});
		} else if(obj.event === 'exception' && (obj.data.referenceFlag == 2 || obj.data.referenceFlag == 3)){
			$.ajax({
				url: 'log/eventExceptionList',
				method: 'get',
				data: {
					eventId: data.eventId
				},
				success: function (response){
					var content = '';
					if(response.code == 0){
						if(response.data.length > 0){
							content = response.data[0].traceLine;
						}
						for(var i = 1, length = response.data.length; i < length; i++){
							content += '<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + response.data[i].traceLine;
						}
						layer.open({
							type: 1,
							content: content,
							area: ['800px','600px'],
							title: '异常栈'
						});
					} else {
						layer.msg(response.msg,{icon: 5});
					}
				},
				error: function (){
					layer.msg('连接失败！',{icon: 5});
				}
			});
		}
	});
});