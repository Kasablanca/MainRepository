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
	$.get("newUser/getDailyNewUser",function (data){
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
			//option.xAxis[1].data = account.header;
			option.series[1].data = account.value;
			myChart.setOption(option);
			$('#start').val(data.data.request.start);
			$('#end').val(data.data.request.end);
		} else {
			// 说明获取数据失败
			layer.msg('获取图表数据失败：'+data.message, {icon: 5});
		}
	});
	
	$('#searchBtn').click(function (){
		myChart.showLoading();
		$.ajax({
			url: 'newUser/getDailyNewUser'
			,data: $.param($('#searchForm').serializeArray())
			,method: 'post'
			,success: function (data){
				myChart.hideLoading();
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
					//option.xAxis[1].data = account.header;
					option.series[1].data = account.value;
					myChart.setOption(option);
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
	});
	$('#platform').change(dropdownListCallback);
	$('#serverId').change(dropdownListCallback);
});