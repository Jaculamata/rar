/**
 * Created by xq on 2017/1/4.
 */
function append(){
    var row = {
        name:'AddName',
        value:'',
        group:'Marketing Settings',
        editor:'text'
    };
    $('#pg2').propertygrid('appendRow',row);
}
function save(){
   // 得到所有行的对象
   /*var rows = $('#pg2').propertygrid('getRows');
    console.log(rows);*/

//    得到变化行的对象
    var res = '';
    var rows = $('#pg2').propertygrid('getChanges');
    $.each(rows, function (i,value) {
        res += rows[i].name + ':' + rows[i].value + ',';
        console.log(i,value);
    });
    console.log(res);
}
$(function () {
    var proptitle = [[{
        field: 'name'
        , title: '属性名'
        , width: 100
        //,sortable: true
    },{
        field: 'value'
        , title: '属性值'
        , width: 100
        , resizable: false
        ,
    }]];
    /*
     type: string, the edit type,
     possible type is: text,textarea,checkbox,numberbox,validatebox,datebox,combobox,combotree.*/
    $('#pg2').propertygrid({
        url: 'prop.json'
        , showGroup: true
        , scrollbarSize: 0
        , columns: proptitle
        , rownumbers: true
        //, striped: true
        ,
    });



    /*var row = {
        name: 'AddName'
        , value: ''
        , group: 'Marketing Settings'
        , editor: 'text'
    };
    $('#pg').propertygrid('appendRow', row);*/

});