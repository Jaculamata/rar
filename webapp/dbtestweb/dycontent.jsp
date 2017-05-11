
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	if (request.getSession().getAttribute("loginUser") == null){
		response.sendRedirect("index.html");
	}
%><!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="easyui/themes/bootstrap/easyui.css"/>
    <!--<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">-->
    <link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
    <!--<link rel="stylesheet" type="text/css" href="easyui/demo/demo.css">-->

    <link rel="stylesheet" type="text/css" href="./font-awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="./font-awesome/css/ui.css"/>
    <link rel="stylesheet" type="text/css" href="css/demo.css"/>


    <script type="text/javascript" src="easyui/jquery.min.js"></script>
    <script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script>
    <script src="js/dycontent.js"></script>
    <!--<script src="js/jquery.min.js"></script>-->
    <!--<script type="text/javascript" src="js/tabs.js"></script>-->
    <script type="text/javascript" src="js/downloadReport.js"></script>
    <script type="text/javascript" src="js/ichart.1.2.min.js"></script>
    <script type="text/javascript" src="js/statisticInformatica.js"></script>
    <script type="text/javascript">
//        $(function(){
//            var data = [
//                {name : 'IE',value : 35.75,color:'#9d4a4a'},
//                {name : 'Chrome',value : 29.84,color:'#5d7f97'},
//                {name : 'Firefox',value : 24.88,color:'#97b3bc'},
//                {name : 'Safari',value : 6.77,color:'#a5aaaa'},
//                {name : 'Opera',value : 2.02,color:'#778088'},
//                {name : 'Other',value : 0.73,color:'#6f83a5'}
//            ];
//
//            new iChart.Pie2D({
//                render : 'canvasDiv',
//                data: data,
//                title : 'Top 5 Browsers from 1 to 29 Feb 2012',
//                legend : {
//                    enable : true
//                },
//                showpercent:true,
//                decimalsnum:2,
//                width : 800,
//                height : 400,
//                radius:140
//            }).draw();
//        });
    </script>
</head>
<body>
<button id="mybt"  onclick="getSelected()" >执行</button>
<button id="information"  onclick="statistic()" >统计信息</button>
<button id="downloadReport"  onclick="downloadReport()" >生成测试报告</button>
<table id="tg" class="easyui-treegrid" ></table>
//统计信息
<div id="info" class="easyui-dialog" title="统计信息" style="width:350px ;height: 450px;padding: 10px" buttons="#information-btn" closed="true" data-options="inline:true">
    <table id="pg" style="width:300px"></table>
</div>
<div id="information-btn">
    <table cellpadding="0" cellspacing="0" style="width: 100%">
        <tr>
            <td>
                <a href="#" id="yes" class="easyui-linkbutton" iconCls="fa fa-save" plain="true">确定</a>
                <a href="#" id="no" class="easyui-linkbutton" iconCls="fa fa-times" plain="true">取消</a>
            </td>
        </tr>
    </table>
</div>
<%-- 测试评价 --%>
<div id="inputReport" class="easyui-dialog" title="测试评价" style="width: 400px;height: 300px;padding: 10px"
     buttons="#report-btn" closed="true" data-options="inline:true">
    <div style="margin-bottom: 10px;">
        <span>请选择安全等级：&nbsp;</span>
        <select id="securityLevel">
            <option selected = "selected">安全标记保护级</option>
            <option>系统审计保护级</option>
            <option>用户自主保护级</option>
        </select>
    </div>
    <span>测试评价：</span>
    <div style="text-align: center;height: 65%;padding: 10px 0 10px 0;">
        <textarea id="evaluation"
                  style="width: 95%;height: 100%;padding: 5px; border: 1px solid #000;resize: none;">
        </textarea>
    </div>
</div>
<div id="report-btn">
    <table cellpadding="0" cellspacing="0" style="width: 100%">
        <tr>
            <td>
                <a href="#" id="saveReport" class="easyui-linkbutton" iconCls="fa fa-save" plain="true">确定</a>
                <a href="#" id="cancelReport" class="easyui-linkbutton" iconCls="fa fa-times" plain="true">取消</a>
            </td>
        </tr>
    </table>
</div>
<!--<table id=”mytg“ title="Folder Browser" class="easyui-treegrid" data-options="-->
<!--url:'get_data.do',-->
<!--idField:'id',-->
<!--rownumbers:true,-->
<!--method:'get',-->
<!--treeField:'text',-->
<!--singleSelect:false,-->
<!--checkbox:true-->

<!--">-->
    <!--<thead>-->
    <!--<tr>-->
        <!--<th id ="ck" data-options="field:'id'" checkbox="true"></th>-->
        <!--<th data-options="field:'text'" width="220" >Name</th>-->
        <!--<th data-options="field:'type'" width="100" align="right">Description</th>-->
        <!--<th data-options="field:'url'" width="150">Content</th>-->
    <!--</tr>-->
    <!--</thead>-->
<!--</table>-->
<div id="content">

</div>
<script>
    var test=window.frameElement.id;
//    alert(test);
//alert(dyindex);
    var path='../etree/';
    $('#tg').treegrid({
        method:"GET",
        url:path+'get_treegrid.do'+"?id="+test,
        rownumbers: true,
        width: $(window).width() - 20,
        height: $(window).height() - 40,
        fitColumns: true,
//        formatter: function (node) {
////                    $.parser.parse();
//            return '<a>'+node.text+'</a>';
//        },

        columns:[[
            {checkbox:true},
            {field:'id',title:'id',width:1},
            {field:'_parendId',title:'_parentId',width:1},
//            {filed:'level',title:'level',width:2},
            {field:'text',title:'text',width:4},
            {field:'attr',title:'attr',width:4},
//            {field:'type',title:'type',width:100,align:'right'},
//            {field:'url',title:'url',width:100},
//            {filed:'state',title:'state',width:100},
//            {filed:'level',title:'level',width:100},

        ]],
        treeField:"text",
        idField:"id",
        parentField:'fj',
//        checkbox:true,
        dnd: false,
        singleSelect:false,
        onBeforeExpand:function(node){
            var row=node.id;
//            node.url="../etree/get_data?id="+node.attr;

            node.url = '../etree/get_treegrid.do?id='+node.attr;
            $("#tg").treegrid("options").url =node.url;
//            alert($("#tg").treegrid("options").url);
//            alert(test+"test"+JSON.stringify(node)+"====="+node.url);
            return true;
        },
        loadFilter: function(data,parentId){
            var temp=JSON.stringify(data);
//         alert(temp);
            temp=temp.replace(/fj/g,"_parentId");
            var  id = 'id', pid = '_parentId';
            data=JSON.parse(temp);
//         alert(JSON.stringify(data));
            for(var i=0;i<data.length;i++)
            {

                var t=data[i][id];
                var p=data[i][pid];
//                alert("t="+t);
//                alert("p="+p);
//            alert(t+p)
//             alert("t before change"+t);
                data[i]["attr"]=data[i][id];
//                alert("data[i][attr]"+data[i]["attr"]);
                t= t.replace(/\./g,"");

                p=p.replace(/\./g,"");

                data[i][id]=parseInt(t);
//             alert("t after change"+t);
                data[i][pid]=parseInt(p);
//             alert(data[i][pid]);
//转成int后第一个0被省略了
            }
//         var r = [], hash = {}, id = 'id', pid = 'fj', children = 'children', i = 0, j = 0, len = data.length;
//         for(; i < len; i++){
//             hash[data[i][id]] = data[i];
//         }
//         for(; j < len; j++){
//             var aVal = data[j], hashVP = hash[aVal[pid]];
//             if(hashVP){
//                 !hashVP[children] && (hashVP[children] = []);
//                 hashVP[children].push(aVal);
//             }else{
//                 r.push(aVal);
//             }
//         }
//         alert(JSON.stringify(r));
//         return r;
//         alert(JSON.stringify(data));
            return data;
        }

    });
    $(window).resize(function (){
        $('#tg').treegrid('resize', {
            width: $(window).width() - 20,
            height: $(window).height() - 40
        });
    });
</script>
</body>
</html>
<!--private String id;-->
<!--private String fj;-->
<!--private int level;-->
<!--private String text;-->
<!--private String attr;-->
<!--private String url;-->
<!--private String state;-->
<!--private String type;-->
