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
				text: '实时在线',
				left: 'center',
				top: '10'
			},
			tooltip: {
				trigger: 'axis'
			},
			legend: {
				data: ['实时在线'],
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
				name: '实时在线',
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

/**
 * 自动向服务器请求数据
 * @param end 数据截止日期
 * @returns 无
 */
function autoRefresh(end){
	if(end >= new Date().format('yyyy-MM-dd')){
		//说明查询内容包括今天，需要自动每5分钟请求服务器最新数据
		if(window.timedTask){
			//如果之前设置过定时任务，则取消
			clearTimeout(window.timedTask);
			window.timedTask = null;
		}
		window.timedTask = setTimeout(function (){
			$('#searchBtn').trigger('click');
		},300000);
	}
}

function thisAjax(chart,option,data){
	echartsAjax({
		chart: chart,
		url: 'generalInfo/realTimeOnlineChart/getRealTimeOnline',
		data: data,
		success: function (data){
			chart.hideLoading();
			if(data.code == 0){
				// 说明获取数据成功
				var header = [];
				var value = [];
				for(var v of data.data.response){
					header.push(v.time);
					value.push(v.count);
				}
				option.xAxis[0].data = header;
				option.series[0].data = value;
				chart.setOption(option);
				
				autoRefresh(data.data.request.end);
				
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