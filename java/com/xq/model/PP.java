package com.xq.model;

/**
 * Created by lu on 2017/4/5.
 */
public class PP {
    private int projectid;
    private int propertyid;
    private String value;

    @Override
    public String toString() {
        return "PP{" +
                "projectid=" + projectid +
                ", propertyid='" + propertyid + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public int getProjectid() {
        return projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }

    public int getPropertyid() {
        return propertyid;
    }

    public void setPropertyid(int propertyid) {
        this.propertyid = propertyid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
