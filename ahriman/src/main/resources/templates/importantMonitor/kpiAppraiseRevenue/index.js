$(function (){
	$('.chosen-select').chosen();
	
	var laydate = layui.laydate;
	laydate.render({
		elem: '#start'
	});
	laydate.render({
		elem: '#end'
	});
	
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init($('#main')[0]);
	// 指定图表的配置项和数据
	var option = {
			title: {
				text: 'KPI考核收入'
			},
			tooltip: {
				trigger: 'axis'
			},
			legend: {
				data: ['KPI考核收入']
			},
			calculable : true,
			xAxis: [{
				boundaryGap : false,
				data: []
			}],
			yAxis: [{
				type: 'value'
			}],
			series: [{
				name: 'KPI考核收入',
				type: 'line',
				itemStyle: { 
					normal: {
						label: {
							show: false
						}
					}
				},
				data: []
			}]
	};
	$.get("/kpiAppraiseRevenue/getDailyRevenue",function (data){
		if(data.code == 0){
			// 说明获取数据成功
			var header = [];
			var value = [];
			for(var v of data.data){
				header.push(v.date);
				value.push(v.money);
			}
			option.xAxis[0].data = header;
			option.series[0].data = value;
			myChart.setOption(option);
		} else {
			// 说明获取数据失败
			layer.msg('获取图标数据失败：'+data.message, {icon: 5});
		}
	});
/*
	layui.form.on('submit(search)', function (data){
		$.ajax({
			url: '/kpiAppraiseRevenue/getDailyRevenue'
			,data: data.field
			,method: 'post'
			,success: function (data){
				if(data.code == 0){
					// 说明获取数据成功
					var header = [];
					var value = [];
					for(var v of data.data){
						header.push(v.date);
						value.push(v.money);
					}
					option.xAxis.data = header;
					option.series[0].data = value;
					myChart.setOption(option);
				} else {
					// 说明获取数据失败
					layer.msg('获取图标数据失败：'+data.msg, {icon: 5});
				}
			}
			,error: function (){
				// 说明连接服务器失败
				layer.msg('连接服务器失败', {icon: 5});
			}
		});
	});*/
	$('#searchBtn').click(function (){
		myChart.showLoading();
		$.ajax({
			url: '/kpiAppraiseRevenue/getDailyRevenue'
			,data: $.param($('#searchForm').serializeArray())
			,method: 'post'
			,success: function (data){
				myChart.hideLoading();
				if(data.code == 0){
					// 说明获取数据成功
					var header = [];
					var value = [];
					for(var v of data.data){
						header.push(v.date);
						value.push(v.money);
					}
					option.xAxis[0].data = header;
					option.series[0].data = value;
					myChart.setOption(option);
				} else {
					// 说明获取数据失败
					layer.msg('获取图标数据失败：'+data.message, {icon: 5});
				}
			}
			,error: function (){
				myChart.hideLoading();
				// 说明连接服务器失败
				layer.msg('连接服务器失败', {icon: 5});
			}
		});
	});
});