/**
 * Created by Administrator on 2017/5/9.
 */

function statistic(){
    $("#info").dialog({
        closed:false
    });
    alert("sent  ajax");
    
    $.ajax({
        url:"../countInformation.do",
        success:function(result){
            alert("success");
            containNotAuto=result["halfNumber"]+result["manualNumber"];
            alert(JSON.stringify(result));
            var mydata={"total":4,"rows":[
                {"name":"所有脚本数量","value":result["allNumber"],"group":"所有脚本信息","editor":"text"},
                {"name":"已测脚本数量","value":result["testNumber"],"group":"所有脚本信息","editor":"text"},
                {"name":"未测脚本数量","value":result["restNumber"],"group":"所有脚本信息","editor":"text"},
                {"name":"自动化脚本数量","value":result["autoNumber"],"group":"所有脚本信息","editor":"text"},
                {"name":"半自动化脚本数量","value":result["halfNumber"],"group":"所有脚本信息","editor":"text"},
                {"name":"人工脚本数量","value":result["manualNumber"],"group":"所有脚本信息","editor":"text"},
                {"name":"所有参与测试的脚本数量","value":result["testNumber"],"group":"测试脚本信息","editor":"text"},
                {"name":"成功脚本数量","value":result["successNumber"],"group":"测试脚本信息","editor":"text"},
                {"name":"失败脚本数量","value":result["failNumber"],"group":"测试脚本信息","editor":"text"},
                {"name":"取消测试的脚本数量","value":result["cancelNumber"],"group":"测试脚本信息","editor":"text"}

            ]}
            $('#pg').propertygrid({
                 data:mydata,
                showGroup: true,
                scrollbarSize: 0,
 
            });

        },
        error:function(){
            alert("fail");
        }
    })
}