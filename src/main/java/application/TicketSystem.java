package application;

import database.Database;
import database.classes.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;
import java.awt.*;


public class TicketSystem {
    private JFrame jFrame;
    private Database database;
    private User user;

    private static ClassPathXmlApplicationContext context;
    static {
        context = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
    }


    public static void main(String[] args) {
        TicketSystem ticketSystem = TicketSystem.context.getBean("Application", TicketSystem.class);
        ticketSystem.start();

    }

    public TicketSystem(JFrame jFrame, Database database) {
        this.jFrame = jFrame;
        this.database = database;
    }

    public void start(){
        this.jFrame.getContentPane().add((Component) TicketSystem.context.getBean(Window.LOGIN.getBeanName(), Window.LOGIN.getaClass()), BorderLayout.CENTER);
        this.jFrame.setTitle(Window.LOGIN.getTitle());
        this.jFrame.setResizable(false);
        this.jFrame.setSize(800, 600);
        this.jFrame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - this.jFrame.getWidth() / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - this.jFrame.getHeight() / 2);
        this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jFrame.setVisible(true);
    }

    public void switchWindow(Window window){
        this.jFrame.getContentPane().removeAll();
        this.jFrame.setTitle(window.getTitle());
        this.jFrame.getContentPane().add((Component) TicketSystem.context.getBean(window.getBeanName(), window.getaClass()),
                BorderLayout.CENTER);
        this.jFrame.repaint();
        this.jFrame.revalidate();
    }

    public Database getDatabase() {
        return database;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
