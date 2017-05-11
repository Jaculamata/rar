package com.xq.util;

/**
 * Created by xq on 2017/3/16.
 */
public class Test {
    public static void main(String[] args){
        String str = "0.1.";
        String[] sarr = str.split("\\.");
        System.out.println(sarr.length);
        for (String s:sarr){
            System.out.println(s);
        }
    }
}
