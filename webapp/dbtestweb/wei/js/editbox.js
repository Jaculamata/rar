// var runType="";
$(document).ready(function () {

	var LocString = String(window.document.location.href);
	console.log("href",LocString);

	function getValue(key) {
		var rs = new RegExp("(^|)" + key + "=([^&]*)(&|$)", "gi")
			.exec(LocString),tmp;
		if (tmp = rs){
			console.log("tmp",tmp);
			return tmp[2];
		}
		return "null";
	}
	var id = getValue('id');
	var content = 'id:'+id+'<br>'+'state:'+getValue('state');
//			$('#content').html(content);
	console.log(content);

	$('#xmltitle').val(id);

	var editor = CodeMirror.fromTextArea(document.getElementById("code"), {
        lineNumbers: true,
        extraKeys: {"Ctrl": "autocomplete"},//输入s然后ctrl就可以弹出选择项
        mode: {name: "text/x-mysql"}, //x-mysql
    });

	editor.setSize('auto', $(window).height()-85);
	
	$(window).resize(function (){
		editor.setSize('auto', $(window).height()-85);
	});

    //var text = editor.getValue();  //获取编辑框内的内容
    //console.log(txt);

	//设置编辑框内容并刷新
	//editor.getDoc().setValue(txt);   
    //editor.refresh();

	$.ajax({
		url:'../../showXml.do',
		data:{
			'title': id
		},
		success:function (res) {
			console.log(res);
			var type = '';
			var content = '';
			if(res != null && res != ''){
				type = res.type;
				content = res.content;
			}
			if(type == '人工'){
				$('#non-auto').attr("checked","true");
			}else if(type == '半自动'){
				$('#semi-auto').attr("checked","true");
			}else{
				$('#full-auto').attr("checked","true");
			}
			editor.setValue(content);

			console.log("finished");
		}
	});

	//保存
	$('#save').unbind('click').bind('click',function(){
		var xmltitle = $.trim($('#xmltitle').val());
		var text = editor.getValue();
		var type = '';
		if($('#full-auto').is(':checked')){
			type = '全自动';
		}else if($('#semi-auto').is(':checked')){
			type = '半自动';
		}else if($('#non-auto').is(':checked')){
			type = '人工';
		}
		if (text == "" || text == null){
			// alert("请输入内容！");
			$.messager.alert('警告','请输入内容后提交！','info',function(){
				// alert('关闭时，回调这个函数');
			});
		}else if(type == '' || type == null){
			$.messager.confirm('保存测试脚本','您尚未选择测试脚本类型，请确定是否保存？',function(r){
				if (r){
					saveXml(xmltitle,text,type);
				}
			});
		}else{
			saveXml(xmltitle,text,type);
		}
	});

	function saveXml(xmltitle,text,type) {
		var lowerText = text.toLowerCase();
		console.log("lowerText",lowerText);
		if (lowerText.indexOf("<"+"ctrlmsg"+">") > 0 && type != "半自动"){
			$.messager.alert('提示信息','当前脚本类型为半自动，不能保存为其他类型！','info',function(){});
			return;
		} else if (lowerText.indexOf("<"+"ctrlmsg"+">") < 0 && type == "半自动") {
			$.messager.alert('提示信息','当前脚本类型为全自动，不能保存为半自动类型！','info',function(){});
			return;
		} else {
			$.ajax({
				type: 'post',
				async: true,
				url:'../../uploadXml.do',
				data:{
					'title': xmltitle,
					'content': text,
					'type':type
				},
				error:function(){
					// alert("error");
					$.messager.show({
						title:'提示信息',
						msg:'保存失败！请记录本次出错',
						timeout:2000,
						showType:'slide'
					});
				},
				success:function(data){
					if(data == "1"){
						// alert("保存成功！");
						$.messager.show({
							title:'提示信息',
							msg:'保存成功！',
							timeout:2000,
							showType:'slide'
						});
					}else if(data == "2"){
						// alert("保存成功！");
						$.messager.alert('提示信息','当前脚本类型为半自动，不能保存为其他类型！','info',function(){});

					}else if(data == "3"){
						// alert("保存成功！");
						$.messager.alert('提示信息','当前脚本类型为全自动，不能保存为其他类型！','info',function(){});
					}else {
						$.messager.alert('提示信息','保存失败，请重新保存','info',function(){});
					}
				}
			});
		}

	}

	//获取测试脚本内容
	$('#obtain').unbind('click').bind('click',function(){
		var xmltitle = $.trim($('#xmltitle').val());
		if (xmltitle == "" || xmltitle ==null){
			alert("请输入XmlTitle！");
		}else{
			$.ajax({
				type: 'post',
				async: true,
				url:'../../showXml.do',
				data:{
					'title': xmltitle,
				},
				error:function(){
					$.messager.show({
						title:'提示信息',
						msg:'获取脚本失败！请记录本次出错',
						timeout:2000,
						showType:'slide'
					});
				},
				success:function(res){
					console.log(res);
					var type = '';
					var content = '';
					if(res != null && res != ''){
						type = res.type;
						content = res.content;
					}
					if(type == '人工'){
						$('#non-auto').attr("checked","true");
					}else if(type == '半自动'){
						$('#semi-auto').attr("checked","true");
					}else{
						$('#full-auto').attr("checked","true");
					}
					editor.setValue(content);
				}
			});
		}
	});

	$('#run').unbind('click').bind('click',function(){
		var xml = editor.getValue();
		var idset=window.frameElement.id;
		console.log(xml)
		$.ajax({
			url: '../../get.do'
			, type: 'post'
			, data: {
				'zl': xml,
				'id':idset
				//'param': '123'  //后台使用user取代param，不需要传此参数（lw 4.14)
			},
			cache: false,
			success: function (data) {
//                alert(data);
				console.log(data);
				/*accept_editor.getDoc().setValue(data);
				 accept_editor.refresh();*/
			}
		});
		/*$.messager.show({
			title:'提示信息',
			msg:'暂未开放执行功能！',
			timeout:2000,
			showType:'slide'
		});*/
	});

	$('#pause').unbind('click').bind('click',function(){
		//$('#run').attr("disabled",false);
		//$('#pause').attr("disabled",true);
		$.ajax({
			url: '../../get.do',
			data:{
				//param:'123',//后台使用user取代param，不需要传此参数（lw 4.14)
				zl:'end'
			},
			cache: false,
			success: function (res) {
				if (res ==1 )
					clearInterval(timer);
			},
			complete:function (XMLHttpRequest, textStatus) {
				clearInterval(timer);
			}
		});
		/*$.messager.show({
		 title:'提示信息',
		 msg:'暂未开放暂停功能！',
		 timeout:2000,
		 showType:'slide'
		 });*/
	});

	//设置导入控件大小
	var height = $("#save").height();
	var width = $("#save").width();
	$("#edit_import").height(height);
	$("#edit_import").width(width);

	//导入脚本
	$("#edit_import").on("change","input[type='file']",function(){
		var filePath = $(this).val();
		console.log(id);
		console.log(filePath);

		if(filePath.indexOf("xml")!=-1){
			var fileNumber = $(this)[0].files.length;
			console.log("fileNumber : ",fileNumber);
			var formData = new FormData();
			formData.append('file', $(this)[0].files[0]);
			console.log('file name : ',$(this)[0].files[0].name);
			console.log("formData:",formData);

			$.messager.confirm('提示信息','您确定替换原来的内容？',function(r){
				if(r){
					$.ajax({
						type: 'post',
						url: '../../import.do?title='+id,
						data:formData,
						cache: false,
						processData: false,
						contentType: false,
						success:function(data){
							console.log("data:",data);
							editor.setValue(data);
							$("#edit_import").html("导入<input type='file' accept='.xml' />");
							$.messager.show({
								title:'提示信息',
								msg:'导入成功！',
								timeout:2000,
								showType:'slide'
							});
						}
					});
				}else{
					$("#edit_import").html("导入<input type='file' accept='.xml' />");
				}
			});

		}else {
			$.messager.show({
				title: '提示信息',
				msg: '您未上传文件，或者您上传文件类型有误！',
				timeout: 2000,
				showType: 'slide'
			});
			return false
		}
	});

	var tree_window = window.parent.document;
	console.log("tree_window:",tree_window);

	//树节点右键导入
	/*$(tree_window.getElementById("btn_Import")).on("change","input[type='file']",function(){
		var filePath = $(this).val();
		var formData = new FormData();
		var fileNumber = $(this)[0].files.length;
		console.log("fileNumber : ",fileNumber);

		formData.append('file', $(this)[0].files[0]);

		file_import(filePath,formData);
	});*/

	//文件导出函数
	function file_export(type){

		var form = $("<form>");//定义一个form表单
		form.attr("style","display:none");
		form.attr("target","");
		form.attr("method","post");
		form.attr("action","../../export1.do");
		//传递title
		var input = $("<input>");
		input.attr("type","hidden");
		input.attr("name","title");
		input.attr("value",id);
		//传递file type
		var input2 = $("<input>");
		input2.attr("type","hidden");
		input2.attr("name","type");
		input2.attr("value",type);

		$("body").append(form);//将表单放置在web中
		form.append(input);
		form.append(input2);
		form.submit();//表单提交
	}
	
	//导出脚本
	$("#edit_export").unbind('click').bind('click',function(){
		console.log("edit_export");
		$("#type_select").val(".xml");
		$('#export_dlg').dialog('open');
		$('#export_submit').unbind('click').bind('click',function(){
			var type = $('#type_select').val();
			console.log("file type:",type);
			file_export(type);
			$('#export_dlg').dialog('close');

		});
		$('#export_close').unbind('click').bind('click',function(){
			$('#export_dlg').dialog('close');
		});

	});

	//树节点右键导出
	/*$(tree_window.getElementById("btn_Export")).unbind('click').bind('click',function(){
		$("#type_select").val(".xml");
		$('#export_dlg').dialog('open');
		$('#export_submit').unbind('click').bind('click',function(){
			var type = $('#type_select').val();
			file_export(type);
			$('#export_dlg').dialog('close');
		});
		$('#export_close').unbind('click').bind('click',function(){
			$('#export_dlg').dialog('close');
		});
	});*/

	$('#export_dlg').dialog({
		title : '导出类型',
		width : 250,
		height : 160,
		closed:true,
		buttons : '#export_dlg_btn',
	});

});
 