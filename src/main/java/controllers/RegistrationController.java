package controllers;

import database.Database;
import database.classes.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.*;

@Controller
public class RegistrationController {
    @GetMapping("/registration")
    public String registrationPage(){
        return "Registration";
    }
    @PostMapping("/registration")
    public String registration(@RequestParam String familia, @RequestParam String name,
                               @RequestParam String otchestvo, @RequestParam String login, @RequestParam String password, Model model){
        if (login.length() == 0 || password.length() == 0 || familia.length() == 0 || name.length() == 0 || otchestvo.length() == 0){
            model.addAttribute("message_type", "error");
            model.addAttribute("info_message", "Заполните все поля.");
            return "registration";
        }
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        if (database.loginIsRegistered(login)){
            model.addAttribute("message_type", "error");
            model.addAttribute("info_message", "Логин уже занят.");
            return "registration";
        }
        User user = new User(-1, familia, name, otchestvo, login, password, 1);
        if (database.accountRegistration(user)){
            model.addAttribute("message_type", "success");
            model.addAttribute("info_message", "Аккаунт зарегистрирован.");
            return "registration";
        } else {
            model.addAttribute("message_type", "error");
            model.addAttribute("info_message", "Ошибка при регистрации аккаунта.");
            return "registration";
        }
    }
}
