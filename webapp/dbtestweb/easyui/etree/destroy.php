<?php
/**
 * Created by PhpStorm.
 * User: xq
 * Date: 2017/3/2
 * Time: 12:01
 */
require_once "../../db/config.php";
$str = $_REQUEST['id'];
$arr=explode(".",$str);
$fj='';
$id;
$i=0;
for(; $i<count($arr)-2; $i++){
    $fj=$fj.$arr[$i].".";
}
$id=$arr[$i];//.'.';
//echo $fj.':'.$id;
// 1.先删除自己 再删除其下所有子节点
$dbres=mysql_query("delete from table_menu WHERE fj='$fj' AND id='$id'");
$dbres=mysql_query("delete from table_menu WHERE fj LIKE '$str%'");
$dbres=mysql_query("select count(*) from table_menu where fj='$fj'");
$row = mysql_fetch_array($dbres);
var_dump($row);
if(intval($row[0])==0){
    $arr=explode(".",$fj);
    $fj='';
    $id;
    $i=0;
    for(; $i<count($arr)-2; $i++){
        $fj=$fj.$arr[$i].".";
    }
    $pid=$arr[$i];
    mysql_query("update table_menu set state='open' where fj='$fj' AND id=$pid");
}
$json=array();
if($dbres){
    $json['success']=true;
}else{
    $json['success']=false;
}
echo json_encode($json);