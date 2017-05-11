/**
 * Created by xq on 2016/12/26.
 */
$(function(){
   $('#win').window({
       width: 600,
       height: 400,
       modal: true,
       onMinimize: function(){
           $('#win').window('move',{
               left : '58%',
               top : '97%'
           }).window('collapse').window('open');
       }
   });
});