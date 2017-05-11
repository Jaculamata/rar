/**
 * Created by xq on 2016/12/27.
 */

$(function(){
    $('#tree2').tree({
        url: 'tree2.json',
        onClick: function(node){
            //alert(node.text);
            $('#tree2').tree('update',{
                target: node.target,
                text: 'new '+node.text,
            });
        }
    });
});
