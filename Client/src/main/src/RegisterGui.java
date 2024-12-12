import custom.ErrorLabel;
import custom.PasswordFieldCustom;
import custom.TextFieldCustom;
import project.cs3360.object.RequestParameter;
import project.cs3360.response.RegistrationResponse;
import project.cs3360.utils.HttpUtils;
import project.cs3360.utils.RequestBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterGui extends JFrame implements ActionListener,FocusListener {
    private ErrorLabel firstnameErrorLabel, lastnameErrorLabel, passwordErrorLabel, phoneNumberErrorLabel, emailErrorLabel;
    private TextFieldCustom firstnameField,lastnameField,phoneNumberField, emailField;
    private PasswordFieldCustom passwordField;
    private Font customFont;
    public RegisterGui(){
        super("Banking App Register");
        setSize(CommonConstants.FRAME_SIZE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(CommonConstants.PRIMARY_COLOR);

        // create custom font
        customFont = CustomTools.createFont(CommonConstants.FONT_PATH);
        addGuiComponent();
    }

    private void addGuiComponent(){
        //register Label
        JLabel registerLabel = new JLabel("Register");
        registerLabel.setFont(customFont.deriveFont(68f));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registerLabel.setBounds(
                0,
                0,
                CommonConstants.REGISTER_LABEL_SIZE.width, CommonConstants.REGISTER_LABEL_SIZE.height
        );
        registerLabel.setForeground(CommonConstants.SECONDARY_COLOR);


        //firstname field
        firstnameField = new TextFieldCustom("Enter First Name",30);
        firstnameField.setFont(customFont.deriveFont(32f));
        firstnameField.setBackground(CommonConstants.SECONDARY_COLOR);
        firstnameField.setBounds(
                50,
                registerLabel.getY() + 150,
                CommonConstants.TEXTFIELD_SIZE.width, CommonConstants.TEXTFIELD_SIZE.height
        );
        firstnameField.addFocusListener(this);

        //username error label
        firstnameErrorLabel = new ErrorLabel("Invalid First: Can't be empty. Please try again!");
        firstnameErrorLabel.setFont(customFont.deriveFont(14f));
        firstnameErrorLabel.setBounds(
                50,
                firstnameField.getY()+50,
                CommonConstants.TEXTFIELD_SIZE.width, 25
        );
        //username field
        lastnameField = new TextFieldCustom("Enter Username",30);
        lastnameField.setFont(customFont.deriveFont(32f));
        lastnameField.setBackground(CommonConstants.SECONDARY_COLOR);
        lastnameField.setBounds(
                50,
                firstnameField.getY() + 100,
                CommonConstants.TEXTFIELD_SIZE.width, CommonConstants.TEXTFIELD_SIZE.height
        );
        lastnameField.addFocusListener(this);

        //username error label
        lastnameErrorLabel = new ErrorLabel("Invalid Last: Can't be empty. Please try again!");
        lastnameErrorLabel.setFont(customFont.deriveFont(14f));
        lastnameErrorLabel.setBounds(
                50,
                lastnameField.getY()+50,
                CommonConstants.TEXTFIELD_SIZE.width, 25
        );

        //password field
        passwordField = new PasswordFieldCustom("Enter Password",30);
        passwordField.setFont(customFont.deriveFont(32f));
        passwordField.setBackground(CommonConstants.SECONDARY_COLOR);
        passwordField.setBounds(
                50,
                lastnameField.getY() + 100,
                CommonConstants.TEXTFIELD_SIZE.width, CommonConstants.TEXTFIELD_SIZE.height
        );
        passwordField.addFocusListener(this);

        //password error label
        passwordErrorLabel = new ErrorLabel("Invalid password: size > 6, 1 Upper and Lower, 1 # and 1 special character");
        passwordErrorLabel.setFont(customFont.deriveFont(14f));
        passwordErrorLabel.setBounds(
                50,
                passwordField.getY() + 50,
                CommonConstants.TEXTFIELD_SIZE.width, 25
        );

        //phone number field
        phoneNumberField = new TextFieldCustom("Enter your phone number", 30);
        phoneNumberField.setFont(customFont.deriveFont(32f));
        phoneNumberField.setBackground(CommonConstants.SECONDARY_COLOR);
        phoneNumberField.setBounds(
                50,
                passwordField .getY() + 100,
                CommonConstants.TEXTFIELD_SIZE.width, CommonConstants.TEXTFIELD_SIZE.height
        );
        phoneNumberField.addFocusListener(this);

        //phone number error label
        phoneNumberErrorLabel = new ErrorLabel("Invalid: Phone Number can't be < 9 numbers");
        phoneNumberErrorLabel.setFont(customFont.deriveFont(14f));
        phoneNumberErrorLabel.setBounds(
                50,
                phoneNumberField.getY() + 50,
                CommonConstants.TEXTFIELD_SIZE.width, 25
        );

        //email field
        emailField = new TextFieldCustom("Enter email",30);
        emailField.setFont(customFont.deriveFont(32f));
        emailField.setBackground(CommonConstants.SECONDARY_COLOR);
        emailField.setBounds(
                50,
                phoneNumberField.getY() + 100,
                CommonConstants.TEXTFIELD_SIZE.width, CommonConstants.TEXTFIELD_SIZE.height
        );
        emailField.addFocusListener(this);

        //email error label
        emailErrorLabel = new ErrorLabel("Invalid: not valid format");
        emailErrorLabel.setFont(customFont.deriveFont(14f));
        emailErrorLabel.setBounds(
                50,
                emailField.getY() + 50,
                CommonConstants.TEXTFIELD_SIZE.width, 25
        );

        //Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setFont(customFont.deriveFont(32f));
        registerButton.setBackground(CommonConstants.BUTTON_COLOR);
        registerButton.setBounds(
                50,
                emailField.getY() + 100,
                CommonConstants.TEXTFIELD_SIZE.width, CommonConstants.TEXTFIELD_SIZE.height
        );
        registerButton.addActionListener(this);

        //register -> login label
        JLabel loginLabel = new JLabel("Already a user? Login Here");
        loginLabel.setFont(customFont.deriveFont(32f));
        loginLabel.setForeground(CommonConstants.SECONDARY_COLOR);
        loginLabel.setBounds(
                (CommonConstants.FRAME_SIZE.width - loginLabel.getPreferredSize().width)/2,
                registerButton.getY() + 100,
                loginLabel.getPreferredSize().width,loginLabel.getPreferredSize().height

        );
        loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new LoginGUI().setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                loginLabel.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginLabel.setForeground(CommonConstants.SECONDARY_COLOR);
            }
        });

        //add to frame
        getContentPane().add(registerLabel);

        //firstname
        getContentPane().add(firstnameField);
        getContentPane().add(firstnameErrorLabel);

        //lastname
        getContentPane().add(lastnameField);
        getContentPane().add(lastnameErrorLabel);

        //password
        getContentPane().add(passwordField);
        getContentPane().add(passwordErrorLabel);
        
        //confirm password
        getContentPane().add(phoneNumberField);
        getContentPane().add(phoneNumberErrorLabel);

        //email
        getContentPane().add(emailField);
        getContentPane().add(emailErrorLabel);
        
        getContentPane().add(loginLabel);
        getContentPane().add(registerButton);
    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {
        Object fieldSource = e.getSource();
        if (fieldSource == firstnameField) {
            //Valid username has to be >= 6
            if (firstnameField.getText().isEmpty() || firstnameField.isHasPlaceHolder()){
                firstnameErrorLabel.setVisible(true);
            }else{
                firstnameErrorLabel.setVisible(false);
            }

        }
        if (fieldSource == lastnameField) {
            //Valid username has to be >= 6
            if (lastnameField.getText().isEmpty() || lastnameField.isHasPlaceHolder()){
                lastnameErrorLabel.setVisible(true);
            }else{
                lastnameErrorLabel.setVisible(false);
            }

        }
        else if (fieldSource == passwordField){
            // if password isn't 6 characters long, has 1 uppercase and lowercase, and a special character it is invalid
            String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*(),.?:{}|<>])(?=\\S+$).{8,}$";
            Pattern p = Pattern.compile(passwordRegex);
            Matcher m = p.matcher(passwordField.getText());
            if (!m.find()) {
                passwordErrorLabel.setVisible(true);
            }
            else{
                passwordErrorLabel.setVisible(false);
            }
        }
        else if (fieldSource == phoneNumberField){
            //if pw does not match then it is invalid
            if (phoneNumberField.getText().length() < 10 || phoneNumberField.isHasPlaceHolder()){
                phoneNumberErrorLabel.setVisible(true);
            }
            else{
                phoneNumberErrorLabel.setVisible(false);
            }
        }
        else if (fieldSource == emailField){
            //check email if it has valid format
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            Pattern p = Pattern.compile(emailRegex);
            Matcher m = p.matcher(emailField.getText());
            if (!m.find()) {
                emailErrorLabel.setVisible(true);
            }else{
                emailErrorLabel.setVisible(false);
            }

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Register")){
            //validate that there are no error label and not any left placeholder state
            boolean isValid = !firstnameErrorLabel.isVisible() && !lastnameErrorLabel.isVisible() && !passwordErrorLabel.isVisible() && !phoneNumberErrorLabel.isVisible()
                    && !emailErrorLabel.isVisible() && !firstnameField.isHasPlaceHolder() && !lastnameField.isHasPlaceHolder() && !passwordField.isHasPlaceHolder() &&
                    !phoneNumberField.isHasPlaceHolder() && !emailField.isHasPlaceHolder();
            //result Dialog
            JDialog resultDialog = new JDialog();
            resultDialog.setSize(CommonConstants.RESULT_DIALOG_SIZE);
            resultDialog.setLocationRelativeTo(this);
            resultDialog.setModal(true);
            resultDialog.getContentPane().setBackground(CommonConstants.PRIMARY_COLOR);

            //result Label
            JLabel resultLabel = new JLabel();
            resultLabel.setFont(customFont.deriveFont(22f));
            resultLabel.setForeground(CommonConstants.SECONDARY_COLOR);
            resultLabel.setPreferredSize(CommonConstants.RESULT_DIALOG_SIZE);
            resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
            resultDialog.add(resultLabel);

            if (isValid) {
                System.out.println("Test ok");
                // Collect user information
                String firstName = firstnameField.getText();
                String lastName = lastnameField.getText();
                String password = passwordField.getText();
                String email = emailField.getText();
                String phoneNumber = phoneNumberField.getText();
                // Build API request
                RequestBuilder requestBuilder = new RequestBuilder("http://127.0.0.1:8080/register");
                requestBuilder.add(
                        new RequestParameter("firstName", firstName),
                        new RequestParameter("lastName", lastName),
                        new RequestParameter("phoneNumber", phoneNumber),
                        new RequestParameter("email", email),
                        new RequestParameter("password", password)
                        );

                // Send API request
                HttpUtils.sendGETRequest(requestBuilder.getRequest()).thenAccept(response -> {
                    RegistrationResponse registrationResponse = RegistrationResponse.deserialize(response);
                    SwingUtilities.invokeLater(() -> {
                        if (registrationResponse.state) {
                            // Show success dialog
                            resultLabel.setText("Account Registered! Your ID: " + registrationResponse.getID());
                            resultDialog.setVisible(true);
                            // Navigate to LoginGUI after dialog closes
                            resultDialog.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosing(WindowEvent e) {
                                    dispose(); // Dispose the RegisterGui frame
                                    new LoginGUI().setVisible(true);
                                }
//                                @Override
//                                public void windowClosing(WindowEvent e) {
//                                    resultDialog.dispose(); // Ensure dialog is properly disposed
//                                }
                            });
                        } else {
                            // Show failure dialog
                            resultLabel.setText("Registration Failed! ");
                            resultDialog.setVisible(true);
                        }
                    });
                }).exceptionally(ex -> {
                    SwingUtilities.invokeLater(() -> {
                        resultLabel.setText("Error: Unable to connect to server.");
                        resultDialog.setVisible(true);
                    });
                    return null;
                });
            } else {
                // Show error message if validation failed
                resultLabel.setText("Invalid Credentials. Please correct errors and try again.");
                resultDialog.setVisible(true);
            }
        }
    }
}
