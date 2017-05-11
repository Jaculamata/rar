<?php
/**
 * Created by PhpStorm.
 * User: xq
 * Date: 2017/3/2
 * Time: 10:04
 */
require_once "../../db/config.php";
$str = $_REQUEST['id'];
$arr=explode(".",$str);
$fj='';
$i=0;
for(; $i<count($arr)-2; $i++){
    $fj=$fj.$arr[$i].".";
}
$id=$arr[$i];//.'.';
$text=$_REQUEST['text'];
$dbres = mysql_query("update table_menu set text='$text' where fj='$fj' AND id='$id'");

if($dbres){
    $json = array("id" => $str,
        "text" => urlencode($text)
    ); //.' with '.$id
    echo urldecode(json_encode($json));
}else{
    echo "fail";
}
