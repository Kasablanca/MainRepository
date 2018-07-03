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
		,height: 450
		,url: 'generalInfo/newValidUser/getNewValidUser'
		,page: true //开启分页
		,limit: 30
		,loading: true
		,text: {
			none: '暂无相关数据'
		}
		,where: {
			field: "date",
			sord: "asc"
		}
		,headers: {
			accept: 'application/json'
		}
		,cols: [[ //二级表头
			{field: 'date', title: '日期', sort: true,align: 'center'}
			,{field: 'newUserCount', title: '新增用户数', sort: false,align: 'center'}
			,{field: 'disposableCount', title: '一次性用户', sort: false,align: 'center'}
			,{field: 'commonlyCount', title: '一般用户', sort: false,align: 'center'}
			,{field: 'loyaltyCount', title: '忠实用户', sort: false,align: 'center'}
			,{field: 'disposableRate', title: '一次性用户占比', sort: false,align: 'center',templet: function (value){
				return (value.disposableRate*100).toFixed(2)+'%';
			}}
			,{field: 'commonlyRate', title: '一般用户占比', sort: false,align: 'center',templet: function (value){
				return (value.commonlyRate*100).toFixed(2)+'%';
			}}
			,{field: 'loyaltyRate', title: '忠实用户占比', sort: false,align: 'center',templet: function (value){
				return (value.loyaltyRate*100).toFixed(2)+'%';
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
			url: 'generalInfo/newValidUser/getNewValidUser?'+$.param($('#searchForm').serializeArray()),
		});
	});
	
	$('#platform').change(dropdownListCallback);
	$('#serverId').change(dropdownListCallback);
});