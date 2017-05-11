/**
 * Created by xq on 2016/12/27.
 */
$(function(){
    $('#panel2').panel({
        width: 500,
        height: 500,
        title: 'my panel',
        /*tools: [{
            iconCls: 'icon-add',
            handler: function(){alert('new');},

        },{
            iconCls: 'icon-save',
            handler: function(){alert('save');}
        }],*/
        collapsible: true,
        minimizable: true,
        maximizable: true,

        onMinimize: function(){
            //alert('minizied');
          $('#panel2').panel('move',{
              left: 0 ,
              top: 500
          }).panel('collapse').panel('open');
        },

    });


});