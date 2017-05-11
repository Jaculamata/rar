/**
 * Created by xq on 2016/12/27.
 */
$(function () {

    $('#tabs1').tabs({
        //border: false,
        /*onSelect: function(title){
            alert(title+"is selected.");
        }*/
    });

    $('#tabs1').tabs('add',{
        title: 'new tab',
        content: 'tab body',
        closable: true,
        tools:[{
            iconCls: 'icon-mini-refresh',
            handler: function(){
                alert('refresh');
            }
        }]
    });
});