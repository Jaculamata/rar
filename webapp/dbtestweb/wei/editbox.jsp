<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	if (request.getSession().getAttribute("loginUser") == null){
		response.sendRedirect("../index.html");
	}
%>
<!DOCTYPE HTML>
<html>
<head>
	<title>EditBox</title>
	<meta charset="UTF-8"/>
	<link rel="stylesheet" type="text/css"
		  href="../easyui/themes/bootstrap/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css"/>
	<link type="text/css" rel="stylesheet" href="../css/link.css"/>
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
	<!--ambiance主题风格-->
	<link rel="stylesheet" href="../codemirror-5.2/theme/ambiance.css">
	<!--核心文件-->
	<link type="text/css" rel="stylesheet" href="../codemirror-5.2/lib/codemirror.css"/>
	<script type="text/javascript" src="../codemirror-5.2/lib/codemirror.js"></script>

	<link type="text/css" rel="stylesheet" href="../codemirror-5.2/addon/hint/show-hint.css"/>
	<script type="text/javascript" src="../codemirror-5.2/addon/hint/show-hint.js"></script>
	<!--mysql高亮提示框-->
	<script type="text/javascript" src="../codemirror-5.2/addon/hint/sql-hint.js"></script>
	<script type="text/javascript" src="../codemirror-5.2/mode/sql/sql.js"></script>

	<link type="text/css" rel="stylesheet" href="css/editbox.css"/>
	<script type="text/javascript" src="js/editbox.js"></script>
</head>
<body scroll="no">

	<div class="operation">
		<!--<span>XmlTitle : </span>-->
		<input id="xmltitle" class="xmlid" disabled type="hidden"/>
		<button id="save" class="btn btn-primary">保存</button>
		<button id="obtain" class="btn btn-primary">获取</button>
		<button id="run" class="btn btn-primary">执行</button>
		<button id="pause" class="btn btn-primary">暂停</button>
		<a href="#" id="edit_import" class="file">导入<input type="file" accept=".xml" /></a>
		<button id="edit_export" class="btn btn-primary">导出</button>
	</div>
	<div style="margin: 50px 0 5px 0;background: #ffffff">
		<span>测试脚本类型：&nbsp;</span>
		<label><input id="full-auto" type="radio" name="type" />全自动 </label>
		<label><input id="semi-auto" type="radio" name="type" />半自动 </label>
		<label><input id="non-auto" type="radio" name="type" />人工 </label>
	</div>

	<div>
		<textarea id="code" name="code"></textarea>
	</div>

	<div id="export_dlg">
		<div class="content">
			<span>请选择导出文件类型：</span>
			<select id="type_select" class="dlg-select">
				<option selected = "selected">.xml</option>
				<option>.txt</option>
				<!--<option>.doc</option>-->
			</select>
		</div>
	</div>
	<div id="export_dlg_btn">
		<a href="#" id="export_submit" class="easyui-linkbutton">确定</a>
		<a href="#" id="export_close" class="easyui-linkbutton">取消</a>
	</div>

</body>
</html>
