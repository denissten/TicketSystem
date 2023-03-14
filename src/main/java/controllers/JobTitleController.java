package controllers;

import database.Database;
import database.classes.Doljnost;
import gui.Utill;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class JobTitleController {
    @GetMapping("/jobtitle")
    public String jobTitlePage(Model model,
                               @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                               @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if ((!role.equals("2") && !role.equals("3")) || userId .equals("-1")) return "redirect:/";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        model.addAttribute("doljnosts", database.getAllDoljnosts());
        return "JobTitle";
    }
    @PostMapping("/addjobtitle")
    public String addJobTitle(@RequestParam String oklad, @RequestParam String jobTitle,
                              @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                              @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if ((!role.equals("2") && !role.equals("3")) || userId .equals("-1")) return "redirect:/";
        if (oklad.length() == 0 || jobTitle.length() == 0 || !Utill.isNumberF(oklad)) return "redirect:/jobtitle";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        Doljnost doljnost = new Doljnost();
        doljnost.setName_doljnost(jobTitle);
        doljnost.setOklad(Float.parseFloat(oklad));
        database.addDoljnost(doljnost);
        return "redirect:/jobtitle";
    }
    @PostMapping("/changejobtitle")
    public String changeJobTitle(@RequestParam String oklad, @RequestParam String jobTitle, @RequestParam String id,
                                 @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                                 @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if ((!role.equals("2") && !role.equals("3")) || userId .equals("-1")) return "redirect:/";
        if (id.length() == 0 || oklad.length() == 0 || jobTitle.length() == 0 || !Utill.isNumberF(oklad)) return "redirect:/jobtitle";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        Doljnost doljnost = new Doljnost();
        doljnost.setId_doljnost(Integer.parseInt(id));
        doljnost.setOklad(Float.parseFloat(oklad));
        doljnost.setName_doljnost(jobTitle);
        database.changeDoljnost(doljnost);
        return "JobTitle";
    }
    @PostMapping("/removejobtitle")
    public String removeJobTitle(@RequestParam String id,
                                 @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                                 @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if ((!role.equals("2") && !role.equals("3")) || userId .equals("-1")) return "redirect:/";
        if (id.length() == 0) return "redirect:/jobtitle";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        Doljnost doljnost = new Doljnost();
        doljnost.setId_doljnost(Integer.parseInt(id));
        database.deleteDoljnost(doljnost);
        return "JobTitle";
    }
}
