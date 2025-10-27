package com.headlaugh.controller;

import com.headlaugh.dao.ApplyDAO;
import com.headlaugh.dao.JobDAO;
import com.headlaugh.dao.UserDAO;
import com.headlaugh.model.ApplyInfo;
import com.headlaugh.model.JobInfo;
import com.headlaugh.model.UserInfo;
import com.headlaugh.view.*;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class MainController {
    private JobDAO jobDAO = new JobDAO();
    private UserDAO userDAO = new UserDAO();
    private ApplyDAO applyDAO = new ApplyDAO();

    private MainFrame mainFrame;

    public MainController() {
        SwingUtilities.invokeLater(() -> {
            mainFrame = new MainFrame();
            bindListeners();
            refreshAll();
            mainFrame.setVisible(true);
        });
    }

    private void bindListeners() {
        JobPanel jobPanel = mainFrame.getJobPanel();
        UserPanel userPanel = mainFrame.getUserPanel();
        ApplyPanel applyPanel = mainFrame.getApplyPanel();
        ReviewPanel reviewPanel = mainFrame.getReviewPanel();

        // Job actions
        jobPanel.setOnAdd(job -> {
            try {
                if (job.getJobType() == null || job.getJobType().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "职位类型不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean ok = jobDAO.addJob(job);
                if (ok) {
                    JOptionPane.showMessageDialog(mainFrame,
                            "职位添加成功！\n职位类型：" + job.getJobType(),
                            "成功",
                            JOptionPane.INFORMATION_MESSAGE);
                    jobPanel.clearForm();
                    refreshJobs();
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "添加失败", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                showError(ex, "添加职位失败");
            }
        });

        jobPanel.setOnDelete(id -> {
            try {
                if (jobDAO.deleteJob(id)) {
                    JOptionPane.showMessageDialog(mainFrame, "删除成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                    refreshJobs();
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "删除失败或不存在", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                showError(ex, "删除职位失败");
            }
        });

        // User actions
        userPanel.setOnRegister(u -> {
            try {
                if (u.getLoginName() == null || u.getLoginName().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "登录名不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (u.getPassword() == null || u.getPassword().isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "密码不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (userDAO.findByLoginName(u.getLoginName()) != null) {
                    JOptionPane.showMessageDialog(mainFrame,
                            "登录名 '" + u.getLoginName() + "' 已存在，请更换",
                            "错误",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean ok = userDAO.addUser(u);
                if (ok) {
                    JOptionPane.showMessageDialog(mainFrame,
                            "注册成功！\n登录名：" + u.getLoginName() + "\n姓名：" + u.getUserName(),
                            "成功",
                            JOptionPane.INFORMATION_MESSAGE);
                    userPanel.clearForm();
                    refreshUsers();
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "注册失败", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                showError(ex, "注册用户失败");
            }
        });

        // Apply actions
        applyPanel.setOnApply(a -> {
            try {
                boolean ok = applyDAO.addApplication(a);
                if (ok) {
                    JOptionPane.showMessageDialog(mainFrame,
                            "申请投递成功！\n状态：待审核",
                            "成功",
                            JOptionPane.INFORMATION_MESSAGE);
                    applyPanel.clearForm();
                    refreshApplications();
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "投递失败", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                showError(ex, "投递申请失败");
            }
        });

        // Review actions: approve/reject
        reviewPanel.setOnApprove(id -> {
            try {
                if (applyDAO.updateStatus(id, "APPROVED")) {
                    refreshApplications();
                    JOptionPane.showMessageDialog(mainFrame,
                            "申请已通过！",
                            "审核成功",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "操作失败", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                showError(ex, "审核操作失败");
            }
        });

        reviewPanel.setOnReject(id -> {
            try {
                if (applyDAO.updateStatus(id, "REJECTED")) {
                    refreshApplications();
                    JOptionPane.showMessageDialog(mainFrame,
                            "申请已拒绝",
                            "审核完成",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "操作失败", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                showError(ex, "审核操作失败");
            }
        });
    }

    private void refreshAll() {
        refreshJobs();
        refreshUsers();
        refreshApplications();
    }

    private void refreshJobs() {
        try {
            List<JobInfo> jobs = jobDAO.getAllJobs();
            mainFrame.getJobPanel().setJobList(jobs);
            mainFrame.getApplyPanel().setJobList(jobs);
        } catch (SQLException ex) {
            showError(ex, "加载职位列表失败");
        }
    }

    private void refreshUsers() {
        try {
            List<UserInfo> users = userDAO.getAllUsers();
            mainFrame.getUserPanel().setUserList(users);
            mainFrame.getApplyPanel().setUserList(users);
        } catch (SQLException ex) {
            showError(ex, "加载用户列表失败");
        }
    }

    private void refreshApplications() {
        try {
            List<ApplyInfo> apps = applyDAO.getAllApplications();
            mainFrame.getReviewPanel().setApplicationList(apps);
        } catch (SQLException ex) {
            showError(ex, "加载申请列表失败");
        }
    }

    private void showError(Exception ex, String message) {
        ex.printStackTrace();
        String errorMsg = message + "\n错误信息: " + ex.getMessage();
        JOptionPane.showMessageDialog(mainFrame, errorMsg, "系统错误", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        new MainController();
    }
}