package controllers;

import database.Database;
import database.classes.Performance;
import database.classes.Ticket;
import database.classes.User;
import database.classes.Zal;
import gui.Utill;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.image.AreaAveragingScaleFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Controller
public class TicketsController {
    @GetMapping("/tickets")
    public String ticketsPage(Model model,
                              @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                              @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if ((!role.equals("2") && !role.equals("3")) || userId .equals("-1")) return "redirect:/";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        ArrayList<TicketsView> ticketsViews = new ArrayList<>();
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Ticket ticket : database.getAllTickets()){
            TicketsView ticketsView = new TicketsView();
            ticketsView.setUserid(ticket.getUser_id());
            ticketsView.setId(ticket.getId_ticket());
            User user = database.getUser(ticket.getUser_id());
            ticketsView.setName(user.getName());
            ticketsView.setFamilia(user.getFamilia());
            ticketsView.setOtchestvo(user.getOtchestvo());
            ticketsView.setOrgname(ticket.getNaimenovanie_organizacii());
            Performance performance = database.getPerformance(ticket.getPerformance_id());
            ticketsView.setPerformance(performance.getName_perfomance());
            ticketsView.setDate(sdf.format(performance.getData()));
            Zal zal = database.getZal(ticket.getZal_id());
            ticketsView.setAdres(zal.getAdres());
            ticketsView.setTickets(Integer.toString(ticket.getKolichestvo_biletov()));
            ticketsView.setPrice(Float.toString(ticket.getFinal_price()));
            ticketsView.setStatus(Utill.getStatusName(ticket.getStatus()));
            ticketsViews.add(ticketsView);
        }
        model.addAttribute("tickets", ticketsViews);
        return "Tickets";
    }
    @PostMapping("/tickets")
    public String changeStatus(@RequestParam String status, @RequestParam String id, @RequestParam String uid,
                               @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                               @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if ((!role.equals("2") && !role.equals("3")) || userId .equals("-1")) return "redirect:/";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        database.changeTicketStatus(Integer.parseInt(status), Integer.parseInt(id), Integer.parseInt(uid));
        return "Tickets";
    }
    class TicketsView{
        int id;
        String name;
        String familia;
        String otchestvo;
        String orgname;
        String date;
        String adres;
        String performance;
        String tickets;
        String price;
        String status;
        int userid;

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFamilia() {
            return familia;
        }

        public void setFamilia(String familia) {
            this.familia = familia;
        }

        public String getOtchestvo() {
            return otchestvo;
        }

        public void setOtchestvo(String otchestvo) {
            this.otchestvo = otchestvo;
        }

        public String getOrgname() {
            return orgname;
        }

        public void setOrgname(String orgname) {
            this.orgname = orgname;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getAdres() {
            return adres;
        }

        public void setAdres(String adres) {
            this.adres = adres;
        }

        public String getPerformance() {
            return performance;
        }

        public void setPerformance(String performance) {
            this.performance = performance;
        }

        public String getTickets() {
            return tickets;
        }

        public void setTickets(String tickets) {
            this.tickets = tickets;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
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
