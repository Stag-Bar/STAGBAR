package edu.nku.CSC440.stagbar.ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import edu.nku.CSC440.stagbar.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class ChangePasswordUI {
    private JPanel contentPane;
    private JLabel errorMessage;
    private JTextField usernameTextField;
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private JLabel usernameLabel;
    private JLabel oldPasswordLabel;
    private JLabel newPasswordLabel;
    private JLabel confirmPasswordLabel;
    private JPasswordField confirmPasswordField;
    private JButton cancelButton;
    private JButton okButton;
    private UserService userService;

    public ChangePasswordUI() {
        userService = UserService.getInstance();

        contentPane.setName("Change Password");

        okButton.addActionListener(e -> onOK());
        cancelButton.addActionListener(e -> onCancel());
    }

    public JPanel getContentPane() {return contentPane;}
    private void highlightEmptyFields() {
        usernameLabel.setForeground(usernameTextField.getText().isEmpty() ?
                Color.RED : Color.BLACK);

        oldPasswordLabel.setForeground(0 == oldPasswordField.getPassword().length ?
                Color.RED : Color.BLACK);

        newPasswordLabel.setForeground(0 == oldPasswordField.getPassword().length ?
                Color.RED : Color.BLACK);

        confirmPasswordLabel.setForeground(0 == confirmPasswordField.getPassword().length ?
                Color.RED : Color.BLACK);

    }

    private void onCancel() {
        uiHacks.killMeThenGoToLastPage(contentPane);
    }

    private void onOK() {

    }
}
