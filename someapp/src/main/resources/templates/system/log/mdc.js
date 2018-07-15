$(function (){
	var table = layui.table;
	
	table.render({
		elem: '#main'
		,height: 400
		,url: 'log/eventPropertyList'
		,page: true
		,limit: 30
		,loading: true
		,text: {
			none: '暂无相关数据'
		}
		,where: {
			field: "mappedKey",
			sord: "asc",
			eventId: eventId
		}
		,cols: [[
			{field: 'eventId', title: '事件ID', sort: true},
			{field: 'mappedKey', title: '键', sort: true},
			{field: 'mappedValue', title: '值', sort: true}
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