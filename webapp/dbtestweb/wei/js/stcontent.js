/**
 * Created by Liuwei on 2017/3/28.
 */
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

    console.log("window.height1 = ",$(window).height());
    $('#standard_content').height($(window).height()-93);
    $('#description').height($(window).height()-93);

    $(window).resize(function (){
        console.log("window.height2 = ",$(window).height());
        $('#standard_content').height($(window).height()-93);
        console.log("standard_content height = ",$('#standard_content').height());
        $('#description').height($(window).height()-93);
        console.log("description height = ",$('#description').height());
    });

    var id = getValue("id");
    console.log("id:",id);
    //加载页面时获取标准内容和描述内容
    $.ajax({
        url:'../../getStandard.do',
        data:{
            'title': id
        },

        success:function (data) {
            console.log("data:",data);

            var content = '';
            var description = '';
            if (data != null && data != ''){
                content = data.content;
                description = data.description;
            }
            console.log("content:",content);
            console.log("description:",description);

            $('#standard_content').val(content);
            $('#description').val(description);
            console.log("finished");
        }
    });


    function saveStandard(id,content,description) {
        $.ajax({
            type: 'post',
            async: true,
            url:'../../uploadStandard.do',
            data:{
                'title': id,
                'content': content,
                'description': description
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
                }
            }
        });
    }

    //保存
    $('#save2').unbind('click').bind('click',function(){
        var content = $.trim($('#standard_content').val());
        var description = $.trim($('#description').val());
        if(content == "" || content == null || description == "" || description == null){
            $.messager.confirm('提示信息','温馨提示，标准规范内容或描述内容为空，按确认提交保存，按取消返回修改。',function(r){
                if(r){
                    saveStandard(id,content,description);
                }
            });
        }else{
            saveStandard(id,content,description);
        }
    });

    //获取
    $('#obtain2').unbind('click').bind('click',function(){

        $.ajax({
            type: 'post',
            async: true,
            url:'../../getStandard.do',
            data:{
                title: id
            },
            error:function(){
                $.messager.show({
                    title:'提示信息',
                    msg:'获取标准内容失败！请记录本次出错',
                    timeout:2000,
                    showType:'slide'
                });
            },
            success:function(data){
                console.log("obtain data:",data);

                var content = '';
                var description = '';
                if (data != null && data != ''){
                    content = data.content;
                    description = data.description;
                }
                console.log("content:",content);
                console.log("description:",description);

                $('#standard_content').val(content);
                $('#description').val(description);

            }
        });

    });

});