<?php
/**
 * Created by PhpStorm.
 * User: xq
 * Date: 2017/3/2
 * Time: 10:14
 */
require_once "../../db/config.php";
/*
{
    "id": "1.",
    "text": "Node 1",
    "state": "closed",
    "children": [{
        "id": 11,
        "text": "Node 11"
    },{
        "id": 12,
        "text": "Node 12",
        "url":"http://baidu.com"
    }]
},
*/
$json = '[{
    "id": "0.",
    "text": "安全等级标准框架",
    "state": "closed",
    "info":"hustxq"
}]';
$id = isset($_REQUEST['id'])?$_REQUEST['id']:-1;
if ($id == -1) {
    echo $json;
} else{// if ($id == '0.')
//    返回第一级菜单
    $json1 = array();
    $res = mysql_query("select * from table_menu where fj='$id'");
    while($row = mysql_fetch_array($res)){
//        var_dump($row);
        $tmp=array();
        $tmp['id']=$id.$row['id'].'.';
        $tmp['text']=urlencode($row['text']);
        $tmp['state']=$row['state'];

        array_push($json1,($tmp));
    }
    echo urldecode(json_encode($json1));
    /*$json = '[{
    "id": 21,
    "text": "Node 21",
    "state": "closed"
    }]';*/
//    echo $json;
}
