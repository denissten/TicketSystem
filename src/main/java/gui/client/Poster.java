package gui.client;

import application.TicketSystem;
import application.Window;
import database.classes.Performance;
import gui.SwitchWindowActionListener;
import gui.Utill;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Poster extends JPanel{
    private static final String[] columnsHeader = {"Наименование постановки", "Дата", "Цена"};
    private TicketSystem ticketSystem;
    private JTable table;
    private JButton backButton;
    private DefaultTableModel tableModel;


    public Poster(TicketSystem ticketSystem){
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

        fillPoster();

        backButton = new JButton("Вернуться");

        backButton.addActionListener(new SwitchWindowActionListener(ticketSystem, Window.USER_MENU));

        GridBagConstraints gbc = Utill.createGridBagConstrains(0, 0, 0, 0, 10, 10, 0, 0, 2);
        this.add(jScrollPane, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 10, 0, 2, 1, 0, 0, 2);
        this.add(Box.createHorizontalStrut(50),gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 12, 0, 1, 1, 0, 0, 2);
        this.add(backButton, gbc);
    }

    private void fillPoster(){
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        ArrayList<Performance> performances = ticketSystem.getDatabase().getPerformances();
        for (Performance performance : performances){
            tableModel.addRow(new Object[]{performance.getName_perfomance(), sdf.format(performance.getData()), performance.getPrice()});
        }
    }
}
