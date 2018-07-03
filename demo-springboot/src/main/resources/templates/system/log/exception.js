$(function (){
	var table = layui.table;
	
	table.render({
		elem: '#main'
		,height: 400
		,url: 'log/eventExceptionList'
		,page: true
		,limit: 30
		,loading: true
		,text: {
			none: '暂无相关数据'
		}
		,where: {
			field: "i",
			sord: "asc"
		}
		,cols: [[
			{field: 'eventId', title: '事件ID', sort: true},
			{field: 'i', title: '序号', sort: true},
			{field: 'trace_line', title: '异常行', sort: true}
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
});