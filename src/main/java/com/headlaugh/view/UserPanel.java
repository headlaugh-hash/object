package com.headlaugh.view;

import com.headlaugh.model.UserInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class UserPanel extends JPanel {
    private JTextField loginNameField = new JTextField(15);
    private JTextField userNameField = new JTextField(15);
    private JTextField eduField = new JTextField(15);
    private JTextField jobProcessField = new JTextField(15);
    private JTextField rewardField = new JTextField(15);
    private JPasswordField passwordField = new JPasswordField(15);

    private DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"ID","登录名","姓名","学历","工作经历","奖励经历"}, 0) {
        public boolean isCellEditable(int row, int col) { return false; }
    };
    private JTable table = new JTable(tableModel);

    private Consumer<UserInfo> onRegister;

    public UserPanel() {
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(7,2,4,4));
        form.add(new JLabel("登录名:")); form.add(loginNameField);
        form.add(new JLabel("密码:")); form.add(passwordField);
        form.add(new JLabel("姓名:")); form.add(userNameField);
        form.add(new JLabel("学历:")); form.add(eduField);
        form.add(new JLabel("工作经历:")); form.add(jobProcessField);
        form.add(new JLabel("奖励经历:")); form.add(rewardField);

        JButton regBtn = new JButton("注册");
        regBtn.addActionListener(e -> {
            if (onRegister != null) {
                UserInfo u = new UserInfo();
                u.setLoginName(loginNameField.getText().trim());
                u.setPassword(new String(passwordField.getPassword()));
                u.setUserName(userNameField.getText().trim());
                u.setEduBackground(eduField.getText().trim());
                u.setJobProcess(jobProcessField.getText().trim());
                u.setRewardProcess(rewardField.getText().trim());
                onRegister.accept(u);
            }
        });

        form.add(new JLabel()); form.add(regBtn);

        add(form, BorderLayout.SOUTH);
    }

    public void setUserList(List<UserInfo> users) {
        tableModel.setRowCount(0);
        for (UserInfo u : users) {
            tableModel.addRow(new Object[]{
                    u.getId(),
                    u.getLoginName(),
                    u.getUserName(),
                    u.getEduBackground(),
                    u.getJobProcess(),
                    u.getRewardProcess()
            });
        }
    }

    public void clearForm() {
        loginNameField.setText("");
        passwordField.setText("");
        userNameField.setText("");
        eduField.setText("");
        jobProcessField.setText("");
        rewardField.setText("");
    }

    public void setOnRegister(Consumer<UserInfo> onRegister) {
        this.onRegister = onRegister;
    }
}