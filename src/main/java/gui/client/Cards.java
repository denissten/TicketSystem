package gui.client;

import application.TicketSystem;
import application.Window;
import database.classes.Card;
import gui.SwitchWindowActionListener;
import gui.Utill;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Cards extends JPanel {
    private TicketSystem ticketSystem;
    private JLabel balanceLabel;
    private JTextField bankField;
    private JTextField cardNumberField;
    private JTextField dateField;
    private JTextField cvvField;
    private JButton issueButton;
    private JButton depositButton;
    private JTextField amountField;
    private JButton deleteButton;
    private JComboBox cardListBox;
    private JButton backButton;
    private ArrayList<Card> cards;

    public Cards(TicketSystem ticketSystem){
        this.ticketSystem = ticketSystem;

        this.setLayout(new GridBagLayout());
        balanceLabel = new JLabel("0.00");
        balanceLabel.setForeground(new Color(8, 144, 0));
        bankField = new JTextField();
        cardNumberField = new JTextField();
        dateField = new JTextField();
        cvvField = new JTextField();
        issueButton = new JButton("Оформить");
        depositButton = new JButton("Пополнить баланс");
        amountField = new JTextField();
        deleteButton = new JButton("Удалить");
        backButton = new JButton("Вернуться");
        cardListBox = new JComboBox();

        issueButton.addActionListener(new CreateCardActionListener());
        depositButton.addActionListener(new DepositActionListener());
        deleteButton.addActionListener(new DeleteCardActionListener());
        cardListBox.addActionListener(new SwitchCardActionListener());
        backButton.addActionListener(new SwitchWindowActionListener(ticketSystem, Window.USER_MENU));

        fillCards();

        GridBagConstraints gbc = Utill.createGridBagConstrains(0, 0, 0, 0, 2, 1, 0, 0, 2);
        this.add(balanceLabel, gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 2, 0, 2, 1, 0, 0, 2);
        this.add(Box.createHorizontalStrut(50),gbc);

        gbc = Utill.createGridBagConstrains(0, 0,4, 0, 2, 1, 150, 0, 2);
        this.add(new JLabel("Выбор банка"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0,4, 1, 2, 1, 150, 0, 2);
        this.add(bankField, gbc);

        gbc = Utill.createGridBagConstrains(0, 0,4, 2, 2, 1, 150, 0, 2);
        this.add(new JLabel("Номер карты"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0,8, 2, 2, 1, 0, 0, 2);
        this.add(issueButton, gbc);
        gbc = Utill.createGridBagConstrains(0, 0,4, 3, 2, 1, 150, 0, 2);
        this.add(cardNumberField, gbc);
        gbc = Utill.createGridBagConstrains(0, 0,8, 3, 2, 1, 0, 0, 2);
        this.add(depositButton, gbc);

        gbc = Utill.createGridBagConstrains(0, 0,4, 4, 2, 1, 150, 0, 2);
        this.add(new JLabel("Срок"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0,8, 4, 2, 1, 150, 0, 2);
        this.add(amountField, gbc);
        gbc = Utill.createGridBagConstrains(0, 0,4, 5, 2, 1, 150, 0, 2);
        this.add(dateField, gbc);
        gbc = Utill.createGridBagConstrains(0, 0,8, 5, 2, 1, 0, 0, 2);
        this.add(deleteButton, gbc);

        gbc = Utill.createGridBagConstrains(0, 0,4, 6, 2, 1, 150, 0, 2);
        this.add(new JLabel("CVV"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0,8, 6, 2, 1, 0, 0, 2);
        this.add(new JLabel("Выберите карту"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0,4, 7, 2, 1, 150, 0, 2);
        this.add(cvvField, gbc);
        gbc = Utill.createGridBagConstrains(0, 0,8, 7, 2, 1, 0, 0, 2);
        this.add(cardListBox, gbc);

        gbc = Utill.createGridBagConstrains(0, 0, 6, 0, 2, 1, 0, 0, 2);
        this.add(Box.createHorizontalStrut(50),gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 0, 1, 1, 0, 0, 2);
        this.add(backButton, gbc);
    }

    private void fillCards(){
        int userId = ticketSystem.getUser().getUser_id();
        cards = ticketSystem.getDatabase().getCards(userId);
        if (cards.size() != 0) balanceLabel.setText(Float.toString(cards.get(0).getBalance()));
        for (Card card : cards){
            cardListBox.addItem(card.getBankName());
        }
    }

    class CreateCardActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String bankName = bankField.getText();
            String cardNumber = cardNumberField.getText();
            String date = dateField.getText();
            String cvv = cvvField.getText();
            if (bankName.length() == 0 || cardNumber.length() == 0 || date.length() == 0 || cvv.length() == 0 ||
                    cvv.length() > 3 || cardNumber.length() > 16 || !Utill.isNumber(cardNumber) || !Utill.isNumber(cvv)){
                JOptionPane.showMessageDialog(Cards.this, "Проверьте правильность введенных данных.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Card card = new Card();
            card.setBankName(bankName);
            card.setCardNumber(cardNumber);
            card.setDate(date);
            card.setCVV(cvv);
            card.setUser_id(ticketSystem.getUser().getUser_id());
            if (ticketSystem.getDatabase().addCard(card)){
                ticketSystem.switchWindow(Window.CARDS);
            } else {
                JOptionPane.showMessageDialog(Cards.this, "Ошибка при добавлении карты.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
    class DepositActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int selected = cardListBox.getSelectedIndex();
            float deposit;
            try {
                deposit = Float.parseFloat(amountField.getText());
            } catch (NumberFormatException exception){
                JOptionPane.showMessageDialog(Cards.this, "Проверьте правильность ввода данных.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Card cl = cards.get(selected);
            Card card = new Card();
            card.setBalance(cl.getBalance() + deposit);
            card.setId_card(cl.getId_card());
            card.setUser_id(cl.getUser_id());
            if (ticketSystem.getDatabase().updateCardBalance(card)){
                ticketSystem.switchWindow(Window.CARDS);
            } else {
                JOptionPane.showMessageDialog(Cards.this, "Ошибка при пополнении карты.", "Внимание!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    class DeleteCardActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int selected = cardListBox.getSelectedIndex();
            if (selected == 0){
                JOptionPane.showMessageDialog(Cards.this, "Выберите карточку, чтобы удалить ее.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Card card = cards.get(selected - 1);
            if (ticketSystem.getDatabase().deleteCard(card)) {
                ticketSystem.switchWindow(Window.CARDS);
            } else {
                JOptionPane.showMessageDialog(Cards.this, "Ошибка при удалении карточки.", "Внимание!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class SwitchCardActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int selected = cardListBox.getSelectedIndex();
            Card card = cards.get(selected);
            balanceLabel.setText(Float.toString(card.getBalance()));
        }
    }
}
