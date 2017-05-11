package com.xq.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by LIU on 2017/4/28.
 */
public class Response {
    public ConcurrentHashMap<String,Vector<String>> resmes=new ConcurrentHashMap();
    public Response(String id,Vector<String> mes){

       this.resmes.put(id,mes);

    }
}
