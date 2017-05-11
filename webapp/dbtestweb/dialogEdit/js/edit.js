$(function () {
	//Dialog界面
	$('#dialog_edit').dialog({
		title : '编辑窗口',
		width : 500,
		height : 350,
		modal : true,
		buttons : '#btn',
	});
	
	$('#save').click(function(){
		var content1 = $("#textarea1").val();
		var content2 = $("#textarea2").val();
		if (content1 == "" || content1 == null){
			alert("请在Text1中输入内容。");
			$("#textarea1").focus();
		}else if(content2 == "" || content2 == null){
			alert("请在Text2中输入内容。");
			$("#textarea2").focus();
		}else {
			$("#textarea1").each(function(){
				$(this).val("");
			});
			$("#textarea2").each(function(){
				$(this).val("");
			});
			alert(content1);
			alert(content2);
		}
		//$('#dialog_edit').dialog('close')
	});
	
	$('#close').click(function(){
		$('#dialog_edit').dialog('close')
	});
});