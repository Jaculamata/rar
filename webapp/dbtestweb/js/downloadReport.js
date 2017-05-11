/**
 * Created by Liuwei on 2017/4/17.
 */
function downloadReport() {
    $('#inputReport').dialog({
        closed:false,
        //关闭对话框时清除输入框的内容
        onClose:function(){
            $('#evaluation').val('');
            $('#securityLevel').val('安全标记保护级');
        }
    });
    $('#evaluation').val('');
    $('#evaluation').focus();

    $('#saveReport').unbind('click').bind('click',function () {
        var evaluation = $.trim($('#evaluation').val());
        var securityLevel = $('#securityLevel').val();
        console.log('evaluation = ',evaluation);
        console.log('securityLevel = ',securityLevel);
        if (evaluation == ''){
            $.messager.confirm("生成测试报告","您输入的测试评价为空，请确定是否继续生成测试报告？",function (r) {
                if(r){
                    saveReport(evaluation,securityLevel);
                }
            });
        }else{
            saveReport(evaluation,securityLevel);
        }

    });

    $('#cancelReport').unbind('click').bind('click',function () {
        $('#inputReport').dialog('close');
        $('#evaluation').val('');
        $('#securityLevel').val('安全标记保护级');
    });
}

function saveReport(evaluation, securityLevel){
    var form = $("<form>");//定义一个form表单
    form.attr("style","display:none");
    form.attr("target","");
    form.attr("method","post");
    form.attr("action","../downloadReport.do");
    //传递evaluation
    var input = $("<input>");
    input.attr("type","hidden");
    input.attr("name","evaluation");
    input.attr("value",evaluation);
    //安全等级
    var inputLevel = $("<input>");
    inputLevel.attr("type","hidden");
    inputLevel.attr("name","securityLevel");
    inputLevel.attr("value",securityLevel);

    $("body").append(form);//将表单放置在web中
    form.append(input);
    form.append(inputLevel);

    form.submit();//表单提交
    $('#inputReport').dialog('close');
    $('#evaluation').val('');
    $('#securityLevel').val('安全标记保护级');
}
