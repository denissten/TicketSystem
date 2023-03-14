package controllers;

import database.Database;
import database.classes.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class EmployeesController {
    @GetMapping("/employees")
    public String employeesPage(Model model,
                                @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                                @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if ((!role.equals("2") && !role.equals("3")) || userId .equals("-1")) return "redirect:/";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        ArrayList<SotrudnikView> sotrudnikViews = new ArrayList<>();
        for (Sotrudnik sotrudnik : database.getAllSotrudniks()){
            SotrudnikView sotrudnikView = new SotrudnikView();
            sotrudnikView.setId_sotrudnik(sotrudnik.getId_sotrudnik());
            sotrudnikView.setFamilia(sotrudnik.getFamilia());
            sotrudnikView.setName(sotrudnik.getName());
            sotrudnikView.setOtchestvo(sotrudnik.getOtchestvo());
            Pol pol = database.getPol(sotrudnik.getPol_id());
            sotrudnikView.setPol(pol.getName_pol());
            Voice voice = database.getVoice(sotrudnik.getVoice_id());
            sotrudnikView.setVoice(voice.getTembr_voice());
            Doljnost doljnost = database.getDoljnost(sotrudnik.getDoljnost_id());
            sotrudnikView.setOklad(doljnost.getOklad());
            sotrudnikView.setDoljnost(doljnost.getName_doljnost());
            sotrudnikViews.add(sotrudnikView);
        }
        model.addAttribute("employees", sotrudnikViews);
        model.addAttribute("pols", database.getAllPol());
        model.addAttribute("voices", database.getAllVoices());
        model.addAttribute("doljnosts", database.getAllDoljnosts());
        return "Employees";
    }
    @PostMapping("/addemployee")
    public String addEmployee(@RequestParam String name, @RequestParam String familia,
                              @RequestParam String otchestvo, @RequestParam String pol,
                              @RequestParam String voice, @RequestParam String doljnost,
                              @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                              @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if ((!role.equals("2") && !role.equals("3")) || userId .equals("-1")) return "redirect:/";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        if (name.length() == 0 || familia.length() == 0 || otchestvo.length() == 0 || pol.length() == 0 || voice.length() == 0 || doljnost.length() == 0)
            return "redirect:/employees";
        Database database = context.getBean("Database", Database.class);
        Sotrudnik sotrudnik = new Sotrudnik();
        sotrudnik.setName(name);
        sotrudnik.setFamilia(familia);
        sotrudnik.setOtchestvo(otchestvo);
        sotrudnik.setDoljnost_id(Integer.parseInt(doljnost));
        sotrudnik.setVoice_id(Integer.parseInt(voice));
        sotrudnik.setPol_id(Integer.parseInt(pol));
        database.addSotrudnik(sotrudnik);
        return "Employees";
    }
    @PostMapping("/changeemployee")
    public String changeEmployee(@RequestParam String name, @RequestParam String familia,
                              @RequestParam String otchestvo, @RequestParam String pol,
                              @RequestParam String voice, @RequestParam String doljnost, @RequestParam String id,
                              @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                              @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if ((!role.equals("2") && !role.equals("3")) || userId .equals("-1")) return "redirect:/";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        if (name.length() == 0 || familia.length() == 0 || otchestvo.length() == 0 || pol.length() == 0 || voice.length() == 0 || doljnost.length() == 0)
            return "redirect:/employees";
        Database database = context.getBean("Database", Database.class);
        Sotrudnik sotrudnik = new Sotrudnik();
        sotrudnik.setId_sotrudnik(Integer.parseInt(id));
        sotrudnik.setName(name);
        sotrudnik.setFamilia(familia);
        sotrudnik.setOtchestvo(otchestvo);
        sotrudnik.setDoljnost_id(Integer.parseInt(doljnost));
        sotrudnik.setVoice_id(Integer.parseInt(voice));
        sotrudnik.setPol_id(Integer.parseInt(pol));
        database.changeSotrudnik(sotrudnik);
        return "Employees";
    }
    @PostMapping("/removeemployee")
    public String removeEmployee(@RequestParam String id,
                                 @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                                 @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if ((!role.equals("2") && !role.equals("3")) || userId .equals("-1")) return "redirect:/";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        Sotrudnik sotrudnik = new Sotrudnik();
        sotrudnik.setId_sotrudnik(Integer.parseInt(id));
        database.deleteSotrudnik(sotrudnik);
        return "Employees";
    }
    class SotrudnikView{
        private int id_sotrudnik;
        private String familia;
        private String name;
        private String otchestvo;
        private String pol;
        private String voice;
        private String doljnost;
        private float oklad;

        public float getOklad() {
            return oklad;
        }

        public void setOklad(float oklad) {
            this.oklad = oklad;
        }

        public int getId_sotrudnik() {
            return id_sotrudnik;
        }

        public void setId_sotrudnik(int id_sotrudnik) {
            this.id_sotrudnik = id_sotrudnik;
        }

        public String getFamilia() {
            return familia;
        }

        public void setFamilia(String familia) {
            this.familia = familia;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOtchestvo() {
            return otchestvo;
        }

        public void setOtchestvo(String otchestvo) {
            this.otchestvo = otchestvo;
        }

        public String getPol() {
            return pol;
        }

        public void setPol(String pol) {
            this.pol = pol;
        }

        public String getVoice() {
            return voice;
        }

        public void setVoice(String voice) {
            this.voice = voice;
        }

        public String getDoljnost() {
            return doljnost;
        }

        public void setDoljnost(String doljnost) {
            this.doljnost = doljnost;
        }
    }
}
