package gui.employee;

import application.TicketSystem;
import application.Window;
import database.classes.Doljnost;
import gui.SwitchWindowActionListener;
import gui.Utill;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class JobTitle extends JPanel {
    private TicketSystem ticketSystem;
    private ArrayList<Doljnost> doljnosts;
    private static final String[] columnsHeader = {"Наименование должности", "Оклад"};
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton changeButton;
    private JButton deleteButton;
    private JTextField jobTitleField;
    private JTextField okladField;
    private JButton backButton;

    public JobTitle(TicketSystem ticketSystem){
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

        addButton = new JButton("Добавить");
        changeButton = new JButton("Изменить");
        deleteButton = new JButton("Удалить");
        jobTitleField = new JTextField();
        okladField = new JTextField();
        backButton = new JButton("Вернуться");

        addButton.addActionListener(new AddDoljnostActionListener());
        changeButton.addActionListener(new ChangeDoljnostActionListener());
        deleteButton.addActionListener(new DeleteDoljnostActionListener());
        application.Window window = switch (ticketSystem.getUser().getRole()){
            case 2 -> application.Window.EMPLOYEE_MENU;
            case 3 -> application.Window.ADMIN_MENU;
            default -> Window.LOGIN;
        };
        backButton.addActionListener(new SwitchWindowActionListener(ticketSystem, window));

        fillDoljnosts();

        GridBagConstraints gbc = Utill.createGridBagConstrains(0, 0, 0, 0, 8, 10, 0, 0, 0);
        this.add(jScrollPane, gbc);

        gbc = Utill.createGridBagConstrains(0, 0, 8, 0, 1, 1, 0, 0, 2);
        this.add(Box.createHorizontalStrut(20),gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 0, 2, 1, 50, 0, 2);
        this.add(addButton, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 1, 2, 1, 50, 0, 2);
        this.add(changeButton, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 2, 2, 1, 50, 0, 2);
        this.add(deleteButton, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 3, 2, 1, 50, 0, 2);
        this.add(new JLabel("Должность"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 4, 2, 1, 50, 0, 2);
        this.add(jobTitleField, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 5, 2, 1, 50, 0, 2);
        this.add(new JLabel("Оклад"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 6, 2, 1, 50, 0, 2);
        this.add(okladField, gbc);


        gbc = Utill.createGridBagConstrains(0, 0, 11, 0, 2, 1, 0, 0, 2);
        this.add(Box.createHorizontalStrut(20),gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 13, 0, 2, 1, 0, 0, 2);
        this.add(backButton, gbc);
    }

    private void fillDoljnosts(){
        doljnosts = ticketSystem.getDatabase().getAllDoljnosts();
        for (Doljnost doljnost : doljnosts){
            tableModel.addRow(new Object[]{doljnost.getName_doljnost(), doljnost.getOklad()});
        }
    }

    class AddDoljnostActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String jobTitle = jobTitleField.getText();
            String oklad = okladField.getText();
            if (jobTitle.length() == 0 || oklad.length() == 0 || !Utill.isNumberF(oklad)){
                JOptionPane.showMessageDialog(JobTitle.this, "Проверьте правильность полей.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Doljnost doljnost = new Doljnost();
            doljnost.setName_doljnost(jobTitle);
            doljnost.setOklad(Float.parseFloat(oklad));
            if (ticketSystem.getDatabase().addDoljnost(doljnost)){
                ticketSystem.switchWindow(Window.JOB_TITLE);
            } else {
                JOptionPane.showMessageDialog(JobTitle.this, "Ошибка при добавление должности.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
    class ChangeDoljnostActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (table.getSelectedRows().length == 0){
                JOptionPane.showMessageDialog(JobTitle.this, "Выделите строчку для редактирования.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (table.getSelectedRows().length > 1){
                JOptionPane.showMessageDialog(JobTitle.this, "Выделите только одну строчку для редактирования.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String jobTitle = jobTitleField.getText();
            String oklad = okladField.getText();
            if (jobTitle.length() == 0 || oklad.length() == 0 || !Utill.isNumberF(oklad)){
                JOptionPane.showMessageDialog(JobTitle.this, "Правильно заполните поля \"Должность\" и \"Оклад\" для редактирования.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int selectedRow = table.getSelectedRows()[0];
            Doljnost doljnost = new Doljnost(doljnosts.get(selectedRow).getId_doljnost(), jobTitle, Float.parseFloat(oklad));
            if (ticketSystem.getDatabase().changeDoljnost(doljnost)){
                ticketSystem.switchWindow(Window.JOB_TITLE);
            } else {
                JOptionPane.showMessageDialog(JobTitle.this, "Ошибка при изменении параметров должности.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }

    class DeleteDoljnostActionListener implements  ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (table.getSelectedRows().length == 0){
                JOptionPane.showMessageDialog(JobTitle.this, "Выделите строчку для удаления.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (table.getSelectedRows().length > 1){
                JOptionPane.showMessageDialog(JobTitle.this, "Выделите только одну строчку для удаления.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int selectedRow = table.getSelectedRows()[0];
            if (ticketSystem.getDatabase().deleteDoljnost(doljnosts.get(selectedRow))){
                ticketSystem.switchWindow(Window.JOB_TITLE);
            } else {
                JOptionPane.showMessageDialog(JobTitle.this, "Ошибка при удалении должности.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
