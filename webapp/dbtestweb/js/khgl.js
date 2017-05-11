$(function () {

	$('#search_kh').searchbox({
		menu:'#menu_kh',
		prompt:'Please input value',
		width : 330,
		searcher: function(value,name){
			
			//alert(value+":"+name);//$("#manager_kh").datagrid({
			/*$('#manager_kh').datagrid(
					"load",
					{	
						index:value,
					//title:'searchBox',
					//iconCls:'icon-ok',
					url:'./zlgl/getManager_kh_cid.php?cid='+value+'&name='+name,
					fit : true,
					fitColumns : true,
					striped : true,
					rownumbers : true,
					border : false,
					pagination : true,
					pageSize : 20,
					pageList : [10, 20, 30, 40, 50],
					pageNumber : 1,
					sortName : 'cid',
					sortOrder : 'desc',
					//toolbar : '#manager_kh_tool',
					columns : [[
						{
							field : 'id',
							title : '序号',
							width : 100,
							checkbox : true,
						},
						
						{
							field : 'xm',
							title : '姓名',
							width : 100,
						},
						{
							field : 'dh',
							title : '电话',
							width : 100,
						},
						{
							field : 'gs',
							title : '公司',
							width : 100,
						},
						{
							field : 'zw',
							title : '职位',
							width : 100,
						},
						{
							field : 'dz',
							title : '地址',
							width : 100,
						},
						{
							field : 'bz',
							title : '备注',
							width : 100,
						},
					]],
			});*/
		},		
	
	});
	
	$('#manager_kh_search').dialog({
		width : 350,
		title : '搜索客户',
		modal : true,
		closed : true,
		iconCls : 'icon-user-add',
		buttons : [{
			text : '提交',
			iconCls : 'icon-add-new',
			handler : function () {
				/*if ($('#manager_kh_add').form('validate')) {
					$.ajax({
						url : './zlgl/addManager_kh.php',
						type : 'post',
						data : {
							xm : $('input[name="xm"]').val(),
							dh : $('input[name="dh"]').val(),
							gs : $('input[name="gs"]').val(),
							zw : $('input[name="zw"]').val(),
							dz : $('input[name="dz"]').val(),
							bz : $('input[name="bz"]').val(),
							//auth : $('#auth').combotree('getText'),
						},
						beforeSend : function () {
							$.messager.progress({
								text : '正在新增中...',
							});
						},
						success : function (data, response, status) {
							$.messager.progress('close');
							
							if (data > 0) {
								$.messager.show({
									title : '提示',
									msg : '新增客户成功',
								});
								$('#manager_kh_add').dialog('close').form('reset');
								$('#manager_kh').datagrid('reload');
							} else {
								$.messager.alert('新增失败！', '未知错误导致失败，请重试！', 'warning');
							}
						
					});
				}}*/
			},
		},{
			text : '取消',
			iconCls : 'icon-redo',
			handler : function () {
				$('#manager_kh_add').dialog('close').form('reset');
			},
		}],
	});
	
	$('#manager_kh').datagrid({
		url : './zlgl/khgl_data.php',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 20,
		pageList : [10, 20, 30, 40, 50],
		pageNumber : 1,
		sortName : 'cid',
		sortOrder : 'desc',
		toolbar : '#manager_kh_tool',
		columns : [[
			{
				field : 'id',
				title : '序号',
				width : 100,
				checkbox : true,
			},
			/*{
				field : 'cid',
				title : '编号',
				width : 100,
				
			},*/
			{
				field : 'xm',
				title : '姓名',
				width : 100,
			},
			{
				field : 'dh',
				title : '电话',
				width : 100,
			},
			{
				field : 'gs',
				title : '公司',
				width : 100,
			},
			{
				field : 'zw',
				title : '职位',
				width : 100,
			},
			{
				field : 'dz',
				title : '地址',
				width : 100,
			},
			{
				field : 'bz',
				title : '备注',
				width : 100,
			},
		]],
	});
	
	$('#manager_kh_add').dialog({
		width : 350,
		title : '新增客户',
		modal : true,
		closed : true,
		iconCls : 'icon-user-add',
		buttons : [{
			text : '提交',
			iconCls : 'icon-add-new',
			handler : function () {
				if ($('#manager_kh_add').form('validate')) {
					$.ajax({
						url : './zlgl/addManager_kh.php',
						type : 'post',
						data : {
							xm : $('input[name="xm"]').val(),
							dh : $('input[name="dh"]').val(),
							gs : $('input[name="gs"]').val(),
							zw : $('input[name="zw"]').val(),
							dz : $('input[name="dz"]').val(),
							bz : $('input[name="bz"]').val(),
							//auth : $('#auth').combotree('getText'),
						},
						beforeSend : function () {
							$.messager.progress({
								text : '正在新增中...',
							});
						},
						success : function (data, response, status) {
							$.messager.progress('close');
							
							if (data > 0) {
								$.messager.show({
									title : '提示',
									msg : '新增客户成功',
								});
								$('#manager_kh_add').dialog('close').form('reset');
								$('#manager_kh').datagrid('reload');
							} else {
								$.messager.alert('新增失败！', '未知错误导致失败，请重试！', 'warning');
							}
						}
					});
				}
			},
		},{
			text : '取消',
			iconCls : 'icon-redo',
			handler : function () {
				$('#manager_kh_add').dialog('close').form('reset');
			},
		}],
	});
	
	$('#manager_kh_edit').dialog({
		width : 350,
		title : '修改客户信息',
		modal : true,
		closed : true,
		iconCls : 'icon-user-add',
		buttons : [{
			text : '提交',
			iconCls : 'icon-edit-new',
			handler : function () {
				if ($('#manager_kh_edit').form('validate')) {
					$.ajax({
						url : './zlgl/updateManager_kh.php',
						type : 'post',
						data : {
							cid : $('input[name="cid_edit"]').val(),
							xm : $('input[name="xm_edit"]').val(),
							dh : $('input[name="dh_edit"]').val(),
							gs : $('input[name="gs_edit"]').val(),
							zw : $('input[name="zw_edit"]').val(),
							dz : $('input[name="dz_edit"]').val(),
							bz : $('input[name="bz_edit"]').val(),
						},
						beforeSend : function () {
							$.messager.progress({
								text : '正在修改中...',
							});
						},
						success : function (data, response, status) {
							$.messager.progress('close');
							
							if (data > 0) {
								$.messager.show({
									title : '提示',
									msg : '修改客户信息成功',
								});
								$('#manager_kh_edit').dialog('close').form('reset');
								$('#manager_kh').datagrid('reload');
							} else {
								$.messager.alert('修改失败！', '未知错误或没有任何修改，请重试！', 'warning');
							}
						}
					});
				}
			},
		},{
			text : '取消',
			iconCls : 'icon-redo',
			handler : function () {
				$('#manager_kh_edit').dialog('close').form('reset');
			},
		}],
	});
	
	/*//管理帐号
	$('input[name="manager_kh"]').validatebox({
		required : true,
		validType : 'length[2,20]',
		missingMessage : '请输入管理名称',
		invalidMessage : '管理名称在 2-20 位',
	});
	
	//管理密码
	$('input[name="password"]').validatebox({
		required : true,
		validType : 'length[6,30]',
		missingMessage : '请输入管理密码',
		invalidMessage : '管理密码在 6-30 位',
	});
	
	//修改管理密码
	$('input[name="password_edit"]').validatebox({
		//required : true,
		validType : 'length[6,30]',
		missingMessage : '请输入管理密码',
		invalidMessage : '管理密码在 6-30 位',
	});*/
	
	/*//分配权限
	$('#auth').combotree({
		url : 'nav.php',
		required : true,
		lines : true,
		multiple : true,
		checkbox : true,
		onlyLeafCheck : true,
		onLoadSuccess : function (node, data) {
			var _this = this;
			if (data) {
				$(data).each(function (index, value) {
					if (this.state == 'closed') {
						$(_this).tree('expandAll');
					}
				});
			}
		},
	});*/
	
	
	manager_kh_tool = {
		
		reload : function () {
			$('#manager_kh').datagrid('reload');
		},
		redo : function () {
			$('#manager_kh').datagrid('unselectAll');
		},
		add : function () {
			$('#manager_kh_add').dialog('open');
			$('input[name="manager_kh"]').focus();
		},
		remove : function () {
			var rows = $('#manager_kh').datagrid('getSelections');
			if (rows.length > 0) {
				$.messager.confirm('确定操作', '您正在要删除所选的记录吗？', function (flag) {
					if (flag) {
						var ids = [];
						for (var i = 0; i < rows.length; i ++) {
							ids.push(rows[i].cid);
						}
						//console.log(ids.join(','));
						$.ajax({
							type : 'POST',
							url : './zlgl/deleteManager_kh.php',
							data : {
								ids : ids.join(','),
							},
							beforeSend : function () {
								$('#manager_kh').datagrid('loading');
							},
							success : function (data) {
								if (data) {
									$('#manager_kh').datagrid('loaded');
									$('#manager_kh').datagrid('load');
									$('#manager_kh').datagrid('unselectAll');
									$.messager.show({
										title : '提示',
										msg : data + '个客户记录被删除成功！',
									});
								}
							},
						});
					}
				});
			} else {
				$.messager.alert('提示', '请选择要删除的记录！', 'info');
			}
		},
		edit : function () {
			var rows = $('#manager_kh').datagrid('getSelections');
			console.log(rows);
			if (rows.length > 1) {
				$.messager.alert('警告操作！', '编辑记录只能选定一条数据！', 'warning');
			} else if (rows.length == 1) {
				$.ajax({
					url : './zlgl/getManager_kh.php',
					type : 'post',
					data : {
						cid : rows[0].cid,
					},
					beforeSend : function () {
						$.messager.progress({
							text : '正在获取中...',
						});
					},
					success : function (data, response, status) {
						$.messager.progress('close');
						
						if (data) {
							
							var obj = $.parseJSON(data);
							//var auth = obj[0].auth.split(',');
							
							$('#manager_kh_edit').form('load', {
								cid_edit : obj[0].cid,
								xm_edit : obj[0].xm,
								dh_edit : obj[0].dh,
								gs_edit : obj[0].gs,
								zw_edit : obj[0].zw,
								dz_edit : obj[0].dz,
								bz_edit : obj[0].bz,
								//zw : obj[0].zw,
								//auth_edit : obj[0].auth,
							}).dialog('open');
							
							//分配权限
							/*$('#auth_edit').combotree({
								url : 'nav.php',
								required : true,
								lines : true,
								multiple : true,
								checkbox : true,
								onlyLeafCheck : true,
								onLoadSuccess : function (node, data) {
									var _this = this;
									if (data) {
										$(data).each(function (index, value) {
											if ($.inArray(value.text, auth) != -1) {
												$(_this).tree('check', value.target);
											}
											if (this.state == 'closed') {
												$(_this).tree('expandAll');
											}
										});
									}
								},
							});*/
							
						} else {
							$.messager.alert('获取失败！', '未知错误导致失败，请重试！', 'warning');
						}
					}
				});
			} else if (rows.length == 0) {
				$.messager.alert('警告操作！', '编辑记录至少选定一条数据！', 'warning');
			}
		},
	};
});