package controllers;

import database.Database;
import database.classes.Performance;
import database.classes.Ticket;
import database.classes.Zal;
import gui.Utill;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Controller
public class MyTicketsController {
    @GetMapping("mytickets")
    public String myTicketsPage(Model model,
                                @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                                @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if (!role.equals("1") || userId .equals("-1")) return "redirect:/";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        ArrayList<TicketsView> tickets = new ArrayList<>();
        ArrayList<Ticket> userTickets = database.getUserTickets(Integer.parseInt(userId));
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(Ticket ticket : userTickets){
            TicketsView ticketsView = new TicketsView();
            ticketsView.setNaimenovanie_organizacii(ticket.getNaimenovanie_organizacii());
            Performance performance = database.getPerformance(ticket.getPerformance_id());
            ticketsView.setPerfomance_name(performance.getName_perfomance());
            Zal zal = database.getZal(ticket.getZal_id());
            ticketsView.setAdres(zal.getAdres());
            ticketsView.setDate(sdf.format(performance.getData()));
            ticketsView.setTicketCount(ticket.getKolichestvo_biletov());
            ticketsView.setPrice(ticket.getFinal_price());
            ticketsView.setStatus(Utill.getStatusName(ticket.getStatus()));
            tickets.add(ticketsView);
        }
        model.addAttribute("tickets", tickets);
        return "MyTickets";
    }
    class TicketsView{
        String naimenovanie_organizacii;
        String perfomance_name;
        String adres;
        String date;
        int ticketCount;
        float price;
        String status;

        public String getNaimenovanie_organizacii() {
            return naimenovanie_organizacii;
        }

        public void setNaimenovanie_organizacii(String naimenovanie_organizacii) {
            this.naimenovanie_organizacii = naimenovanie_organizacii;
        }

        public String getPerfomance_name() {
            return perfomance_name;
        }

        public void setPerfomance_name(String perfomance_name) {
            this.perfomance_name = perfomance_name;
        }

        public String getAdres() {
            return adres;
        }

        public void setAdres(String adres) {
            this.adres = adres;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getTicketCount() {
            return ticketCount;
        }

        public void setTicketCount(int ticketCount) {
            this.ticketCount = ticketCount;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
