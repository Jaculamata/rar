/**
 * Created by xq on 2016/12/27.
 */
$(function(){
   $('#p2').progressbar({
       value: 0,
   }) ;

    var value = $('#p2').progressbar('getValue');
    var timer = setInterval(function(){
        value+=Math.floor(Math.random()*10);
        if(value>=100){
            value=100;
            $('#p2').progressbar('setValue',value);

        }else{
            $('#p2').progressbar('setValue',value);
        }
    },1000);

});