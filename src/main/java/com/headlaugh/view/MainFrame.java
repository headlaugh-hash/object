package com.headlaugh.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JobPanel jobPanel;
    private UserPanel userPanel;
    private ApplyPanel applyPanel;
    private ReviewPanel reviewPanel;

    public MainFrame() {
        setTitle("招聘与求职管理系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // 设置窗口图标（如果有的话）
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        jobPanel = new JobPanel();
        userPanel = new UserPanel();
        applyPanel = new ApplyPanel();
        reviewPanel = new ReviewPanel();

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("微软雅黑", Font.BOLD, 13));
        tabs.setTabPlacement(JTabbedPane.TOP);

        tabs.addTab("  职位管理  ", jobPanel);
        tabs.addTab("  求职者注册  ", userPanel);
        tabs.addTab("  投递申请  ", applyPanel);
        tabs.addTab("  申请审核  ", reviewPanel);

        // 设置tab的颜色
        tabs.setBackground(new Color(240, 240, 240));

        add(tabs, BorderLayout.CENTER);

        // 添加状态栏
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        JLabel statusLabel = new JLabel("就绪");
        statusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 11));
        statusBar.add(statusLabel);
        add(statusBar, BorderLayout.SOUTH);
    }

    public JobPanel getJobPanel() {
        return jobPanel;
    }

    public UserPanel getUserPanel() {
        return userPanel;
    }

    public ApplyPanel getApplyPanel() {
        return applyPanel;
    }

    public ReviewPanel getReviewPanel() {
        return reviewPanel;
    }
}