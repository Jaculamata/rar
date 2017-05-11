<?php
/**
 * Created by PhpStorm.
 * User: xq
 * Date: 2017/3/2
 * Time: 9:39
 */
require_once "../../db/config.php";

$parentId = $_REQUEST['parentId'];
$dbres=mysql_query("select max(id), level from table_menu where fj='$parentId'");
$row = mysql_fetch_array($dbres,MYSQL_ASSOC) or die('sql语句执行失败，错误信息是：' . mysql_error());;
//var_dump($row);
$id=intval($row['max(id)'])+1;
//$id=$id.".";
$level=count(explode(".",$parentId))-1;
//var_dump($id,$parentId,$level);
$arr=explode(".",$parentId);
$fj='';
$i=0;
for(; $i<count($arr)-2; $i++){
    $fj=$fj.$arr[$i].".";
}
$pid=$arr[$i];
mysql_query("update table_menu set state='closed' where fj='$fj' AND id=$pid");
$dbres = mysql_query("insert into table_menu (id, fj,level,text, state) VALUES ('$id', '$parentId',$level,'新节点','open')")
or die('sql语句执行失败，错误信息是：' . mysql_error());
if($dbres){
    $json=array(
        "id" => $parentId.$id.'.',
        "text" => urlencode("新节点")
    );
    echo urldecode( json_encode($json));
}else{
    echo "fail";
}



