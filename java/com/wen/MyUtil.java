package com.wen;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.rmi.RemoteException;

import com.wen.remote.JmiItf.IService;
import org.apache.ibatis.ognl.ObjectElementsAccessor;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
/**
 * Created by wen on 2017/3/13.
 */
public class MyUtil {
    public static  String getUserChoose(String outStr)
    {
        Object[] options = { "SUCCESS", "FAIL" };
        int ret = JOptionPane.showOptionDialog(null, outStr, "提示",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        return options[ret].toString();
    }
    public  static String getJsonFromObject(Object obj){
        ObjectMapper om = new ObjectMapper();
        try
        {
            return om.writeValueAsString(obj);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
    public  static Object  getObjectByJsonString(String jsonStr, String Classname){
        Object ob = null;
        ObjectMapper om = new ObjectMapper();
        try {
            JsonNode node=om.readTree(jsonStr);
            try {
                ob = om.readValue(node.toString(),Class.forName(Classname));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return ob;
    }
    public static Object RunJarFun(String filepath, String classname, String funname, String[] inargs)throws Exception
    {
        File file = new File(filepath);//jar包的路径
        URL url = file.toURI().toURL();
        ClassLoader loader = new URLClassLoader(new URL[]{url});             //创建类加载器
        Class<?> cls = loader.loadClass(classname);               //加载指定类，注意一定要带上类的包名
        Method method = cls.getMethod(funname,String[].class);        //方法名和对应的各个参数的类型
        Object o = method.invoke(cls.newInstance(),(Object)inargs);              //调用得到的上边的方法method(第一个参数类实例)
        return o;
    }

}
