package com.xq.thread;


import com.wen.XmlProcess;
import com.xq.model.CtrlMsg;
import com.xq.model.Xml;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xq on 2017/2/17.
 */
public class Worker {
    private CallBack callBack;
    private String num;
    private XmlProcess  processThread;
    AtomicInteger i = new AtomicInteger(1);
    public Worker(CallBack callBack, String num, Vector<Xml> xmlset) {
        this.callBack = callBack;
      //  CtrlMsg msg = new CtrlMsg();
     //   msg.setType("compareFile");
      //  msg.setMsg1("msg1");
      //  msg.setMsg2("msg2");
     //   callBack.callbackCtrl(num,msg);
        this.num = num;
        this.processThread = new XmlProcess(num,this.callBack);
        int i=0;
        do{
            push(xmlset.get(i).getTitle(),xmlset.get(i).getContent());
            i++;

        }while(i<xmlset.size());

    }

    public synchronized void work()
    {
        processThread.run_xml();
    }
    public void push(String jid,String str)
    {
        try {
            String content=URLDecoder.decode(str, "UTF-8");
            System.out.println("传给引擎的内容是：\n"+content);
            //content = content.replace("%2B","+"); //4.24lu，将所有+号替换
            processThread.push_xml(jid,content);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public void start()
    {

    }



    public void stop()
    {
        processThread.pause_xml();
    }
    public synchronized void restart()
    {
        processThread.restart_xml();
    }
    public void return_ctrlmsg(CtrlMsg msg)
    {
        System.out.println("Ctrl msg"+msg);
        processThread.push_CtrlMsg(msg);
    }
}