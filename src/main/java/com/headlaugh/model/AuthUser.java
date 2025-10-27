package com.headlaugh.model;

/**
 * 简单的认证用户信息：登录后返回给界面/控制器使用
 */
public class AuthUser {
    private long id;
    private String loginName;
    private Role role;
    private String displayName; // 管理员使用 realName，求职者使用 user_name

    public AuthUser() {}

    public AuthUser(long id, String loginName, Role role, String displayName) {
        this.id = id;
        this.loginName = loginName;
        this.role = role;
        this.displayName = displayName;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}