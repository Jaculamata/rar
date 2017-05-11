$(function(){
	$('#dg_gsy').datagrid({
		
		view: detailview,
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
		},
		
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
		toolbar : '#toolbar_gsy',
		columns : [[
			{
				field : 'id',
				title : '序号',
				width : 100,
				checkbox : true,
			},
			{
				field : 'cid',
				title : '编号',
				width : 100,
				
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
	});
			/*$('#dg_gsy').datagrid({
				view: detailview,
				detailFormatter:function(index,row){
					return '<div class="ddv"></div>';
				},
				onExpandRow: function(index,row){
					var ddv = $(this).datagrid('getRowDetail',index).find('div.ddv');
					ddv.panel({
						border:false,
						cache:true,
						href:'show_form.php?index='+index,
						onLoad:function(){
							$('#dg_gsy').datagrid('fixDetailRowHeight',index);
							$('#dg_gsy').datagrid('selectRow',index);
							$('#dg_gsy').datagrid('getRowDetail',index).find('form').form('load',row);
						}
					});
					$('#dg_gsy').datagrid('fixDetailRowHeight',index);
				}
			});*/
		
		function saveItem(index){
			var row = $('#dg_gsy').datagrid('getRows')[index];
			var url = row.isNewRecord ? './ui/save_user.php' : 'update_user.php?id='+row.id;
			$('#dg_gsy').datagrid('getRowDetail',index).find('form').form('submit',{
				url: url,
				onSubmit: function(){
					return $(this).form('validate');
				},
				success: function(data){
					data = eval('('+data+')');
					data.isNewRecord = false;
					$('#dg_gsy').datagrid('collapseRow',index);
					$('#dg_gsy').datagrid('updateRow',{
						index: index,
						row: data
					});
				}
			});
		}
		function cancelItem(index){
			var row = $('#dg_gsy').datagrid('getRows')[index];
			if (row.isNewRecord){
				$('#dg_gsy').datagrid('deleteRow',index);
			} else {
				$('#dg_gsy').datagrid('collapseRow',index);
			}
		}
		function destroyItem(){
			var row = $('#dg_gsy').datagrid('getSelected');
			if (row){
				$.messager.confirm('Confirm','Are you sure you want to remove this user?',function(r){
					if (r){
						var index = $('#dg_gsy').datagrid('getRowIndex',row);
						$.post('./ui/destroy_user.php',{id:row.id},function(){
							$('#dg_gsy').datagrid('deleteRow',index);
						});
					}
				});
			}
		}
		function newItem(){
			$('#dg_gsy').datagrid('appendRow',{isNewRecord:true});
			var index = $('#dg_gsy').datagrid('getRows').length - 1;
			$('#dg_gsy').datagrid('expandRow', index);
			$('#dg_gsy').datagrid('selectRow', index);
		}

});