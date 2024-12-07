package project.cs3360;

import project.cs3360.object.RequestParameter;
import project.cs3360.object.Response;
import project.cs3360.response.AccountInformationResponse;
import project.cs3360.response.AuthenticationResponse;
import project.cs3360.response.RegistrationResponse;
import project.cs3360.utils.HttpUtils;
import project.cs3360.utils.RequestBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Client {
    private String sectionToken = "";
    private String ID;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            Client client = new Client();
            client.start();
        });
    }

    private void start() {
        JFrame frame = new JFrame();
        frame.setSize(1200, 800);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the gray block panel
        JPanel grayBlock = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(0, 0, 0, 200)); // RGBA: 0,0,0 with 80% opacity
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        grayBlock.setBounds(200, 100, 800, 500); // Adjust size and position
        grayBlock.setOpaque(false);
        grayBlock.setLayout(null); // Allow manual positioning

        JLabel announcementLabel = new JLabel();
        announcementLabel.setFont(new Font("Arial", Font.BOLD, 10));
        announcementLabel.setBounds(150, 80, 1000, 50);

        // Create Login layout components
        JLabel loginTitle = new JLabel("LOGIN");
        loginTitle.setForeground(Color.WHITE);
        loginTitle.setFont(new Font("Arial", Font.BOLD, 24));
        loginTitle.setBounds(300, 50, 100, 50);
        grayBlock.add(loginTitle);

        JLabel registerTitle = new JLabel("REGISTER");
        registerTitle.setForeground(Color.WHITE);
        registerTitle.setFont(new Font("Arial", Font.BOLD, 24));
        registerTitle.setBounds(450, 50, 150, 50);
        grayBlock.add(registerTitle);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBounds(200, 150, 100, 25);
        grayBlock.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(300, 150, 200, 25);
        grayBlock.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(200, 200, 100, 25);
        grayBlock.add(passwordLabel);

        JTextField passwordField = new JTextField();
        passwordField.setBounds(300, 200, 200, 25);
        grayBlock.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(300, 260, 100, 30);
        grayBlock.add(loginButton);



        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setForeground(Color.WHITE);
        firstNameLabel.setBounds(200, 150, 100, 25);

        JTextField firstNameField = new JTextField();
        firstNameField.setBounds(300, 150, 200, 25);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setForeground(Color.WHITE);
        lastNameLabel.setBounds(200, 200, 100, 25);

        JTextField lastNameField = new JTextField();
        lastNameField.setBounds(300, 200, 200, 25);

        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberLabel.setForeground(Color.WHITE);
        phoneNumberLabel.setBounds(200, 250, 120, 25);

        JTextField phoneNumberField = new JTextField();
        phoneNumberField.setBounds(300, 250, 200, 25);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setBounds(200, 300, 100, 25);

        JTextField emailField = new JTextField();
        emailField.setBounds(300, 300, 200, 25);

        JLabel registerPasswordLabel = new JLabel("Password");
        registerPasswordLabel.setForeground(Color.WHITE);
        registerPasswordLabel.setBounds(200, 350, 100, 25);

        JTextField registerPasswordField = new JTextField();
        registerPasswordField.setBounds(300, 350, 200, 25);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(300, 400, 100, 30);
        // Register label functionality to switch layouts
        registerTitle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                grayBlock.removeAll(); // Clear the gray block panel
                grayBlock.add(loginTitle);
                grayBlock.add(registerTitle);
                grayBlock.add(firstNameLabel);
                grayBlock.add(lastNameLabel);
                grayBlock.add(phoneNumberLabel);
                grayBlock.add(emailLabel);
                grayBlock.add(firstNameField);
                grayBlock.add(lastNameField);
                grayBlock.add(phoneNumberField);
                grayBlock.add(emailField);
                grayBlock.add(registerButton);
                grayBlock.add(registerPasswordLabel);
                grayBlock.add(registerPasswordField);
                grayBlock.revalidate(); // Refresh the panel to apply changes
                grayBlock.repaint();
            }
        });

        loginTitle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                grayBlock.removeAll(); // Clear the gray block panel
                grayBlock.add(loginTitle);
                grayBlock.add(registerTitle);
                grayBlock.add(usernameField);
                grayBlock.add(passwordField);
                grayBlock.add(usernameLabel);
                grayBlock.add(passwordLabel);
                grayBlock.add(loginButton);
                grayBlock.revalidate(); // Refresh the panel to apply changes
                grayBlock.repaint();
            }
        });

        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(usernameField.getText()==null||usernameField.getText().isEmpty()){
                    announcementLabel.setText("Username is empty");
                    grayBlock.add(announcementLabel);
                    grayBlock.revalidate();
                    grayBlock.repaint();
                    return;
                }
                if(passwordField.getText()==null||passwordField.getText().isEmpty()){
                    announcementLabel.setText("Password is empty");
                    grayBlock.add(announcementLabel);
                    grayBlock.revalidate();
                    grayBlock.repaint();
                    return;
                }
                RequestBuilder requestBuilder = new RequestBuilder("http://127.0.0.1:8080/auth");
                requestBuilder.add(
                        new RequestParameter("ID", usernameField.getText()),
                        new RequestParameter("password", passwordField.getText())
                );

                HttpUtils.sendGETRequest(requestBuilder.getRequest()).thenAccept(response -> {
                    System.out.println(response);
                    AuthenticationResponse registrationResponse = AuthenticationResponse.deserialize(response);
                    if(registrationResponse.state){
                        announcementLabel.setText("Login Successful");
                        grayBlock.add(announcementLabel);
                        grayBlock.revalidate();
                        grayBlock.repaint();
                        Client.this.ID = usernameField.getText();
                        Client.this.sectionToken = registrationResponse.token;
                        System.out.println("CALLED#2");
                        frame.dispose();
                        intoTheMainMenu();
                    }else {
                        announcementLabel.setText("Login Failed!");
                        grayBlock.add(announcementLabel);
                        grayBlock.revalidate();
                        grayBlock.repaint();
                    }
                });
            }
        });

        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(firstNameField.getText()==null||firstNameField.getText().isEmpty()){
                    announcementLabel.setText("First Name is empty");
                    grayBlock.add(announcementLabel);
                    grayBlock.revalidate();
                    grayBlock.repaint();
                    return;
                }
                if(lastNameField.getText()==null||lastNameField.getText().isEmpty()){
                    announcementLabel.setText("Last Name is empty");
                    grayBlock.add(announcementLabel);
                    grayBlock.revalidate();
                    grayBlock.repaint();
                    return;
                }

                if(phoneNumberField.getText()==null||phoneNumberField.getText().isEmpty()){
                    announcementLabel.setText("Phone Number is empty");
                    grayBlock.add(announcementLabel);
                    grayBlock.revalidate();
                    grayBlock.repaint();
                    return;
                }

                if(emailField.getText()==null||emailField.getText().isEmpty()){
                    announcementLabel.setText("Email is empty");
                    grayBlock.add(announcementLabel);
                    grayBlock.revalidate();
                    grayBlock.repaint();
                    return;
                }

                if(registerPasswordField.getText()==null||registerPasswordField.getText().isEmpty()){
                    announcementLabel.setText("Password is empty");
                    grayBlock.add(announcementLabel);
                    grayBlock.revalidate();
                    grayBlock.repaint();
                    return;
                }

                RequestBuilder requestBuilder = new RequestBuilder("http://127.0.0.1:8080/register");
                requestBuilder.add(
                        new RequestParameter("firstName", firstNameField.getText()),
                        new RequestParameter("lastName", lastNameField.getText()),
                        new RequestParameter("phoneNumber", phoneNumberField.getText()),
                        new RequestParameter("email", emailField.getText()),
                        new RequestParameter("password", registerPasswordField.getText())
                );
                HttpUtils.sendGETRequest(requestBuilder.getRequest()).thenAccept(response -> {
                    RegistrationResponse registrationResponse = RegistrationResponse.deserialize(response);
                    if(registrationResponse.state){
                        announcementLabel.setText("Registered Successfully Here is your ID: "+registrationResponse.ID);
                        grayBlock.add(announcementLabel);
                        grayBlock.revalidate();
                        grayBlock.repaint();
                    }else {
                        announcementLabel.setText("Registration Failed!");
                        grayBlock.add(announcementLabel);
                        grayBlock.revalidate();
                        grayBlock.repaint();
                    }
                });
            }
        });

        // Add gray block to the JFrame
        frame.add(grayBlock);

        // Make the frame visible
        frame.setVisible(true);


    }

    private void intoTheMainMenu() {
        JFrame frame = new JFrame("User Information UI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);

        // Create a tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Create a panel for user details
        JPanel userDetailsPanel = new JPanel();
        userDetailsPanel.setLayout(new GridLayout(4, 2, 10, 10));

        // Add user details labels and text fields
        userDetailsPanel.add(new JLabel("User ID:"));
        JLabel userID = new JLabel();
        userDetailsPanel.add(userID);
        userDetailsPanel.add(new JLabel("Name:"));
        JLabel userName = new JLabel();
        userDetailsPanel.add(userName);
        userDetailsPanel.add(new JLabel("Email:"));
        JLabel userEmail = new JLabel();
        userDetailsPanel.add(userEmail);
        userDetailsPanel.add(new JLabel("Phone Number:"));
        JLabel userPhone = new JLabel();
        userDetailsPanel.add(userPhone);

        // Add the user details panel to the tabbed pane
        tabbedPane.addTab("User Details", userDetailsPanel);

        // Create a panel for user balance
        JPanel balancePanel = new JPanel();
        balancePanel.setLayout(new BorderLayout());

        // Add a label for balance display
        JLabel balanceLabel = new JLabel("User Balance: $0.00", SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        balancePanel.add(balanceLabel, BorderLayout.CENTER);

        // Add the tabbed pane and balance panel to the frame
        frame.setLayout(new BorderLayout());
        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.add(balancePanel, BorderLayout.SOUTH);

        RequestBuilder requestBuilder = new RequestBuilder("http://127.0.0.1:8080/info");
        requestBuilder.add(
                new RequestParameter("ID", ID),
                new RequestParameter("token", sectionToken)
        );
        HttpUtils.sendGETRequest(requestBuilder.getRequest()).thenAccept(response -> {
            AccountInformationResponse registrationResponse = AccountInformationResponse.deserialize(response);
            if(registrationResponse.state){
                userID.setText(registrationResponse.ID);
                userName.setText(registrationResponse.lastName+" "+registrationResponse.firstName);
                userEmail.setText(registrationResponse.email);
                userPhone.setText(registrationResponse.phoneNumber);
                frame.repaint();
                frame.revalidate();
            }else {
                frame.dispose();
                start();
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }
}