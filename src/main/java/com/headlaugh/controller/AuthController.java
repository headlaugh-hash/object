package com.headlaugh.controller;

import com.headlaugh.dao.AuthDAO;
import com.headlaugh.model.AuthUser;

import javax.swing.*;
import java.sql.SQLException;

/**
 * 认证控制器（简单包装 DAO，返回 AuthUser 或在界面上显示错误提示）
 */
public class AuthController {

    private AuthDAO authDAO = new AuthDAO();

    /**
     * 尝试登录，成功返回 AuthUser，失败返回 null（并在需要时通过 JOptionPane 提示）
     */
    public AuthUser login(String loginName, String password) {
        if (loginName == null || loginName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "登录名不能为空", "错误", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if (password == null || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "密码不能为空", "错误", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        try {
            AuthUser u = authDAO.authenticate(loginName.trim(), password);
            if (u == null) {
                JOptionPane.showMessageDialog(null, "用户名或密码错误", "登录失败", JOptionPane.ERROR_MESSAGE);
            }
            return u;
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "登录发生数据库错误：" + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}