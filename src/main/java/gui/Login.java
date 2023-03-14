package gui;

import application.TicketSystem;
import application.Window;
import database.classes.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JPanel {
    TicketSystem ticketSystem;
    private JTextField loginField;
    private JTextField passwordField;
    private JButton loginButton;
    private JButton regButton;
    private JLabel statusLabel;

    public Login(TicketSystem ticketSystem) {
        this.ticketSystem = ticketSystem;

        this.setLayout(new GridBagLayout());
        this.loginField = new JTextField();
        this.passwordField = new JTextField();
        this.loginButton = new JButton("Войти");
        this.regButton = new JButton("Регистрация");
        this.statusLabel = new JLabel();
        this.statusLabel.setVerticalAlignment(SwingConstants.CENTER);

        this.loginButton.addActionListener(new LoginButtonActionListener());
        this.regButton.addActionListener(new SwitchWindowActionListener(ticketSystem, Window.REGISTRATION));

        GridBagConstraints gbc = Utill.createGridBagConstrains(0, 0, 0, 0, 2, 1, 10, 0, 2);
        this.add(new JLabel("Логин"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 2, 0, 2, 1, 100, 0, 2);
        this.add(loginField, gbc);

        gbc = Utill.createGridBagConstrains(0, 0, 0, 1, 2, 1, 10, 0, 2);
        this.add(new JLabel("Пароль"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 2, 1, 2, 1, 100, 0, 2);
        this.add(passwordField, gbc);

        gbc = Utill.createGridBagConstrains(0, 0, 0, 3, 4, 1, 0, 0, 2);
        this.add(loginButton, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 0, 4, 4, 1, 0, 0, 2);
        this.add(regButton, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 0, 5, 4, 1, 0, 0, 2);
        this.add(statusLabel, gbc);
    }

    public void updateStatusLabel(String status, Color color){
        statusLabel.setForeground(color);
        statusLabel.setText(status);
    }
    class LoginButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String login = loginField.getText();
            String password = passwordField.getText();
            if (login.length() == 0 || password.length() == 0){
                updateStatusLabel("Заполните все поля.", Color.red);
                return;
            }
            User user = ticketSystem.getDatabase().userAuthorization(login, password);
            if (user.getUser_id() == -1){
                updateStatusLabel("Неверный логин или пароль.", Color.red);
                return;
            }
            System.out.println(user.getUser_id());
            System.out.println(user.getLogin());
            System.out.println(user.getPassword());
            updateStatusLabel("Успешно.", Color.green);
            ticketSystem.setUser(user);
            Window window = switch (user.getRole()) {
                case 1 -> Window.USER_MENU;
                case 2 -> Window.EMPLOYEE_MENU;
                case 3 -> Window.ADMIN_MENU;
                default -> Window.LOGIN;
            };
            ticketSystem.switchWindow(window);

        }
    }
}
