/**
 * Created by xq on 2016/12/27.
 */
$(function(){
    $('#combo1').combo({
        required: true,
        multiple: true,
        onShowPanel: function(){
            var val = $('#combo1').combo('getValue');
            alert(val);
        },
    });
})