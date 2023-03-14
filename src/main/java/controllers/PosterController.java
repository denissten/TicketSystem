package controllers;

import database.Database;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PosterController {
    @GetMapping("/poster")
    public String posterPage(Model model, @CookieValue(value = "role", required = false, defaultValue = "none") String role){
        if (!role.equals("1")) return "redirect:/";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        model.addAttribute("posters", database.getPerformances());
        return "Poster";
    }
}
