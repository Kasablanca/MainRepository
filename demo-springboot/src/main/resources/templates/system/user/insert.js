$(function (){
	$('.chosen-select').chosen();
	
	var form = layui.form;

	form.verify({
		accName: [/^\S{5,15}$/, '账号必须5到15位'],
		username: [/^\S{2,15}$/, '用户名必须2到15位'],
		password: function(value){
			if(!/^\S{5,15}$/.test(value)){
				return '密码必须5到15位';
			}
		},
		password2: function(value){
			if(!/^\S{5,15}$/.test(value)){
				return '确认密码必须5到15位';
			} else if($('#password').val() != $('#password2').val()){
				return '两次密码不一样';
			}
		}
	});

	//监听提交
	form.on('submit(insert)', function(data){
		data.field.password = hex_md5(hex_md5(data.field.password));
		data.field.password2 = hex_md5(hex_md5(data.field.password2));
		var authorityGroupIds = $('#authorityGroup').val();
		if(authorityGroupIds && authorityGroupIds.length > 0){
			data.field.authorityGroupIds = JSON.stringify(authorityGroupIds);
		}
		$.ajax({
			url: 'user/insert',
			method: 'post',
			data: data.field,
			success: function (data){
				if(data.code == 0){
					layer.open({
						content: '操作成功',
						btn: ['确定'],
						yes: function (){
							parent.layer.close(parent.layer.getFrameIndex(window.name));
						}
					});
					parent.layui.table.reload('main');
				} else {
					layer.msg(data.message,{icon:5});
				}
			},
			error: function (data){
				layer.msg('连接服务器失败',{icon: 5});
			}
		});
		return false;
	});
});
