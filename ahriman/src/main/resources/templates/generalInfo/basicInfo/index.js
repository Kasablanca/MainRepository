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
		,url: 'generalInfo/basicInfo/getBasicInfo'
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
		,cols: [[//一级表头
			{title: '日期',field: 'date', width:120, align: 'center',sort: false,rowspan: 2},
			{title: '安装注册',align: 'center',colspan: 3},
			{title: '总收入',align: 'center',colspan: 4},
			{title: '活跃用户',align: 'center',colspan: 3},
			{title: '次日',align: 'center',colspan: 6},
			{title: '美元',align: 'center',colspan: 2},
			{title: '充值用户数',align: 'center',colspan: 3},
			{title: '付费率',align: 'center',colspan: 3}
		],
		[ //二级表头
			{field: 'date', title: '日期', width:120, sort: false,align: 'center'}
			,{field: 'totalInstalled', title: '总安装', width:120, sort: false,align: 'center',templet: function (value){
				return 0;
			}}
			,{field: 'newInstalled', title: '新安装', width:120, sort: false,align: 'center',templet: function (value){
				return 0;
			}}
			,{field: 'totalRegistered', title: '总注册', width:120, sort: false,templet: function (value){
				if(value.totalRegistered){
					return value.totalRegistered;
				}
				return '--';
			},align: 'center'}
			,{field: 'totalRevenue', title: '总收入', width:130, sort: false,templet: function (value){
				return value.totalRevenue.toFixed(0);
			},align: 'center'}
			,{field: 'dailyRevenue', title: '日收入', width:120, sort: false,templet: function (value){
				return value.dailyRevenue.toFixed(0);
			},align: 'center'}
			,{field: 'dailyRevenueNew', title: '新用户', width:120, sort: false,templet: function (value){
				return value.dailyRevenueNew.toFixed(0);
			},align: 'center'}
			,{field: 'dailyRevenueOld', title: '老用户', width:120, sort: false,templet: function (value){
				return value.dailyRevenueOld.toFixed(0);
			},align: 'center'}
			,{field: 'liveUser', title: '活跃用户', width:120, sort: false,align: 'center'}
			,{field: 'liveUserNew', title: '活跃老用户', width:120, sort: false,align: 'center'}
			,{field: 'liveUserOld', title: '新增用户', width:120, sort: false,align: 'center'}
			,{field: 'retentionDay2', title: '次日', width:160, sort: true,templet: function (value){
				if(!value.retentionDay2) return '0%';
				return new Number(value.retentionDay2*100).toFixed(2).toString()+'%';
			},align: 'center'}
			,{field: 'retentionDay3', title: '3日', width:160, sort: true,templet: function (value){
				if(!value.retentionDay3) return '0%';
				return new Number(value.retentionDay3*100).toFixed(2).toString()+'%';
			},align: 'center'}
			,{field: 'retentionDay5', title: '5日', width:160, sort: true,templet: function (value){
				if(!value.retentionDay5) return '0%';
				return new Number(value.retentionDay5*100).toFixed(2).toString()+'%';
			},align: 'center'}
			,{field: 'retentionDay7', title: '7日', width:160, sort: true,templet: function (value){
				if(!value.retentionDay7) return '0%';
				return new Number(value.retentionDay7*100).toFixed(2).toString()+'%';
			},align: 'center'}
			,{field: 'retentionDay15', title: '15日', width:160, sort: true,templet: function (value){
				if(!value.retentionDay15) return '0%';
				return new Number(value.retentionDay15*100).toFixed(2).toString()+'%';
			},align: 'center'}
			,{field: 'retentionDay30', title: '30日', width:160, sort: true,templet: function (value){
				if(!value.retentionDay30) return '0%';
				return new Number(value.retentionDay30*100).toFixed(2).toString()+'%';
			},align: 'center'}
			,{field: 'arpu', title: 'ARPU', width:140, sort: false,templet: function (value){
				return value.arpu.toFixed(2);
			},align: 'center'}
			,{field: 'arppu', title: 'ARPPU', width:140, sort: false,templet: function (value){
				return value.arppu.toFixed(2);
			},align: 'center'}
			,{field: 'payUser', title: '充值用户数', width:140, sort: false,align: 'center'}
			,{field: 'payUserNew', title: '新用户', width:140, sort: false,align: 'center'}
			,{field: 'payUserOld', title: '老用户', width:140, sort: false,align: 'center'}
			,{field: 'payRate', title: '付费率', width:130, sort: false,templet: function (value){
				return (value.payRate*100).toFixed(2)+'%';
			},align: 'center'}
			,{field: 'payRateNew', title: '新用户', width:130, sort: false,templet: function (value){
				return (value.payRateNew*100).toFixed(2)+'%';
			},align: 'center'}
			,{field: 'payRateOld', title: '老用户', width:130, sort: false,templet: function (value){
				return (value.payRateOld*100).toFixed(2)+'%';
			},align: 'center'}
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
			url: 'generalInfo/basicInfo/getBasicInfo?'+$.param($('#searchForm').serializeArray()),
		});
	});
	
	$('#platform').change(dropdownListCallback);
	$('#serverId').change(dropdownListCallback);
});