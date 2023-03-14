package gui.employee;

import application.TicketSystem;
import application.Window;
import database.classes.User;
import gui.SwitchWindowActionListener;
import gui.Utill;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Users extends JPanel {
    private TicketSystem ticketSystem;
    private ArrayList<User> users;
    private static final String[] columnsHeader = {"Имя", "Фамилия", "Отчество", "Роль"};
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton deleteButton;
    private JButton changeButton;
    private JTextField nameField;
    private JTextField familiaField;
    private JTextField otchestvoField;
    private JComboBox roleBox;
    private JButton backButton;

    public Users(TicketSystem ticketSystem){
        this.ticketSystem = ticketSystem;

        this.setLayout(new GridBagLayout());
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };
        tableModel.setColumnIdentifiers(columnsHeader);
        table = new JTable(tableModel);
        JScrollPane jScrollPane = new JScrollPane(table);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        changeButton = new JButton("Изменить");
        deleteButton = new JButton("Удалить");
        nameField = new JTextField();
        familiaField = new JTextField();
        otchestvoField = new JTextField();
        roleBox = new JComboBox(new String[]{"Пользователь", "Сотрудник", "Администратор"});
        backButton = new JButton("Вернуться");

        changeButton.addActionListener(new ChangeUserActionListener());
        deleteButton.addActionListener(new DeleteUserActionListener());
        backButton.addActionListener(new SwitchWindowActionListener(ticketSystem, Window.ADMIN_MENU));

        fillUsers();

        GridBagConstraints gbc = Utill.createGridBagConstrains(0, 0, 0, 0, 8, 11, 0, 0, 0);
        this.add(jScrollPane, gbc);

        gbc = Utill.createGridBagConstrains(0, 0, 8, 0, 1, 1, 0, 0, 2);
        this.add(Box.createHorizontalStrut(20),gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 0, 2, 1, 0, 0, 2);
        this.add(changeButton, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 1, 2, 1, 0, 0, 2);
        this.add(deleteButton, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 2, 2, 1, 0, 0, 2);
        this.add(new JLabel("Имя"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 3, 2, 1, 0, 0, 2);
        this.add(nameField, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 4, 2, 1, 0, 0, 2);
        this.add(new JLabel("Фамилия"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 5, 2, 1, 0, 0, 2);
        this.add(familiaField, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 6, 2, 1, 0, 0, 2);
        this.add(new JLabel("Отчество"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 7, 2, 1, 0, 0, 2);
        this.add(otchestvoField, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 8, 2, 1, 0, 0, 2);
        this.add(new JLabel("Роль"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 9, 2, 1, 0, 0, 2);
        this.add(roleBox, gbc);

        gbc = Utill.createGridBagConstrains(0, 0, 11, 0, 2, 1, 0, 0, 2);
        this.add(Box.createHorizontalStrut(20),gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 13, 0, 2, 1, 0, 0, 2);
        this.add(backButton, gbc);
    }

    private void fillUsers(){
        users = ticketSystem.getDatabase().getUsers();
        for (User user : users){
            tableModel.addRow(new Object[]{user.getName(), user.getFamilia(), user.getOtchestvo(), Utill.getRoleName(user.getRole())});
        }
    }

    class ChangeUserActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (table.getSelectedRows().length == 0){
                JOptionPane.showMessageDialog(Users.this, "Выделите строчку для редактирования пользователя.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (table.getSelectedRows().length > 1){
                JOptionPane.showMessageDialog(Users.this, "Выделите только одну строчку для редактирования пользователя.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int selecterRow = table.getSelectedRows()[0];
            User lu = users.get(selecterRow);
            if (lu.getUser_id() == ticketSystem.getUser().getUser_id()) {
                JOptionPane.showMessageDialog(Users.this, "Невозможно редактировать свой аккаунт.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String name = nameField.getText();
            String familia = familiaField.getText();
            String otchestvo = otchestvoField.getText();
            if (name.length() == 0 || familia.length() == 0 || otchestvo.length() == 0){
                JOptionPane.showMessageDialog(Users.this, "Заполните все поля.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int role = roleBox.getSelectedIndex() + 1;
            User user = new User(lu.getUser_id(), familia, name, otchestvo,
                    lu.getLogin(), lu.getPassword(), role);
            if (ticketSystem.getDatabase().changeUser(user)){
                ticketSystem.switchWindow(Window.USERS);
            } else {
                JOptionPane.showMessageDialog(Users.this, "Ошибка при изменении пользователя.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
    class DeleteUserActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (table.getSelectedRows().length == 0){
                JOptionPane.showMessageDialog(Users.this, "Выделите строчку для удалении пользователя.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (table.getSelectedRows().length > 1){
                JOptionPane.showMessageDialog(Users.this, "Выделите только одну строчку для удаления пользователя.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int selecterRow = table.getSelectedRows()[0];
            User user = users.get(selecterRow);
            if (user.getUser_id() == ticketSystem.getUser().getUser_id()) {
                JOptionPane.showMessageDialog(Users.this, "Невозможно удалить свой аккаунт.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (ticketSystem.getDatabase().deleteUser(user)){
                ticketSystem.switchWindow(Window.USERS);
            } else {
                JOptionPane.showMessageDialog(Users.this, "Ошибка при удалении пользователя.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
