package gui;

import application.TicketSystem;
import application.Window;
import database.classes.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Registration extends JPanel {
    private TicketSystem ticketSystem;
    private JTextField loginField;
    private JTextField passwordField;
    private JTextField familyField;
    private JTextField nameField;
    private JTextField otchestvoField;
    private JButton regButton;
    private JButton backButton;
    private JLabel statusLabel;

    public Registration(TicketSystem ticketSystem){
        this.ticketSystem = ticketSystem;

        this.setLayout(new GridBagLayout());
        loginField = new JTextField();
        passwordField = new JTextField();
        familyField = new JTextField();
        nameField = new JTextField();
        otchestvoField = new JTextField();
        regButton = new JButton("Регистрация");
        statusLabel = new JLabel();
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        backButton = new JButton("Вернуться");

        regButton.addActionListener(new RegistrationActionListener());
        backButton.addActionListener(new SwitchWindowActionListener(ticketSystem, Window.LOGIN));

        GridBagConstraints gbc = Utill.createGridBagConstrains(0, 0, 0, 0, 2, 1, 25, 0, 2);
        this.add(new JLabel("Логин"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 2, 0, 2, 1, 100, 0, 2);
        this.add(loginField, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 0, 1, 2, 1, 25, 0, 2);
        this.add(new JLabel("Пароль"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 2, 1, 2, 1, 100, 0, 2);
        this.add(passwordField, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 0, 2, 2, 1, 25, 0, 2);
        this.add(new JLabel("Фамилия"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 2, 2, 2, 1, 100, 0, 2);
        this.add(familyField, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 0, 3, 2, 1, 25, 0, 2);
        this.add(new JLabel("Имя"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 2, 3, 2, 1, 100, 0, 2);
        this.add(nameField, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 0, 4, 2, 1, 25, 0, 2);
        this.add(new JLabel("Отчество"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 2, 4, 2, 1, 100, 0, 2);
        this.add(otchestvoField, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 0, 5, 4, 1, 0, 0, 2);
        this.add(regButton, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 0, 6, 4, 1, 0, 0, 2);
        this.add(statusLabel, gbc);

        gbc = Utill.createGridBagConstrains(0, 0, 4, 0, 2, 1, 0, 0, 2);
        this.add(Box.createHorizontalStrut(50),gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 6, 0, 1, 1, 0, 0, 2);
        this.add(backButton, gbc);
    }

    public void updateStatusLabel(String status, Color color){
        statusLabel.setForeground(color);
        statusLabel.setText(status);
    }

    class RegistrationActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String login = loginField.getText();
            String password = passwordField.getText();
            String family = familyField.getText();
            String name = nameField.getText();
            String otchestvo = otchestvoField.getText();
            if (login.length() == 0 || password.length() == 0 || family.length() == 0 || name.length() == 0 || otchestvo.length() == 0){
                updateStatusLabel("Заполните все поля.", Color.red);
                return;
            }
            if (ticketSystem.getDatabase().loginIsRegistered(login)){
                updateStatusLabel("Логин уже занят.", Color.red);
                return;
            } else {
                User user = new User(-1, family, name, otchestvo, login, password, 1);
                if (ticketSystem.getDatabase().accountRegistration(user)){
                    updateStatusLabel("Аккаунт успешно зарегистрирован.", Color.green);
                    return;
                } else {
                    updateStatusLabel("Ошибка при регистрации.", Color.red);
                    return;
                }
            }
        }
    }

}
