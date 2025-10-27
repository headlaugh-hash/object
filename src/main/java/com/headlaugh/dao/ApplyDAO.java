package com.headlaugh.dao;

import com.headlaugh.model.ApplyInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplyDAO {

    // 假定表名为 apply，列: id, job_id, user_id, resume, status, apply_time
    public boolean addApplication(ApplyInfo a) throws SQLException {
        String sql = "INSERT INTO apply (job_id, user_id, resume, status, apply_time) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, a.getJobId());
            ps.setLong(2, a.getUserId());
            ps.setString(3, a.getResume());
            ps.setString(4, a.getStatus() == null ? "PENDING" : a.getStatus());
            ps.setTimestamp(5, a.getApplyTime() == null ? new Timestamp(System.currentTimeMillis()) : a.getApplyTime());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        a.setId(rs.getLong(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    public ApplyInfo getApplicationById(long id) throws SQLException {
        String sql = "SELECT id, job_id, user_id, resume, status, apply_time FROM apply WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ApplyInfo a = new ApplyInfo();
                    a.setId(rs.getLong("id"));
                    a.setJobId(rs.getLong("job_id"));
                    a.setUserId(rs.getLong("user_id"));
                    a.setResume(rs.getString("resume"));
                    a.setStatus(rs.getString("status"));
                    a.setApplyTime(rs.getTimestamp("apply_time"));
                    return a;
                }
            }
        }
        return null;
    }

    public List<ApplyInfo> getAllApplications() throws SQLException {
        List<ApplyInfo> list = new ArrayList<>();
        String sql = "SELECT id, job_id, user_id, resume, status, apply_time FROM apply ORDER BY apply_time DESC";
        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                ApplyInfo a = new ApplyInfo();
                a.setId(rs.getLong("id"));
                a.setJobId(rs.getLong("job_id"));
                a.setUserId(rs.getLong("user_id"));
                a.setResume(rs.getString("resume"));
                a.setStatus(rs.getString("status"));
                a.setApplyTime(rs.getTimestamp("apply_time"));
                list.add(a);
            }
        }
        return list;
    }

    public List<ApplyInfo> getApplicationsByUser(long userId) throws SQLException {
        List<ApplyInfo> list = new ArrayList<>();
        String sql = "SELECT id, job_id, user_id, resume, status, apply_time FROM apply WHERE user_id = ? ORDER BY apply_time DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ApplyInfo a = new ApplyInfo();
                    a.setId(rs.getLong("id"));
                    a.setJobId(rs.getLong("job_id"));
                    a.setUserId(rs.getLong("user_id"));
                    a.setResume(rs.getString("resume"));
                    a.setStatus(rs.getString("status"));
                    a.setApplyTime(rs.getTimestamp("apply_time"));
                    list.add(a);
                }
            }
        }
        return list;
    }

    public boolean updateStatus(long id, String newStatus) throws SQLException {
        String sql = "UPDATE apply SET status = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setLong(2, id);
            return ps.executeUpdate() > 0;
        }
    }
}