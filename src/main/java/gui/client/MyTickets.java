package gui.client;

import application.TicketSystem;
import application.Window;
import database.classes.Performance;
import database.classes.Ticket;
import database.classes.Zal;
import gui.SwitchWindowActionListener;
import gui.Utill;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class MyTickets extends JPanel {
    private static final String[] columsHeader = {"Организация","Представление", "Адрес", "Дата", "Билеты", "Цена", "Статус"};
    private TicketSystem ticketSystem;
    private JTable table;
    private JButton backButton;
    private DefaultTableModel tableModel;


    public MyTickets(TicketSystem ticketSystem){
        this.ticketSystem = ticketSystem;

        this.setLayout(new GridBagLayout());
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };
        tableModel.setColumnIdentifiers(columsHeader);
        table = new JTable(tableModel);
        JScrollPane jScrollPane = new JScrollPane(table);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        backButton = new JButton("Вернуться");

        backButton.addActionListener(new SwitchWindowActionListener(ticketSystem, Window.USER_MENU));

        fillTickets();

        GridBagConstraints gbc = Utill.createGridBagConstrains(0, 0, 0, 0, 10, 10, 0, 0, 2);
        this.add(jScrollPane, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 10, 0, 2, 1, 0, 0, 2);
        this.add(Box.createHorizontalStrut(50),gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 12, 0, 1, 1, 0, 0, 2);
        this.add(backButton, gbc);
    }

    private void fillTickets(){
        ArrayList<Ticket> tickets = ticketSystem.getDatabase().getUserTickets(ticketSystem.getUser().getUser_id());
        for (Ticket ticket : tickets){
            Performance performance = ticketSystem.getDatabase().getPerformance(ticket.getPerformance_id());
            Zal zal = ticketSystem.getDatabase().getZal(ticket.getZal_id());
            tableModel.addRow(new Object[]{ticket.getNaimenovanie_organizacii(), performance.getName_perfomance(),
            zal.getAdres(), performance.getData(), ticket.getKolichestvo_biletov(), ticket.getFinal_price(), Utill.getStatusName(ticket.getStatus())});
        }
    }
}
