// 定义命名空间
var com = {};
com.syhd = {};
com.syhd.utils = {};

com.syhd.utils.ztree = {};

// 设置zTree节点的图标样式
com.syhd.utils.ztree.setNodeIcon = function (node){
	switch(node.type){
	case 0:
		node.iconClose = 'depend/ztree/css/zTreeStyle/img/diy/1_close.png';
		node.iconOpen = 'depend/ztree/css/zTreeStyle/img/diy/1_open.png';
		break;
	case 1:
		node.icon = 'depend/ztree/css/zTreeStyle/img/diy/4.png';
		break;
	case 2:
		node.icon = 'depend/ztree/css/zTreeStyle/img/diy/3.png';
		node.isParent = false;
		break;
	default:
		break;
	}
};

com.syhd.utils.dateUtils = {};
com.syhd.utils.dateUtils.getTodayTime0 = function (){
	var date = new Date();
	date.setHours(0);
	date.setMinutes(0);
	date.setSeconds(0);
	return date;
};

Date.prototype.format = function(fmt) { //author: meizz   
	var o = {   
			"M+" : this.getMonth()+1,                 //月份   
			"d+" : this.getDate(),                    //日   
			"h+" : this.getHours(),                   //小时   
			"m+" : this.getMinutes(),                 //分   
			"s+" : this.getSeconds(),                 //秒   
			"q+" : Math.floor((this.getMonth()+3)/3), //季度   
			"S"  : this.getMilliseconds()             //毫秒   
	};   
	if(/(y+)/.test(fmt))   
		fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
	for(var k in o)   
		if(new RegExp("("+ k +")").test(fmt))   
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
	return fmt;   
}
