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
		,url: 'generalInfo/generalOnlineInfo/getGeneralOnlineInfo'
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
		,cols: [/*[//一级表头
				{title: '时间',sort: false,align: 'center'},
				{title: '注册',sort: false,align: 'center'},
				{title: '收入金额(美元)',colspan: 4,sort: false,align: 'center'},
				{title: '用户',colspan: 3,sort: false,align: 'center'},
				{title: '留存率(%)',colspan: 6,sort: false,align: 'center'},
				{title: '美元',colspan: 2,sort: false,align: 'center'},
				{title: '充值用户数',colspan: 3,sort: false,align: 'center'},
				{title: '付费率(%)',colspan: 3,sort: false,align: 'center'}
			],*/
			[ //二级表头
			{field: 'date', title: '日期', width:120, sort: false,align: 'center'}
			,{field: 'totalDuration', title: '平均在线', width:120, sort: false,align: 'center',templet: function (value){
				return value.totalDuration.toFixed(2);
			}}
			,{field: 'maxOnline', title: '最高在线', width:120, sort: false,align: 'center'}
			,{field: 'liveness', title: '活跃用户', width:120, sort: false,align: 'center'}
			,{field: 'loginCount', title: '日登陆次数', width:140, sort: false,align: 'center'}
			,{field: 'loginCountAvg', title: '平均登陆次数', width:140, sort: false,templet: function (value){
				return value.loginCountAvg.toFixed(2);
			},align: 'center'}
			,{field: 'newPlayerCount', title: '日新增角色数', width:140, sort: false,align: 'center'}
			,{field: 'newUserCount', title: '日新增账户数', width:140, sort: false,templet: function (value){
				if(value.newUserCount != undefined){
					return value.newUserCount;
				}
				return '--';
			},align: 'center'}
			,{field: 'backUserCount', title: '回归用户', width:120, sort: false,align: 'center'}
			,{field: 'onlineTimeAvg', title: '平均在线时长(分钟)', width:160, sort: false,templet: function (value){
				return value.onlineTimeAvg.toFixed(2);
			},align: 'center'}
			,{field: 'totalDurationNew', title: '新用户在线时长(分钟)', width:170, sort: false,align: 'center'}
			,{field: 'totalDurationOld', title: '老用户在线时长(分钟)', width:170, sort: false,align: 'center'}
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
			url: 'generalInfo/generalOnlineInfo/getGeneralOnlineInfo?'+$.param($('#searchForm').serializeArray()),
		});
	});
	
	$('#platform').change(dropdownListCallback);
	$('#serverId').change(dropdownListCallback);
});