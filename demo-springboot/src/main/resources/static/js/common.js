/**
 * 下拉多选框回调函数
 * @param target 多选框dom
 * @param trigger 触发change的dom
 */
function dropdownListCallback(target,trigger){
	var array = $(this).val();
	if(array && array.length > 1){
		if(trigger.selected=='-1'){
			var options = $(this)[0].options;
			for(var i=0;i<options.length;i++){
				if(options[i].value != "-1"){
					options[i].selected = false;
				}
			}
			$(this).trigger('chosen:updated');
		} else { // 点击的其他渠道
			var options = $(this)[0].options;
			for(var i=0;i<options.length;i++){
				if(options[i].value == "-1" && options[i].selected == true){
					options[i].selected = false;
					break;
				}
			}
			$(this).trigger('chosen:updated');
		}
	}
}

/**
 * echarts帮助方法
 * @param option 参数,必须属性有:<br>
 * 	chart	代表echarts实例;<br>
 * 	url		echarts请求地址;<br>
	data	请求参数;<br>
	success	成功回调
 */
function echartsAjax(option){
	option.chart.showLoading();
	$.ajax({
		url: option.url
		,data: option.data
		,dataType: 'json'
		,method: 'post'
		,success: option.success
		,error: function (){
			option.chart.hideLoading();
			// 说明连接服务器失败
			layer.msg('连接服务器失败', {icon: 5});
		}
	});
}