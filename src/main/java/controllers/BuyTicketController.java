package controllers;

import database.Database;
import database.classes.Card;
import database.classes.Performance;
import database.classes.Ticket;
import gui.Utill;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BuyTicketController {
    @GetMapping("/buyticket")
    public String getBuyTicketPage(Model model,
                                   @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                                   @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if (!role.equals("1") || userId .equals("-1")) return "redirect:/";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        model.addAttribute("perfomances", database.getPerformances());
        model.addAttribute("cards", database.getCards(Integer.parseInt(userId)));
        return "BuyTicket";
    }

    @PostMapping("/buyticket")
    public String buyticket(@CookieValue(value = "userid", required = false, defaultValue = "-1") String userId,
                            @RequestParam String ticketcount, @RequestParam String id_perfomance, @RequestParam String bank_card,
                            Model model){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        if (!Utill.isNumber(ticketcount)){
            model.addAttribute("message_type", "error");
            model.addAttribute("info_message", "Введите верное количество билетов.");
            model.addAttribute("perfomances", database.getPerformances());
            model.addAttribute("cards", database.getCards(Integer.parseInt(userId)));
            return "BuyTicket";
        }
        Card card = database.getCard(Integer.parseInt(userId), Integer.parseInt(bank_card));
        Performance performance = database.getPerformance(Integer.parseInt(id_perfomance));
        if (performance.getPrice() * Integer.parseInt(ticketcount) > card.getBalance()){
            model.addAttribute("perfomances", database.getPerformances());
            model.addAttribute("cards", database.getCards(Integer.parseInt(userId)));
            model.addAttribute("message_type", "error");
            model.addAttribute("info_message", "Недостаточно денег на карте.");
            return "BuyTicket";
        }
        card.setBalance(card.getBalance() - performance.getPrice() * Integer.parseInt(ticketcount));
        Ticket ticket = new Ticket();
        ticket.setKolichestvo_biletov(Integer.parseInt(ticketcount));
        ticket.setNaimenovanie_organizacii("Circle");
        ticket.setStatus(3);
        ticket.setUser_id(Integer.parseInt(userId));
        ticket.setZal_id(1);
        ticket.setPerformance_id(performance.getId_perfomance());
        ticket.setFinal_price(performance.getPrice() * Integer.parseInt(ticketcount));
        if (database.addTicket(ticket)){
            model.addAttribute("perfomances", database.getPerformances());
            model.addAttribute("cards", database.getCards(Integer.parseInt(userId)));
            model.addAttribute("message_type", "success");
            model.addAttribute("info_message", "Билет успешно куплен.");
            database.updateCardBalance(card);
            return "BuyTicket";
        } else {
            model.addAttribute("perfomances", database.getPerformances());
            model.addAttribute("cards", database.getCards(Integer.parseInt(userId)));
            model.addAttribute("message_type", "error");
            model.addAttribute("info_message", "Ошибка при покупке билета.");
            return "BuyTicket";
        }
    }
}
