package gui.employee;

import application.TicketSystem;
import application.Window;
import gui.SwitchWindowActionListener;
import gui.Utill;

import javax.swing.*;
import java.awt.*;

public class AdministratorMenu extends JPanel {
    private TicketSystem ticketSystem;
    private JButton performanceButton;
    private JButton participantsButton;
    private JButton genreButton;
    private JButton jobTitleButton;
    private JButton employee;
    private JButton ticketsButton;
    private JButton usersButton;
    private JButton backButton;

    public AdministratorMenu(TicketSystem ticketSystem){
        this.ticketSystem = ticketSystem;

        this.setLayout(new GridBagLayout());
        performanceButton = new JButton("Постановки");
        participantsButton = new JButton("Список участников");
        genreButton = new JButton("Жанры");
        jobTitleButton = new JButton("Должности");
        employee = new JButton("Сотрудники");
        ticketsButton = new JButton("Билеты");
        usersButton = new JButton("Пользователи");
        backButton = new JButton("Вернуться");


        performanceButton.addActionListener(new SwitchWindowActionListener(ticketSystem, Window.PERFORMANCES));
        participantsButton.addActionListener(new SwitchWindowActionListener(ticketSystem, Window.PARTICIPANTS));
        genreButton.addActionListener(new SwitchWindowActionListener(ticketSystem, Window.GENRES));
        jobTitleButton.addActionListener(new SwitchWindowActionListener(ticketSystem, Window.JOB_TITLE));
        employee.addActionListener(new SwitchWindowActionListener(ticketSystem, Window.EMPLOYEES));
        ticketsButton.addActionListener(new SwitchWindowActionListener(ticketSystem, Window.TICKETS));
        usersButton.addActionListener(new SwitchWindowActionListener(ticketSystem, Window.USERS));
        backButton.addActionListener(new SwitchWindowActionListener(ticketSystem, Window.LOGIN));

        GridBagConstraints gbc = Utill.createGridBagConstrains(0, 0, 0, 0, 2, 1, 0, 0, 2);
        this.add(performanceButton, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 0, 1, 2, 1, 0, 0, 2);
        this.add(participantsButton, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 0, 2, 2, 1, 0, 0, 2);
        this.add(genreButton, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 0, 3, 2, 1, 0, 0, 2);
        this.add(jobTitleButton, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 0, 4, 2, 1, 0, 0, 2);
        this.add(employee, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 0, 5, 2, 1, 0, 0, 2);
        this.add(ticketsButton, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 0, 6, 2, 1, 0, 0, 2);
        this.add(usersButton, gbc);

        gbc = Utill.createGridBagConstrains(0, 0, 2, 0, 2, 1, 0, 0, 2);
        this.add(Box.createHorizontalStrut(50),gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 4, 0, 1, 1, 0, 0, 2);
        this.add(backButton, gbc);
    }
}
