$(function () {
	
	//登录界面
	$('#login').dialog({
		title : '登录测试管理系统',
		width : 300,
		height : 210,
		modal : true,
		iconCls : 'icon-login',
		buttons : '#btn',
	});
	
	//管理员帐号验证
	$('#manager').validatebox({
		required : true,
		missingMessage : '请输入登录帐号',
		invalidMessage : '登录帐号不得为空',
	});
	
	//管理员密码验证
	$('#password').validatebox({
		required : true,
		//validType : 'length[6,30]',
		missingMessage : '请输入登录密码',
		invalidMessage : '登录密码不得为空',
	});
	
	//加载时判断验证
	if (!$('#manager').validatebox('isValid')) {
		$('#manager').focus();
	} else if (!$('#password').validatebox('isValid')) {
		$('#password').focus();
	}
	//enter键 登录
	$("#loginbody").keyup(function (e) {
		e.preventDefault();
		if(e.keyCode == 13){
			//alert("enter click");
			//console.log("enter");
			login();
		}
	});

	var login = function () {
		if (!$('#manager').validatebox('isValid')) {
			$('#manager').focus();
		} else if (!$('#password').validatebox('isValid')) {
			$('#password').focus();
		} else {
			var role = '';
			if($('#admin').is(':checked')){
				role = 'admin';
			}
			if($('#user').is(':checked')){
				role = 'user';
			}
			console.log("role: ",role);
			$.ajax({
				url : '../login.do',
				type : 'post',
				data : {
					username : $.trim($('#manager').val()),
					password : $.trim($('#password').val()),
					role : role
				},
				beforeSend : function () {
					$.messager.progress({
						text : '正在登录中...',
					});
				},
				success : function (data, response, status) {
					$.messager.progress('close');

					if (data == 1) {
						location.href = 'home.jsp';
					}
					else if(data == 2){
						 location.href = 'TesterHomePage.jsp';
					}
					else{
						$.messager.alert('登录失败！', '用户名或密码错误！', 'warning', function () {
							$('#password').select();
						});
					}
				}
			});
		}
	};

	//单击登录
	$('#btn a').click(function(){
		//alert("login btn");
		login();
	});
});