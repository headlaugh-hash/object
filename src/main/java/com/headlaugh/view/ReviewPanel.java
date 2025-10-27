package com.headlaugh.view;

import com.headlaugh.model.ApplyInfo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class ReviewPanel extends JPanel {
    private DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"ID", "职位ID", "用户ID", "状态", "申请时间", "简历"}, 0) {
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    };
    private JTable table = new JTable(tableModel);
    private JTextArea detailArea = new JTextArea(8, 50);

    private Consumer<Long> onApprove;
    private Consumer<Long> onReject;

    public ReviewPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // 表格面板
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "申请列表",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("微软雅黑", Font.BOLD, 14)
        ));

        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 12));
        table.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // 设置状态列的颜色渲染
        table.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected && value != null) {
                    String status = value.toString();
                    if ("APPROVED".equals(status)) {
                        c.setForeground(new Color(34, 139, 34));
                    } else if ("REJECTED".equals(status)) {
                        c.setForeground(Color.RED);
                    } else if ("PENDING".equals(status)) {
                        c.setForeground(new Color(255, 140, 0));
                    }
                }
                return c;
            }
        });

        // 添加选择监听器
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    Object resume = tableModel.getValueAt(row, 5);
                    detailArea.setText(resume != null ? resume.toString() : "");
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(0, 250));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);

        // 详情和按钮面板
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));

        // 详情面板
        JPanel detailPanel = new JPanel(new BorderLayout());
        detailPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "简历详情",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("微软雅黑", Font.BOLD, 14)
        ));

        detailArea.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        detailArea.setLineWrap(true);
        detailArea.setWrapStyleWord(true);
        detailArea.setEditable(false);
        detailArea.setBackground(new Color(245, 245, 245));
        JScrollPane detailScroll = new JScrollPane(detailArea);
        detailPanel.add(detailScroll, BorderLayout.CENTER);

        bottomPanel.add(detailPanel, BorderLayout.CENTER);

        // 按钮面板
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));

        JButton approveBtn = new JButton("✓ 通过");
        approveBtn.setFont(new Font("微软雅黑", Font.BOLD, 13));
        approveBtn.setPreferredSize(new Dimension(120, 35));
        approveBtn.setBackground(new Color(60, 179, 113));
        approveBtn.setForeground(Color.WHITE);
        approveBtn.setFocusPainted(false);
        approveBtn.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r >= 0 && onApprove != null) {
                Object statusObj = tableModel.getValueAt(r, 3);
                String status = statusObj != null ? statusObj.toString() : "";

                if ("APPROVED".equals(status)) {
                    JOptionPane.showMessageDialog(this, "该申请已经通过", "提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "确定通过该申请吗?",
                        "确认通过",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    Object val = tableModel.getValueAt(r, 0);
                    Long id = val instanceof Long ? (Long) val : ((Number) val).longValue();
                    onApprove.accept(id);
                }
            } else {
                JOptionPane.showMessageDialog(this, "请先选择要审核的申请", "提示", JOptionPane.WARNING_MESSAGE);
            }
        });

        JButton rejectBtn = new JButton("✗ 拒绝");
        rejectBtn.setFont(new Font("微软雅黑", Font.BOLD, 13));
        rejectBtn.setPreferredSize(new Dimension(120, 35));
        rejectBtn.setBackground(new Color(220, 20, 60));
        rejectBtn.setForeground(Color.WHITE);
        rejectBtn.setFocusPainted(false);
        rejectBtn.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r >= 0 && onReject != null) {
                Object statusObj = tableModel.getValueAt(r, 3);
                String status = statusObj != null ? statusObj.toString() : "";

                if ("REJECTED".equals(status)) {
                    JOptionPane.showMessageDialog(this, "该申请已经被拒绝", "提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "确定拒绝该申请吗?",
                        "确认拒绝",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    Object val = tableModel.getValueAt(r, 0);
                    Long id = val instanceof Long ? (Long) val : ((Number) val).longValue();
                    onReject.accept(id);
                }
            } else {
                JOptionPane.showMessageDialog(this, "请先选择要审核的申请", "提示", JOptionPane.WARNING_MESSAGE);
            }
        });

        JButton refreshBtn = new JButton("刷新");
        refreshBtn.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        refreshBtn.setPreferredSize(new Dimension(120, 35));

        btnPanel.add(approveBtn);
        btnPanel.add(rejectBtn);

        bottomPanel.add(btnPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void setApplicationList(List<ApplyInfo> apps) {
        tableModel.setRowCount(0);
        for (ApplyInfo a : apps) {
            tableModel.addRow(new Object[]{
                    a.getId(),
                    a.getJobId(),
                    a.getUserId(),
                    a.getStatus(),
                    a.getApplyTime(),
                    a.getResume()
            });
        }
        // 清空详情区
        detailArea.setText("");
    }

    public void setOnApprove(Consumer<Long> onApprove) {
        this.onApprove = onApprove;
    }

    public void setOnReject(Consumer<Long> onReject) {
        this.onReject = onReject;
    }
}