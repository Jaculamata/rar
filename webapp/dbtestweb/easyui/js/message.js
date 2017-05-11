/**
 * Created by xq on 2016/12/26.
 */
$(function(){
    /*alert 警告信息
    title
    msg
    icon : info warning
     */
    /*$.messager.alert('警告','警告消息','info',function(){
        alert('关闭时，回调这个函数');
    });*/

    /*弹出确认信息*/
    /*$.messager.confirm('确认','您确认删除此项',function(r){
        if(r){/!*ok: true*!/
            alert('确认删除'+r);
        }
    });*/

    /*提示信息*/
   /* $.messager.prompt('提示信息','请输入您的姓名',function(r){
        if(r){
            alert('您的姓名是:'+r);
        }
    });*/

    /*progress进度条*/
    /*$.messager.progress({
        title: '我是进度条',
        msg: '我是信息',
        //text: '进度条上的文字',
        interval : 300,
    });*/
    $.messager.show({
       title:' my info',
        msg: '消息将在5秒后关闭',
        timeout: 5000,
        showType: 'show',//''slide'/*slide 从下往上划出*/
        /*style:{
            right: '',
            top:document.body.scrollTop+document.documentElement.scrollTop,
            bottom: ''
        }*/
    });

});