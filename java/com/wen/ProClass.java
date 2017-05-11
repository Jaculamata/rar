package com.wen;

import java.util.Map;
import java.util.HashMap;

/**
 * Created by wen on 2017/2/20.
 */

/// <summary>
/// com.wen.ProClass 的摘要说明。
/// </summary>
class ProHelp
{
    String value;
    public ProHelp(String invalue)
    {
        value = invalue;
    }
    public void setvalue(String invalue)
    {
        value = invalue;
    }
    public String getvalue()
    {
        return value;
    }
}
public class ProClass {

   // 数据库连接信息

    public  String sValUserId = "SYSDBA";        //默认用户名

    public  String sValPassword = "SYSDBA";     //默认密码

    public  String sValServer = "115.156.187.8";         //默认数据库服务器IP

    public  String sValPort= "5236";          //默认数据库服务器端口

    public  String sValDriveName = "dm.jdbc.driver.DmDriver";   // JDBC驱动名称

    public  String sValConnUrl = "jdbc:dm://115.156.187.8:5236";     //默认数据库连接串

    //执行消息设置
    public  String sValIsOutMsg = "true";     //是否输出中间执行消息

    public  String sValIsErrRun = "false";    //执行错误后是否继续运行

    public  boolean bValIsShowResult = true;    //是否显示SQL结果集

    public  boolean bValIsErrRun = false;      //错误后是否继续运行

    public  boolean bValIsOutTime = false;     //是否输出每条语句执行时间

    //测试服务器配置

    public  String sValOS = "WINDOWS";           //测试服务器的操作系统版本

    //public  String sValJarPath = "C:\\main_lib";             //第三方Jar的默认路径

    public String sValRemoteUrl = "rmi://115.156.186.48:6600/remoteService";     //远程过程调用连接串

    public  String sValOSUid = "Administrator";                       //远程服务器系统用户名 （用于执行远程命令）

    public  String sValOSPwd = "123456";                     //远程服务器系统密码


    //到此截止

    public  Map<String,ProHelp>  valclass = new HashMap<String, ProHelp>();      //存储脚本的全局变量

    public  synchronized String getVal(String key)
    {
        if(valclass.containsKey(key))
            return valclass.get(key).getvalue();
        return null;
    }
    public  synchronized void setVal(String key, String value)
    {
        if(valclass.containsKey(key))
            valclass.get(key).setvalue(value);
        else
            valclass.put(key,new ProHelp(value));
    }
    public  synchronized void delVal(String key)
    {
        valclass.remove(key);
    }
}


