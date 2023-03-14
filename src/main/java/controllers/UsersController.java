package controllers;

import database.Database;
import database.classes.User;
import gui.Utill;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class UsersController {
    @GetMapping("/users")
    public String usersPage(Model model,
                            @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                            @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if (!role.equals("3") || userId .equals("-1")) return "redirect:/";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        ArrayList<UserView> userViews = new ArrayList<>();
        for (User user : database.getUsers()){
            UserView userView = new UserView();
            userView.setId(user.getUser_id());
            userView.setName(user.getName());
            userView.setFamily(user.getFamilia());
            userView.setOtchestvo(user.getOtchestvo());
            userView.setRole(Utill.getRoleName(user.getRole()));
            userViews.add(userView);
        }
        model.addAttribute("users", userViews);
        return "Users";
    }
    @PostMapping("/changeuser")
    public String changeuser(@RequestParam String name, @RequestParam String familia, @RequestParam String id,
                             @RequestParam String otchestvo, @RequestParam String urole,
                             @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                             @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if (!role.equals("3") || userId .equals("-1")) return "redirect:/";
        if (name.length() == 0 || familia.length() == 0 || otchestvo.length() == 0) return "redirect:/users";
        if (id.equals(userId) && !urole.equals(role)) return "redirect:/users";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        User user = database.getUser(Integer.parseInt(id));
        user.setName(name);
        user.setFamilia(familia);
        user.setOtchestvo(otchestvo);
        user.setRole(Integer.parseInt(urole));
        database.changeUser(user);
        return "Users";
    }
    @PostMapping("/removeuser")
    public String removeuser(@RequestParam String id,
                             @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                             @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if (!role.equals("3") || userId .equals("-1")) return "redirect:/";
        if (id.equals(userId)) return "redirect:/users";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        User user = new User();
        user.setUser_id(Integer.parseInt(id));
        database.deleteUser(user);
        return "Users";
    }
    class UserView{
        int id;
        String name;
        String family;
        String otchestvo;
        String role;

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

        public String getFamily() {
            return family;
        }

        public void setFamily(String family) {
            this.family = family;
        }

        public String getOtchestvo() {
            return otchestvo;
        }

        public void setOtchestvo(String otchestvo) {
            this.otchestvo = otchestvo;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
}
