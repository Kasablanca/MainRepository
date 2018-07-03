$(function (){
	$('.chosen-select').chosen();
	
	var laydate = layui.laydate;
	laydate.render({
		elem: '#start'
	});
	laydate.render({
		elem: '#end'
	});
	
	var table = layui.table;
	
	table.render({
		elem: '#main'
		//,height: 450
		,url: 'playerRemain/getPlayerRemain'
		,page: false //开启分页
		//,limit: 30
		,loading: true
		,text: {
			none: '暂无相关数据'
		}
		,where: {
			field: "date",
			sord: "asc"
		}
		,cols: [[ //表头
			{field: 'date', title: '日期', width:160, sort: true}
			,{field: 'day1', title: '新增角色数', width:160, sort: true}
			,{field: 'day2', title: '次日留存', width:160, sort: true,templet: function formatRate(value){
				if(!value.day2) return '0%';
				return new Number(value.day2*100).toFixed(2).toString()+'%';
			}}
			,{field: 'day3', title: '第3日留存', width:160, sort: true,templet: function formatRate(value){
				if(!value.day3) return '0%';
				return new Number(value.day3*100).toFixed(2).toString()+'%';
			}}
			,{field: 'day4', title: '第4日留存', width:160, sort: true,templet: function formatRate(value){
				if(!value.day4) return '0%';
				return new Number(value.day4*100).toFixed(2).toString()+'%';
			}}
			,{field: 'day5', title: '第5日留存', width:160, sort: true,templet: function formatRate(value){
				if(!value.day5) return '0%';
				return new Number(value.day5*100).toFixed(2).toString()+'%';
			}}
			,{field: 'day6', title: '第6日留存', width:160, sort: true,templet: function formatRate(value){
				if(!value.day6) return '0%';
				return new Number(value.day6*100).toFixed(2).toString()+'%';
			}}
			,{field: 'day7', title: '第7日留存', width:160, sort: true,templet: function formatRate(value){
				if(!value.day7) return '0%';
				return new Number(value.day7*100).toFixed(2).toString()+'%';
			}}
			,{field: 'day14', title: '第14日留存', width:160, sort: true,templet: function formatRate(value){
				if(!value.day14) return '0%';
				return new Number(value.day14*100).toFixed(2).toString()+'%';
			}}
			,{field: 'day30', title: '第30留存', width:160, sort: true,templet: function formatRate(value){
				if(!value.day30) return '0%';
				return new Number(value.day30*100).toFixed(2).toString()+'%';
			}}
		]]
		,done: function (response){
			$('#start').val(response.extra.start);
			$('#end').val(response.extra.end);
		}
	});

	table.on('sort(main)', function(obj){
		table.reload('main', {
			initSort: obj
			,where: {
				field: obj.field //排序字段
				,sord: obj.type //排序方式
			}
		});
	});
	$('#searchBtn').click(function (){
		table.reload('main', {
			url: 'playerRemain/getPlayerRemain?'+$.param($('#searchForm').serializeArray()),
		});
	});
	
	$('#platform').change(dropdownListCallback);
	$('#serverId').change(dropdownListCallback);
});