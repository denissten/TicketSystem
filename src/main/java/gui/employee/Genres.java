package gui.employee;

import application.TicketSystem;
import application.Window;
import database.classes.Genre;
import gui.SwitchWindowActionListener;
import gui.Utill;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Genres extends JPanel {
    private TicketSystem ticketSystem;
    private ArrayList<Genre> genres;
    private static final String[] columnsHeader = {"Наименование жанра"};
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton changeButton;
    private JButton deleteButton;
    private JTextField genreField;
    private JButton backButton;

    public Genres(TicketSystem ticketSystem){
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
        genreField = new JTextField();
        backButton = new JButton("Вернуться");

        addButton.addActionListener(new AddGenreActionListener());
        changeButton.addActionListener(new ChangeGenreActionListener());
        deleteButton.addActionListener(new DeleteGenreActionListener());
        application.Window window = switch (ticketSystem.getUser().getRole()){
            case 2 -> application.Window.EMPLOYEE_MENU;
            case 3 -> application.Window.ADMIN_MENU;
            default -> Window.LOGIN;
        };
        backButton.addActionListener(new SwitchWindowActionListener(ticketSystem, window));

        fillGenres();

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
        this.add(new JLabel("Жанр"), gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 9, 4, 2, 1, 50, 0, 2);
        this.add(genreField, gbc);

        gbc = Utill.createGridBagConstrains(0, 0, 11, 0, 2, 1, 0, 0, 2);
        this.add(Box.createHorizontalStrut(20),gbc);
        gbc = Utill.createGridBagConstrains(0, 0, 13, 0, 2, 1, 0, 0, 2);
        this.add(backButton, gbc);
    }

    private void fillGenres(){
        genres = ticketSystem.getDatabase().getAllGenres();
        for (Genre genre : genres){
            tableModel.addRow(new Object[]{genre.getName_genre()});
        }
    }

    class AddGenreActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String genreName = genreField.getText();
            if (genreName.length() == 0){
                JOptionPane.showMessageDialog(Genres.this, "Заполните название жанра.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Genre genre = new Genre();
            genre.setName_genre(genreName);
            if (ticketSystem.getDatabase().addGenre(genre)){
                ticketSystem.switchWindow(Window.GENRES);
            } else {
                JOptionPane.showMessageDialog(Genres.this, "Ошибка при добавление жанра.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
    class ChangeGenreActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (table.getSelectedRows().length == 0){
                JOptionPane.showMessageDialog(Genres.this, "Выделите строчку для редактирования.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (table.getSelectedRows().length > 1){
                JOptionPane.showMessageDialog(Genres.this, "Выделите только одну строчку для редактирования.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String genreName = genreField.getText();
            if (genreName.length() == 0){
                JOptionPane.showMessageDialog(Genres.this, "Введите новое название для жанра в поле \"Жанр\".", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int selectedRow = table.getSelectedRows()[0];
            Genre genre = new Genre(genres.get(selectedRow).getId_genre(), genreName);
            if (ticketSystem.getDatabase().changeGenreName(genre)){
                ticketSystem.switchWindow(Window.GENRES);
            } else {
                JOptionPane.showMessageDialog(Genres.this, "Ошибка при редактировании названия жанра.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }

    class DeleteGenreActionListener implements  ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (table.getSelectedRows().length == 0){
                JOptionPane.showMessageDialog(Genres.this, "Выделите строчку для удаления.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (table.getSelectedRows().length > 1){
                JOptionPane.showMessageDialog(Genres.this, "Выделите только одну строчку для удаления.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int selectedRow = table.getSelectedRows()[0];
            if (ticketSystem.getDatabase().deleteGenre(genres.get(selectedRow))){
                ticketSystem.switchWindow(Window.GENRES);
            } else {
                JOptionPane.showMessageDialog(Genres.this, "Ошибка при удалении жанра.", "Внимание!", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
