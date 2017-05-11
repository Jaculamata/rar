$(function(){
	$('#dg_gsy').datagrid({
		url : './ui/get_users.php',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 20,
		pageList : [10, 20, 30, 40, 50],
		pageNumber : 1,
		//sortName : 'id',
		//sortOrder : 'desc',
		toolbar : '#toolbar_gsy',
		columns : [[
			{
				field : 'id',
				title : '序号',
				width : 100,
				checkbox : true,
			},
			{
				field : 'firstname',
				title : '名',
				width : 100,
			},
			{
				field : 'lastname',
				title : '姓',
				width : 100,
			},
			{
				field : 'phone',
				title : '电话',
				width : 100,
			},
			{
				field : 'email',
				title : '邮箱',
				width : 100,
			},
			{
				field : 'bj',
				title : '编辑',
				width : 100,
			},
		]],
		
		/*view: 'detailview',
		detailFormatter:function(index,row){
			return '<div class="ddv_gsy"></div>';
		},
		onExpandRow: function(index,row){
			var ddv = $(this).datagrid('getRowDetail',index).find('div.ddv_gys');
			ddv.panel({
				border:false,
				cache:true,
				href:'./ui/show_form.php?index='+index,
				onLoad:function(){
					$('#dg_gsy').datagrid('fixDetailRowHeight',index);
					$('#dg_gsy').datagrid('selectRow',index);
					$('#dg_gsy').datagrid('getRowDetail',index).find('form').form('load',row);
				}
			});
			$('#dg_gsy').datagrid('fixDetailRowHeight',index);
		},*/
	});
});