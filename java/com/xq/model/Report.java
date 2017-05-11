package com.xq.model;

/**
 * Created by Liuwei on 2017/4/17.
 */
public class Report {
    private int reportid;
    private String xml_title;
    private int projectid;
    private String detail;
    private String result;
    private String time;

    public int getReportid() {
        return reportid;
    }

    public void setReportid(int reportid) {
        this.reportid = reportid;
    }

    public String getXml_title() {
        return xml_title;
    }

    public void setXml_title(String xml_title) {
        this.xml_title = xml_title;
    }

    public int getProjectid() {
        return projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
