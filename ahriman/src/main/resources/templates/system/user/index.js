layui.use('table', function(){
	var table = layui.table;

	//第一个实例
	table.render({
		elem: '#demo'
		,height: 315
		,url: /*[[@{/test/user}]]*/ 'test/user' //数据接口
		,page: true //开启分页
		,limit: 30
		,loading: true
		,text: {
			none: '暂无相关数据'
		}
		,where: {
			field: "id",
			sord: "asc"
		}
		,cols: [[ //表头
			{field: 'id', title: 'ID', width:80, sort: true}
			,{field: 'username', title: '用户名', width:120, sort: true}
			,{field: 'sex', title: '性别', width:80, sort: true}
			,{field: 'age', title: '年龄', width:80, sort: true}
			,{field: 'email', title: '电子邮箱', width:160, sort: true}
			,{field: 'birthday', title: '生日', width:160, sort: true}
		]]
	});

	table.on('sort(test)', function(obj){ //注：sort是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
		//console.log(obj.field); //当前排序的字段名
		//console.log(obj.type); //当前排序类型：desc（降序）、asc（升序）、null（空对象，默认排序）
		//console.log(this); //当前排序的 th 对象

		//尽管我们的 table 自带排序功能，但并没有请求服务端。
		//有些时候，你可能需要根据当前排序的字段，重新向服务端发送请求，从而实现服务端排序，如：
		table.reload('demo', {
			initSort: obj //记录初始排序，如果不设的话，将无法标记表头的排序状态。 layui 2.1.1 新增参数
			,where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
				field: obj.field //排序字段
				,sord: obj.type //排序方式
			}
		});
	});

});

layui.use('laydate', function(){
	var laydate = layui.laydate;

	//执行一个laydate实例
	laydate.render({
		elem: '#start' //指定元素
	});

	//执行一个laydate实例
	laydate.render({
		elem: '#end' //指定元素
	});
});

/*用户-停用*/
function member_stop(obj,id){
	layer.confirm('确认要停用吗？',function(index){
		if($(obj).attr('title')=='启用'){
			//发异步把用户状态进行更改
			$(obj).attr('title','停用')
			$(obj).find('i').html('&#xe62f;');

			$(obj).parents("tr").find(".td-status").find('span').addClass('layui-btn-disabled').html('已停用');
			layer.msg('已停用!',{icon: 5,time:1000});

		}else{
			$(obj).attr('title','启用')
			$(obj).find('i').html('&#xe601;');

			$(obj).parents("tr").find(".td-status").find('span').removeClass('layui-btn-disabled').html('已启用');
			layer.msg('已启用!',{icon: 5,time:1000});
		}
	});
}

/*用户-删除*/
function member_del(obj,id){
	layer.confirm('确认要删除吗？',function(index){
		//发异步删除数据
		$(obj).parents("tr").remove();
		layer.msg('已删除!',{icon:1,time:1000});
	});
}

function delAll (argument) {
	var data = tableCheck.getData();

	layer.confirm('确认要删除吗？'+data,function(index){
		//捉到所有被选中的，发异步进行删除
		layer.msg('删除成功', {icon: 1});
		$(".layui-form-checked").not('.header').parents('tr').remove();
	});
}