package com.xq.controller;

import com.xq.model.MenuItem;
import com.xq.model.Report;
import com.xq.model.Xml;
import com.xq.service.ReportService;
import com.xq.util.ReportMarkerUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Liuwei on 2017/4/17.
 */
@Controller
public class ReportController {
    @Resource
    private ReportService reportService;

    @RequestMapping(value = "/downloadReport.do", produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String downloadReport(HttpServletRequest request,
                                 HttpServletResponse response, String evaluation, String securityLevel)
            throws IOException {
        request.setCharacterEncoding("utf-8");
        String order = "";             //序号
        String testItem = "";          //测试脚本id + text
        String result = "";            //测试结果
        String summary = "";           //测试总结
        String detail = "";            //测试要求及详细结果
        int scriptNum = 0;             //当前测试等级脚本总数
        int testedNum = 0;             //已测数量
        int untestedNum = 0;           //未测数量
        int testSuccessNum = 0;        //测试通过数量
        int testFailNum = 0;           //测试失败数量
        int unfinishedNum = 0;         //测试未完成数量
        int projectid = Integer.parseInt(request.getSession().getAttribute("projectid").toString());
        String projectName = reportService.selectProjectName(projectid);
        projectName += "（" + "当前测试等级为" + securityLevel + "）";

        String xml_title = "";
        if (securityLevel.equals("用户自主保护级")){
            xml_title = "0.1";
        } else if (securityLevel.equals("系统审计保护级")) {
            xml_title = "0.2";
        } else {
            xml_title = "0.3";
        }
        //查询report表中当前project的记录
        List<Report> reportList = reportService.getReport(projectid, xml_title);

        String menuFj = xml_title + ".1.";
        //获取menu表中安全等级为securityLevel的安全功能子目录
        List<MenuItem> menuList = reportService.selectSecurityDir(projectid,menuFj);
        //此处xml_title表示menu表中的fj
        scriptNum = reportService.selectScriptNum(projectid, xml_title);

        summary = securityLevel + "测试脚本总数为：" + scriptNum + "。";
        //若report表中存在当前project记录
        if (reportList != null){
            for (int i = 0;i < reportList.size();i++){
                System.out.println(reportList.get(i).getReportid()+"\t"+reportList.get(i).getXml_title()
                        +"\t"+reportList.get(i).getProjectid()+"\t"+ reportList.get(i).getResult());
            }
            String innerOrder = "5.1.";  //测试报告中详细结果的目录
            int toOrder = 0;    //测试结果简表序号
            for (int i = 0; i < menuList.size(); i ++) {
                //报告中安全等级为securityLevel的安全功能子目录序号+目录名
                detail += innerOrder + (i + 1) + "  " + menuList.get(i).getText() + "<w:br/>";
                //查询report表中安全功能某一子目录下的所有已测脚本记录
                String title = xml_title + ".1." + (i + 1);
                System.out.println("title=="+title);
                List<Report> limitReportList = reportService.getReport(projectid, title);
                for (int j = 0; j < limitReportList.size(); j ++) {
                    //获取realId，即节点的真实id
                    String realId = limitReportList.get(j).getXml_title();
                    System.out.println("realId==="+realId);
                    //获取脚本内容,并将其转换为小写
                    Xml xml = reportService.selectXmlContent(projectid, realId);
                    String xmlContent = "";
                    if (xml != null) {
                        xmlContent = xml.getContent();
                        if (xmlContent.length() != 0){
                            xmlContent = URLDecoder.decode(xmlContent, "utf-8");
                            xmlContent = xmlContent.toLowerCase();
                        }
                    }

                    realId = realId.substring(0,realId.length()-1);
                    //节点的id,fj
                    String id = realId.substring(realId.length()-1,realId.length());
                    String fj = realId.substring(0,realId.length()-1);
                    //根据id、fj以及projectid从menu表中获取节点text，即脚本文件名
                    MenuItem item = new MenuItem();
                    item.setId(id);
                    item.setFj(fj);
                    item.setProjectid(projectid);
                    System.out.println("id="+id+"\t"+"fj="+fj);
                    String text = reportService.selectMenuText(item);

                    toOrder ++;
                    //简表序号
                    order += toOrder + "<w:br/>";
                    //简表中目录序号+脚本名
                    testItem += innerOrder + (i + 1) + "." + (j + 1) + "  " + text + "<w:br/>";
                    //简表测试结果
                    result += limitReportList.get(j).getResult() + "<w:br/>";
                    //详细结果目录序号+脚本名（子目录），该目录下包含测试项和测试结果
                    detail += innerOrder + (i + 1) + "." + (j + 1) + "  " + text + "<w:br/>";
                    //content为脚本中的测试思路，即报告中的测试项
                    detail += "\t" + "测试项：" + "<w:br/>";
                    String content = "";
                    if (xmlContent.indexOf("<content>") > 0 && xmlContent.indexOf("</content>") > 0){
                        content = xmlContent.substring(
                                xmlContent.indexOf("<content>") + 9, xmlContent.indexOf("</content>"));
                    }
                    detail += "\t" + "\t" + content + "<w:br/>";
                    //测试结果包含xml表中的result和detail字段的内容
                    detail += "\t" + "测试结果：" + "<w:br/>";
                    String atom_detail = "";
                    atom_detail = URLDecoder.decode(limitReportList.get(j).getDetail(),"utf-8");
                    System.out.println(atom_detail);
                    //atom_detail.replace("\\n","<w:br/>");
                    detail += "\t" + "\t" + limitReportList.get(j).getResult() + "。" + "<w:br/>" + atom_detail + "。" + "<w:br/>";

                }

            }
            /*for(int i = 0; i < reportList.size(); i ++){
                //获取realId，即节点的真实id
                String realId = reportList.get(i).getXml_title();
                realId = realId.substring(0,realId.length()-1);
                //节点的id,fj
                String id = realId.substring(realId.length()-1,realId.length());
                String fj = realId.substring(0,realId.length()-1);
                //获取节点text，即脚本文件名
                MenuItem item = new MenuItem();
                item.setId(id);
                item.setFj(fj);
                item.setProjectid(projectid);
                System.out.println("id="+id+"\t"+"fj="+fj);
                String text = reportService.selectMenuText(item);

                order += (i + 1) + "<w:br/>";
                testItem += realId + "  " + text + "<w:br/>";
                result += reportList.get(i).getResult() + "<w:br/>";
            }*/
            //删去order，testItem，result最后的换行符
            if (order.length() != 0) {
                order = order.substring(0, order.length() - 7);
                testItem = testItem.substring(0, testItem.length() - 7);
                result = result.substring(0, result.length() - 7);
            }
            testedNum = reportService.selectTestedNum(projectid, xml_title);
            testSuccessNum = reportService.selectResultNum(projectid, xml_title, "测试通过");
            testFailNum = reportService.selectResultNum(projectid, xml_title, "测试失败");
            unfinishedNum = testedNum - testSuccessNum - testFailNum;
            System.out.println("order = " + order + "\n" + "testItem = " + testItem + "\n" + "result = " + result);
        }
        untestedNum = scriptNum - testedNum;
        summary += "其中已测 " + testedNum + " 项，" + "未测 " + untestedNum + " 项，" + "测试通过 " +
                testSuccessNum + " 项，" + "测试失败 " + testFailNum + " 项，" + "测试未完成 " + unfinishedNum + " 项。";

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("projectName",projectName);
        map.put("order",order);
        map.put("evaluation",evaluation);
        map.put("testItem",testItem);
        map.put("result",result);
        map.put("summary",summary);
        map.put("detail",detail);

        //提示：在调用工具类生成Word文档之前应当检查所有字段是否完整
        //否则Freemarker的模板殷勤在处理时可能会因为找不到值而报错，这里暂时忽略这个步骤
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;
        try{
            //调用工具类ReportMarkerUtil的createDoc方法生成Word文档
            ReportMarkerUtil reportMarker = new ReportMarkerUtil();
            file = reportMarker.createDoc(map, "report");
            fin = new FileInputStream(file);

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            // 设置浏览器以下载的方式处理该文件默认名为report.doc
            response.addHeader("Content-Disposition", "attachment;filename=report.doc");

            out = response.getOutputStream();
            byte[] buffer = new byte[1024];//缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }

        } catch(Exception ex){
            ex.printStackTrace();
        } finally{
            if(fin != null) {
                fin.close();
            }
            if(out != null) {
                out.close();
            }
            if(file != null) {
                file.delete(); // 删除临时文件
            }
        }
        return null;
    }

    @RequestMapping(value = "/saveReport.do", produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String saveReport(String xml_title, int projectid, String result){
        Report report = new Report();
        xml_title = "0.1.1.1";
        projectid = 0;
        result = "123456789";
        report.setXml_title(xml_title);
        report.setProjectid(projectid);
        report.setResult(result);
        int res = reportService.saveReport(report);
        if (res == 1){
            return "1";
        }
        return "0";
    }
    @RequestMapping(value = "/countInformation",produces = "application/json; charset=utf-8")
    @ResponseBody
    public  Map<String,Integer> countInformation(HttpServletRequest request)
    {
        System.out.println("information request"+request);
        String id;
        int projectid = Integer.parseInt(request.getSession().getAttribute("projectid").toString());
        System.out.println("项目id"+projectid);
        String securityLevel = request.getSession().getAttribute("grade").toString();
        System.out.println("level"+securityLevel);
        if (securityLevel.equals("用户自主保护级")){
            id = "0.1.";
        } else if (securityLevel.equals("系统审计保护级")) {
            id = "0.2.";
        } else {
            id = "0.3.";
        }
// 需要的 总共测试的脚本数量  已经测试的脚本数量 通过的数量  失败的数量 未完成的数量 全自动的数量 半自动的数量
        int allNumber = 0;// 全部脚本数量
        int testNumber = 0;//已测脚本数量
        int restNumber=0;//未测脚本数量
        int failNumber = 0;//测试失败脚本的数量
        int successNumber = 0;//测试成功脚本数量
        int cancelNumber = 0;//取消测试的脚本数量
        int autoNumber = 0;//全自动脚本的数量
        int halfNumber = 0;//半自动脚本数量
        int manualNumber = 0;//人工脚本数量
        Map<String,Integer> result=new HashMap<>();

        allNumber = reportService.selectScriptNum(projectid,id);
        System.out.println("allnumber: "+allNumber);
        testNumber = reportService.selectTestedNum(projectid,id);
        System.out.println("testNumber: "+testNumber);

        restNumber=allNumber-testNumber;
        successNumber=reportService.selectResultNum(projectid, id, "测试通过");
        System.out.println("successNumber: "+successNumber);
        failNumber=reportService.selectResultNum(projectid, id, "测试失败");
        System.out.println("failNumber: "+failNumber);
        cancelNumber=testNumber-successNumber-failNumber;
        autoNumber=reportService.selectScriptTypeNum(projectid,"全自动");
        System.out.println("autoNumber: "+autoNumber);
        halfNumber=reportService.selectScriptTypeNum(projectid,"半自动");
        System.out.println("halfNumber: "+halfNumber);
        manualNumber=reportService.selectScriptTypeNum(projectid,"人工");
        System.out.println("manualNumber: "+manualNumber);
        result.put("allNumber",allNumber);
        result.put("testNumber",testNumber);
        result.put("restNumber",restNumber);
        result.put("failNumber",failNumber);
        result.put("successNumber",successNumber);
        result.put("cancelNumber",cancelNumber);
        result.put("autoNumber",autoNumber);
        result.put("halfNumber",halfNumber);
        result.put("manualNumber",manualNumber);
        System.out.println(result.toString());


        return result;
    }
}

