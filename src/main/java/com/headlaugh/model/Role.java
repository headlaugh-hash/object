package com.headlaugh.model;

/**
 * 角色枚举
 */
public enum Role {
    ADMIN,
    REVIEWER,
    USER;

    public static Role fromString(String s) {
        if (s == null){
            return null;
        }
        try {
            return Role.valueOf(s.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }
}