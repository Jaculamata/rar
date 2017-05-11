package com.xq.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xq on 2017/3/22.
 */
public class CtrlMsg {
    String type;                               //交互类型，交互窗口显示的标题
    List<String> allmsg;                       //文本框显示的文本，用户的选择通过最后一个字符串回传


    public List<String> getAllch() {
        return allch;
    }

    public void setAllch(List<String> allch) {
        this.allch = allch;
    }

    List<String> allch;         //按钮显示的文本

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }





    public List<String> getAllMsg() {
        return allmsg;
    }

    public void setAllMsg(List<String> msg) {
        this.allmsg = msg;
    }




}
