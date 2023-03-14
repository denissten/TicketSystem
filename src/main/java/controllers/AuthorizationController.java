package controllers;

import database.Database;
import database.classes.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthorizationController {
    @GetMapping("/")
    public String authorizationPage(){
        return "Authorization";
    }

    @PostMapping("/")
    public String authorization(@RequestParam String login, @RequestParam String password, Model model, HttpServletResponse response){
        if (password.length() == 0 || login.length() ==0){
            model.addAttribute("message_type", "error");
            model.addAttribute("info_message", "Заполните все поля.");
            return "Authorization";
        }
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        User user = database.userAuthorization(login, password);
        if (user.getUser_id() != -1){
            response.addCookie(new Cookie("role", Integer.toString(user.getRole())));
            response.addCookie(new Cookie("userid", Integer.toString(user.getUser_id())));
            return "redirect:/menu";
        } else {
            model.addAttribute("message_type", "error");
            model.addAttribute("info_message", "Неверный логин или пароль.");
            return "Authorization";
        }
    }
}
