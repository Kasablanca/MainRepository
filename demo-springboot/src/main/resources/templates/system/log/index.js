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
			sord: "asc"
		}
		,cols: [[
			{field: 'eventId', title: '事件ID', sort: true},
			{field: 'timestmp', title: '时间', sort: true},
			{field: 'loggerName', title: '记录器名', sort: true},
			{field: 'levelString', title: '日志级别', sort: true},
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
		console.log(obj);
		var data = obj.data;
		if(obj.event === 'mdc' && (obj.data.referenceFlag == 1 || obj.data.referenceFlag == 3)){
			layer.open({
				type: 2,
				content: 'log/eventPropertyPage?id='+data.eventId,
				id: 'eventPropertyPage',
				area: ['800px','600px'],
				title: 'MDC列表'
			});
		} else if(obj.event === 'exception' && (obj.data.referenceFlag == 2 || obj.data.referenceFlag == 3)){
			layer.open({
				type: 2,
				content: 'log/eventExceptionPage?id='+data.eventId,
				id: 'eventExceptionPage',
				area: ['800px','600px'],
				title: '异常栈'
			});
		}
	});
});