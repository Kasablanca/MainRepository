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
				text: '活跃用户',
				left: 'center',
				top: '10'
			},
			tooltip: {
				trigger: 'axis'
			},
			legend: {
				data: ['活跃用户'],
				left: 'center',
				bottom: '10'
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
				name: '活跃用户',
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
	
	thisAjax(myChart,option,null);
	$('#searchBtn').click(function (){
		thisAjax(myChart,option,$.param($('#searchForm').serializeArray()));
	});
	
	$('#platform').change(dropdownListCallback);
	$('#serverId').change(dropdownListCallback);
});

function thisAjax(chart,option,data){
	echartsAjax({
		chart: chart,
		url: 'activeUser/getDailyActiveUser',
		data: data,
		success: function (data){
			chart.hideLoading();
			if(data.code == 0){
				// 说明获取数据成功
				var header = [];
				var value = [];
				for(var v of data.data.response){
					header.push(v.date);
					value.push(v.count);
				}
				option.xAxis[0].data = header;
				option.series[0].data = value;
				chart.setOption(option);
				
				if(!$('#start').val()){
					$('#start').val(data.data.request.start);
				}
				if(!$('#end').val()){
					$('#end').val(data.data.request.end);
				}
				
				if(value.length == 0){
					layer.msg('暂无数据');
				}
			} else {
				// 说明获取数据失败
				layer.msg('获取图表数据失败：'+data.message, {icon: 5});
			}
		}
	});
}
