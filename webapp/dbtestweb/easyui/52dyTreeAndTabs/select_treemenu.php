<?php
/**
 * Created by PhpStorm.
 * User: xq
 * Date: 2016/12/29
 * Time: 10:48
 */
require_once '../../db/config.php';
/*获取一级节点数*/
$rs= mysql_query("select count(*) from table_menu where fj= '0.' ");
$row1 = mysql_fetch_array($rs);
//var_dump($row);  //2
$m1 = $row1['count(*)'];
$res = array();
$r1 =array();
for($i = 1;$i<=$m1;$i++){
    $r2=array();
    $rs= mysql_query("select count(*) from table_menu where fj= '0.$i.' ");
    $row2 = mysql_fetch_array($rs);
    //var_dump($row);
    $m2 = $row2['count(*)'];

    for($j=1;$j<=$m2;$j++){
        $rs= mysql_query("select * from table_menu where fj= '0.$i.' and id= '$j.' ");
        $row3 = mysql_fetch_array($rs);
//        $m3 = $row[0];
//        var_dump($row3);
        $r3= array("id" => $row3["fj"].$row3['id'],
            "text" => urlencode($row3['text']),
            "state" => $row3['state'],
        );
        if(!empty($row3["attr"])){
            $r3["attributes"] = [
                "url" => $row3['url']
            ];
//            var_dump(1);
        }
//        var_dump($r3);
        array_push($r2,$r3);
    }

    $rs= mysql_query("select * from table_menu where fj= '0.' and id= '$i.' ");
    $row2 = mysql_fetch_array($rs);
    //var_dump($r2);
    //break;
    $r1= array("id" => $row2['fj'].$row2['id'],
        "text" => urlencode($row2['text']),
        "state" => $row2['state'],
        "children" => $r2
    );


    array_push($res,$r1);
}
echo urldecode(json_encode($res));