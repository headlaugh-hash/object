package com.headlaugh.dao;

import com.headlaugh.model.UserInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // 假定表名为 user_info，列名: id, user_name, edu_background, job_process, reward_process, login_name, password
    public boolean addUser(UserInfo u) throws SQLException {
        String sql = "INSERT INTO user_info (user_name, edu_background, job_process, reward_process, login_name, password) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getUserName());
            ps.setString(2, u.getEduBackground());
            ps.setString(3, u.getJobProcess());
            ps.setString(4, u.getRewardProcess());
            ps.setString(5, u.getLoginName());
            ps.setString(6, u.getPassword());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        u.setId(rs.getLong(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    public UserInfo getUserById(long id) throws SQLException {
        String sql = "SELECT id, user_name, edu_background, job_process, reward_process, login_name, password FROM user_info WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UserInfo u = new UserInfo();
                    u.setId(rs.getLong("id"));
                    u.setUserName(rs.getString("user_name"));
                    u.setEduBackground(rs.getString("edu_background"));
                    u.setJobProcess(rs.getString("job_process"));
                    u.setRewardProcess(rs.getString("reward_process"));
                    u.setLoginName(rs.getString("login_name"));
                    u.setPassword(rs.getString("password"));
                    return u;
                }
            }
        }
        return null;
    }

    public UserInfo findByLoginName(String loginName) throws SQLException {
        String sql = "SELECT id, user_name, edu_background, job_process, reward_process, login_name, password FROM user_info WHERE login_name = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, loginName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UserInfo u = new UserInfo();
                    u.setId(rs.getLong("id"));
                    u.setUserName(rs.getString("user_name"));
                    u.setEduBackground(rs.getString("edu_background"));
                    u.setJobProcess(rs.getString("job_process"));
                    u.setRewardProcess(rs.getString("reward_process"));
                    u.setLoginName(rs.getString("login_name"));
                    u.setPassword(rs.getString("password"));
                    return u;
                }
            }
        }
        return null;
    }

    public List<UserInfo> getAllUsers() throws SQLException {
        List<UserInfo> list = new ArrayList<>();
        String sql = "SELECT id, user_name, edu_background, job_process, reward_process, login_name, password FROM user_info";
        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                UserInfo u = new UserInfo();
                u.setId(rs.getLong("id"));
                u.setUserName(rs.getString("user_name"));
                u.setEduBackground(rs.getString("edu_background"));
                u.setJobProcess(rs.getString("job_process"));
                u.setRewardProcess(rs.getString("reward_process"));
                u.setLoginName(rs.getString("login_name"));
                u.setPassword(rs.getString("password"));
                list.add(u);
            }
        }
        return list;
    }
}