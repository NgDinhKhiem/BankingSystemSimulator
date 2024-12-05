package project.cs3360;

import project.cs3360.ui.AuthenticationForm;

import javax.swing.*;

public class Client {
    private final String sectionToken = "";

    public static void main(String[] args){
        JFrame frame = new AuthenticationForm();
        frame.setVisible(true);
    }
}
