/**
 * Created by Administrator on 2017/4/27.
 */
var containNotAuto=0;
function getSelected(){
    var t= $("#tg").treegrid("getSelections");
    var autocount=0;
    var halfcount=0;
    var manalcount=0;
    var idset="";
//    alert("test");
    for(var i=0;i<t.length;i++){
      // alert("test"+t[i].attr);
       if(t[i].runType=="全自动"){
           autocount++;
       }else if (t[i].runType=="半自动"){
           halfcount++;
       }else{
           manalcount++;
       }
        if(t[i].type=="file"){
            idset=idset+t[i].attr+"#";
        }else {
            
        }
    }
    if(halfcount||manalcount){
        $.messager.confirm('提示','您选择的脚本中包含半自动或全自动，可能需要工作人员配合测试，请勿长时间离开，确定是否继续？',function(r){
            if (r){
                // saveXml(xmltitle,text,type);
                $.ajax({
                    url:'../get.do',
                    type:'post',
                    data:{
                        id:idset,
                        zl:"start"
                        // projectid:projectid
                    }
                })
                alert("test: "+idset);
            }
        });
    }else {
        alert("开始测试脚本: "+idset);
        $.ajax({
            url:'../get.do',
            type:'post',
            data:{
                id:idset,
                zl:"start"
                // projectid:projectid
            }
        })

    }

//    alert(t.length);

}

