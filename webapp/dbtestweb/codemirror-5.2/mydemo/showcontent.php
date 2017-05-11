<?php
/**
 * Created by PhpStorm.
 * User: xq
 * Date: 2017/1/5
 * Time: 17:47
 */
require_once "../../db/config.php";
$msg2 = ($_REQUEST['code']);//
//var_dump($msg2);
echo $msg2.'<br>';
$msg = urlencode($msg2);
echo $msg.'<br>';
if(isset($msg)){
    $sql = "insert into table_xml(content) values('$msg')";
    $result = @mysql_query($sql)or die('数据库错误：'.mysql_error());
//    var_dump($result);
    if ($result){
        //echo json_encode(array('success'=>true));
        $sql = "select * from table_xml where xmlid=6";
        $result = @mysql_query($sql)or die('数据库错误：'.mysql_error());
        if($res = mysql_fetch_array($result)){
//            var_dump($res);
            echo urldecode($res['content']);//urldecode
            return;
        }
    } else {
        echo json_encode(array('msg'=>'Some errors occured.'));
    }
}
//echo $msg;
