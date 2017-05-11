<?php
/**
 * Created by PhpStorm.
 * User: xq
 * Date: 2016/12/27
 * Time: 17:01
 */
$id=$_REQUEST['id'];
$res = array();
$obj0 = array(
    'id'=>0,
    'text'=>urlencode("请选择")
);
array_push($res,$obj0);
if($id==1){

    $obj1 = array(
        'id'=>1,
        'text'=>urlencode('武汉市')
    );
    $obj2 = array(
        'id'=>2,
        'text'=>urlencode('荆州市')
    );
    array_push($res,$obj1);
    array_push($res,$obj2);

}else{
    $obj1 = array(
        'id'=>1,
        'text'=>urlencode('长沙市')
    );
    $obj2 = array(
        'id'=>2,
        'text'=>urlencode('岳阳市')
    );
    array_push($res,$obj1);
    array_push($res,$obj2);
}

echo urldecode(json_encode($res));