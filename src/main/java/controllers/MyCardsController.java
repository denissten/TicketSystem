package controllers;

import database.Database;
import database.classes.Card;
import gui.Utill;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MyCardsController {

    @GetMapping("/mycards")
    public String myCardsPage(Model model,
                              @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                              @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if (!role.equals("1") || userId .equals("-1")) return "redirect:/";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        model.addAttribute("cards", database.getCards(Integer.parseInt(userId)));
        return "MyCards";
    }

    @PostMapping("/mycards")
    public String addCard(@CookieValue(value = "role", required = false, defaultValue = "none") String role,
                          @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId,
                          @RequestParam String bankName, @RequestParam String cardNumber, @RequestParam String date,
                          @RequestParam String cvv, Model model){
        if (!role.equals("1") || userId .equals("-1")) return "redirect:/";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        if (bankName.length() == 0 || cardNumber.length() == 0 || date.length() == 0 || cvv.length() == 0 ||
                    cvv.length() > 3 || cardNumber.length() > 16 || !Utill.isNumber(cardNumber) || !Utill.isNumber(cvv)){
            model.addAttribute("message_type", "error");
            model.addAttribute("info_message", "Проверьте правильность введенных данных.");
            model.addAttribute("cards", database.getCards(Integer.parseInt(userId)));
            return "MyCards";
        }
        Card card = new Card();
        card.setCardNumber(cardNumber);
        card.setBalance(0);
        card.setCVV(cvv);
        card.setDate(date);
        card.setBankName(bankName);
        card.setUser_id(Integer.parseInt(userId));
        if (database.addCard(card)) {
            model.addAttribute("message_type", "success");
            model.addAttribute("info_message", "Карта успешно добавлена.");
            model.addAttribute("cards", database.getCards(Integer.parseInt(userId)));
            return "MyCards";
        } else {
            model.addAttribute("message_type", "error");
            model.addAttribute("info_message", "Ошибка при добавлении карты.");
            model.addAttribute("cards", database.getCards(Integer.parseInt(userId)));
        }
        return "MyCards";
    }
    @PostMapping("/addbalance")
    public String addBalance(@RequestParam(value = "id", required = false) String id_card,
                             @RequestParam(value = "value", required = false) String value, Model model,
                             @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                             @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if (!role.equals("1") || userId .equals("-1")) return "redirect:/";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        if (id_card.equals("-1")){
            model.addAttribute("message_type", "error");
            model.addAttribute("info_message", "Выберите карточку.");
            model.addAttribute("cards", database.getCards(Integer.parseInt(userId)));
            return "MyCards";
        }
        if (!Utill.isNumberF(value)){
            model.addAttribute("message_type", "error");
            model.addAttribute("info_message", "Проверьте правильность введенных данных.");
            model.addAttribute("cards", database.getCards(Integer.parseInt(userId)));
            return "MyCards";
        }
        Card card = database.getCard(Integer.parseInt(userId), Integer.parseInt(id_card));
        card.setBalance(card.getBalance() + Float.parseFloat(value));
        database.updateCardBalance(card);
        model.addAttribute("cards", database.getCards(Integer.parseInt(userId)));
        return "redirect:/mycards";
    }
    @PostMapping("/deletecard")
    public String deleteCard(@RequestParam(value = "id", required = false) String id_card,
                             @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                             @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if (!role.equals("1") || userId .equals("-1")) return "redirect:/";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        Card card = new Card();
        card.setId_card(Integer.parseInt(id_card));
        card.setUser_id(Integer.parseInt(userId));
        database.deleteCard(card);
        return "redirect:/mycards";
    }
}
