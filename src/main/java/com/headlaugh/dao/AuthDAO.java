package com.headlaugh.dao;

import com.headlaugh.model.AuthUser;
import com.headlaugh.model.Role;

import java.sql.*;

/**
 * 认证 DAO：优先从 admin_info 表查找（管理员/审核员），找不到再到 user_info 表查找（求职者）
 *
 * 说明：
 * - 密码示例中使用明文对比，生产环境请使用安全哈希（bcrypt/argon2 等）
 */
public class AuthDAO {

    /**
     * 根据登录名和密码进行认证，成功返回 AuthUser，失败返回 null
     */
    public AuthUser authenticate(String loginName, String password) throws SQLException {
        // 1) 检查 admin_info 表
        String sqlAdmin = "SELECT id, login_name, password, real_name, role FROM admin_info WHERE login_name = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlAdmin)) {
            ps.setString(1, loginName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String dbPwd = rs.getString("password");
                    if (dbPwd != null && dbPwd.equals(password)) {
                        long id = rs.getLong("id");
                        String realName = rs.getString("real_name");
                        String roleStr = rs.getString("role");
                        Role role = Role.fromString(roleStr);
                        return new AuthUser(id, loginName, role == null ? Role.ADMIN : role, realName);
                    } else {
                        // 管理员表存在但密码不对 -> 认证失败
                        return null;
                    }
                }
            }
        }

        // 2) 检查 user_info 表（求职者）
        String sqlUser = "SELECT id, login_name, password, user_name FROM user_info WHERE login_name = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlUser)) {
            ps.setString(1, loginName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String dbPwd = rs.getString("password");
                    if (dbPwd != null && dbPwd.equals(password)) {
                        long id = rs.getLong("id");
                        String userName = rs.getString("user_name");
                        return new AuthUser(id, loginName, Role.USER, userName);
                    } else {
                        return null;
                    }
                }
            }
        }

        // 都没找到
        return null;
    }
}