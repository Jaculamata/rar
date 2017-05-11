<%--<?php
session_start();
if (!isset ($_SESSION ['admin'])) {
    header('location:index.php');
}
?>--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String username = (String) request.getSession().getAttribute("loginUser");

    if (request.getSession().getAttribute("loginUser") == null){
        response.sendRedirect("index.html");

    }
%>
<!DOCTYPE HTML>
<html>
<head>
    <%--<base href="<%=basePath%>">--%>
    <title>后台管理</title>
    <meta charset="UTF-8"/>
    <link rel="stylesheet" type="text/css" href="easyui/themes/bootstrap/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="easyui/themes/icon.css"/>
    <link type="text/css" rel="stylesheet" href="css/link.css"/>
    <!--    <link rel="stylesheet" type="text/css" href="css/admin.css"/>-->
    <link rel="stylesheet" type="text/css" href="dialogEdit/css/edit.css">
    <link rel="stylesheet" type="text/css" href="css/regulation.css">

    <%--<script type="text/javascript" src="easyui/jquery.min.js"></script>--%>
    <script type="text/javascript" src="js/jquery1.7.min.js"></script>
    <script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script>
    <link rel="stylesheet" type="text/css" href="./font-awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="./font-awesome/css/ui.css"/>
    <%--控制tab--%>
    <script type="text/javascript" src="js/tabs.js"></script>
    <script type="text/javascript" src="js/regulation.js"></script>
        <script type="text/javascript" src="wei/js/editbox.js"></script>
    <%--<script type="text/javascript" src="dialogEdit/js/edit.js"></script>--%>
    <%--<script src="js/newproject.js"></script>--%>
    <script type="text/javascript" src="easyui/etree/jquery.etree.js"></script>
    <script>

        $(function () {
            /*用于半自动化等提示*/
            var index=0;
            var click=false;
            timer = setInterval(function () {
                $.ajax({
                    url: '../getctrlres.do',
                    cache: false,
                    data:{
                        //param:'123',  //后台使用user取代param，不需要传此参数（lw 4.14)
                        zl:'getres'
                    },
                    /*asyn: false,*/
                    success: function (res) {
                        //   console.log(res);
                        if (res==null|| res.length==0){

                        }
                        else {
                            var jsonTransfer=res;
//                          alert(jsonTransfer.allMsg.length);
                            var msgLength=jsonTransfer.allMsg.length;
                            var chLength=jsonTransfer.allch.length;;
//    alert(jsonTransfer.allMsg[1]+"---msg length: "+msgLength+"--ch length "+chLength+" "+jsonTransfer.allch[2]);
                            var inputs = document.getElementById("ff");
                            var btinputs=document.getElementById("bt");
                            $('#ff').html("");
                            $('#bt').html("");

                            //    while(inputs.hasChildNodes()) //每次动态添加应该更新内容，当div下还存在子节点时 循环继续
                            //    {
                            //        inputs.removeChild(inputs.firstChild);
                            //    }
                            for (var i=0;i<msgLength;i++){
                                var textarea = document.createElement("textarea");
                                textarea.type="text";
                                textarea.name="name_"+i;
                                textarea.id=i;//给这个input赋予id值
                                textarea.value=jsonTransfer.allMsg[i];
                                textarea.readOnly=true;
                                textarea.className="text1";
//        textarea.rows=4;
                                inputs.appendChild(textarea);
                                inputs.appendChild(document.createElement("br"));
                                index++;
                                //提交信息直接用表单提交，本来内容就是循环在Form里的，你应该懂吧
                            };
                            for (var i=0;i<chLength;i++){
                                var buttons = document.createElement("input");
                                buttons.type="button";
//        alert(buttons.type);
//        buttons.name="name_"+i;
                                buttons.id=jsonTransfer.allch[i];//给这个input赋予id值
                                buttons.value=jsonTransfer.allch[i];
                                buttons.className="myinput";
//        buttons.onclick='submit()';
//        textarea.readOnly=true;
//        textarea.cols=100;
//        textarea.rows=4;
                                btinputs.appendChild(buttons);
//        btinputs.appendChild(document.createElement("br"));
                                //提交信息直接用表单提交，本来内容就是循环在Form里的，你应该懂吧
                            };
                            $('#pp').dialog({
                                width: 400,
                                height: 300,
                                title:jsonTransfer.type,
                                closed:false,
                                onClose:function(){
                                    var un = '<%=username%>';
                                    if(!click){
                                        $.ajax({
                                            url:'../getctrlretuenresnull.do',
                                            data: un,
                                            type : "POST",
                                            async : false,
                                            contentType : "application/json;charset=utf-8", //设置请求头信息
                                            success: function (d){
                                                //   alert("success"+d);

                                            },
                                            error:function (error) {
                                                console.log(error)
                                                // alert("fail"+error);
                                            }
                                        });
                                        click=true;
                                    }
                                }

                            });
                            $(".myinput").click(function(){
                                click=true;
                                jsonTransfer.allMsg.length = 0;//push(this.value);
                                for (var i=0; i<index; i++){
                                    jsonTransfer.allMsg.push($('#'+i).val());
                                }
                                jsonTransfer.allMsg.push(this.value);
                                console.log(jsonTransfer)
                                index = 0;

//                                var l=jsonTransfer.allMsg.length;
//post的url自定
//            $('#bt').submit(function(){
                                /*$.post("../getctrlretuenres.do",jsonTransfer,function (d){
                                 alert("success"+d);
                                 });*/;
//                                jsonTransfer = '{"type":"test","msg":["1","2"]}';
                                var un = '<%=username%>';
                                jsonTransfer.type = jsonTransfer+"#"+un+'';
                                jsonTransfer = JSON.stringify(jsonTransfer);
                                console.log(jsonTransfer)
                                $.ajax({
                                    url:'../getctrlretuenres.do',
                                    data: jsonTransfer,
                                    type : "POST",
                                    async : false,
                                    contentType : "application/json;charset=utf-8", //设置请求头信息
                                    success: function (d){
                                        //   alert("success"+d);

                                    },
                                    error:function (error) {
                                        console.log(error)
                                        // alert("fail"+error);
                                    }
                                })

                                $('#pp').dialog('close');
                                click=false;

//            });
                            });
                        }
                    }
                });
            },50);
            var upath="../etree/";
            $('#etree').etree({
                url: upath+'get_data.do', /*tree_data.json*/
                createDirectoryUrl:upath+'createDirectory.do',
                createUrl:upath+'create.do',
                updateUrl:upath+'update.do',
                destroyUrl:upath+'destroy.do',
//                    dndUrl:upath+'dnd.php',
                dnd: false,
                formatter: function (node) {
//                    $.parser.parse();
                    return '<a>'+node.text+'</a>';
                },
                onClick:function(node){
//                    alert(node.info);
                    dyindex=node.id;
                    console.log(node);
//                    addPanel('./test_1_login/index.html','1.登录测试');
//                    addPanel('./codemirror-5.2/mydemo/index.html',node.text);
                    var params ='id='+node.id+'&state='+node.state+'&type='+node.type;
//                    alert("id when the eteee was clicked: "+node.id);
                    if (node.type == 'file'){
                        addPanel('./wei/editbox.jsp?'+params,node.text,node.id);
                    }else {
                        addPanel('./wei/stcontent.jsp?'+params,node.text,node.id);
                    }
                }

            });
            //修改后的版本已不使用
            /*$('#dialog_edit').dialog({
                title : '规范内容',
                width : 500,
                height : 350,
                modal : true,
                buttons : '#btn',
                closed:true,
                buttons:'#dialog_btn',
            });*/
        });
        //此函数目前已经不使用
        /*function addContent(){
            //Dialog界面
            $('#dialog_edit').dialog('open');//.dialog('setTitle', '参数设置');

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
        }*/
    </script>
    <style>
        .text1{
            width: 95%;
            height: 200px;
            resize: none;
            disabled:disabled
        }
        .myinput{
            float:right;
            margin:10px 0 0 5px;
        }
    </style>
</head>

<body class="easyui-layout">

 <div  data-options="region:'north',title:'header',split:true,noheader:true, disabled:true"
        style="height: 68px; background: #fff;">
    <div id="head" style="background: #2992b1;">
        <i class="e-icon fa fa-fw fa-database on fa-square-o  fa-2x"></i><span class="logo">数据库安全评级工具</span>
        <span class="logout" style="">
            您好，<i class="fa fa-user fa-fw" aria-hidden="true"></i>
            <span id="loginUser"><%=username%></span>
            <%--<?php echo $_SESSION['admin']; ?>--%>| <i class="fa fa-sign-out fa-fw" aria-hidden="true"></i>
            <%--<a class="head" href="logout.jsp">退出</a>--%>
            <a class="head" onclick="logout()">退出</a>														 
        </span>
    </div>
    <div style="clear: both"></div>
    <div id="toptools" style="height: 10px;
    /*padding: 10px;*/
    "><!--border: 1px solid #29938B;-->
        <!--工具栏-->
        <a href="javascript:void(0)" id="mb" class="easyui-menubutton"
           data-options="menu:'#mmnew'">管理</a>
        <div id="mmnew" style="width:70px;">
            <%--<div onclick="newProject()">新建项目</div>
            <div class="menu-sep"></div>--%>
            <div onclick="openProjectDialog()">打开项目</div>
            <div class="menu-sep"></div>
            <div onclick="userRegulation()">用户管理</div>
            <div class="menu-sep"></div>
            <div onclick="editPassword()">修改密码</div>
            <div class="menu-sep"></div>
            <%--<div onclick="importJar()">导入驱动jar包</div>
            <div class="menu-sep"></div>--%>

            <%--<div><a class="head" href="logout.jsp">退出</a></div>--%>
            <div onclick="logout()">退出</div>
        </div>

        <a href="javascript:void(0)" id="mbhelp" class="easyui-menubutton"
           data-options="menu:'#mmhelp'">帮助</a><!-- ,iconCls:'icon-help' -->
        <div id="mmhelp" style="width:70px;">
            <div>帮助文档</div>
            <div>联系我们</div>
        </div>
    </div>
</div>
<!--新建项目-->
<div id="winnew" class="easyui-dialog" title="参数设置" style="width: 600px;height: 400px;padding: 10px"
     buttons="#dlg-buttons" closed="true" data-options="inline:true">
    <table id="pg2" style="width: 560px;"></table>
    <!--<iframe src="../5datagridAndTree/propertygrid.html" width="99%" height="95%" scrolling="yes">此浏览器不支持iframe.</iframe>-->
</div>
<div id="dlg-buttons">
    <table cellpadding="0" cellspacing="0" style="width: 100%">
        <tr>
            <td>
                <a href="#" class="easyui-linkbutton" iconCls="fa fa-save" plain="true"
                   onclick="saveForm()">保存</a>
                <a href="#" class="easyui-linkbutton" iconCls="fa fa-times" plain="true"
                   onclick="javascript:$('#winnew').dialog('close');">取消</a>
            </td>
        </tr>
    </table>
</div>
 <!-- 打开项目 -->
 <div id="openProjectDiv" class="easyui-dialog" title="打开项目" style="width: 600px;height: 400px;padding: 10px"
      closed="true" data-options="inline:true">
     <table id="openProjectTable" class="easyui-datagrid" toolbar="#project-toolbar" style="width: auto; height: 340px;"></table>
     <div id="project-toolbar">
         <div class="operation">
             <a href="#" id="openProject" class="easyui-linkbutton" iconCls="fa fa-folder-open" onclick="openProject()" plain="true">确定</a>
             <a href="#" id="removeProject" class="easyui-linkbutton" iconCls="icon-remove" onclick="deleteProject()" plain="true">删除</a>
         </div>
     </div>
 </div>
<!-- 用户管理 -->
<div id="userRgl" class="easyui-dialog" title="用户管理" style="width: 600px;height: 400px;padding: 10px"
     closed="true" data-options="inline:true">
    <table id="userInfo" class="easyui-datagrid" toolbar="#user-toolbar" style="width: auto; height: 340px;"></table>
    <div id="user-toolbar">
        <div class="operation">
            <a href="#" id="addUser" class="easyui-linkbutton" iconCls="icon-plus" onclick="addUser()" plain="true">添加</a>
            <a href="#" id="editUserPwd" class="easyui-linkbutton" iconCls="icon-edit" onclick="resetUserPwd()" plain="true">重置密码</a>
            <a href="#" id="removeUser" class="easyui-linkbutton" iconCls="icon-remove" onclick="deleteUser()" plain="true">删除</a>
        </div>
        <div class="user-search">
            <input id="user-search" class="easyui-searchbox" searcher="searchUser"
                   style="width: 150px;height: 26px; vertical-align: middle;" prompt="请输入用户名" />
        </div>
    </div>
</div>
<div id="addUserInfo" class="easyui-dialog" title="添加用户" style="width: 300px;height: 200px;padding: 10px"
     buttons="#adduser-btn" closed="true" data-options="inline:true">
    <table class="userInput">
        <tr>
            <td>用户名：</td>
            <td><input id="addUsername" class="textInput" /></td>
        </tr>
        <tr>
            <td>密码：</td>
            <td><input id="addPassword" class="textInput" /></td>
        </tr>
    </table>
</div>
<div id="adduser-btn">
    <table cellpadding="0" cellspacing="0" style="width: 100%">
        <tr>
            <td>
                <a href="#" id="saveUser" class="easyui-linkbutton" iconCls="fa fa-save" plain="true">保存</a>
                <a href="#" id="cancelUser" class="easyui-linkbutton" iconCls="fa fa-times" plain="true">取消</a>
            </td>
        </tr>
    </table>
</div>
<!-- 修改密码 -->
<div id="editPwd" class="easyui-dialog" title="修改密码" style="width: 300px;height: 220px;padding: 10px"
    buttons="#pwd-btn" closed="true" data-options="inline:true">
    <table class="pwdInput">
        <tr>
            <td>原密码：</td>
            <td><input id="oldPwd" class="textbox" type="password" /><span class="text">*</span></td>
        </tr>
        <tr>
            <td>新密码：</td>
            <td><input id="newPwd" class="textbox" type="password" /><span class="text">*</span></td>
        </tr>
        <tr>
            <td>确认密码：</td>
            <td><input id="confirmPwd" type="password" class="textbox" /><span class="text">*</span></td>
        </tr>
    </table>
</div>
<div id="pwd-btn">
    <table cellpadding="0" cellspacing="0" style="width: 100%">
        <tr>
            <td>
                <a href="#" id="savePwd" class="easyui-linkbutton" iconCls="fa fa-save" plain="true">保存</a>
                <a href="#" id="cancelPwd" class="easyui-linkbutton" iconCls="fa fa-times" plain="true">取消</a>
            </td>
        </tr>
    </table>
</div>
<!-- 导入驱动jar包 -->
<div id="importJar" class="easyui-dialog" title="导入驱动jar包" style="width: 300px;height: 180px;padding: 10px"
     buttons="#jar-btn" closed="true" data-options="inline:true">
    <div style="margin: 10px 0 10px 10px">
        <span>驱动jar包名：</span>
        <span id="jarName"></span>
    </div>
    <div id="importJar-btn" style="margin: 20px 0 10px 0;text-align: center">
        <input id="jarFile" type="file" accept=".jar" />
    </div>
</div>
<div id="jar-btn">
    <table cellpadding="0" cellspacing="0" style="width: 100%">
        <tr>
            <td>
                <a href="#" id="saveJar" class="easyui-linkbutton" iconCls="fa fa-save" plain="true">导入</a>
                <a href="#" id="cancelJar" class="easyui-linkbutton" iconCls="fa fa-times" plain="true">取消</a>
            </td>
        </tr>
    </table>
</div>
<%--控制台--%>
<div
        data-options="region:'south',title:'控制台',split:true,
		collapse:'south', border:true,"
        style="height: 200px;  text-align: center;">
    <%-- src="./dev/panel/scroll.html" --%>
    <iframe id="kzt1iframe" src="./thread.html" width="99%" height="95%" scrolling="yes">
        <p>Your browser does not support iframes.</p>
    </iframe>
</div>

<div id="tree-layout" data-options="region:'west',title:'导航',split:true,iconCls:'icon-world'"
        style="width: 235px; padding: 10px;">
    <ul id="etree"></ul>
    <div id="dmenu" class="easyui-menu" style="width: 30px; display: none;">
        <!--放置一个隐藏的目录菜单Div-->
        <!--具体的菜单事件请自行添加，跟toolbar的方法是基本一样的-->
        <div id="btn_AddContent" data-options="iconCls:'fa fa-plus'" onclick="javascript:$('#etree').etree('createDirectory')">新增规范</div>
        <div id="btn_Add" data-options="iconCls:'fa fa-plus'" onclick="javascript:$('#etree').etree('create')">新增脚本</div>
        <div id="btn_Modify" data-options="iconCls:'icon-edit'" onclick="javascript:$('#etree').etree('edit')">修改</div>
        <div id="btn_Delete" data-options="iconCls:'icon-remove'" onclick="javascript:$('#etree').etree('destroy')">删除</div>

    </div>
    <div id="fmenu" class="easyui-menu" style="width: 30px; display: none;">
        <!--放置一个隐藏的文件菜单Div-->
        <!--具体的菜单事件请自行添加，跟toolbar的方法是基本一样的-->
        <%--<div id="btn_Add1" data-options="iconCls:'fa fa-plus'" onclick="javascript:$('#etree').etree('create')">新增</div>--%>
        <div id="btn_Modify1" data-options="iconCls:'icon-edit'" onclick="javascript:$('#etree').etree('edit')">修改</div>
        <div id="btn_Delete1" data-options="iconCls:'icon-remove'" onclick="javascript:$('#etree').etree('destroy')">删除</div>
        <%--<div id="btn_Import" data-options="iconCls:'fa fa-sign-in'" onclick="">
            <a class="bar_file">导入<input type="file" accept=".xml" /></a>
        </div>
        <div id="btn_Export" data-options="iconCls:'fa fa-sign-out'" onclick="">导出</div>--%>
    </div>
</div>
<div id="dialog_edit" class="dlg">

</div>
<div data-options="region:'center'" style="overflow: hidden;">
    <div id="tt" class="easyui-tabs" fit="true" border="false">
        <div title="起始页" iconCls="icon-house"
             style="padding: 10px 10px; display: block;">欢迎来到后台管理系统！
        </div>
    </div>
</div>
<div id="pp"  style="padding:10px;">
    <div id="ff" style="text-align: center"></div>
    <div class="buttons-div">
        <form id="bt" target="_self"></form>
        <!--<button id="mybt1" >按钮一</button><button id="mybt2">按钮二</button>-->
    </div>
</div>
<div id="mm" class="easyui-menu" style="width: 120px;">
    <div id="mm-tabclosrefresh" data-options="name:6">刷新</div>
    <div id="mm-tabclose" data-options="name:1">关闭</div>
    <div id="mm-tabcloseall" data-options="name:2">全部关闭</div>
    <div id="mm-tabcloseother" data-options="name:3">除此之外全部关闭</div>
    <div class="menu-sep"></div>
    <div id="mm-tabcloseright" data-options="name:4">当前页右侧全部关闭</div>
    <div id="mm-tabcloseleft" data-options="name:5">当前页左侧全部关闭</div>
</div>

<div id="kzt1menu" class="easyui-menu">
    <div id="btn_Clear" data-options="iconCls:'icon-remove'" onclick="removeAll()">清空</div>
</div>
</body>
</html>