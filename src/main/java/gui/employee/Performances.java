package gui.employee;

import application.TicketSystem;
import application.Window;
import database.classes.Performance;
import gui.SwitchWindowActionListener;
import gui.Utill;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Performances extends JPanel {
    private TicketSystem ticketSystem;
    ArrayList<Performance> performances;
    private static final String[] columnsHeader = {"Наименование постановки", "Дата", "Цена"};
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton changeButton;
    private JButton deleteButton;
    private JTextField dateField;
    private JTextField nameField;
    private JTextField priceField;
    private JButton backButton;

    public Performances(TicketSystem ticketSystem){
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
        dateField = new JTextField();
        nameField = new JTextField();
        priceField = new JTextField();
        backButton = new JButton("Вернуться");

        addButton.addActionListener(new AddPerformanceActionListener());
        changeButton.addActionListener(new ChangePerformanceActionListener());
        deleteButton.addActionListener(new DeletePerformanceActionListener());
        application.Window window = switch (ticketSystem.getUser().getRole()){
            case 2 -> application.Window.EMPLOYEE_MENU;
            case 3 -> application.Window.ADMIN_MENU;
            default -> Window.LOGIN;
        };
        backButton.addActionListener(new SwitchWindowActionListener(ticketSystem, window));

        fillPerformances();

        GridBagConstraints gbc = Utill.createGridBagConstrains(0, 0, 0, 0, 8, 10, 0, 0, 2);
        this.add(jScrollPane, gbc);

        gbc = Utill.createGridBagConstrains(0, 0, 8, 0, 1, 1, 0, 0, 2);
        this.add(Box.createHorizontalStrut(10),gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 0, 2, 1, 50, 0, 2);
        this.add(addButton, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 1, 2, 1, 0, 0, 2);
        this.add(changeButton, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 2, 2, 1, 0, 0, 2);
        this.add(deleteButton, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 3, 2, 1, 0, 0, 2);
        this.add(new JLabel("Дата"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 4, 2, 1, 0, 0, 2);
        this.add(dateField, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 5, 2, 1, 0, 0, 2);
        this.add(new JLabel("Наименование постановки"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 6, 2, 1, 0, 0, 2);
        this.add(nameField, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 7, 2, 1, 0, 0, 2);
        this.add(new JLabel("Цена за билет"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 8, 2, 1, 0, 0, 2);
        this.add(priceField, gbc);

        gbc = Utill.createGridBagConstrains(0, 0, 11, 0, 2, 1, 0, 0, 2);
        this.add(Box.createHorizontalStrut(20),gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 13, 0, 2, 1, 0, 0, 2);
        this.add(backButton, gbc);
    }

    private void fillPerformances(){
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        performances = ticketSystem.getDatabase().getPerformances();
        for (Performance performance : performances){
            tableModel.addRow(new Object[]{performance.getName_perfomance(), sdf.format(performance.getData()), performance.getPrice()});
        }
    }

     class AddPerformanceActionListener implements ActionListener {

         @Override
         public void actionPerformed(ActionEvent e) {
             String date = dateField.getText();
             String name = nameField.getText();
             String price = priceField.getText();
             if (date.length() == 0 || name.length() == 0 || price.length() == 0){
                 JOptionPane.showMessageDialog(Performances.this, "Заполните все поля.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                 return;
             }
             if (!Utill.isNumberF(price)){
                 JOptionPane.showMessageDialog(Performances.this, "Проверьте правильность введенной цены.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                 return;
             }
             java.util.Date utilDate;
             try {
                 utilDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(date);
             } catch (ParseException ex) {
                 JOptionPane.showMessageDialog(Performances.this, "Введите дату в формате dd-MM-yyyy HH:mm:ss.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                 return;
             }

             Performance performance = new Performance();
             performance.setName_perfomance(name);
             performance.setData(new Date(utilDate.getTime()));
             performance.setPrice(Float.parseFloat(price));

             if (ticketSystem.getDatabase().addPerformance(performance)){
                 ticketSystem.switchWindow(Window.PERFORMANCES);
             } else {
                 JOptionPane.showMessageDialog(Performances.this, "Ошибка при добавлении мероприятия.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                 return;
             }
         }
     }

     class ChangePerformanceActionListener implements ActionListener{

         @Override
         public void actionPerformed(ActionEvent e) {
             if (table.getSelectedRows().length == 0){
                 JOptionPane.showMessageDialog(Performances.this, "Выделите строчку для редактирования.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                 return;
             }
             if (table.getSelectedRows().length > 1){
                 JOptionPane.showMessageDialog(Performances.this, "Выделите только одну строчку для редактирования.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                 return;
             }
             String date = dateField.getText();
             String name = nameField.getText();
             String price = priceField.getText();
             if (date.length() == 0 || name.length() == 0 || price.length() == 0){
                 JOptionPane.showMessageDialog(Performances.this, "Заполните все поля.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                 return;
             }
             if (!Utill.isNumberF(price)){
                 JOptionPane.showMessageDialog(Performances.this, "Проверьте правильность введенной цены.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                 return;
             }
             java.util.Date utilDate;
             try {
                 utilDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(date);
             } catch (ParseException ex) {
                 JOptionPane.showMessageDialog(Performances.this, "Введите дату в формате dd-MM-yyyy HH:mm:ss.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                 return;
             }
             int selectedRow = table.getSelectedRows()[0];
             Performance performance = new Performance(performances.get(selectedRow).getId_perfomance(),
                     name, new Date(utilDate.getTime()), Float.parseFloat(price));

             if (ticketSystem.getDatabase().changePerformance(performance)){
                 ticketSystem.switchWindow(Window.PERFORMANCES);
             } else {
                 JOptionPane.showMessageDialog(Performances.this, "Ошибка при изменении постановки.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                 return;
             }

         }
     }

     class DeletePerformanceActionListener implements ActionListener{

         @Override
         public void actionPerformed(ActionEvent e) {
             if (table.getSelectedRows().length == 0){
                 JOptionPane.showMessageDialog(Performances.this, "Выделите строчку для удаления.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                 return;
             }
             if (table.getSelectedRows().length > 1){
                 JOptionPane.showMessageDialog(Performances.this, "Выделите только одну строчку для удаления.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                 return;
             }
             int selectedRow = table.getSelectedRows()[0];
             if (ticketSystem.getDatabase().deletePerformance(performances.get(selectedRow))){
                 ticketSystem.switchWindow(Window.PERFORMANCES);
             } else {
                 JOptionPane.showMessageDialog(Performances.this, "Ошибка при удалении постановки.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                 return;
             }
         }
     }
}
