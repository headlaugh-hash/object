package com.headlaugh.view;

import com.headlaugh.view.LoginFrame;

import javax.swing.*;

/**
 * 程序入口
 */
public class AppLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame login = new LoginFrame();
            login.setVisible(true);
        });
    }
}