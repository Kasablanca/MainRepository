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
}