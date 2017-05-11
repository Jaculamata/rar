/**
 * Created by xq on 2016/12/26.
 */
$(function () {
    $('#pp2').pagination({
        total: 2000,
        pageSize: 10,
        pageNumber: 1,/*默认显示第一页的数据*/
        pageList: [10,20,50,100],/*改变页面大小，一页显示的数据量变化*/
        //loading: true, /*显示页面正在加载，默认关闭*/
        buttons: [{
            iconCls: 'icon-add',
            handler: function(){
                alert('add');
            }
        },'-',{
            iconCls: 'icon-save',
            handler: function(){
                alert('save');
            }
        },'-'],
        //layout: ['first','sep','prev','next','links','last'],
        displayMsg: '{from}/{to},共{total}',
        /*事件*/
        onSelectPage: function(n,size){/*n:新的页数，size:页面大小*/
            alert('第'+n+'页,'+size+'项');
        },
    });
})