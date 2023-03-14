package gui.client;

import application.TicketSystem;
import application.Window;
import gui.*;

import javax.swing.*;
import java.awt.*;

public class UserMenu extends JPanel{
    TicketSystem ticketSystem;
    private JButton posterButton;
    private JButton buyTicketButton;
    private JButton myTicketsButton;
    private JButton cardsButton;
    private JButton backButton;

    public UserMenu(TicketSystem ticketSystem){
        this.ticketSystem = ticketSystem;

        this.setLayout(new GridBagLayout());
        posterButton = new JButton("Афиша");
        buyTicketButton = new JButton("Покупка билета");
        myTicketsButton = new JButton("Мои билеты");
        cardsButton = new JButton("Мои карты");
        backButton = new JButton("Вернуться");

        posterButton.addActionListener(new SwitchWindowActionListener(ticketSystem, Window.POSTER));
        buyTicketButton.addActionListener(new SwitchWindowActionListener(ticketSystem, Window.BUY_TICKET));
        myTicketsButton.addActionListener(new SwitchWindowActionListener(ticketSystem, Window.MY_TICKETS));
        cardsButton.addActionListener(new SwitchWindowActionListener(ticketSystem, Window.CARDS));
        backButton.addActionListener(new SwitchWindowActionListener(ticketSystem, Window.LOGIN));

        GridBagConstraints gbc = Utill.createGridBagConstrains(0, 0, 0, 0, 2, 1, 0, 0, 2);
        this.add(posterButton, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 0, 1, 2, 1, 0, 0, 2);
        this.add(buyTicketButton, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 0, 2, 2, 1, 0, 0, 2);
        this.add(myTicketsButton, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 0, 3, 2, 1, 0, 0, 2);
        this.add(cardsButton, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 2, 0, 2, 1, 0, 0, 2);
        this.add(Box.createHorizontalStrut(50),gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 4, 0, 1, 1, 0, 0, 2);
        this.add(backButton, gbc);

    }

}
