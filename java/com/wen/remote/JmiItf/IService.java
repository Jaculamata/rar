package com.wen.remote.JmiItf;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by wen on 2017/3/16.
 */
public interface IService extends Remote {
    //声明服务器端必须提供的服务
    Object Remoteexe(String fun_name, String[] args) throws RemoteException;
}
