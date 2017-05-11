/**
 * Created by xq on 2016/12/26.
 */
$(function () {

    /*$('#dd').tooltip({
        position: 'right',
        content: '<span style="color:#fff">This is the tooltip message.</span>',
        onShow: function () {
            $(this).tooltip('tip').css({backgroundColor: '#666', borderColor: '#666'});
        }
    });*/
    $('#dd').tooltip({
     position: 'right',
     content: '<span style="color: #fff">这是一个提示框</span>',
     onShow:function(){
     $(this).tooltip('tip').css({
     backgroundColor: '#666',
     borderColor: '#666'
     });
     }
     }) ;
});