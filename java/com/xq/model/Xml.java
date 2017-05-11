package com.xq.model;

/**
 * Created by lu on 2017/3/13.
 */
public class Xml {
    private  int xmlid;
    private  String title;
    private  String description;
    private  String content;
    private  String type;
    private int projectid;

    @Override
    public String toString() {
        return "Xml{" +
                "xmlid=" + xmlid +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", projectid='" + projectid + '\'' +
                '}';
    }

    public int getXmlid() {
        return xmlid;
    }

    public void setXmlid(int xmlid) {
        this.xmlid = xmlid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public int getProjectid() {
        return projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }
}
