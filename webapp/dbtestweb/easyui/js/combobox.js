/**
 * Created by xq on 2016/12/27.
 */
$(function(){
   $('#combobox2').combobox({
       url: 'combobox_data.json',
       valueField: 'id',
       textField: 'text'
   });

    /*创建级联下拉框*/
    $('#cc1').combobox({
        valueField: 'id',
        textField: 'text',
        url: 'get_data1.php',
        onSelect: function(rec){
            var url = 'get_data2.php?id='+rec.id;
            $('#cc2').combobox('enable');
            $('#cc2').combobox('reload',url);

        }
    });

    $('#cc2').combobox({
        valueField: 'id',
        textField: 'text',
        text: 'please select items',
        disabled: true,
        onSelect: function(item){
          var sh = $('#cc1').combobox('getText');
            console.log(sh,item.text);
        },
    });
});