package com.headlaugh.view;

import com.headlaugh.controller.AuthController;
import com.headlaugh.model.AuthUser;
import com.headlaugh.controller.MainController;

import javax.swing.*;
import java.awt.*;

/**
 * 登录窗口：登录成功后创建 MainFrame 并由 MainController 绑定逻辑
 */
public class LoginFrame extends JFrame {

    private JTextField loginField = new JTextField(16);
    private JPasswordField passwordField = new JPasswordField(16);
    private AuthController authController = new AuthController();

    public LoginFrame() {
        setTitle("系统登录 - 招聘与求职管理系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 220);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = 0;
        form.add(new JLabel("登录名："), c);
        c.gridx = 1;
        form.add(loginField, c);

        c.gridx = 0; c.gridy = 1;
        form.add(new JLabel("密码："), c);
        c.gridx = 1;
        form.add(passwordField, c);

        JButton loginBtn = new JButton("登录");
        loginBtn.addActionListener(e -> doLogin());
        JButton exitBtn = new JButton("退出");
        exitBtn.addActionListener(e -> System.exit(0));

        JPanel btnPanel = new JPanel();
        btnPanel.add(loginBtn);
        btnPanel.add(exitBtn);

        add(form, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void doLogin() {
        String login = loginField.getText();
        String pwd = new String(passwordField.getPassword());
        AuthUser u = authController.login(login, pwd);
        if (u != null) {
            // 登录成功：由 MainFrame + MainController 继续流程
            SwingUtilities.invokeLater(() -> {
                MainFrame main = new MainFrame(u);
                // 用新的 MainController 绑定事件并刷新数据
                new MainController(main);
                main.setVisible(true);
            });
            this.dispose();
        }
    }
}