package project.cs3360.ui;

import javax.swing.*;
import java.awt.*;

public class TestApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create the main frame
            JFrame frame = new JFrame("Login and Register");
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

            // Card layout for animation
            JPanel cardPanel = new JPanel(new CardLayout());
            cardPanel.setBounds(150, 50, 500, 300);
            backgroundPanel.add(cardPanel);

            // Login panel
            JPanel loginPanel = new JPanel();
            loginPanel.setLayout(null);
            loginPanel.setBackground(new Color(0, 0, 0, 0)); // Transparent background

            JLabel loginTitle = new JLabel("Login");
            loginTitle.setFont(new Font("Arial", Font.BOLD, 30));
            loginTitle.setForeground(Color.WHITE);
            loginTitle.setBounds(200, 20, 200, 40);
            loginPanel.add(loginTitle);

            JTextField usernameField = new JTextField("Enter your username");
            usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
            usernameField.setBounds(125, 80, 250, 40);
            loginPanel.add(usernameField);

            JPasswordField passwordField = new JPasswordField("Enter your password");
            passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
            passwordField.setBounds(125, 140, 250, 40);
            loginPanel.add(passwordField);

            JButton loginButton = new JButton("LOGIN");
            loginButton.setFont(new Font("Arial", Font.BOLD, 16));
            loginButton.setBounds(125, 200, 250, 40);
            loginButton.setBackground(new Color(70, 160, 200));
            loginButton.setForeground(Color.WHITE);
            loginPanel.add(loginButton);

            // Register panel
            JPanel registerPanel = new JPanel();
            registerPanel.setLayout(null);
            registerPanel.setBackground(new Color(0, 0, 0, 0)); // Transparent background

            JLabel registerTitle = new JLabel("Register");
            registerTitle.setFont(new Font("Arial", Font.BOLD, 30));
            registerTitle.setForeground(Color.WHITE);
            registerTitle.setBounds(200, 20, 200, 40);
            registerPanel.add(registerTitle);

            JTextField registerUsername = new JTextField("Enter your username");
            registerUsername.setFont(new Font("Arial", Font.PLAIN, 16));
            registerUsername.setBounds(125, 80, 250, 40);
            registerPanel.add(registerUsername);

            JTextField emailField = new JTextField("Enter your email");
            emailField.setFont(new Font("Arial", Font.PLAIN, 16));
            emailField.setBounds(125, 140, 250, 40);
            registerPanel.add(emailField);

            JPasswordField registerPassword = new JPasswordField("Enter your password");
            registerPassword.setFont(new Font("Arial", Font.PLAIN, 16));
            registerPassword.setBounds(125, 200, 250, 40);
            registerPanel.add(registerPassword);

            JButton registerButton = new JButton("REGISTER");
            registerButton.setFont(new Font("Arial", Font.BOLD, 16));
            registerButton.setBounds(125, 260, 250, 40);
            registerButton.setBackground(new Color(70, 160, 200));
            registerButton.setForeground(Color.WHITE);
            registerPanel.add(registerButton);

            // Add panels to card layout
            cardPanel.add(loginPanel, "Login");
            cardPanel.add(registerPanel, "Register");

            // Tab buttons
            JButton loginTab = new JButton("Login");
            loginTab.setBounds(200, 10, 100, 30);
            loginTab.setForeground(Color.WHITE);
            loginTab.setBackground(new Color(70, 160, 200));
            backgroundPanel.add(loginTab);

            JButton registerTab = new JButton("Register");
            registerTab.setBounds(500, 10, 100, 30);
            registerTab.setForeground(Color.WHITE);
            registerTab.setBackground(new Color(70, 160, 200));
            backgroundPanel.add(registerTab);

            // Action listeners for tab buttons with animations
            loginTab.addActionListener(e -> {
                CardLayout cl = (CardLayout) cardPanel.getLayout();
                cl.show(cardPanel, "Login");
            });

            registerTab.addActionListener(e -> {
                CardLayout cl = (CardLayout) cardPanel.getLayout();
                cl.show(cardPanel, "Register");
            });

            // Show the frame
            frame.setVisible(true);
        });
    }
}


