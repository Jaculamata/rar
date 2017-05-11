package com.xq.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Created by Liuwei on 2017/4/24.
 */
public class ReportMarkerUtil {

    /*private ReportMarkerUtil() {
        throw new AssertionError();
    }*/

    public File createDoc(Map<?, ?> dataMap, String type) {
        Configuration configuration = null;
        Map<String, Template> allTemplates = null;

        {
            configuration = new Configuration();
            configuration.setDefaultEncoding("utf-8");
            configuration.setClassForTemplateLoading(ReportMarkerUtil.class, "/template");
            allTemplates = new HashMap<>();
            try {
                allTemplates.put("report", configuration.getTemplate("report.ftl"));

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        String name = "temp" + (int) (Math.random() * 100000) + ".doc";
        File file = new File(name);
        Template temp = allTemplates.get(type);
        try {
            // 这个地方不能使用FileWriter因为需要指定编码类型否则生成的Word文档会因为有无法识别的编码而无法打开
            Writer w = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
            temp.process(dataMap, w);
            w.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return file;
    }
}
