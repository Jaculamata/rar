package com.xq.controller;

//import com.google.gson.Gson;

import com.xq.model.Xml;
import com.xq.service.XmlService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.apache.log4j.Logger;

@Controller
//@RequestMapping("/test")
public class XmlController {

    //Log4j -日志的配置和路径问题

    //    private Logger log = Logger.getLogger(XmlController.class);
    @Resource
    private XmlService xmlService;

    //  获取xml内容
    @RequestMapping(value = "/selectXml.do")//, produces = "application/json; charset=utf-8"
    @ResponseBody
    public String selectXml(String title, HttpServletRequest request) {


        Xml xml = new Xml();
//        xml.setContent(content);
        int projectid=Integer.parseInt(request.getSession().getAttribute("projectid").toString());
        xml.setTitle(title);
        xml.setProjectid(projectid);
        System.out.println("title:"+title + ":projectid:" + projectid);
        Xml res = xmlService.selectXml(xml);
        /*if(res.contains("success"))
            return "1";
        return "0";*/
        System.out.println(res.getContent());
        if (null == res) return "";
        return res.getContent();
    }

    //      保存测试脚本内容，如果有记录则更新，没有则插入记录
    @RequestMapping(value = "/uploadXml.do", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String uploadXml(String title, String content, String type, HttpServletRequest request) {
        //System.out.println("content===="+content);

        //content=content.replaceAll("\\n","\\r\\n");
        /*content=content.replaceAll("TEMPSTR","TEMPVAL");
        content=content.replaceAll("SQLSTR1","SQLVAL1");
        content=content.replaceAll("LOOP","FOR");
        content=content.replaceAll("TIMES","FORTIMES");
        content=content.replaceAll("UID","USERID");
        content=content.replaceAll("PWD","PASSWD");
        content=content.replaceAll("FMES","ERRPRINT");
        content=content.replaceAll("SMES","NORPRINT");
        content=content.replaceAll("BREAK","BREAKCASE");
        content=content.replaceAll("CASEEXPRESULT","EXPRESULT");*/
//        判断全自动误存为半自动和半自动误存为全自动的情况
        String str=content.toUpperCase();
        if(str.contains("<CTRLMSG>")&&type.equals("全自动")){
            System.out.println("半自动误存为全自动");
            return "2";     //返回2表示半自动误存为全自动
        }
        if(!str.contains("<CTRLMSG>")&&type.equals("半自动")){
            System.out.println("全自动误存为半自动");
            return "3";     //返回3表示全自动误存为半自动
        }
        Xml xml = new Xml();
        String encode = "";
        try {
            //content = content.replace("+","%2B"); //4.24lu，将所有+号替换
            encode = URLEncoder.encode(content, "utf-8");

            //System.out.println("dencode2===="+URLDecoder.decode(encode,"UTF-8"));
            //encode = encode.replace("%2B","+"); //4.24lu，将所有+号替换

            System.out.println(encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int projectid=Integer.parseInt(request.getSession().getAttribute("projectid").toString());
        xml.setContent(encode);//content //"中文"
        xml.setTitle(title);
        xml.setProjectid(projectid);
        xml.setType(type);

        System.out.println("title:"+title + ":type:" + type + ":projectid:"+projectid);

//        log.info("上传一个xml信息");
        String res = xmlService.UploadXml(xml);
        if (res.contains("success"))
            return "1";
        return "0";
    }

    //      保存标准规范和描述内容，如果有记录则更新，没有则插入记录 ---3.28
    @RequestMapping(value = "/uploadStandard.do", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String uploadStandard(String title, String content, String description, HttpServletRequest request) {
        Xml xml = new Xml();
        String content_encode = "";
        String description_encode = "";
        try {
            content_encode = URLEncoder.encode(content, "utf-8");
            description_encode = URLEncoder.encode(description, "utf-8");
            System.out.println("content_encode = " + content_encode);
            System.out.println("description_encode = " + description_encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int projectid=Integer.parseInt(request.getSession().getAttribute("projectid").toString());
        xml.setContent(content_encode);//content //"中文"
        xml.setDescription(description_encode);
        xml.setTitle(title);
        xml.setProjectid(projectid);

        System.out.println(title + ":" + projectid + "\n" + content + "\n" + description);

//        log.info("上传一个标准规范信息");
        String res = xmlService.UploadStandard(xml);
        if (res.contains("success"))
            return "1";
        return "0";
    }

    //获取标准规范内容--3.28
    @RequestMapping(value = "/getStandard.do", produces = "application/json; charset=utf-8")//
    @ResponseBody
    public Map getStandard(String title, HttpServletRequest request) {
        System.out.println("title是" + title);

        int projectid=Integer.parseInt(request.getSession().getAttribute("projectid").toString());
        Xml xml =new Xml();
        xml.setProjectid(projectid);
        xml.setTitle(title);
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("title", title);
//        map.put("projectid", projectid);
        List<Xml> xmlList = xmlService.getAllXml(xml);
        if (xmlList.get(0).getContent() ==null ) {
            //return null;从模板项目中取出xml得内容
            System.out.println("从模板项目中获取xml标准内容");
            projectid=0;   //以后有多个模板项目的时候这个要改
            xml.setTitle(title);
            xml.setProjectid(projectid);
            xmlList=xmlService.getXmlFromModel(xml);
        }
        String content = "";
        String description = "";
        try {
            content = URLDecoder.decode(xmlList.get(0).getContent(), "utf-8");
            description = URLDecoder.decode(xmlList.get(0).getDescription(), "utf-8");
            System.out.println(content);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("content", content);
        map1.put("description", description);
        return map1;
    }

    //    清空该测试脚本文件
    @RequestMapping(value = "/deleteXml.do", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String deleteXml(String title, HttpServletRequest request) {
        int projectid=Integer.parseInt(request.getSession().getAttribute("projectid").toString());
        Xml xml = new Xml();
        xml.setTitle(title);
        xml.setProjectid(projectid);
        System.out.println(title);
        String res = xmlService.deleteXml(xml);
        if (res.contains("success"))
            return "1";
        return "0";
    }

    @RequestMapping(value = "/showXml2.do")//, produces = "text/plain;charset=utf-8"
    @ResponseBody
    public List<Xml> showUser2(String title, HttpServletRequest request) {
        int projectid=Integer.parseInt(request.getSession().getAttribute("projectid").toString());
        Xml xml = new Xml();
        xml.setTitle(title);
        xml.setProjectid(projectid);
        /*Map<String, String> map = new HashMap<String, String>();
        map.put("title", title);
        map.put("projectid", projectid);*/
        List<Xml> xmlList = xmlService.getAllXml(xml);

        return xmlList;
    }


    @RequestMapping(value = "/showXml.do", produces = "application/json;charset=utf-8")//text/plain;
    @ResponseBody
    public Map showUser(String title, HttpServletRequest request) {
        int projectid=Integer.parseInt(request.getSession().getAttribute("projectid").toString());
        System.out.println(title + ":" + projectid);
//        log.info("查询所有xml信息");
        Xml xml = new Xml();
        xml.setTitle(title);
        xml.setProjectid(projectid);
       /* Map<String, String> map = new HashMap<String, String>();
        map.put("title", title);
        map.put("projectid", projectid);*/
        List<Xml> xmlList = xmlService.getAllXml(xml);
        if (xmlList.get(0).getContent() ==null ) {
            //return null;从模板项目中取出xml得内容
            System.out.println("从模板项目中获取xml内容");
            projectid=0;   //以后有多个模板项目的时候这个要改
            xml.setTitle(title);
            xml.setProjectid(projectid);
            xmlList=xmlService.getXmlFromModel(xml);
        }
        String content = "";
        try {
            content = URLDecoder.decode(xmlList.get(0).getContent(), "utf-8");
            //content = content.replace("%2B","+"); //4.24lu，将所有+号替换
            System.out.println(content);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("content", content);
        map1.put("type", xmlList.get(0).getType());
        return map1;
    }
}
