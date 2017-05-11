package com.xq.controller;

import com.xq.model.Xml;
import com.xq.service.XmlService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.Date;

/**
 * Created by lu on 2017/3/24.
 */
@Controller
public class ExportFileController {

    @Resource
    private XmlService xmlService;

    @RequestMapping(value = "/export1.do",produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String download(HttpServletRequest request, HttpServletResponse response) {
        String title = request.getParameter("title");
        String type = request.getParameter("type");
        //String title="0.3.";
        //先从数据库中读出content
        int projectid=Integer.parseInt(request.getSession().getAttribute("projectid").toString());
        Xml xml=new Xml();
        xml.setTitle(title);
        xml.setProjectid(projectid);
        System.out.println("title是"+title);
        Xml res = xmlService.selectXml(xml);
        String content = "";
        if (res == null)  //如果content为空
        {
            content ="xml内容为空";

        }else {
            //content = res.getContent();
            try {
                content = URLDecoder.decode(res.getContent(),"utf-8");
                System.out.println(content);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        System.out.println("content是"+content);
        String fileName =new Date().getTime()+ type;
        String str="/"; //文件夹地址，通常是根据他获取到文件全路径
        String path1 = request.getSession().getServletContext().getRealPath(str);//获取到tomcat下该文件的全路径
        System.out.println("绝对路径是"+path1);
        path1=path1.replaceAll("\\\\", "/");
        if(!path1.endsWith("/")){
            path1=path1+"/";
        }
        path1=path1+ fileName;
        File localFile = new File(path1);

        if(!localFile.exists()){
            try {
                localFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("创建文件失败");
            }
        }
        //写文件到本地
        FileOutputStream o=null;
        try {
            o = new FileOutputStream(localFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            o.write(content.getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            o.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName="
                +fileName);

        //获取数据
        //########################下载文件begin########################
        try {
            InputStream is = new FileInputStream(new File(path1));
            OutputStream os = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = is.read(b)) > 0) {
                os.write(b, 0, length);
            }

            // 这里主要关闭文件流。
            os.close();
            is.close();
            //最后删除文件
            if (localFile.exists()){
                localFile.delete();
            }
        } catch (FileNotFoundException e) {
            System.out.println("没有找到文件");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //########################下载文件end########################
        //  返回值要注意，要不然就出现下面这句错误！
        //java+getOutputStream() has already been called for this response
        return null;
    }


}
