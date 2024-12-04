package project.cs3360.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AuthenticationView extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the main background
        Pane backgroundPane = new Pane();
        backgroundPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #FFB6C1, #FF8C00, #9370DB);");

        // Create a semi-transparent foreground
        VBox foregroundBox = new VBox();
        foregroundBox.setAlignment(Pos.CENTER);
        foregroundBox.setPadding(new Insets(20));
        foregroundBox.setSpacing(10);
        foregroundBox.setStyle("-fx-background-color: rgba(50, 50, 50, 0.8); -fx-background-radius: 15;");
        foregroundBox.setPrefWidth(400);
        foregroundBox.setPrefHeight(300);

        // Create a TabPane for Login and Register
        TabPane tabPane = new TabPane();

        // Login Tab
        Tab loginTab = new Tab("Login");
        loginTab.setClosable(false);
        loginTab.setContent(createLoginForm());

        // Register Tab
        Tab registerTab = new Tab("Register");
        registerTab.setClosable(false);
        registerTab.setContent(createRegisterForm());

        tabPane.getTabs().addAll(loginTab, registerTab);

        foregroundBox.getChildren().add(tabPane);

        // Center the foreground box
        StackPane root = new StackPane(backgroundPane, foregroundBox);
        StackPane.setAlignment(foregroundBox, Pos.CENTER);

        // Set the scene
        Scene scene = new Scene(root, 1080, 720);
        primaryStage.setTitle("Login/Register Form");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to create the login form
    private VBox createLoginForm() {
        VBox loginForm = new VBox();
        loginForm.setSpacing(10);
        loginForm.setAlignment(Pos.CENTER);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #9370DB; -fx-text-fill: white;");

        loginForm.getChildren().addAll(usernameField, passwordField, loginButton);

        return loginForm;
    }

    // Method to create the register form
    private VBox createRegisterForm() {
        VBox registerForm = new VBox();
        registerForm.setSpacing(10);
        registerForm.setAlignment(Pos.CENTER);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");

        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: #9370DB; -fx-text-fill: white;");

        registerForm.getChildren().addAll(usernameField, passwordField, confirmPasswordField, registerButton);

        return registerForm;
    }

    public static void main(String[] args) {
        launch(args);
    }
}