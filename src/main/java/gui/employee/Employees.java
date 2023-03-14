package gui.employee;

import application.TicketSystem;
import application.Window;
import database.classes.Doljnost;
import database.classes.Pol;
import database.classes.Sotrudnik;
import database.classes.Voice;
import gui.SwitchWindowActionListener;
import gui.Utill;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Employees extends JPanel{
    private TicketSystem ticketSystem;
    private ArrayList<Pol> pols;
    private ArrayList<Voice> voices;
    private ArrayList<Doljnost> doljnosts;
    private ArrayList<Sotrudnik> sotrudniks;
    private static final String[] columnsHeader = {"Фамилия", "Имя", "Отчество", "Пол", "Тембр голоса", "Оклад", "Должность"};
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton changeButton;
    private JButton deleteButton;
    private JTextField nameField;
    private JTextField familyField;
    private JTextField otchestvoField;
    private JComboBox sexBox;
    private JComboBox tembrBox;
    private JComboBox jobTitleBox;
    private JButton backButton;

    public Employees(TicketSystem ticketSystem){
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
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane jScrollPane = new JScrollPane(table);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        addButton = new JButton("Добавить");
        changeButton = new JButton("Изменить");
        deleteButton = new JButton("Удалить");
        nameField = new JTextField();
        familyField = new JTextField();
        otchestvoField = new JTextField();
        sexBox = new JComboBox();
        tembrBox = new JComboBox();
        jobTitleBox = new JComboBox();
        backButton = new JButton("Вернуться");

        addButton.addActionListener(new AddEmployeeActionListener());
        changeButton.addActionListener(new ChangeEmployeeActionListener());
        deleteButton.addActionListener(new DeleteEmployeeActionListener());
        Window window = switch (ticketSystem.getUser().getRole()){
            case 2 -> Window.EMPLOYEE_MENU;
            case 3 -> Window.ADMIN_MENU;
            default -> Window.LOGIN;
        };
        backButton.addActionListener(new SwitchWindowActionListener(ticketSystem, window));

        fillSexBox();
        fillTembrBox();
        fillJobTitleBox();
        fillEmployees();

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
        gbc = Utill.createGridBagConstrains(0, 0, 9, 3, 1, 1, 0, 0, 2);
        this.add(new JLabel("Имя"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 10, 3, 1, 1, 0, 0, 2);
        this.add(new JLabel("Пол"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 4, 1, 1, 0, 5, 2);
        this.add(nameField, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 10, 4, 1, 1, 0, 0, 2);
        this.add(sexBox, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 5, 1, 1, 0, 0, 2);
        this.add(new JLabel("Фамилия"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 10, 5, 1, 1, 0, 0, 2);
        this.add(new JLabel("Тембр голоса"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 6, 1, 1, 0, 5, 2);
        this.add(familyField, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 10, 6, 1, 1, 0, 0, 2);
        this.add(tembrBox, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 7, 1, 1, 0, 0, 2);
        this.add(new JLabel("Отчество"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 10, 7, 1, 1, 0, 0, 2);
        this.add(new JLabel("Должность"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 8, 1, 1, 0, 5, 2);
        this.add(otchestvoField, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 10, 8, 1, 1, 0, 0, 2);
        this.add(jobTitleBox, gbc);

        gbc = Utill.createGridBagConstrains(0, 0, 11, 0, 2, 1, 0, 0, 2);
        this.add(Box.createHorizontalStrut(20),gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 13, 0, 2, 1, 0, 0, 2);
        this.add(backButton, gbc);
    }

    private void fillSexBox(){
        pols = ticketSystem.getDatabase().getAllPol();
        for (Pol pol : pols){
            sexBox.addItem(pol.getName_pol());
        }
    }

    private void fillTembrBox(){
        voices = ticketSystem.getDatabase().getAllVoices();
        for (Voice voice : voices){
            tembrBox.addItem(voice.getTembr_voice());
        }
    }

    private void fillJobTitleBox(){
        doljnosts = ticketSystem.getDatabase().getAllDoljnosts();
        for (Doljnost doljnost : doljnosts){
            jobTitleBox.addItem(doljnost.getName_doljnost());
        }
    }

    private void fillEmployees(){
        sotrudniks = ticketSystem.getDatabase().getAllSotrudniks();
        for (Sotrudnik sotrudnik : sotrudniks){
            String tembr = ticketSystem.getDatabase().getVoice(sotrudnik.getVoice_id()).getTembr_voice();
            String name_pol = ticketSystem.getDatabase().getPol(sotrudnik.getPol_id()).getName_pol();
            Doljnost doljnost = ticketSystem.getDatabase().getDoljnost(sotrudnik.getDoljnost_id());
            tableModel.addRow(new Object[]{sotrudnik.getFamilia(), sotrudnik.getName(),
                    sotrudnik.getOtchestvo(), name_pol, tembr, doljnost.getOklad(), doljnost.getName_doljnost()});
        }
    }

    class AddEmployeeActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String familia = familyField.getText();
            String otchestvo = otchestvoField.getText();
            if (name.length() == 0 || familia.length() == 0 || otchestvo.length() == 0){
                JOptionPane.showMessageDialog(Employees.this, "Заполните все поля.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int pol_id = pols.get(sexBox.getSelectedIndex()).getId_pol();
            int voice_id = voices.get(tembrBox.getSelectedIndex()).getVoice_id();
            int doljnost_id = doljnosts.get(jobTitleBox.getSelectedIndex()).getId_doljnost();
            Sotrudnik sotrudnik = new Sotrudnik(-1, familia, name, otchestvo, pol_id, voice_id, doljnost_id);
            if (ticketSystem.getDatabase().addSotrudnik(sotrudnik)){
                ticketSystem.switchWindow(Window.EMPLOYEES);
            } else {
                JOptionPane.showMessageDialog(Employees.this, "Ошибка при добавлении сотрудника.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }

        }
    }
    class ChangeEmployeeActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (table.getSelectedRows().length == 0){
                JOptionPane.showMessageDialog(Employees.this, "Выделите строчку для редактирования.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (table.getSelectedRows().length > 1){
                JOptionPane.showMessageDialog(Employees.this, "Выделите только одну строчку для редактирования.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String name = nameField.getText();
            String familia = familyField.getText();
            String otchestvo = otchestvoField.getText();
            if (name.length() == 0 || familia.length() == 0 || otchestvo.length() == 0){
                JOptionPane.showMessageDialog(Employees.this, "Заполните все поля.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int selectedRow = table.getSelectedRows()[0];
            int pol_id = pols.get(sexBox.getSelectedIndex()).getId_pol();
            int voice_id = voices.get(tembrBox.getSelectedIndex()).getVoice_id();
            int doljnost_id = doljnosts.get(jobTitleBox.getSelectedIndex()).getId_doljnost();
            Sotrudnik sotrudnik = new Sotrudnik(sotrudniks.get(selectedRow).getId_sotrudnik(), familia, name, otchestvo, pol_id, voice_id, doljnost_id);
            if (ticketSystem.getDatabase().changeSotrudnik(sotrudnik)){
                ticketSystem.switchWindow(Window.EMPLOYEES);
            } else {
                JOptionPane.showMessageDialog(Employees.this, "Ошибка при редактировании сотрудника.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
    class DeleteEmployeeActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (table.getSelectedRows().length == 0){
                JOptionPane.showMessageDialog(Employees.this, "Выделите строчку для удаления.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (table.getSelectedRows().length > 1){
                JOptionPane.showMessageDialog(Employees.this, "Выделите только одну строчку для удаления.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int selectedRow = table.getSelectedRows()[0];
            if (ticketSystem.getDatabase().deleteSotrudnik(sotrudniks.get(selectedRow))){
                ticketSystem.switchWindow(Window.EMPLOYEES);
            } else {
                JOptionPane.showMessageDialog(Employees.this, "Ошибка при удалении сотрудника.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
