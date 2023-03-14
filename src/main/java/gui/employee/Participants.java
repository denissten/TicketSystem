package gui.employee;

import application.TicketSystem;
import application.Window;
import database.classes.*;
import gui.SwitchWindowActionListener;
import gui.Utill;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Participants extends JPanel {
    private TicketSystem ticketSystem;
    private ArrayList<Performance> performances;
    private ArrayList<Sotrudnik> sotrudniks;
    private ArrayList<Uchastnik> uchastniks;
    private static final String[] columnsHeader = {"Роль", "Постановка", "Дата", "Отчество", "Имя",
            "Фамилия", "Должность", "Оклад", "Тембр голоса", "Пол"};
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton changeButton;
    private JButton deleteButton;
    private JTextField roleField;
    private JComboBox performancesBox;
    private JComboBox employeesBox;
    private JButton backButton;

    public Participants(TicketSystem ticketSystem){
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
        roleField = new JTextField();
        performancesBox = new JComboBox();
        employeesBox = new JComboBox();
        backButton = new JButton("Вернуться");

        addButton.addActionListener(new AddParticipantsActionListener());
        changeButton.addActionListener(new ChangeParticipantsActionListener());
        deleteButton.addActionListener(new DeleteParticipantsActionListener());
        application.Window window = switch (ticketSystem.getUser().getRole()){
            case 2 -> application.Window.EMPLOYEE_MENU;
            case 3 -> application.Window.ADMIN_MENU;
            default -> Window.LOGIN;
        };
        backButton.addActionListener(new SwitchWindowActionListener(ticketSystem, window));

        fillPerformanceBox();
        fillEmployeesBox();
        fillParticipants();

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
        this.add(new JLabel("Роль"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 4, 2, 1, 50, 0, 2);
        this.add(roleField, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 5, 2, 1, 50, 0, 2);
        this.add(new JLabel("Выбор постановки"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 6, 2, 1, 50, 0, 2);
        this.add(performancesBox, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 7, 2, 1, 50, 0, 2);
        this.add(new JLabel("Выбор участника"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 8, 2, 1, 50, 0, 2);
        this.add(employeesBox, gbc);

        gbc = Utill.createGridBagConstrains(0, 0, 11, 0, 2, 1, 0, 0, 2);
        this.add(Box.createHorizontalStrut(20),gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 13, 0, 2, 1, 0, 0, 2);
        this.add(backButton, gbc);
    }

    private void fillPerformanceBox(){
        performances = ticketSystem.getDatabase().getPerformances();
        for (Performance performance : performances){
            performancesBox.addItem(performance.getName_perfomance());
        }
    }

    private void fillEmployeesBox(){
        sotrudniks = ticketSystem.getDatabase().getAllSotrudniks();
        for (Sotrudnik sotrudnik : sotrudniks){
            employeesBox.addItem(sotrudnik.getFamilia());
        }
    }

    private void fillParticipants(){
        uchastniks = ticketSystem.getDatabase().getSpisokUchastnikov();
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        for (Uchastnik uchastnik : uchastniks){
            Performance performance = ticketSystem.getDatabase().getPerformance(uchastnik.getPerformance_id());
            Sotrudnik sotrudnik = ticketSystem.getDatabase().getSotrudnik(uchastnik.getSotrudnik_id());
            Doljnost doljnost = ticketSystem.getDatabase().getDoljnost(sotrudnik.getDoljnost_id());
            Voice voice = ticketSystem.getDatabase().getVoice(sotrudnik.getVoice_id());
            Pol pol = ticketSystem.getDatabase().getPol(sotrudnik.getPol_id());
            tableModel.addRow(new Object[]{uchastnik.getCharacters(), performance.getName_perfomance(),
                    sdf.format(performance.getData()), sotrudnik.getOtchestvo(), sotrudnik.getName(),
                    sotrudnik.getFamilia(), doljnost.getName_doljnost(), doljnost.getOklad(),
                    voice.getTembr_voice(), pol.getName_pol()});
        }
    }

    class AddParticipantsActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String role = roleField.getText();
            if (role.length() == 0){
                JOptionPane.showMessageDialog(Participants.this, "Заполните поле \"Роль\".", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int sotrudnik_id =  sotrudniks.get(employeesBox.getSelectedIndex()).getId_sotrudnik();
            int performance_id = performances.get(performancesBox.getSelectedIndex()).getId_perfomance();
            Uchastnik uchastnik = new Uchastnik(-1, role, performance_id, sotrudnik_id);
            if (ticketSystem.getDatabase().addUchastnik(uchastnik)){
                ticketSystem.switchWindow(Window.PARTICIPANTS);
            } else {
                JOptionPane.showMessageDialog(Participants.this, "Ошибка при добавлении участника.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
    class ChangeParticipantsActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (table.getSelectedRows().length == 0){
                JOptionPane.showMessageDialog(Participants.this, "Выделите строчку для редактирования.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (table.getSelectedRows().length > 1){
                JOptionPane.showMessageDialog(Participants.this, "Выделите только одну строчку для редактирования.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String role = roleField.getText();
            if (role.length() == 0){
                JOptionPane.showMessageDialog(Participants.this, "Заполните поле \"Роль\".", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int sotrudnik_id =  sotrudniks.get(employeesBox.getSelectedIndex()).getId_sotrudnik();
            int performance_id = performances.get(performancesBox.getSelectedIndex()).getId_perfomance();
            int selectedRow = table.getSelectedRows()[0];
            Uchastnik uchastnik = new Uchastnik(uchastniks.get(selectedRow).getId_spisok_uchastnikov(), role, performance_id, sotrudnik_id);

            if (ticketSystem.getDatabase().changeUchastnik(uchastnik)){
                ticketSystem.switchWindow(Window.PARTICIPANTS);
            } else {
                JOptionPane.showMessageDialog(Participants.this, "Ошибка при изменении участника.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
    class DeleteParticipantsActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (table.getSelectedRows().length == 0){
                JOptionPane.showMessageDialog(Participants.this, "Выделите строчку для удаления.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (table.getSelectedRows().length > 1){
                JOptionPane.showMessageDialog(Participants.this, "Выделите только одну строчку для удаления.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int selectedRow = table.getSelectedRows()[0];
            if (ticketSystem.getDatabase().deleteUchastnik(uchastniks.get(selectedRow))){
                ticketSystem.switchWindow(Window.PARTICIPANTS);
            } else {
                JOptionPane.showMessageDialog(Participants.this, "Ошибка при удалении участника.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
