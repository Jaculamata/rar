/**
 * Created by xq on 2016/12/27.
 */
$(function () {
   $('#cc').layout('add',{
       region: 'west',
       width: 180,
       title: 'west',
       split: true,
       tools: [{
           iconCls: 'icon-add',
           handler: function(){
               alert('add');
           }
       },{
           iconCls: 'icon-remove',
           handler: function(){
               alert('remove');
           }
       }]
   });
});