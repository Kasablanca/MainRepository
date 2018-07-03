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
		//,height: 750
		,url: 'kpiPay/getKpiPay'
		,page: false //不开启分页
		//,limit: 30
		,loading: true
		,text: {
			none: '暂无相关数据'
		}
		,where: {
			field: "date",
			sord: "asc"
		}
		,size: 'sm'
		,cols: [[ //表头
			{field: 'date', title: '日期',sort: true}
			,{field: 'activeUserNumber', title: '活跃用户', sort: true}
			,{field: 'revenue', title: '每日收入', sort: true,templet: function formatRate(value){
				return value.revenue.toFixed(0);
			}}
			,{field: 'payUserNumber', title: '付费用户数', sort: true}
			,{field: 'payArppu', title: '付费ARPPU', sort: false,templet: function formatRate(value){
				return value.payArppu.toFixed(2);
			}}
			,{field: 'payRate', title: '付费率', sort: false,templet: function formatRate(value){
				return (value.payRate*100).toFixed(2)+'%';
			}}
			,{field: 'activeArpu', title: '活跃ARPU', sort: false,templet: function formatRate(value){
				return value.activeArpu.toFixed(2);
			}}
			,{field: 'oldPlayerNumber', title: '老玩家数量', sort: false}
			,{field: 'oldPayRevenue', title: '老玩家付费额', sort: false,templet: function formatRate(value){
				return value.oldPayRevenue.toFixed(2);
			}}
			,{field: 'oldPayNumber', title: '老玩家付费人数', sort: false}
			,{field: 'payArppuOld', title: '老玩家ARPPU', sort: false,templet: function formatRate(value){
				return value.payArppuOld.toFixed(2);
			}}
			,{field: 'payRateOld', title: '老玩家付费率', sort: false,templet: function formatRate(value){
				return (value.payRateOld*100).toFixed(2)+'%';
			}}
			,{field: 'activeArpuOld', title: '老玩家ARPU', sort: false,templet: function formatRate(value){
				return value.activeArpuOld.toFixed(2);
			}}
			,{field: 'newPlayerNumber', title: '新增角色数', sort: false}
			,{field: 'newPayRevenue', title: '新增付费额', sort: false,templet: function formatRate(value){
				return value.newPayRevenue.toFixed(2);
			}}
			,{field: 'newPayNumber', title: '新增付费人数', sort: false}
			,{field: 'payArppuNew', title: '新增ARPPU', sort: false,templet: function formatRate(value){
				return value.payArppuNew.toFixed(2);
			}}
			,{field: 'payRateNew', title: '首日付费转化率', sort: false,templet: function formatRate(value){
				return (value.payRateNew*100).toFixed(2)+'%';
			},style:'font-size:12px;'}
			,{field: 'activeArpuNew', title: '新增ARPU', sort: false,templet: function formatRate(value){
				return value.activeArpuNew.toFixed(2);
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
			url: 'kpiPay/getKpiPay?'+$.param($('#searchForm').serializeArray()),
		});
	});
	
	$('#platform').change(dropdownListCallback);
	$('#serverId').change(dropdownListCallback);
});