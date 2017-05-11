/**
 * Created by xq on 2016/12/27.
 */
$(function(){
   $('#mm').menu({
       /*onClick: function(item){
           console.log(item);
           //alert(item);
       },*/
       /*onShow: function(){
           alert('show');
       },*/
       /*onHide: function(){
           alert('hide');
       }*/
   }) ;

    $(document).bind('contextmenu',function(e){
        e.preventDefault();
        $('#mm').menu('show',{
            left: e.pageX,// 200,
            top: e.pageY//100
        });
    });

    var itemEl = $('#mnew')[0];
    var item = $('#mm').menu('getItem',itemEl);
    console.log(item);
});