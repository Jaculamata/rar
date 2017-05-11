<?php
/**
 * Created by PhpStorm.
 * User: xq
 * Date: 2017/3/2
 * Time: 14:31
 */
require_once "../../db/config.php";
$id=$_REQUEST['id'];
$targetId=$_REQUEST['targetId'];
$point=$_REQUEST['point'];

$arr=explode(".",$id);
$fj='';
$i=0;
for(; $i<count($arr)-2; $i++){
    $fj=$fj.$arr[$i].".";
}
$did=$arr[$i];

$arr=explode(".",$targetId);
$pre='';
$i=0;
for(;$i<count($arr)-2;$i++){
    $pre = $pre.$arr[$i].'.';
}
$next=$arr[$i];
$level=count($arr)-1;
$tlevel=count($arr)-2;

$dbres = mysql_query("select * from table_menu where fj='$fj' AND id=$did");
$text='';
($row = mysql_fetch_array($dbres));
$text = $row['text'];
//var_dump($text);
$nid;
$dbres=mysql_query("delete from table_menu WHERE fj='$fj' AND id=$did");
if($point=='append'){
//    类似于新增
    mysql_query("update table_menu set state='closed' where fj='$pre' AND id=$next");
    $dbres=mysql_query("select max(id), level from table_menu where fj='$targetId'");
    $row = mysql_fetch_array($dbres,MYSQL_ASSOC) or die('sql语句执行失败，错误信息是：' . mysql_error());;
    $id=intval($row['max(id)'])+1;
    $nid = $id;
    $dbres = mysql_query("insert into table_menu (id, fj,level,text, state) VALUES ($id, '$targetId',$level,'$text','open')")
    or die('sql语句执行失败，错误信息是：' . mysql_error());
}else{

//    1.删除被移动的节点

//    2.添加进新位置
    if($point == 'top'){
        $nid=intval($next)-1;
        $dbres=mysql_query("update table_menu set id = id-1 where fj='$pre' and id<$next ORDER BY id")or die('sql语句执行失败，错误信息2是：' . mysql_error());
//        $dbres=mysql_query("update table_menu set id = id+1 where fj='$pre' and id>$next ORDER BY id DESC ")or die('sql语句执行失败，错误信息2是：' . mysql_error());
        $dbres=mysql_query("insert into table_menu (id, fj,level,text, state) VALUES ($nid,'$pre',$tlevel,'$text','open')")or die('sql语句执行失败，错误信息3是：' . mysql_error());
        $nid = $pre.$nid;
    }else{
        $nid = intval($next);
//        $dbres=mysql_query("update table_menu set id = id+1 where fj='$pre' and id>$next ORDER BY id DESC")or die('sql语句执行失败，错误信息4是：' . mysql_error());
        $dbres=mysql_query("update table_menu set id = id-1 where fj='$pre' and id<=$next ORDER BY id")or die('sql语句执行失败，错误信息4是：' . mysql_error());
        $dbres=mysql_query("insert into table_menu (id, fj,level,text, state) VALUES ($nid,'$pre',$tlevel,'$text','open')")or die('sql语句执行失败，错误信息5是：' . mysql_error());
        $nid = $pre.$nid;
    }
}


$json=array(
  /*"id" => $nid.'.',
    "targetId" => $targetId,
    "point" => $point*/
    "success" => true
);
echo json_encode($json);