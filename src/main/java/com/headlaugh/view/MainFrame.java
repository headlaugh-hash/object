package com.headlaugh.view;

import com.headlaugh.model.AuthUser;
import com.headlaugh.model.Role;

import javax.swing.*;
import java.awt.*;
/**
 * 主界面：根据登录用户角色决定显示哪些 tab
 *
 * 注意：这是对原有 MainFrame 的替换版本（接受 AuthUser），
 * 如果你希望保留原文件名请用此替换原来项目中的 MainFrame.java。
 *
 * 角色权限映射（示例）：
 * - ADMIN: 显示所有功能（职位管理、求职者注册/查看、投递申请、申请审核）
 * - REVIEWER: 仅显示“申请审核”页面（和查看职位）
 * - USER: 显示职位浏览、投递申请、个人信息（注册/查看）页面
 */
public class MainFrame extends JFrame {
    private JobPanel jobPanel;
    private UserPanel userPanel;
    private ApplyPanel applyPanel;
    private ReviewPanel reviewPanel;

    private AuthUser authUser;

    public MainFrame(AuthUser authUser) {
        this.authUser = authUser;
        setTitle("招聘与求职管理系统 - 已登录：" + authUser.getDisplayName() + " (" + authUser.getRole() + ")");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // 尝试使用系统外观
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignore) {}

        jobPanel = new JobPanel();
        userPanel = new UserPanel();
        applyPanel = new ApplyPanel();
        reviewPanel = new ReviewPanel();

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("微软雅黑", Font.BOLD, 13));
        tabs.setTabPlacement(JTabbedPane.TOP);

        // 根据角色选择 tab
        Role role = authUser.getRole();
        if (role == Role.ADMIN) {
            tabs.addTab("  职位管理  ", jobPanel);
            tabs.addTab("  求职者注册  ", userPanel);
            tabs.addTab("  投递申请  ", applyPanel);
            tabs.addTab("  申请审核  ", reviewPanel);
        } else if (role == Role.REVIEWER) {
            tabs.addTab("  申请审核  ", reviewPanel);
            // 适当给予查看职位权限
            tabs.addTab("  职位浏览  ", jobPanel);
        } else { // USER
            tabs.addTab("  职位浏览  ", jobPanel);
            tabs.addTab("  投递申请  ", applyPanel);
            tabs.addTab("  我的信息  ", userPanel);
        }

        add(tabs, BorderLayout.CENTER);
        // 也可以在底部显示登录信息或登出按钮
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutBtn = new JButton("登出");
        logoutBtn.addActionListener(e -> {
            // 直接重新打开登录界面
            SwingUtilities.invokeLater(() -> {
                new LoginFrame().setVisible(true);
            });
            this.dispose();
        });
        south.add(new JLabel("当前用户: " + authUser.getDisplayName() + " 角色:" + authUser.getRole()));
        south.add(logoutBtn);
        add(south, BorderLayout.SOUTH);
    }

    // 便于 MainController/其它逻辑获取各 Panel（如果你还使用原来的 MainController）
    public JobPanel getJobPanel() { return jobPanel; }
    public UserPanel getUserPanel() { return userPanel; }
    public ApplyPanel getApplyPanel() { return applyPanel; }
    public ReviewPanel getReviewPanel() { return reviewPanel; }
}