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
				text: '活跃用户'
			},
			tooltip: {
				trigger: 'axis'
			},
			legend: {
				data: ['活跃用户']
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
	
	ajax(myChart,null,option);
	
	$('#searchBtn').click(function (){
		ajax(myChart,$.param($('#searchForm').serializeArray()),option);
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

function ajax(myChart,payload,option){
	myChart.showLoading();
	$.ajax({
		url: 'activeUser/getDailyActiveUser'
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