package controllers;

import database.Database;
import database.classes.Doljnost;
import database.classes.Performance;
import gui.Utill;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Controller
public class PerfomancesController {
    @GetMapping("/performances")
    public String perfomancesPage(Model model,
                                  @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                                  @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if ((!role.equals("2") && !role.equals("3")) || userId .equals("-1")) return "redirect:/";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        ArrayList<PerformanceView> performanceViews = new ArrayList<>();
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Performance performance : database.getPerformances()){
            PerformanceView performanceView = new PerformanceView();
            performanceView.setPrice(performance.getPrice());
            performanceView.setDate(sdf.format(performance.getData()));
            performanceView.setName_perfomance(performance.getName_perfomance());
            performanceView.setId_perfomance(performance.getId_perfomance());
            performanceViews.add(performanceView);
        }
        model.addAttribute("performances", performanceViews);
        return "Performances";
    }
    @PostMapping("/addperformance")
    public String addPerformance(@RequestParam String date, @RequestParam String name_perfomance, @RequestParam String price,
                                 @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                                 @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if ((!role.equals("2") && !role.equals("3")) || userId .equals("-1")) return "redirect:/";
        if (date.length() == 0 || name_perfomance.length() == 0 || price.length() == 0) return "redirect:/performances";
        if (!Utill.isNumberF(price)) return "redirect:/performances";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        Performance performance = new Performance();
        performance.setData(new Date(Long.parseLong(date)));
        performance.setName_perfomance(name_perfomance);
        performance.setPrice(Float.parseFloat(price));
        database.addPerformance(performance);
        return "redirect:/performances";
    }
    @PostMapping("/changeperformance")
    public String changePerformance(@RequestParam String date, @RequestParam String name_perfomance,
                                    @RequestParam String price, @RequestParam String id,
                                 @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                                 @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if ((!role.equals("2") && !role.equals("3")) || userId .equals("-1")) return "redirect:/";
        if (date.length() == 0 || name_perfomance.length() == 0 || price.length() == 0) return "redirect:/performances";
        if (!Utill.isNumberF(price)) return "redirect:/performances";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        Performance performance = new Performance();
        performance.setId_perfomance(Integer.parseInt(id));
        performance.setData(new Date(Long.parseLong(date)));
        performance.setName_perfomance(name_perfomance);
        performance.setPrice(Float.parseFloat(price));
        database.changePerformance(performance);
        return "redirect:/performances";
    }
    @PostMapping("/removeperformance")
    public String removePerformance(@RequestParam String id,
                                    @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                                    @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if ((!role.equals("2") && !role.equals("3")) || userId .equals("-1")) return "redirect:/";
        if (id.length() == 0) return "redirect:/performances";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        Performance performance = new Performance();
        performance.setId_perfomance(Integer.parseInt(id));
        database.deletePerformance(performance);
        return "redirect:/performances";
    }
    class PerformanceView{
        int id_perfomance;
        String name_perfomance;
        String date;
        float price;

        public int getId_perfomance() {
            return id_perfomance;
        }

        public void setId_perfomance(int id_perfomance) {
            this.id_perfomance = id_perfomance;
        }

        public String getName_perfomance() {
            return name_perfomance;
        }

        public void setName_perfomance(String name_perfomance) {
            this.name_perfomance = name_perfomance;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }
    }
}
