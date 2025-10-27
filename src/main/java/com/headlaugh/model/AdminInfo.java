package com.headlaugh.model;

public class AdminInfo {
    private long id;
    private String loginName;
    private String password;
    private String realName;
    private String role; // ADMIN, REVIEWER, USER

    public AdminInfo() {}

    public AdminInfo(long id, String loginName, String password, String realName, String role) {
        this.id = id;
        this.loginName = loginName;
        this.password = password;
        this.realName = realName;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "AdminInfo{" +
                "id=" + id +
                ", loginName='" + loginName + '\'' +
                ", realName='" + realName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}