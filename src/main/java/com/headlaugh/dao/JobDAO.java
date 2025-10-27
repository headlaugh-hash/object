package com.headlaugh.dao;

import com.headlaugh.model.JobInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class JobDAO {

    // 注意：假定表名为 job，列名为 id, job_type, job_desc, job_requirement, salary, memo
    public List<JobInfo> getAllJobs() throws SQLException {
        List<JobInfo> list = new ArrayList<>();
        String sql = "SELECT id, job_type, job_desc, job_requirement, salary, memo FROM job";
        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                JobInfo j = new JobInfo();
                j.setId(rs.getLong("id"));
                j.setJobType(rs.getString("job_type"));
                j.setJobDesc(rs.getString("job_desc"));
                j.setJobRequirement(rs.getString("job_requirement"));
                BigDecimal sal = rs.getBigDecimal("salary");
                j.setSalary(sal);
                j.setMemo(rs.getString("memo"));
                list.add(j);
            }
        }
        return list;
    }

    public JobInfo getJobById(long id) throws SQLException {
        String sql = "SELECT id, job_type, job_desc, job_requirement, salary, memo FROM job WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    JobInfo j = new JobInfo();
                    j.setId(rs.getLong("id"));
                    j.setJobType(rs.getString("job_type"));
                    j.setJobDesc(rs.getString("job_desc"));
                    j.setJobRequirement(rs.getString("job_requirement"));
                    j.setSalary(rs.getBigDecimal("salary"));
                    j.setMemo(rs.getString("memo"));
                    return j;
                }
            }
        }
        return null;
    }

    public boolean addJob(JobInfo j) throws SQLException {
        String sql = "INSERT INTO job (job_type, job_desc, job_requirement, salary, memo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, j.getJobType());
            ps.setString(2, j.getJobDesc());
            ps.setString(3, j.getJobRequirement());
            ps.setBigDecimal(4, j.getSalary());
            ps.setString(5, j.getMemo());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        j.setId(keys.getLong(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean updateJob(JobInfo j) throws SQLException {
        String sql = "UPDATE job SET job_type = ?, job_desc = ?, job_requirement = ?, salary = ?, memo = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, j.getJobType());
            ps.setString(2, j.getJobDesc());
            ps.setString(3, j.getJobRequirement());
            ps.setBigDecimal(4, j.getSalary());
            ps.setString(5, j.getMemo());
            ps.setLong(6, j.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteJob(long id) throws SQLException {
        String sql = "DELETE FROM job WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}