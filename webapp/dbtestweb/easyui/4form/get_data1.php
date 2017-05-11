<?php
/**
 * Created by PhpStorm.
 * User: xq
 * Date: 2016/12/27
 * Time: 16:57
 */
$res = array();
$obj0 = array(
    'id'=>0,
    'text'=>urlencode('请选择')
);
$obj1 = array(
    'id'=>1,
    'text'=>urlencode('湖北省')
);
$obj2 = array(
    'id'=>2,
    'text'=>urlencode('湖南省')
);
array_push($res,$obj0);
array_push($res,$obj1);
array_push($res,$obj2);
echo urldecode(json_encode($res)) ;