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
		,url: 'kpiPay/getKpiPay'
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
		,cols: [[ //表头
			{field: 'date', title: '日期', width:120, sort: false}
			,{field: 'activeUserNumber', title: '活跃用户', width:120, sort: false}
			,{field: 'revenue', title: '每日收入', width:120, sort: false,templet: function formatRate(value){
				return value.revenue.toFixed(0);
			}}
			,{field: 'payUserNumber', title: '付费用户数', width:130, sort: false}
			,{field: 'payArppu', title: '付费ARPPU', width:120, sort: false,templet: function formatRate(value){
				return value.payArppu.toFixed(2);
			}}
			,{field: 'payRate', title: '付费率', width:120, sort: false,templet: function formatRate(value){
				return (value.payRate*100).toFixed(2)+'%';
			}}
			,{field: 'activeArpu', title: '活跃ARPU', width:120, sort: false,templet: function formatRate(value){
				return value.activeArpu.toFixed(2);
			}}
			,{field: 'oldPlayerNumber', title: '老玩家数量', width:120, sort: false}
			,{field: 'oldPayRevenue', title: '老玩家付费额', width:140, sort: false,templet: function formatRate(value){
				return value.oldPayRevenue.toFixed(2);
			}}
			,{field: 'oldPayNumber', title: '老玩家付费人数', width:150, sort: false}
			,{field: 'payArppuOld', title: '老玩家ARPPU', width:140, sort: false,templet: function formatRate(value){
				return value.payArppuOld.toFixed(2);
			}}
			,{field: 'payRateOld', title: '老玩家付费率', width:140, sort: false,templet: function formatRate(value){
				return (value.payRateOld*100).toFixed(2)+'%';
			}}
			,{field: 'activeArpuOld', title: '老玩家ARPU', width:140, sort: false,templet: function formatRate(value){
				return value.activeArpuOld.toFixed(2);
			}}
			,{field: 'newPlayerNumber', title: '新增角色数', width:130, sort: false}
			,{field: 'newPayRevenue', title: '新增付费额', width:140, sort: false,templet: function formatRate(value){
				return value.newPayRevenue.toFixed(2);
			}}
			,{field: 'newPayNumber', title: '新增付费人数', width:140, sort: false}
			,{field: 'payArppuNew', title: '新增ARPPU', width:130, sort: false,templet: function formatRate(value){
				return value.payArppuNew.toFixed(2);
			}}
			,{field: 'payRateNew', title: '首日付费转化率', width:150, sort: false,templet: function formatRate(value){
				return (value.payRateNew*100).toFixed(2)+'%';
			}}
			,{field: 'activeArpuNew', title: '新增ARPU', width:120, sort: false,templet: function formatRate(value){
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