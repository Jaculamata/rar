<?php
/**
 * Created by PhpStorm.
 * User: xq
 * Date: 2016/12/29
 * Time: 9:28
 */
$res = array();

//节点一
$prod = array(
    'id' => 1,
    'text' => urlencode('商品管理'),
    'state' => 'open',
    'children' => [
        [
            "id" => 2,
            "text" => urlencode("添加商品"),
            "state" => "open",
            "attributes" => [
                "url" => "http://love.hustxq.cn"
            ]
        ],
        [
            "id" => 3,
            "text" => urlencode("修改商品"),
            "state" => "open",
            "attributes" => [
                "url" => "http://love.hustxq.cn"
            ]
        ],
    ]
);

//节点二
$newsArr = [
    "id" => 10,
    "text" => urlencode("新闻管理"),
    "state" => "open",
    "children" => [
        [
            "id" => 12,
            "text" => urlencode("添加新闻"),
            "state" => "open"
        ],
        [
            "id" => 13,
            "text" => urlencode("修改新闻"),
            "state" => "open"
        ]
    ]
];
array_push($res,$prod,$newsArr);
echo urldecode(json_encode($res));