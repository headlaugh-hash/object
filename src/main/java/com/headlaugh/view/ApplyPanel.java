package com.headlaugh.view;

import com.headlaugh.model.JobInfo;
import com.headlaugh.model.UserInfo;
import com.headlaugh.model.ApplyInfo;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class ApplyPanel extends JPanel {
    private JComboBox<JobInfo> jobCombo = new JComboBox<>();
    private JComboBox<UserInfo> userCombo = new JComboBox<>();
    private JTextArea resumeArea = new JTextArea(6, 40);

    private Consumer<ApplyInfo> onApply;

    public ApplyPanel() {
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("选择职位:"));
        top.add(jobCombo);
        top.add(new JLabel("求职者:"));
        top.add(userCombo);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(resumeArea), BorderLayout.CENTER);

        JButton applyBtn = new JButton("投递申请");
        applyBtn.addActionListener(e -> {
            if (onApply != null) {
                JobInfo job = (JobInfo) jobCombo.getSelectedItem();
                UserInfo user = (UserInfo) userCombo.getSelectedItem();
                if (job == null || user == null) {
                    JOptionPane.showMessageDialog(this, "请选择职位和求职者");
                    return;
                }
                ApplyInfo a = new ApplyInfo();
                a.setJobId(job.getId());
                a.setUserId(user.getId());
                a.setResume(resumeArea.getText().trim());
                onApply.accept(a);
            }
        });
        JPanel south = new JPanel();
        south.add(applyBtn);
        add(south, BorderLayout.SOUTH);

        // renderer to show meaningful text
        jobCombo.setRenderer((list, value, index, isSelected, cellHasFocus) ->
                new JLabel(value == null ? "" : value.getJobType()));
        userCombo.setRenderer((list, value, index, isSelected, cellHasFocus) ->
                new JLabel(value == null ? "" : value.getLoginName()));
    }

    public void setJobList(List<JobInfo> jobs) {
        DefaultComboBoxModel<JobInfo> m = new DefaultComboBoxModel<>();
        for (JobInfo j : jobs) m.addElement(j);
        jobCombo.setModel(m);
    }

    public void setUserList(List<UserInfo> users) {
        DefaultComboBoxModel<UserInfo> m = new DefaultComboBoxModel<>();
        for (UserInfo u : users) m.addElement(u);
        userCombo.setModel(m);
    }

    public void clearForm() {
        resumeArea.setText("");
    }

    public void setOnApply(Consumer<ApplyInfo> onApply) {
        this.onApply = onApply;
    }
}