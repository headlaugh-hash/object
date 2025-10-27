package com.headlaugh.view;

import com.headlaugh.model.JobInfo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class JobPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField jobTypeField = new JTextField(20);
    private JTextArea jobDescArea = new JTextArea(4, 30);
    private JTextArea jobReqArea = new JTextArea(4, 30);
    private JTextField salaryField = new JTextField(15);
    private JTextField memoField = new JTextField(20);

    private Consumer<JobInfo> onAdd;
    private Consumer<Long> onDelete;

    public JobPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // 表格面板
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "职位列表",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("微软雅黑", Font.BOLD, 14)
        ));

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "职位类型", "职位描述", "任职要求", "薪资", "备注"}, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 12));
        table.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(0, 250));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);

        // 表单面板
        JPanel formPanel = new JPanel(new BorderLayout(5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "职位信息",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("微软雅黑", Font.BOLD, 14)
        ));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;

        // 职位类型
        c.gridx = 0; c.gridy = 0;
        c.weightx = 0;
        JLabel typeLabel = new JLabel("职位类型:");
        typeLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        inputPanel.add(typeLabel, c);

        c.gridx = 1;
        c.weightx = 1.0;
        jobTypeField.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        inputPanel.add(jobTypeField, c);

        // 薪资
        c.gridx = 2;
        c.weightx = 0;
        JLabel salaryLabel = new JLabel("薪资(元):");
        salaryLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        inputPanel.add(salaryLabel, c);

        c.gridx = 3;
        c.weightx = 0.5;
        salaryField.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        inputPanel.add(salaryField, c);

        // 职位描述
        c.gridx = 0; c.gridy = 1;
        c.weightx = 0;
        c.anchor = GridBagConstraints.NORTHWEST;
        JLabel descLabel = new JLabel("职位描述:");
        descLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        inputPanel.add(descLabel, c);

        c.gridx = 1; c.gridwidth = 3;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 0.5;
        jobDescArea.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        jobDescArea.setLineWrap(true);
        jobDescArea.setWrapStyleWord(true);
        JScrollPane descScroll = new JScrollPane(jobDescArea);
        inputPanel.add(descScroll, c);

        // 任职要求
        c.gridx = 0; c.gridy = 2;
        c.gridwidth = 1;
        c.weightx = 0;
        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTHWEST;
        JLabel reqLabel = new JLabel("任职要求:");
        reqLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        inputPanel.add(reqLabel, c);

        c.gridx = 1; c.gridwidth = 3;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 0.5;
        jobReqArea.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        jobReqArea.setLineWrap(true);
        jobReqArea.setWrapStyleWord(true);
        JScrollPane reqScroll = new JScrollPane(jobReqArea);
        inputPanel.add(reqScroll, c);

        // 备注
        c.gridx = 0; c.gridy = 3;
        c.gridwidth = 1;
        c.weightx = 0;
        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        JLabel memoLabel = new JLabel("备注:");
        memoLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        inputPanel.add(memoLabel, c);

        c.gridx = 1; c.gridwidth = 3;
        c.weightx = 1.0;
        memoField.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        inputPanel.add(memoField, c);

        formPanel.add(inputPanel, BorderLayout.CENTER);

        // 按钮面板
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton addBtn = new JButton("添加职位");
        addBtn.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        addBtn.setPreferredSize(new Dimension(100, 30));
        addBtn.addActionListener(e -> {
            if (onAdd != null) {
                JobInfo j = new JobInfo();
                j.setJobType(jobTypeField.getText().trim());
                j.setJobDesc(jobDescArea.getText().trim());
                j.setJobRequirement(jobReqArea.getText().trim());
                try {
                    String s = salaryField.getText().trim();
                    if (!s.isEmpty()) {
                        j.setSalary(new java.math.BigDecimal(s));
                    } else {
                        j.setSalary(null);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "薪资输入格式不正确", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                j.setMemo(memoField.getText().trim());
                onAdd.accept(j);
            }
        });

        JButton delBtn = new JButton("删除选中");
        delBtn.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        delBtn.setPreferredSize(new Dimension(100, 30));
        delBtn.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r >= 0 && onDelete != null) {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "确定要删除该职位吗?",
                        "确认删除",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    Object val = tableModel.getValueAt(r, 0);
                    Long id = val instanceof Long ? (Long) val : ((Number) val).longValue();
                    onDelete.accept(id);
                }
            } else {
                JOptionPane.showMessageDialog(this, "请先选择要删除的职位", "提示", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnPanel.add(addBtn);
        btnPanel.add(delBtn);
        formPanel.add(btnPanel, BorderLayout.SOUTH);

        add(formPanel, BorderLayout.SOUTH);
    }

    public void setJobList(List<JobInfo> jobs) {
        tableModel.setRowCount(0);
        for (JobInfo j : jobs) {
            tableModel.addRow(new Object[]{
                    j.getId(),
                    j.getJobType(),
                    j.getJobDesc(),
                    j.getJobRequirement(),
                    j.getSalary(),
                    j.getMemo()
            });
        }
    }

    public void clearForm() {
        jobTypeField.setText("");
        jobDescArea.setText("");
        jobReqArea.setText("");
        salaryField.setText("");
        memoField.setText("");
    }

    public void setOnAdd(Consumer<JobInfo> onAdd) {
        this.onAdd = onAdd;
    }

    public void setOnDelete(Consumer<Long> onDelete) {
        this.onDelete = onDelete;
    }
}