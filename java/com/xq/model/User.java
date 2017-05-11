package com.xq.model;

public class User {
    private int id;
    private String user;
    private String password;
    private String role;
    private String name;
    private String reg_time;
    private int recent_projectid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", name='" + name + '\'' +
                ", reg_time='" + reg_time + '\'' +
                ", recent_projectid='" + recent_projectid + '\'' +
                '}';
    }

    public int getProjectid() {
        return recent_projectid;
    }

    public void setProjectid(int projectid) {
        this.recent_projectid = projectid;
    }
}

