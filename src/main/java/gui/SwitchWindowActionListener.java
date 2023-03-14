package gui;

import application.TicketSystem;
import application.Window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwitchWindowActionListener implements ActionListener {
    private TicketSystem ticketSystem;
    private Window window;

    public SwitchWindowActionListener(TicketSystem ticketSystem, Window window) {
        this.ticketSystem = ticketSystem;
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.ticketSystem.switchWindow(this.window);
    }
}