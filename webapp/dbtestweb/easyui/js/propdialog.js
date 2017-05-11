/**
 * Created by xq on 2017/1/4.
 */
$(function () {
    var proptitle = [[{
        field: 'name'
        , title: '属性名'
        , width: 220
        //,sortable: true
    },{
        field: 'value'
        , title: '属性值'
        , width: 220
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
