/**
 * Created by xq on 2016/12/26.
 */
$(function(){
   $('#dlg').dialog({
        title: 'my dialog',
       width: 400,
       height: 200,
       closed: false,
       cache: false,
       //href: "dialog.php",
       modal: true,
       toolbar:[{
           text:'edit',
           iconCls: 'icon-edit',
           handler:function(){
               alert('click edit');
           }
       },{
           text: 'help',
           iconCls: 'icon-help',
           handler: function(){
               alert("click help");
           }
       },{
            //type: 'input'
       }],
       buttons:[{
        text: "save",
        iconCls: 'icon-save',
        handler: function(){
               alert("click save");
           }
       },{
           text: 'cancel',
           iconCls: 'icon-cancel',
           handler: function(){
               //alert("click cancel");
               $('#dlg').dialog('close');
           }
       }],
   }) ;

    /*$('#win').dialog({
       width: 400,
        height: 200,
        modal: true
    });*/
});