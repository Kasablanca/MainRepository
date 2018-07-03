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
				text: '新增用户数',
				left: 'center',
				top: '10'
			},
			tooltip: {
				trigger: 'axis'
			},
			legend: {
				data: ['新增角色','新增账户'],
				left: 'center',
				bottom: '10'
			},
			calculable : true,
			xAxis: {
				boundaryGap : true,
				data: []
			},
			yAxis: {
				type: 'value'
			},
			series: [{
				name: '新增角色',
				type: 'bar',
				itemStyle: { 
					normal: {
						label: {
							show: false
						}
					}
				},
				data: []
			},{
				name: '新增账户',
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
		url: 'newUser/getDailyNewUser',
		data: data,
		success: function (data){
			chart.hideLoading();
			if(data.code == 0){
				// 说明获取数据成功
				var role = {
						header: [],
						value: []
				};
				var account = {
						header: [],
						value: []
				};
				for(var v of data.data.response.newRole){
					role.header.push(v.date);
					role.value.push(v.count);
				}
				for(var v of data.data.response.newAccount){
					account.header.push(v.date);
					account.value.push(v.count);
				}
				option.xAxis.data = role.header;
				option.series[0].data = role.value;
				option.series[1].data = account.value;
				chart.setOption(option);
				
				if(data.data.response.newRole.length == 0 && data.data.response.newAccount){
					layer.msg('暂无数据');
				}
				
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
	});
}