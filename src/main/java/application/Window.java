package application;
import gui.*;
import gui.client.*;
import gui.employee.*;

public enum Window{
    LOGIN ("Авторизация", "Login", Login.class),
    REGISTRATION ("Регистрация", "Registration", Registration.class),
    USER_MENU ("Пользователь", "UserMenu", UserMenu.class),
    POSTER("Постановки", "Poster", Poster.class),
    BUY_TICKET("Покупка билетов", "BuyTicket", BuyTicket.class),
    MY_TICKETS("Мои билеты", "MyTickets", MyTickets.class),
    CARDS("Мои карты", "Cards", Cards.class),
    EMPLOYEE_MENU("Сотрудник", "EmployeeMenu", EmployeeMenu.class),
    ADMIN_MENU("Администратор", "AdminMenu", AdministratorMenu.class),
    EMPLOYEES("Сотрудники", "Employees", Employees.class),
    GENRES("Жанры", "Genres", Genres.class),
    JOB_TITLE("Должности", "JobTitle", JobTitle.class),
    PARTICIPANTS("Список участников", "Participants", Participants.class),
    PERFORMANCES("Постановки", "Performances", Performances.class),
    TICKETS("Билеты", "Tickets", Tickets.class),
    USERS("Пользователи", "Users", Users.class);

    private int windowId;
    private String title;
    private String beanName;
    private Class aClass;

    Window(String title, String beanName, Class aClass){
        this.windowId = 0;
        this.title = title;
        this.beanName = beanName;
        this.aClass = aClass;
    }

    public int getWindowId() {
        return windowId;
    }

    public String getTitle() {
        return title;
    }

    public String getBeanName() {
        return beanName;
    }

    public Class getaClass() {
        return aClass;
    }
}