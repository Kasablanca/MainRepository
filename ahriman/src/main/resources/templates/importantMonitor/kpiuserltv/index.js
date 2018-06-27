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
		,url: 'userltv/getKpiUserLtv'
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
			,{field: 'newUserNumber', title: '导入用户数量', width:120, sort: false}
			,{field: 'totalMoney', title: '累计收入', width:120, sort: false,templet: function formatRate(value){
				return value.totalMoney.toFixed(2);
			}}
			,{field: 'day1', title: '1日LTV', width:120, sort: false,templet: function formatRate(value){
				if(value.day1 == null) return 0;
				return value.day1.toFixed(2);
			}}
			,{field: 'day2', title: '2日LTV', width:120, sort: false,templet: function formatRate(value){
				if(value.day2 == null) return 0;
				return value.day2.toFixed(2);
			}}
			,{field: 'day3', title: '3日LTV', width:120, sort: false,templet: function formatRate(value){
				if(value.day3 == null) return 0;
				return value.day3.toFixed(2);
			}}
			,{field: 'day4', title: '4日LTV', width:120, sort: false,templet: function formatRate(value){
				if(value.day4 == null) return 0;
				return value.day4.toFixed(2);
			}}
			,{field: 'day5', title: '5日LTV', width:120, sort: false,templet: function formatRate(value){
				if(value.day5 == null) return 0;
				return value.day5.toFixed(2);
			}}
			,{field: 'day6', title: '6日LTV', width:120, sort: false,templet: function formatRate(value){
				if(value.day6 == null) return 0;
				return value.day6.toFixed(2);
			}}
			,{field: 'day7', title: '<font color="red">7日LTV</font>', width:120, sort: false,templet: function formatRate(value){
				if(value.day7 == null) return 0;
				return '<font color="red">'+value.day7.toFixed(2)+'</font>';
			}}
			,{field: 'day8', title: '8日LTV', width:120, sort: false,templet: function formatRate(value){
				if(value.day8 == null) return 0;
				return value.day8.toFixed(2);
			}}
			,{field: 'day9', title: '9日LTV', width:120, sort: false,templet: function formatRate(value){
				if(value.day9 == null) return 0;
				return value.day9.toFixed(2);
			}}
			,{field: 'day10', title: '10日LTV', width:120, sort: false,templet: function formatRate(value){
				if(!value.day10) return '';
				return value.day10.toFixed(2);
			}}
			,{field: 'day11', title: '11日LTV', width:120, sort: false,templet: function formatRate(value){
				if(value.day11 == null) return 0;
				return value.day11.toFixed(2);
			}}
			,{field: 'day12', title: '12日LTV', width:120, sort: false,templet: function formatRate(value){
				if(value.day12 == null) return 0;
				return value.day12.toFixed(2);
			}}
			,{field: 'day13', title: '13日LTV', width:120, sort: false,templet: function formatRate(value){
				if(value.day13 == null) return 0;
				return value.day13.toFixed(2);
			}}
			,{field: 'day14', title: '<font color="red">14日LTV</font>', width:120, sort: false,templet: function formatRate(value){
				if(value.day14 == null) return 0;
				return '<font color="red">'+value.day14.toFixed(2)+'</font>';
			}}
			,{field: 'day15', title: '15日LTV', width:120, sort: false,templet: function formatRate(value){
				if(value.day15 == null) return 0;
				return value.day15.toFixed(2);
			}}
			,{field: 'day16', title: '16日LTV', width:120, sort: false,templet: function formatRate(value){
				if(value.day16 == null) return 0;
				return value.day16.toFixed(2);
			}}
			,{field: 'day17', title: '17日LTV', width:120, sort: false,templet: function formatRate(value){
				if(value.day17 == null) return 0;
				return value.day17.toFixed(2);
			}}
			,{field: 'day18', title: '18日LTV', width:120, sort: false,templet: function formatRate(value){
				if(value.day18 == null) return 0;
				return value.day18.toFixed(2);
			}}
			,{field: 'day19', title: '19日LTV', width:120, sort: false,templet: function formatRate(value){
				if(value.day19 == null) return 0;
				return value.day19.toFixed(2);
			}}
			,{field: 'day20', title: '20日LTV', width:120, sort: false,templet: function formatRate(value){
				if(value.day20 == null) return 0;
				return value.day20.toFixed(2);
			}}
			,{field: 'day21', title: '21日LTV', width:120, sort: false,templet: function formatRate(value){
				if(value.day21 == null) return 0;
				return value.day21.toFixed(2);
			}}
			,{field: 'day22', title: '22日LTV', width:120, sort: false,templet: function formatRate(value){
				if(value.day22 == null) return 0;
				return value.day22.toFixed(2);
			}}
			,{field: 'day23', title: '23日LTV', width:120, sort: false,templet: function formatRate(value){
				if(value.day23 == null) return 0;
				return value.day23.toFixed(2);
			}}
			,{field: 'day24', title: '24日LTV', width:120, sort: false,templet: function formatRate(value){
				if(value.day24 == null) return 0;
				return value.day24.toFixed(2);
			}}
			,{field: 'day25', title: '25日LTV', width:120, sort: false,templet: function formatRate(value){
				if(value.day25 == null) return 0;
				return value.day25.toFixed(2);
			}}
			,{field: 'day26', title: '26日LTV', width:120, sort: false,templet: function formatRate(value){
				if(value.day26 == null) return 0;
				return value.day26.toFixed(2);
			}}
			,{field: 'day27', title: '27日LTV', width:120, sort: false,templet: function formatRate(value){
				if(value.day27 == null) return 0;
				return value.day27.toFixed(2);
			}}
			,{field: 'day28', title: '28日LTV', width:120, sort: false,templet: function formatRate(value){
				if(value.day28 == null) return 0;
				return value.day28.toFixed(2);
			}}
			,{field: 'day29', title: '29日LTV', width:120, sort: false,templet: function formatRate(value){
				if(value.day29 == null) return 0;
				return value.day29.toFixed(2);
			}}
			,{field: 'day30', title: '<font color="red">30日LTV</font>', width:120, sort: false,templet: function formatRate(value){
				if(value.day30 == null) return 0;
				return '<font color="red">'+value.day30.toFixed(2)+'</font>';
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
			url: 'userltv/getKpiUserLtv?'+$.param($('#searchForm').serializeArray()),
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