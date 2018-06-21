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
			,{field: 'totalRegistered', title: '总注册', width:120, sort: false,templet: function formatRate(value){
				if(value.totalRegistered){
					return value.totalRegistered;
				}
				return '--';
			},align: 'center'}
			,{field: 'totalRevenue', title: '总收入', width:130, sort: false,templet: function formatRate(value){
				return value.totalRevenue.toFixed(2);
			},align: 'center'}
			,{field: 'dailyRevenue', title: '日收入', width:120, sort: false,templet: function formatRate(value){
				return value.dailyRevenue.toFixed(2);
			},align: 'center'}
			,{field: 'dailyRevenueNew', title: '新用户', width:120, sort: false,templet: function formatRate(value){
				return value.dailyRevenueNew.toFixed(2);
			},align: 'center'}
			,{field: 'dailyRevenueOld', title: '老用户', width:120, sort: false,templet: function formatRate(value){
				return value.dailyRevenueOld.toFixed(2);
			},align: 'center'}
			,{field: 'liveUser', title: '活跃用户', width:120, sort: false,align: 'center'}
			,{field: 'liveUserNew', title: '活跃老用户', width:120, sort: false,align: 'center'}
			,{field: 'liveUserOld', title: '新增用户', width:120, sort: false,align: 'center'}
			,{field: 'retentionDay2', title: '次日', width:160, sort: true,templet: function formatRate(value){
				if(!value.retentionDay2) return '0%';
				return new Number(value.retentionDay2*100).toFixed(2).toString()+'%';
			},align: 'center'}
			,{field: 'retentionDay3', title: '3日', width:160, sort: true,templet: function formatRate(value){
				if(!value.retentionDay3) return '0%';
				return new Number(value.retentionDay3*100).toFixed(2).toString()+'%';
			},align: 'center'}
			,{field: 'retentionDay5', title: '5日', width:160, sort: true,templet: function formatRate(value){
				if(!value.retentionDay5) return '0%';
				return new Number(value.retentionDay5*100).toFixed(2).toString()+'%';
			},align: 'center'}
			,{field: 'retentionDay7', title: '7日', width:160, sort: true,templet: function formatRate(value){
				if(!value.retentionDay7) return '0%';
				return new Number(value.retentionDay7*100).toFixed(2).toString()+'%';
			},align: 'center'}
			,{field: 'retentionDay15', title: '15日', width:160, sort: true,templet: function formatRate(value){
				if(!value.retentionDay15) return '0%';
				return new Number(value.retentionDay15*100).toFixed(2).toString()+'%';
			},align: 'center'}
			,{field: 'retentionDay30', title: '30日', width:160, sort: true,templet: function formatRate(value){
				if(!value.retentionDay30) return '0%';
				return new Number(value.retentionDay30*100).toFixed(2).toString()+'%';
			},align: 'center'}
			,{field: 'arpu', title: 'ARPU', width:140, sort: false,templet: function formatRate(value){
				return value.arpu.toFixed(2);
			},align: 'center'}
			,{field: 'arppu', title: 'ARPPU', width:140, sort: false,templet: function formatRate(value){
				return value.arppu.toFixed(2);
			},align: 'center'}
			,{field: 'payUser', title: '充值用户数', width:140, sort: false,align: 'center'}
			,{field: 'payUserNew', title: '新用户', width:140, sort: false,align: 'center'}
			,{field: 'payUserOld', title: '老用户', width:140, sort: false,align: 'center'}
			,{field: 'payRate', title: '付费率', width:130, sort: false,templet: function formatRate(value){
				return value.payRate.toFixed(2);
			},align: 'center'}
			,{field: 'payRateNew', title: '新用户', width:130, sort: false,templet: function formatRate(value){
				return (value.payRateNew*100).toFixed(2)+'%';
			},align: 'center'}
			,{field: 'payRateOld', title: '老用户', width:130, sort: false,templet: function formatRate(value){
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
	
	$('#platform').change(function (target,trigger){
		var platformArray = $('#platform').val();
		if(platformArray && platformArray.length > 1){
			if(trigger.selected=='-1'){
				var options = $('#platform')[0].options;
				for(var i=0;i<options.length;i++){
					if(options[i].value != "-1"){
						options[i].selected = false;
					}
				}
				$('#platform').trigger('chosen:updated');
			} else { // 点击的其他渠道
				var options = $('#platform')[0].options;
				for(var i=0;i<options.length;i++){
					if(options[i].value == "-1" && options[i].selected == true){
						options[i].selected = false;
						break;
					}
				}
				$('#platform').trigger('chosen:updated');
			}
		}
	});
	$('#serverId').change(function (target,trigger){
		var serverIdArray = $('#serverId').val();
		if(serverIdArray && serverIdArray.length > 1){
			if(trigger.selected=='-1'){
				var options = $('#serverId')[0].options;
				for(var i=0;i<options.length;i++){
					if(options[i].value != "-1"){
						options[i].selected = false;
					}
				}
				$('#serverId').trigger('chosen:updated');
			} else { // 点击的其他渠道
				var options = $('#serverId')[0].options;
				for(var i=0;i<options.length;i++){
					if(options[i].value == "-1" && options[i].selected == true){
						options[i].selected = false;
						break;
					}
				}
				$('#serverId').trigger('chosen:updated');
			}
		}
	});
});