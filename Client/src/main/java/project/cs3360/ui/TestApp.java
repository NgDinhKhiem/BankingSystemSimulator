package project.cs3360.ui;

import javax.swing.*;
import java.awt.*;

public class TestApp {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Login UI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLayout(null);

        // Background panel
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // Gradient background
                Color startColor = new Color(33, 43, 56);
                Color endColor = new Color(19, 24, 31);
                GradientPaint gradient = new GradientPaint(0, 0, startColor, 0, getHeight(), endColor);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setBounds(0, 0, 800, 400);
        backgroundPanel.setLayout(null);
        frame.add(backgroundPanel);

        // Title
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(550, 20, 200, 40);
        backgroundPanel.add(titleLabel);

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome onboard with us!");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        welcomeLabel.setForeground(Color.LIGHT_GRAY);
        welcomeLabel.setBounds(500, 70, 300, 30);
        backgroundPanel.add(welcomeLabel);

        // Username field
        JTextField usernameField = new JTextField("Enter your username");
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameField.setBounds(500, 120, 250, 40);
        backgroundPanel.add(usernameField);

        // Password field
        JPasswordField passwordField = new JPasswordField("Enter your password");
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBounds(500, 180, 250, 40);
        backgroundPanel.add(passwordField);

        // Login button
        JButton loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBounds(500, 240, 250, 40);
        loginButton.setBackground(new Color(70, 160, 200));
        loginButton.setForeground(Color.WHITE);
        backgroundPanel.add(loginButton);

        // Register link
        JLabel registerLabel = new JLabel("New to Login? Register Here");
        registerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        registerLabel.setForeground(Color.LIGHT_GRAY);
        registerLabel.setBounds(500, 290, 300, 30);
        backgroundPanel.add(registerLabel);

        // Show the frame
        frame.setVisible(true);
    }
}


