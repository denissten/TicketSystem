package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {
    @GetMapping("/menu")
    public String menu(@CookieValue(value = "role", required = false, defaultValue = "none") String role){
        return switch (role) {
            case "1" -> "ClientMenu";
            case "2" -> "EmployeeMenu";
            case "3" -> "AdminMenu";
            default -> "redirect:/";
        };
    }
}
