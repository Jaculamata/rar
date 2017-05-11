package com.xq.controller;

import com.wen.MyUtil;
import com.xq.dao.ReportDao;
import com.xq.dao.XmlDao;
import com.xq.model.*;
import com.xq.service.ReportService;
import com.xq.service.XmlService;
import com.xq.thread.CallBack;
import com.xq.thread.Worker;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by xq on 2017/3/9.
 */

@Controller
public class TestController{
    ConcurrentMap<String,Response> map = new ConcurrentHashMap();// user 和回复信息的map
    ConcurrentMap<String,Vector<CtrlMsg>> ctrlmap = new ConcurrentHashMap(); //
    ConcurrentMap<String,Worker> objmap = new ConcurrentHashMap(); //
    ConcurrentMap<String,String> zlmap = new ConcurrentHashMap();//

//    ConcurrentMap<String,String> idmap=new ConcurrentHashMap();//所有脚本的id 和对应的projectid的map
    @Resource
    ReportService reportService;
    @Resource
    XmlService xmlService;
    @RequestMapping(value = "/getzw",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getzw(final String param, final String zl){
        return "我是中文";
    }

    @RequestMapping(value = "/get",produces = "application/json; charset=utf-8")
    @ResponseBody
    //4.14取消参数param的传递，使用user取代原param(lw)
    public String get(/*final String param,*/ String id,String zl, HttpServletRequest request){
        //从session中获取当前登录用户
        final String user = request.getSession().getAttribute("loginUser").toString();
        String projectid =request.getSession().getAttribute("projectid").toString();
//        idmap.put(projectid,id);
//        System.out.println("idmap"+idmap.toString());

//        System.out.println(username+":"+zl);
        //1.  0.1#0.2 string split  ==> id set
        // according to id ,to get the record;
        //push xml
        String[] idset=id.split("#");
//        System.out.println("收到的idset"+idset[0]+"&&"+idset[1]);
        System.out.println("test");
        final Vector<Xml> xmlset=new Vector<Xml>();
        // first commit
        for(String i :idset) {

            Xml xml = new Xml();
            xml.setProjectid(Integer.parseInt(projectid));
            xml.setTitle(i);
            final String t = i;
            final Xml temp = xmlService.selectXml(xml);

//            System.out.println("从数据库中查到的 " + zl);
            xmlset.add(temp);
        }

            if (!map.containsKey(user)&&!objmap.containsKey(user)){
                Thread thread = new Thread(new Runnable() {
                    CallBack callBack = new CallBackImpl();
                    public void run() {
//                        System.out.println("进入worker的zl："+zl);
                        Worker worker = new Worker(callBack, user,xmlset);

                        objmap.put(user,worker);
                        System.out.println("objmap put user"+objmap);
                        zlmap.put(user,"start");
                        worker .work();
                    }
                });
                thread.setName(user);
                thread.start();
            }else {
                if (zlmap.containsKey(user)) {
                    String prezl=zlmap.get(user);
                    // test controller
                    if (prezl.equals("start")&&zl.equals("end")) {
                        System.out.println("end");
                        zlmap.put(user,"end");
                        if (objmap.containsKey(user)) {
                            Worker worker = objmap.get(user);
                            worker.stop();
                            return "1";
                        } else {
                            System.out.println("end no obj");
                            return "0";
                        }
                    } else if (prezl.equals("end")&&zl.equals("start")) {
                        zlmap.put(user,"start");
                        System.out.println("start");
                        if (objmap.containsKey(user)) {
                            Worker worker = objmap.get(user);
                            //                    worker.stop();
                            worker.start();
                            worker.restart();
                        } else {
                            System.out.println("start no obj");
                        }
                    }else { // run xml
//                        System.out.println("run xml push id "+i+"and xml "+zl);
                        for(Xml i:xmlset){
                            objmap.get(user).push(i.getTitle(),i.getContent());
                        }

                    }
                }
            }
//        反馈信息给用户 从消息队列中取值
		/*Vector<String> v = map.get(param);
		if (v==null||v.size()==0)
			return null;
		else {
			return v.remove(0).toString();
		}*/

        System.out.println(map.toString());
        return "success";
    }
    @RequestMapping(value = "/getres",produces = "application/json; charset=utf-8")
    @ResponseBody
    //4.14取消参数param的传递，使用user取代原param(lw)
    public String getres(/*final String param,*/String zl, HttpServletRequest request){
        //   System.out.println(param+":"+ zl);
		/*if (!map.containsKey(param)){
			Thread thread = new Thread(new Runnable() {
				CallBack callBack = new CallBackImpl();
				public void run() {
					Worker worker = new Worker(callBack, param, zl);
					objmap.put(param,worker);
					zlmap.put(param, "start");
					worker .work();
				}
			});
			thread.setName(param);
			thread.start();
		}else {
			if (zlmap.containsKey(param)) {
				String prezl=zlmap.get(param);
				if (prezl.equals("start")&&zl.equals("end")) {
					System.out.println("end");
					zlmap.put(param,"end");
					if (objmap.containsKey(param)) {
						Worker worker = objmap.get(param);
						worker.stop();
						return "1";
					} else {
						System.out.println("end no obj");
						return "0";
					}
				} else if (prezl.equals("end")&&zl.equals("start")) {
					zlmap.put(param,"start");
					System.out.println("start");
					if (objmap.containsKey(param)) {
						Worker worker = objmap.get(param);
//                    worker.stop();
						worker.start();
						worker.restart();
					} else {
						System.out.println("start no obj");
					}
				}else {

				}
			}
		}*/
        //从session中获取当前登录用户
//        System.out.println("test");
        final String user = request.getSession().getAttribute("loginUser").toString();
        int projectid=Integer.parseInt(request.getSession().getAttribute("projectid").toString());
//        final String allid=idmap.get(request.getSession().getAttribute("projectid").toString());
//        String [] idset=allid.split("#");
//        System.out.println("getres :"+idset[0]+idset[1]);

//        System.out.println(map.get(user).resmes);
//        反馈信息给用户 从消息队列中取值
            Response v = map.get(user);
//            System.out.println("test");
if(v.resmes.keySet()!=null){
	for (String key:v.resmes.keySet()){
    if (v.resmes.get(key).size()==0||v.resmes==null ||v.resmes.get(key)==null)
{
//    System.out.println("nulltest");
    continue;
}
else {
    String res = v.resmes.get(key).remove(0).toString();
    //store in databases.  先做插入清空之前的记录  然后在做更新
    Report report=new Report();
    System.out.println("v de id"+key);
    report.setXml_title(key);
//        Blob blob=MyB.createBlob"test";
        String res1="";
        try {
            res1=URLEncoder.encode(res,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        report.setDetail(res1);
    report.setProjectid(projectid);
    report.setResult("fail");
    System.out.println("begin select");
    int   r=reportService.selectResult(report);
    System.out.println("res: "+r);
    System.out.println("slect");
    if(r==0){

        reportService.saveReport(report);
        reportService.updateResult(report);
        System.out.println("save");
    }
    else{
        System.out.println("get detail"+report.getDetail());
        reportService.updateResult(report);
        System.out.println("update");
    }
    System.out.println("replace");
    //return to the web.
    return MyUtil.getJsonFromObject(res);

}
            }
}


return null;
    }

    //
    @RequestMapping(value = "/getctrlretuenres",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getctrlretuenres(@RequestBody CtrlMsg msg){
        String param = msg.getType().split("#")[1];
        System.out.println("test\n"+(msg)+"\n"+param);
        objmap.get(param).return_ctrlmsg(msg);
        return "1";
    }
    @RequestMapping(value = "/getctrlretuenresnull",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getctrlretuenresnull(@RequestBody String param){
//        String param = msg.getType().split("#")[1];
//        System.out.println("test\n"+(msg)+"\n"+param);
        objmap.get(param).return_ctrlmsg(null);
        return "1";
    }

    @RequestMapping(value = "/getctrlres",produces = "application/json; charset=utf-8")
    @ResponseBody
    //4.14取消参数param的传递，使用user取代原param(lw)
    public String getCtrlRes(/*final String param,*/ final String zl, String msg1, String msg2, HttpServletRequest request){
        //从session中获取当前登录用户
        final String user = request.getSession().getAttribute("loginUser").toString();
//        CtrlMsg msg = null;
//        获取测试引擎的中间结果
        if (zl.equals("getres")) {
            Vector<CtrlMsg> v = ctrlmap.get(user);
            if (v == null || v.size() == 0)
                return null;
            else {
                CtrlMsg res = v.remove(0);//.toString();
                System.out.println(res.toString());
                System.out.println(MyUtil.getJsonFromObject(res));
                return MyUtil.getJsonFromObject(res);
            }
        }else {
//          把处理结果返回给测试引擎
            CtrlMsg remsg = new CtrlMsg();
            remsg.setType("COMPARE_FILE");
            List<String> allmsg = new ArrayList();
            allmsg.add(msg1);
            allmsg.add(msg2);
            remsg.setAllMsg(allmsg);
            objmap.get(user).return_ctrlmsg(remsg);
            System.out.println("@@@@@@@@@@@@@@");
            return "{\"msg\":\"success\"}";
        }
//        return msg;
    }

    class CallBackImpl implements CallBack{
        //        返回半自动化过程中的结果
        public void callbackCtrl(String num, CtrlMsg msg){
            if (ctrlmap.containsKey(num)) {
                Vector<CtrlMsg> list = ctrlmap.get(num);
                list.add(msg);
                ctrlmap.put(num,list);
            }else {
                Vector<CtrlMsg> list = new Vector<CtrlMsg>();
                list.add(msg);
                ctrlmap.put(num,list);
            }
        }


        //        返回普通类型的结果
        public void callback(String user,String id,String msg) {
            if (map.containsKey(user)) {

                Response res = map.get(user);
                if(!map.get(user).resmes.containsKey(id)){
                    Vector<String> list = new Vector<>();
                    res.resmes.put(id,list);
                }
                System.out.println("callback in Test1 "+res+" msg "+msg+" id "+id);
                res.resmes.get(id).add(msg);
                System.out.println("callback in Test2"+res);
                map.put(user,res);
            }else {
                Vector<String> list = new Vector<>();
                list.add(msg);
                Response res=new Response(id,list);
                map.put(user,res);

            }
        }
    }
}

