package gui.employee;

import application.TicketSystem;
import application.Window;
import database.classes.Performance;
import database.classes.Ticket;
import database.classes.User;
import database.classes.Zal;
import gui.SwitchWindowActionListener;
import gui.Utill;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Tickets extends JPanel {
    private TicketSystem ticketSystem;
    private ArrayList<Ticket> tickets;
    private static final String[] columnsHeader = {"Имя", "Фамилия", "Отчество", "Организация", "Дата", "Адрес",
            "Название представления", "Количество билетов", "Финальная цена", "Статус"};
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton reservedButton;
    private JButton declineButton;
    private JButton processingButton;
    private JButton backButton;

    public Tickets(TicketSystem ticketSystem){
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

        reservedButton = new JButton("Забронирован");
        declineButton = new JButton("Не одобрен");
        processingButton = new JButton("На рассмотрении");
        backButton = new JButton("Вернуться");

        reservedButton.addActionListener(new ChangeStatusActionListener(1));
        declineButton.addActionListener(new ChangeStatusActionListener(2));
        processingButton.addActionListener(new ChangeStatusActionListener(3));
        application.Window window = switch (ticketSystem.getUser().getRole()){
            case 2 -> application.Window.EMPLOYEE_MENU;
            case 3 -> application.Window.ADMIN_MENU;
            default -> Window.LOGIN;
        };
        backButton.addActionListener(new SwitchWindowActionListener(ticketSystem, window));

        fillTickets();

        GridBagConstraints gbc = Utill.createGridBagConstrains(0, 0, 0, 0, 8, 10, 0, 0, 0);
        this.add(jScrollPane, gbc);

        gbc = Utill.createGridBagConstrains(0, 0, 8, 0, 1, 1, 0, 0, 2);
        this.add(Box.createHorizontalStrut(20),gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 0, 2, 1, 0, 0, 2);
        this.add(reservedButton, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 1, 2, 1, 0, 0, 2);
        this.add(declineButton, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 2, 2, 1, 0, 0, 2);
        this.add(processingButton, gbc);

        gbc = Utill.createGridBagConstrains(0, 0, 11, 0, 2, 1, 0, 0, 2);
        this.add(Box.createHorizontalStrut(20),gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 13, 0, 2, 1, 0, 0, 2);
        this.add(backButton, gbc);
    }

    private void fillTickets(){
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        tickets = ticketSystem.getDatabase().getAllTickets();
        for (Ticket ticket : tickets){
            User user = ticketSystem.getDatabase().getUser(ticket.getUser_id());
            Performance performance = ticketSystem.getDatabase().getPerformance(ticket.getPerformance_id());
            Zal zal = ticketSystem.getDatabase().getZal(ticket.getZal_id());
            tableModel.addRow(new Object[]{user.getName(), user.getFamilia(), user.getOtchestvo(),
                    ticket.getNaimenovanie_organizacii(), sdf.format(performance.getData()), zal.getAdres(),
                    performance.getName_perfomance(), ticket.getKolichestvo_biletov(), ticket.getFinal_price(),
                    Utill.getStatusName(ticket.getStatus())});
        }
    }

    class ChangeStatusActionListener implements ActionListener {
        private int status;
        public ChangeStatusActionListener(int status){
            this.status = status;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if (table.getSelectedRows().length == 0){
                JOptionPane.showMessageDialog(Tickets.this, "Выделите строчку для изменения статуса.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (table.getSelectedRows().length > 1){
                JOptionPane.showMessageDialog(Tickets.this, "Выделите только одну строчку для изменения статуса.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int selecterRow = table.getSelectedRows()[0];
            Ticket lt = tickets.get(selecterRow);
            Ticket ticket = new Ticket(lt.getId_ticket(), lt.getKolichestvo_biletov(),
                    lt.getNaimenovanie_organizacii(), status, lt.getUser_id(), lt.getZal_id(),
                    lt.getPerformance_id(), lt.getFinal_price());
            if (ticketSystem.getDatabase().changeTicket(ticket)){
                ticketSystem.switchWindow(Window.TICKETS);
            } else {
                JOptionPane.showMessageDialog(Tickets.this, "Ошибка при обновлении статуса билета.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
