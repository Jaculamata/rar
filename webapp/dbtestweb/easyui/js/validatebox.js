/**
 * Created by xq on 2016/12/27.
 */
$(function(){
   $('#vv').validatebox({
       required: true,
       validType: 'email'
   }) ;

    /*验证两次密码相同*/
    $.extend($.fn.validatebox.defaults.rules,{
       equals: {
           validator: function(value, param){
               return value == $(param[0]).val();
           },
           message: '两次输入不相同!'
       }
    });

    /*验证最小长度*/
    $.extend($.fn.validatebox.defaults.rules,{
        minLength:{
            validator: function(value,param){
                console.log(param);
                return value.length >= param[0];
            },
            message: '请输入至少{0}个字符'
        }
    });

    /*为jquery添加了一个静态方法*/
    $.extend({
        add : function(a,b){return a+b;}
    })
    /*对jquey add方法的调用*/
    console.log($.add(1,2));
});