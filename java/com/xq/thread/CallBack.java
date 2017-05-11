package com.xq.thread;

import com.xq.model.CtrlMsg;

/**
 * Created by xq on 2017/2/17.
 */
public interface CallBack {
    public void callback(String user,String id, String msg);
    public void callbackCtrl(String num, CtrlMsg msg);
}
