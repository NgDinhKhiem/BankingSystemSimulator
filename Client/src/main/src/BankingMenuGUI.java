import custom.TextFieldCustom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankingMenuGUI extends JFrame implements ActionListener {
    private Font customFont;
    private String userID;
    private String token;

    
    public BankingMenuGUI() {
        super("Banking Menu");
        setSize(CommonConstants.FRAME_SIZE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(CommonConstants.PRIMARY_COLOR);

        customFont = CustomTools.createFont(CommonConstants.FONT_PATH);
        addGuiComponent();
    }

    public BankingMenuGUI(String userID, String token) {
        super("Banking Menu");
        this.userID = userID;
        this.token = token;
        setSize(CommonConstants.FRAME_SIZE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(CommonConstants.PRIMARY_COLOR);

        customFont = CustomTools.createFont(CommonConstants.FONT_PATH);
        addGuiComponent();
    }

    private void addGuiComponent(){
        //Button
        //User's Info Button
        JButton userInfoButton = new JButton("User's Information");
        userInfoButton.setFont(customFont.deriveFont(32f));
        userInfoButton.setBackground(CommonConstants.BUTTON_COLOR);
        userInfoButton.setBounds(
                50,
                100,
                CommonConstants.BUTTON_SIZE.width, CommonConstants.BUTTON_SIZE.height
        );
        userInfoButton.setActionCommand("USER_INFO");
        userInfoButton.addActionListener(this);
        //Check Balance Button
        JButton checkBalanceButton = new JButton("Balance Information");
        checkBalanceButton.setFont(customFont.deriveFont(32f));
        checkBalanceButton.setBackground(CommonConstants.BUTTON_COLOR);
        checkBalanceButton.setBounds(
                50,
                userInfoButton.getY() + 100,
                CommonConstants.BUTTON_SIZE.width, CommonConstants.BUTTON_SIZE.height
        );
        checkBalanceButton.setActionCommand("CHECK_BALANCE");
        checkBalanceButton.addActionListener(this);

        //Transfer Money Button
        JButton transferMoneyButton = new JButton("Balance Information");
        transferMoneyButton.setFont(customFont.deriveFont(32f));
        transferMoneyButton.setBackground(CommonConstants.BUTTON_COLOR);
        transferMoneyButton.setBounds(
                50,
                checkBalanceButton.getY() + 100,
                CommonConstants.BUTTON_SIZE.width, CommonConstants.BUTTON_SIZE.height
        );
        transferMoneyButton.setActionCommand("TRANSFER_MONEY");
        transferMoneyButton.addActionListener(this);

        getContentPane().add(userInfoButton);
        getContentPane().add(checkBalanceButton);
        getContentPane().add(transferMoneyButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        switch (actionCommand) {
            case "USER_INFO":
                System.out.println("UserInfoUI");
                new UserInfoUI();
                break;
            case "CHECK_BALANCE":
                new CheckBalanceUI();
                break;
            case "TRANSFER_MONEY":
                new TransferMoneyUI();
                break;
            default:
                System.out.println("Unknown Action Command: " + actionCommand);
        }
//
//        if (actionCommand.equals("USER_INFO")) {
//            System.out.println("USERINFO");
//        }
    }
}
