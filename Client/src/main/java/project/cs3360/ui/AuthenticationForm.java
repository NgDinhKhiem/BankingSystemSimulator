package project.cs3360.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthenticationForm extends JFrame {
    private final int width;
    private final int height;

    public AuthenticationForm() {
        super("Login UI");
        this.width = 1280;
        this.height = 832;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(width, height);
        this.setLayout(null);
        initialBackGround();
        addingLoginButton();
    }

    private void initialBackGround() {
        // Create the frame
        JFrame frame = new JFrame("Login Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new BorderLayout());

        // Create a panel for the login form
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add username label and text field
        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        panel.add(userLabel);
        panel.add(userField);

        // Add password label and password field
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        panel.add(passLabel);
        panel.add(passField);

        // Add login button
        JButton loginButton = new JButton("Login");
        panel.add(new JLabel()); // Empty space for alignment
        panel.add(loginButton);

        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());

                // Validate credentials
                if ("admin".equals(username) && "password".equals(password)) {
                    JOptionPane.showMessageDialog(frame, "Login Successful!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add the panel to the frame
        frame.add(panel, BorderLayout.CENTER);

        // Make the frame visible
        frame.setVisible(true);
    }

    private void addingLoginButton() {

    }
}
