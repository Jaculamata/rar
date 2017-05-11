<?php
/**
 * Created by PhpStorm.
 * User: xq
 * Date: 2016/12/29
 * Time: 11:56
 */
require_once '../../db/config.php';
$id=$_REQUEST['id'];
$text=$_REQUEST['text'];
$res = array();
$rs1= mysql_query("select count(*) from table_menu where fj= '$id' ");
//var_dump($rs1);
$newid = intval(mysql_fetch_array($rs1)[0])+1;
$rs2= mysql_query("insert into table_menu(fj,id,text,state,attr,url) VALUES ('$id','$newid\.','$text','open','1','../../test_1_login/js.html')");
//var_dump($rs2);

if(($rs2)){
//    echo 1;
    $res['msg']=urlencode('添加成功');
}else{
//    echo 0;
    $res['msg']=urlencode('添加失败');
}
echo urldecode(json_encode($res));