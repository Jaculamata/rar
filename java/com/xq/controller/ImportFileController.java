package com.xq.controller;

import com.xq.model.Xml;
import com.xq.service.XmlService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by lu on 2017/3/23.
 */
@Controller
public class ImportFileController {
    @Resource
    private XmlService xmlService;

    @RequestMapping(value = "/import.do",method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public  String uploadFile(HttpServletRequest request, HttpServletResponse response)
            throws IllegalStateException, IOException{
        String content="";
        //解析器解析request的上下文
        CommonsMultipartResolver multipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());
        //先判断request中是否包涵multipart类型的数据，如果js中通过普通的ajax-form传参，则该处则不会继续执行
        if(multipartResolver.isMultipart(request)){
            //再将request中的数据转化成multipart类型的数据
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            String str="/"; //文件夹地址，通常是根据他获取到文件全路径
            String path1 = request.getSession().getServletContext().getRealPath(str);//获取到tomcat下该文件的全路径
            System.out.println("绝对路径是"+path1);
            path1=path1.replaceAll("\\\\", "/");
            if(!path1.endsWith("/")){
                path1=path1+"/";
            }
            Iterator iter = multiRequest.getFileNames(); //获取文件名称
            while(iter.hasNext()){
                MultipartFile file = multiRequest.getFile((String)iter.next());
                if(file != null){
                    String fileName =new Date().getTime()+ file.getOriginalFilename();

                    path1=path1+ fileName;
                    File localFile = new File(path1);
                    //写文件到本地
                    file.transferTo(localFile);
                    //读取文件内容
                    BufferedReader br = null;

                    //构造BufferedReader对象
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(path1),"utf-8"));//(new FileReader(path1));

                    String line = null;
                    while ((line = br.readLine()) != null) {

                    //将文本打印到控制台
                        content=content+line+"\r\n";
                        System.out.println(line);
                    }
                    br.close();
                    //获取content后删除存到服务器的文件
                    if (localFile.exists()){
                        localFile.delete();
                    }
//                    content = new String(content.getBytes("ISO-8859-1"),"UTF-8");
                    System.out.println("content是"+content);
                    String title=request.getParameter("title");
                    System.out.println("title = " + title);
                    //String title="0.1.3.";//测试title为0.1.
                    Xml xml=new Xml();
                    String encode = "";
                    try {
                        encode = URLEncoder.encode(content,"utf-8");
                        System.out.println("encode = " + encode);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    int projectid=Integer.parseInt(request.getSession().getAttribute("projectid").toString());
                    xml.setTitle(title);
                    xml.setContent(encode);
                    xml.setProjectid(projectid);
                    //向xml表中增加或者修改一条记录
                    String res=xmlService.UploadXml(xml);
                    if(res.contains("success"))
                        return content;
                }
            }
        }
        return content;
    }
}
