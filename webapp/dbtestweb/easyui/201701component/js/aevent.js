/**
 * Created by xq on 2017/1/10.
 */
function showFile() {
//    alert('show File');
  $('#file').css("background-color", '#CA7919');
  $('#file').css('color', 'white');
  /*file.css("backgournd-color",'black');*/
}
/*分析 1.点击后，背景色改变，弹出选项框
 在a 或 div 上 背景色不变
 离开两处区域 背景色消失
 移动到其他上，和1相同
 点击非事件区
 * */
$(function () {
  /*$('#show').click(function (event) {
   //取消事件冒泡
   event.stopPropagation();
   //设置弹出层的位置
   var offset = $(event.target).offset();
   $('#showdiv').css({top: offset.top + $(event.target).height() + "px", left: offset.left});
   //按钮的toggle,如果div是可见的,点击按钮切换为隐藏的;如果是隐藏的,切换为可见的。
   $('#showdiv').toggle('slow');
   });
   //点击空白处或者自身隐藏弹出层，下面分别为滑动和淡出效果。
   $(document).click(function (event) {
   //        $('#divTop').slideUp('slow')
   $('#showdiv').hide();
   });
   $('#showdiv').click(function (event) {
   //        $(this).fadeOut(1000)
   $('#showdiv').hide();
   });
   */
  var clicked = false;
  var id;
  var divid;

  //点击空白处或者自身隐藏弹出层，下面分别为滑动和淡出效果。
  $(document).click(function (event) {
//        $('#divTop').slideUp('slow')
    $('#'+id).css('background-color','');
    $('#' + divid).hide();
  });

  function showdiv(event, id) {
    event.stopPropagation();
    var offset = $(event.target).offset();
    $('#' + id).css({top: offset.top + $(event.target).height() + "px", left: offset.left});
    $('#' + id).toggle('slow');
  }

  $('a').on({
    /*mouseenter: function (event) {
     //        alert('mouseenter');
     console.log(id,$(this)[0].id,event);
     if(id!=$(this)[0].id){
     $('#'+id).css('background-color','');
     }
     if(clicked){
     $(this).css('background-color','#00a0e9');
     console.log('#'+$(this)[0].id+'div');
     showdiv(event,'#'+$(this)[0].id+'div');
     }
     },*/
    /*mouseover: function (e) {
     console.log('mouseover');
     },*/
    /* mouseout: function (e) {
     //        console.log('mouseout');
     //      $(this).css('background-color','');
     if(id!==$(this)[0].id){
     $(id).css('background-color','');
     $('#'+id+'div').hide();
     }

     },*/
    /*hover没反应*/
    /*hover: function (e) {
     e.preventDefault();
     alert('hover');
     },*/
    click: function (e) {
//        alert('click');
      if(id!=$(this)[0].id){
        $('#'+id).css('background-color','');
        $('#'+divid).hide();
      }
      $(this).css('background-color', '#00a0e9');
      console.log($(this)[0].id);
      id = $(this)[0].id;
      divid = $(this)[0].id + 'div';
      clicked = true;
      showdiv(e, divid);
    }
  });
  /*取消绑定
   $('a').off('mouseenter').unbind('mouseleave');
   */
});