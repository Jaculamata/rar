/**
 * Created by Liuwei on 2017/3/29.
 */

$(document).ready(function () {
    var loginUser = $('#loginUser').text();
    console.log("loginUser=",loginUser);
    //显示项目名
    $.ajax({
        type: 'post',
        url :'../showProjectName.do',
        success:function(result){
            if (result.length == 0){
                $("#tree-layout").panel('setTitle','空项目');
            }else {
                $("#tree-layout").panel('setTitle',result);
            }
        }
    });
});

//（仅管理员拥有此权限）初始化用户管理窗口及用户信息表
function userRegulation(){
    $('#userRgl').dialog({
        closed:false,
        onBeforeClose:function(){
            var flag = $("#addUserInfo").parent().is(":hidden");
            console.log('addUserInfo open or not:',flag);
        },
        //关闭用户管理窗口的同时，关闭添加用户窗口（如果此窗口为open状态）、清除其输入框内容
        onClose:function(){
            $('#addUserInfo').dialog('close');
            $('#addUsername').val('');
            $('#addPassword').val('');
        }
    });
    var datatitle = [[{
        /*field: 'ck',
        checkbox: 'true'
    },{*/
        field: 'user',
        title: '用户名',
        width: 50,
        align: 'center',
        editor: { type: 'text', options: { required: true } }
    },{
        field: 'password',
        title: '密码',
        width: 60,
        resizable: false,
        align: 'center',
        editor: { type: 'text', options: { required: true } }
    }]];
    //初始化用户信息表
    $('#userInfo').datagrid({
        columns: datatitle,
        url: '../showTestUser.do',
        idField: 'user',
        singleSelect:true,
        fitColumns: true,
        loadMsg: '数据正在加载中，请稍后......',
        ctrlSelect: false,
        rownumbers: true,     //显示行号列
        pagination: true,     //在底部显示分页工具栏
        pageSize: 10,
        pageList: [10, 15, 20, 25],
        scrollbarSize: 0
    });
}

//（管理员）根据输入的用户名查询用户信息
function searchUser(value,name) {
    $.ajax({
        type:'post',
        url:'../selectTestUser.do?username='+value,
        success:function(data){   //查询成功返回一条用户信息（用户名+密码）
            console.log('search data: ',data);
            if(data != null && data != ''){   //查询结果不为空时显示查询信息
                $('#userInfo').datagrid('loadData',data);
                //$('#user-search').searchbox('clear');
            }else{
                $.messager.alert('提示信息','该用户不存在！','info',function(){
                    $('#user-search').searchbox('clear');
                    $('#userInfo').datagrid('reload');
                });
            }
        }
    });
}

//管理员添加用户
function addUser() {

    $('#addUserInfo').dialog({
        closed:false,
        //关闭对话框时清除输入内容
        onClose:function(){
            $('#addUsername').val('');
            $('#addPassword').val('');
        }
    });
    $('#addUsername').focus();
    $('#saveUser').unbind('click').bind('click',function(){
        //获取输入的用户名和密码，清除输入内容首尾的空格
        var username = $.trim($('#addUsername').val());
        var password = $.trim($('#addPassword').val());
        if (username == '') {
            $.messager.alert('提示信息', '请输入用户名！', 'info', function () {
                $('#addUsername').focus();
                return;
            });
            return;
        }
        if (password == '') {
            $.messager.alert('提示信息', '请输入密码！', 'info', function () {
                $('#addPassword').focus();
                return;
            });
            return;
        }
        $.ajax({
            type: 'post',
            url: '../addUser.do',
            data: {
                'username': username,
                'password': password
            },
            success: function (data) {
                if (data == '2') {
                    $.messager.alert('提示信息', '用户名已存在！', 'info', function () {
                    });
                    return;
                }
                if (data == '1') {
                    $('#addUserInfo').dialog('close');
                    $('#userInfo').datagrid('insertRow', {
                        index: 0,
                        row: {
                            user:username,
                            password:password
                        }
                    });
                    $('#addUsername').val('');
                    $('#addPassword').val('');
                    $.messager.show({
                        title:'提示信息',
                        msg:'添加用户成功！',
                        timeout:2000,
                        showType:'slide'
                    });
                }
            }
        });
    });
    $('#cancelUser').unbind('click').bind('click',function(){
        $('#addUserInfo').dialog('close');
        $('#addUsername').val('');
        $('#addPassword').val('');
    });
}

//管理员重置某用户的密码（设置默认密码，默认为888888）
function resetUserPwd(){
    //获取当前选中行
    var row = $('#userInfo').datagrid('getSelected');
    if (row) {
        $.ajax({
            type:'post',
            url:'../resetUserPwd.do',
            data:{
                'username':row.user
            },
            success:function(data){
                if (data == '1'){
                    //$.messager.alert('提示信息','用户 '+rows[0].user+' 密码重置成功！','info',function(){});
                    //重置密码成功刷新用户信息表
                    $('#userInfo').datagrid('reload');
                    $('#userInfo').datagrid('unselectAll');
                    $('#userInfo').datagrid('uncheckAll');
                    $.messager.show({
                        title:'提示信息',
                        msg:'重置密码成功！',
                        timeout:2000,
                        showType:'slide'
                    });
                }
            }
        });
    }else{
        $.messager.alert('提示信息','您尚未选择信息！','info',function(){});
        return;
    }
}

//管理员删除用户
function deleteUser(){
    //获取当前选中用户信息
    var row = $('#userInfo').datagrid('getSelected');
    if (row){
        console.log("row : ",row);
        console.log("row.user : ",row.user);
        $.messager.confirm('提示信息','您确定删除此用户及其所有信息?',function(r){
            if(r){
                $.ajax({
                    type:'post',
                    url:'../deleteUser.do?username='+row.user,
                    success:function(data){
                        if(data == '1'){ //删除成功刷新用户表
                            $('#userInfo').datagrid('reload');
                            $('#userInfo').datagrid('unselectAll');
                            $('#userInfo').datagrid('uncheckAll');
                            $.messager.show({
                                title:'提示信息',
                                msg:'删除成功！',
                                timeout:2000,
                                showType:'slide'
                            });
                        }
                    }
                });
            }
        });
    }else {
        $.messager.alert('提示信息','您尚未选择信息！','info',function(){});
    }
}

//当前登录用户修改密码
function editPassword(){
    //弹出修改密码对话框（原密码、新密码、确认密码）
    $('#editPwd').dialog({
        closed:false,
        //关闭对话框时清除三个输入框的内容
        onClose:function(){
            $('#oldPwd').val("");
            $('#newPwd').val("");
            $('#confirmPwd').val("");
        }
    });
    $('#oldPwd').focus();

    $('#savePwd').unbind('click').bind('click',function(){
        //获取用户输入的信息
        var oldPwd = $.trim($('#oldPwd').val());
        var newPwd = $.trim($('#newPwd').val());
        var confirmPwd = $.trim($('#confirmPwd').val());
        //当前登录用户
        //var loginUser = $('#loginUser').text();
        console.log("oldPwd = ",oldPwd);
        console.log("newPwd = ",newPwd);
        console.log("confirmPwd = ",confirmPwd);
        //console.log("loginUser = ",loginUser);
        if(oldPwd == "" || oldPwd == null){
            $.messager.alert('提示信息','请输入原密码！','info',function(){
                $('#oldPwd').focus();
            });
            return;
        }
        if (newPwd == "" || newPwd == null){
            $.messager.alert('提示信息','请输入新密码！','info',function(){
                $('#newPwd').focus();
            });
            return;
        }
        if(confirmPwd == "" || confirmPwd == null){
            $.messager.alert('提示信息','请输入确认密码！','info',function(){
                $('#confirmPwd').focus();
            });
            return;
        }
        if(newPwd != confirmPwd){
            $.messager.alert('提示信息','新密码与确认密码不一致！','info',function(){
                $('#newPwd').focus();
            });
            return;
        }
        $.ajax({
            type:'post',
            url:'../changePwd.do',
            data:{
                //'username':loginUser,
                'oldPwd':oldPwd,
                'newPwd':newPwd

            },
            error:function(data){
                console.log("edit pwd error data: ",data);
            },
            success:function(data){
                console.log("edit pwd data: ",data);
                if(data == '3'){
                    $.messager.alert('提示信息','新密码不能与原密码相同！','info',function(){
                        $('#newPwd').focus();
                    });
                    return;
                }
                if(data == '2'){
                    $.messager.alert('提示信息','原密码输入错误！','info',function(){
                        $('#oldPwd').focus();
                    });
                    return;
                }
                if (data == '1'){ //修改密码成功后关闭对话框，并清除输入内容
                    $.messager.alert('提示信息','修改密码成功！','info',function(){
                        $('#editPwd').dialog('close');
                        $('#oldPwd').val("");
                        $('#newPwd').val("");
                        $('#confirmPwd').val("");
                    });
                }
            }
        });

    });
    //关闭对话框时清除三个输入框的内容
    $('#cancelPwd').unbind('click').bind('click',function(){
        $('#editPwd').dialog('close');
        $('#oldPwd').val("");
        $('#newPwd').val("");
        $('#confirmPwd').val("");
    });
}

//新建项目
function newProject(){
    //弹出参数设置对话框
    $('#winnew').dialog('open');
    $('#winnew').dialog({
        closed: false,
        //关闭参数设置窗口的同时，关闭导入jar包窗口（如果此窗口为open状态）、清除选择的文件
        onClose:function(){
            $('#importJar-btn').html("<input id='jarFile' type='file' accept='.jar' />");
            $('#importJar').dialog('close');
        }
    });
    var proptitle = [[{
        field: 'name',
        title: '属性名',
        width: 220
        //,sortable: true
    },{
        field: 'value',
        title: '属性值',
        width: 220,
        resizable: false

    }]];
    /*
     type: string, the edit type,
     possible type is: text,textarea,checkbox,numberbox,validatebox,datebox,combobox,combotree.*/
    var pg = {};
    //显示各参数默认值
    $.ajax({
        type:'post',
        url:'../selectProperty.do',
        success:function (res) {
            pg.showData(res);
        }
    });
    //获取参数
    pg.showData = function(res){
        //console.log(res);
        var values = {};
        for (var i = 0;i < res.length; i ++){
            values[i] = res[i].value;
        }
        var rows=[{"group":"登录信息","name":"数据库","value":values[0],"editor":"text"},
            //5.4lu,新建项目时新增安全等级的选择
            {"group":"登录信息","name":"安全等级","value":"安全标记保护级","editor":{
                "type": "combobox",
                "options":{
                    "data": [{
                        "value": "安全标记保护级", "text": "安全标记保护级"
                    },{
                        "value": "系统审计保护级", "text": "系统审计保护级"
                    },{
                        "value": "用户自主保护级", "text": "用户自主保护级"
                    }],
                    "panelHeight": "auto"
                }
            }},
            {"group":"登录信息","name":"服务器","value":values[1],"editor":"text"},
            {"group":"登录信息","name":"端口号","value":values[2],"editor":"numberbox"},
            {"group":"登录信息","name":"初始库","value":values[3],"editor":"text"},
            {"group":"登录信息","name":"用户","value":values[4],"editor":"text"},
            {"group":"登录信息","name":"口令","value":values[5],"editor":"text"},

            {"group":"消息设置","name":"保存消息数","value":values[6],"editor":"numberbox"},
            {"group":"消息设置","name":"消息检察间隔","value":values[7],"editor":"numberbox"},
            {"group":"消息设置","name":"是否显示输出信息","value":values[8],"editor":{
                "type": "checkbox",
                "options":{
                    "on": true,
                    "off": false
                }
            }},
            {"group":"消息设置","name":"是否自动保存消息到文件","value":values[9],"editor":{
                "type": "checkbox",
                "options":{
                    "on": true,
                    "off": false
                }
            }},
            {"group":"运行设置","name":"线程个数数","value":values[10],"editor":"numberbox"},
            {"group":"运行设置","name":"自动清空消息区","value":values[11],"editor":{
                "type": "checkbox",
                "options":{
                    "on": true,
                    "off": false
                }
            }},
            {"group":"运行设置","name":"发生错误后继续往下执行","value":values[12],"editor":{
                "type": "checkbox",
                "options":{
                    "on": true,
                    "off": false
                }
            }},
            {"group":"高级选项","name":"保存出错前执行的语句","value":values[13],"editor":{
                "type": "checkbox",
                "options":{
                    "on": true,
                    "off": false
                }
            }},
            {"group":"高级选项","name":"语句数","value":values[14],"editor":"numberbox"},
            {"group":"重启服务器设置","name":"服务器断开时自动重启","value":values[15],"editor":{
                "type": "checkbox",
                "options":{
                    "on": true,
                    "off": false
                }
            }},
            {"group":"重启服务器设置","name":"检察间隔(秒)","value":values[16],"editor":"numberbox"}];
        $('#pg2').propertygrid('loadData',rows);
    };

    //赋予控件propertygrid属性
    $('#pg2').propertygrid({
        showGroup: true,
        scrollbarSize: 0,
        loadMsg: '数据正在加载中，请稍后......',
        columns:proptitle,
        rownumbers: true
    });

    //关闭参数设置窗口的同时，关闭导入jar包窗口（如果此窗口为open状态）、清除选择的文件
    $('#cancelNewProject').unbind('click').bind('click',function () {
        $('#winnew').dialog('close');
        $('#importJar-btn').html("<input id='jarFile' type='file' accept='.jar' />");
        $('#importJar').dialog('close');
    });
    
    //点击下一步相应操作
    $('#newProjectNextStep').unbind('click').bind('click',function(){
        //弹出导入jar包对话框
        $('#importJar').dialog({
            closed:false,
            onClose:function(){
                $('#importJar-btn').html("<input id='jarFile' type='file' accept='.jar' />");
            }
        });
        //后台执行新建项目具体操作
        $('#saveProject').unbind('click').bind('click',function () {
            //得到所有行的对象
            var rows = $('#pg2').propertygrid('getRows');
            //为每行添加propertyid键值对
            for (var i = 0;i < rows.length;i ++){
                rows[i].propertyid = i + 1;
            }
            console.log(rows);
            //获取选择的jar包
            var file = $("#jarFile")[0].files[0];
            if(file == undefined){
                $.messager.alert('选择文件','请选择文件！','info',function(){});
                return;
            }
            console.log("file name",file.name);

            //将各参数及jar包添加到formData中，传至后台
            var formData = new FormData();
            formData.append('file', file);
            formData.append('json',JSON.stringify(rows));
            $.ajax({
                type:'post',
                url:'../createProject.do',
                data:formData,
                dataType: 'json',
                cache: false,
                processData: false,
                contentType: false,
                beforeSend : function () {
                    $.messager.progress({
                        text : '正在新建项目...'
                    });
                },
                error:function(){
                    $.messager.progress('close');
                    $.messager.alert('提示信息','服务器请求失败！','info',function(){});
                },
                success:function(result){
                    $.messager.progress('close');
                    //新建项目成功则清空选择的文件信息，并关闭相应对话框
                    console.log("result:",result);
                    $('#importJar-btn').html("<input id='jarFile' type='file' accept='.jar' />");
                    $('#importJar').dialog('close');
                    $('#winnew').dialog('close');
                    //$('#etree').etree('collapseAll');
                    //刷新树形菜单使之显示新建项目的信息
                    $('#etree').etree('reload');
                    //更新项目名
                    $("#tree-layout").panel('setTitle',rows[0].value);
                    $.messager.show({
                        title:'提示信息',
                        msg:'新建项目成功！',
                        timeout:2000,
                        showType:'slide'
                    });
                }
            });

        });
        //关闭导入jar包窗口
        $('#cancelJar').unbind('click').bind('click',function () {
            $('#importJar-btn').html("<input id='jarFile' type='file' accept='.jar' />");
            $('#importJar').dialog('close');
        });
    });
}

//显示当前用户项目列表
function openProjectDialog() {
    $('#openProjectDiv').dialog('open');
    var projectTitle = [[{
        field: 'projectid',
        title: '项目ID',
        width: 150,
        align: 'center'
    },{
        field: 'name',
        title: '项目名',
        width: 200,
        align: 'center'
    },{
        field: 'time',
        title: '创建时间',
        width: 150,
        align: 'center',
        resizeable: false
    }]];
    $('#openProjectTable').datagrid({
        url: '../selectProject.do',
        columns:projectTitle,
        singleSelect:true,
        fitColumns: true,
        loadMsg: '数据正在加载中，请稍后......',
        ctrlSelect: false,
        rownumbers: true,     //显示行号列
        pagination: true,     //在底部显示分页工具栏
        pageSize: 10,
        pageList: [10, 15, 20, 25],
        scrollbarSize: 0
    });
}

//打开项目，刷新树形菜单使其显示新打开的项目信息
function openProject() {
    //取货选中的项目信息（项目ID+项目名+项目创建时间）
    var row = $('#openProjectTable').datagrid('getSelected');
    if (row) {
        var projectid = row.projectid;
        $.ajax({
            type: 'post',
            url: '../openProject.do?projectid=' + projectid,
            success:function (res) {
                if (res == 1){
                    //查询成功则关闭项目列表
                    $('#openProjectDiv').dialog('close');
                    //$('#etree').etree('collapseAll');
                    $("#tree-layout").panel('setTitle',row.name);
                    //刷新树形菜单
                    $('#etree').etree('reload');
                }else{
                    $.messager.alert('提示信息','打开项目失败！','info',function(){});
                }
            }
        });
    }else {
        $.messager.alert('提示信息','请选择待打开项目！','info',function(){});
    }
}

//删除项目
function deleteProject() {
    var row = $('#openProjectTable').datagrid('getSelected');
    if (row){
        $.messager.confirm('提示信息','您确定删除此项目及其所有信息?',function(r){
            if(r){
                $.ajax({
                    type:'post',
                    url:'../deleteProject.do?projectid='+row.projectid,
                    success:function(data){
                        if(data == '1'){   //data='1'表示删除的项目是当前打开的项目
                            //刷新项目列表
                            $('#openProjectTable').datagrid('reload');
                            //刷新树形菜单
                            $('#etree').etree('reload');
                            $("#tree-layout").panel('setTitle','空项目');
                            $.messager.show({
                                title:'提示信息',
                                msg:'删除成功！',
                                timeout:2000,
                                showType:'slide'
                            });

                        }else if (data == '2') {
                            $('#openProjectTable').datagrid('reload');
                            $.messager.show({
                                title:'提示信息',
                                msg:'删除成功！',
                                timeout:2000,
                                showType:'slide'
                            });
                        }else if (data=='3'){
                            $.messager.alert('提示信息','您删除的项目为模板项目，不能删除！','info',function(){});
                        }
                    }
                });
            }
        });
    }else {
        $.messager.alert('提示信息','您尚未选择信息！','info',function(){});
    }
}

function importJar(){
    $('#importJar').dialog({
        closed:false,
        onClose:function(){
            $('#importJar-btn').html("<input id='jarFile' type='file' accept='.jar' />");
        }
    });
    var projectid = 1;
    var propertyid = 1;

    $.ajax({
        type:'post',
        url:'../selectPP.do',
        data:{
            'projectid': projectid,
            'propertyid': propertyid
        },
        success:function (result) {
            console.log("Jar name:",result);
            if(result == "" || result == null){
                $('#jarName').html("无驱动Jar包");
            }else{
                var jarPath = result.value;
                var jarPathArr = jarPath.split('/');
                var jarName = jarPathArr[jarPathArr.length - 1];
                $('#jarName').html(jarName);
            }
        }
    });

    $('#saveJar').unbind('click').bind('click',function () {
        var file = $("#jarFile")[0].files[0];
        if(file == undefined){
            $.messager.alert('选择文件','请选择文件！','info',function(){});
            return;
        }
        var formData = new FormData();
        formData.append('file', file);
        console.log("file name",file.name);
        formData.append('projectid',projectid);
        formData.append('propertyid',propertyid);
        $.ajax({
            type:'post',
            url:'../importDriver.do',
            data:formData,
            cache: false,
            processData: false,
            contentType: false,
            success:function(result){
                console.log("result:",result);
                $('#importJar-btn').html("<input id='jarFile' type='file' accept='.jar' />");
                $('#jarName').html(file.name);
                $.messager.show({
                    title:'提示信息',
                    msg:'导入成功！',
                    timeout:2000,
                    showType:'slide'
                });
            }
        });

    });
    
    $('#cancelJar').unbind('click').bind('click',function () {
        $('#importJar-btn').html("<input id='jarFile' type='file' accept='.jar' />");
        $('#importJar').dialog('close');
    });
}

//退出登录，清除session
function logout() {
    $.ajax({
        type:'post',
        url:'../logout.do',
        success:function (result) {
            if (result == '1'){
                location.href = 'logout.jsp'
            }
        }
    });
}