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
				text: 'KPI考核收入',
				left: 'center',
				top: '10'
			},
			tooltip: {
				trigger: 'axis'
			},
			legend: {
				data: ['KPI考核收入'],
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
	
	ajax(myChart,null,option);

	layui.form.on('submit(search)', function (data){
		ajax(myChart,$.param($('#searchForm').serializeArray()),option);
	});
	
	$('#platform').change(dropdownListCallback);
	$('#serverId').change(dropdownListCallback);
});

function ajax(myChart,payload,option){
	myChart.showLoading();
	$.ajax({
		url: 'kpiAppraiseRevenue/getDailyRevenue'
		,data: payload
		,dataType: 'json'
		,method: 'post'
		,success: function (data){
			myChart.hideLoading();
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
				myChart.setOption(option);
				
				if(!$('#start').val()){
					$('#start').val(data.data.request.start);
				}
				if(!$('#end').val()){
					$('#end').val(data.data.request.end);
				}
			} else {
				// 说明获取数据失败
				layer.msg('获取图表数据失败：'+data.message, {icon: 5});
			}
		}
		,error: function (){
			myChart.hideLoading();
			// 说明连接服务器失败
			layer.msg('连接服务器失败', {icon: 5});
		}
	});
}