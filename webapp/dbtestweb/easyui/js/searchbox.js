/**
 * Created by xq on 2016/12/27.
 */
$(function(){

   $('#searchbox').searchbox({
       searcher : function(value,name){
           alert(value+":"+name);
       },
       //width: 120,
       menu: '#menu2',
       prompt: '请输入值',


   }) ;

    //alert($('#searchbox').searchbox('options').prompt);
});