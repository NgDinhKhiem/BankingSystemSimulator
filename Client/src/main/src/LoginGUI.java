import custom.PasswordFieldCustom;
import custom.TextFieldCustom;
import project.cs3360.object.RequestParameter;
import project.cs3360.response.AuthenticationResponse;
import project.cs3360.utils.HttpUtils;
import project.cs3360.utils.RequestBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoginGUI extends JFrame implements ActionListener {
    private TextFieldCustom usernameField;
    private PasswordFieldCustom passwordField;
    private Font customFont;
    private File file = new File("/home/xuankhai/Downloads/BankingSystemSimulator-master(1) (2)/BankingSystemSimulator-master/Client/src/UIapp/src/UserDB/UserDB.txt");
//    private Scanner userDB = new

    public LoginGUI() {
        super("Banking App Login");
        setSize(CommonConstants.FRAME_SIZE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(CommonConstants.PRIMARY_COLOR);

        //create custom font
        customFont = CustomTools.createFont(CommonConstants.FONT_PATH);
        addGuiComponents();
    }

    private void addGuiComponents(){
        //login image
        JLabel loginImage = CustomTools.loadImage(CommonConstants.LOGIN_IMAGE_PATH);
        loginImage.setBounds(
                (CommonConstants.FRAME_SIZE.width - loginImage.getPreferredSize().width)/2,
                25,
                CommonConstants.LOGIN_IMAGE_SIZE.width, CommonConstants.LOGIN_IMAGE_SIZE.height
        );

        //username field
        usernameField = new TextFieldCustom("Enter Username", 30);
        usernameField.setFont(customFont.deriveFont(32f));
        usernameField.setBackground(CommonConstants.SECONDARY_COLOR);
        usernameField.setBounds(
                50,
                loginImage.getY() + 315,
                CommonConstants.TEXTFIELD_SIZE.width, CommonConstants.TEXTFIELD_SIZE.height
        );

        //password field
        passwordField = new PasswordFieldCustom("Enter Password", 30);
        passwordField.setFont(customFont.deriveFont(32f));
        passwordField.setBackground(CommonConstants.SECONDARY_COLOR);
        passwordField.setBounds(
                50,
                usernameField.getY() + 100,
                CommonConstants.TEXTFIELD_SIZE.width, CommonConstants.TEXTFIELD_SIZE.height
        );

        //login button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(customFont.deriveFont(32f));
        loginButton.setBackground(CommonConstants.BUTTON_COLOR);
        loginButton.setBounds(
                50,
                passwordField.getY() + 100,
                CommonConstants.BUTTON_SIZE.width, CommonConstants.BUTTON_SIZE.height
        );
        loginButton.addActionListener(this);

        //login -> register label
        JLabel registerLabel = new JLabel("Not register? Click here!");
        registerLabel.setFont(customFont.deriveFont(32f));
        registerLabel.setForeground(CommonConstants.SECONDARY_COLOR);
        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerLabel.setBounds(
                (CommonConstants.FRAME_SIZE.width - registerLabel.getPreferredSize().width)/2,
                loginButton.getY() + 100,
                registerLabel.getPreferredSize().width, registerLabel.getPreferredSize().height
        );
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new RegisterGui().setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                registerLabel.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerLabel.setForeground(CommonConstants.SECONDARY_COLOR);
            }
        });

        //add to frame
        getContentPane().add(loginImage);
        getContentPane().add(usernameField);
        getContentPane().add(passwordField);
        getContentPane().add(loginButton);
        getContentPane().add(registerLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and Password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Perform API request for authentication
        RequestBuilder requestBuilder = new RequestBuilder("http://127.0.0.1:8080/auth");
        requestBuilder.add(
                new RequestParameter("ID", username),
                new RequestParameter("password", password)
        );

        // Asynchronous API call
        HttpUtils.sendGETRequest(requestBuilder.getRequest()).thenAccept(response -> {
            AuthenticationResponse authResponse = AuthenticationResponse.deserialize(response);

            if (authResponse.state) {
                // Login successful
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new BankingMenuGUI(username, authResponse.getToken()).setVisible(true);
                });
            } else {
                // Login failed
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(this, "Invalid Username or Password", "Login Failed", JOptionPane.ERROR_MESSAGE)
                );
            }
        }).exceptionally(ex -> {
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(this, "Error: Unable to connect to server", "Login Failed", JOptionPane.ERROR_MESSAGE)
            );
            return null;
        });
    }
}
