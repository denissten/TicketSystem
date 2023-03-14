package gui.client;

import application.TicketSystem;
import application.Window;
import database.classes.Card;
import database.classes.Performance;
import database.classes.Ticket;
import gui.SwitchWindowActionListener;
import gui.Utill;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BuyTicket extends JPanel {
    private ArrayList<Performance> performances;
    private ArrayList<Card> cards;
    private TicketSystem ticketSystem;
    private JButton buyButton;
    private JButton countButton;
    private JTextField amountTicketsField;
    private JComboBox eventListBox;
    private JLabel totalLabel;
    private JComboBox cardListBox;
    private JButton backButton;

    public BuyTicket(TicketSystem ticketSystem){
        this.ticketSystem = ticketSystem;

        this.setLayout(new GridBagLayout());
        buyButton = new JButton("Купить");
        countButton = new JButton("Посчитать");
        amountTicketsField = new JTextField();
        eventListBox = new JComboBox();
        totalLabel = new JLabel("0");
        totalLabel.setForeground(new Color(8, 144, 0));
        cardListBox = new JComboBox();
        backButton = new JButton("Вернуться");

        countButton.addActionListener(new CountTicketPriceActionListener());
        buyButton.addActionListener(new BuyTicketActionListener());
        backButton.addActionListener(new SwitchWindowActionListener(ticketSystem, Window.USER_MENU));

        fillEventBox();
        fillCardBox();

        GridBagConstraints gbc = Utill.createGridBagConstrains(0, 0, 2, 0, 2, 1, 0, 0, 2);
        this.add(Box.createHorizontalStrut(50),gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 4, 0, 2, 1, 0, 0, 2);
        this.add(backButton, gbc);

        gbc = Utill.createGridBagConstrains(0, 0, 0, 0, 2, 1, 0, 0, 2);
        this.add(buyButton, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 0, 1, 2, 1, 0, 0, 2);
        this.add(countButton, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 0, 2, 2, 1, 0, 0, 2);
        this.add(new JLabel("Количество билетов"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 0, 3, 2, 1, 100, 0, 2);
        this.add(amountTicketsField, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 0, 4, 2, 1, 0, 0, 2);
        this.add(new JLabel("Выбор мероприятия"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 0, 5, 2, 1, 0, 0, 2);
        this.add(eventListBox, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 0, 6, 2, 1, 0, 0, 2);
        this.add(new JLabel("К оплате"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 0, 7, 2, 1, 0, 0, 2);
        this.add(totalLabel, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 0, 8, 2, 1, 0, 0, 2);
        this.add(new JLabel("Карточка банка"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 0, 9, 2, 1, 0, 0, 2);
        this.add(cardListBox, gbc);
    }

    private void fillEventBox(){
        performances = ticketSystem.getDatabase().getPerformances();
        for (Performance performance : performances){
            eventListBox.addItem(performance.getName_perfomance());
        }
    }
    private void fillCardBox(){
        cards = ticketSystem.getDatabase().getCards(ticketSystem.getUser().getUser_id());
        for (Card card : cards){
            cardListBox.addItem(card.getBankName());
        }
    }

    class BuyTicketActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String count = amountTicketsField.getText();
            if (cards.size() == 0){
                JOptionPane.showMessageDialog(BuyTicket.this, "Добавьте карточку для оплаты.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (count.length() == 0) {
                JOptionPane.showMessageDialog(BuyTicket.this, "Введите количество билетов.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int count_int;
            try {
                count_int = Integer.parseInt(count);
            } catch (Exception exception){
                JOptionPane.showMessageDialog(BuyTicket.this, "Неверное количество билетов.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int event = eventListBox.getSelectedIndex();
            int card = cardListBox.getSelectedIndex();
            float total = performances.get(event).getPrice() * count_int;
            if (total > cards.get(card).getBalance()) {
                JOptionPane.showMessageDialog(BuyTicket.this, "Недостаточно денег на карте.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Ticket ticket = new Ticket();
            ticket.setKolichestvo_biletov(count_int);
            ticket.setNaimenovanie_organizacii("Circle");
            ticket.setStatus(3);
            ticket.setUser_id(ticketSystem.getUser().getUser_id());
            ticket.setZal_id(1);
            ticket.setPerformance_id(performances.get(event).getId_perfomance());
            ticket.setFinal_price(total);
            if (ticketSystem.getDatabase().addTicket(ticket)){
                Card cl = cards.get(card);
                Card card1 = new Card(cl.getId_card(), cl.getBankName(), cl.getCardNumber(), cl.getDate(),
                        cl.getCVV(), cl.getUser_id(), cl.getBalance() - total);
                if (ticketSystem.getDatabase().updateCardBalance(card1)) {
                    JOptionPane.showMessageDialog(BuyTicket.this, "Билеты куплены.", "Успешно!", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } else {
                    JOptionPane.showMessageDialog(BuyTicket.this, "Ошибка при оплате билета.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

            } else {
                JOptionPane.showMessageDialog(BuyTicket.this, "Ошибка при оформлении билета.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
    class CountTicketPriceActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String count = amountTicketsField.getText();
            int event = eventListBox.getSelectedIndex();
            if (count.length() == 0) {
                JOptionPane.showMessageDialog(BuyTicket.this, "Введите количество билетов.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int count_int;
            try {
                count_int = Integer.parseInt(count);
            } catch (Exception exception){
                JOptionPane.showMessageDialog(BuyTicket.this, "Неверное количество билетов.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            float total = performances.get(event).getPrice() * count_int;
            totalLabel.setText(Float.toString(total));
        }
    }
}
