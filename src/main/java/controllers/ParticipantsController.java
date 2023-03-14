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

import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Controller
public class ParticipantsController {
    @GetMapping("/participants")
    public String participantsPage(Model model,
                                   @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                                   @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if ((!role.equals("2") && !role.equals("3")) || userId .equals("-1")) return "redirect:/";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        ArrayList<ParticipantsView> participantsViews = new ArrayList<>();
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Uchastnik uchastnik : database.getSpisokUchastnikov()){
            ParticipantsView participantsView = new ParticipantsView();
            participantsView.setId(uchastnik.getId_spisok_uchastnikov());
            participantsView.setCharacters(uchastnik.getCharacters());
            Performance performance = database.getPerformance(uchastnik.getPerformance_id());
            participantsView.setPerformance_name(performance.getName_perfomance());
            participantsView.setDate(sdf.format(performance.getData()));
            Sotrudnik sotrudnik = database.getSotrudnik(uchastnik.getSotrudnik_id());
            participantsView.setOtchestvo(sotrudnik.getOtchestvo());
            participantsView.setName(sotrudnik.getName());
            participantsView.setFamilia(sotrudnik.getFamilia());
            Doljnost doljnost = database.getDoljnost(sotrudnik.getDoljnost_id());
            participantsView.setDoljnost(doljnost.getName_doljnost());
            participantsView.setOklad(Float.toString(doljnost.getOklad()));
            Voice voice = database.getVoice(sotrudnik.getVoice_id());
            participantsView.setVoice(voice.getTembr_voice());
            Pol pol = database.getPol(sotrudnik.getPol_id());
            participantsView.setPol(pol.getName_pol());
            participantsViews.add(participantsView);
        }
        model.addAttribute("participants", participantsViews);
        model.addAttribute("performances", database.getPerformances());
        model.addAttribute("sotrudniks", database.getAllSotrudniks());
        return "Participants";
    }
    @PostMapping("/addparticipants")
    public String addParticipants(@RequestParam String characters, @RequestParam String performances,
                                  @RequestParam String sotrudniks,
                                  @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                                  @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if ((!role.equals("2") && !role.equals("3")) || userId .equals("-1")) return "redirect:/";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        if (characters.length() == 0 || performances.length() == 0 || sotrudniks.length() == 0)
            return "redirect:/participants";
        Database database = context.getBean("Database", Database.class);
        Uchastnik uchastnik = new Uchastnik();
        uchastnik.setCharacters(characters);
        uchastnik.setSotrudnik_id(Integer.parseInt(sotrudniks));
        uchastnik.setPerformance_id(Integer.parseInt(performances));
        database.addUchastnik(uchastnik);
        return "Participants";
    }
    @PostMapping("/changeparticipants")
    public String changeparticipants(@RequestParam String characters, @RequestParam String performances,
                                     @RequestParam String sotrudniks, @RequestParam String id,
                                     @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                                     @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if ((!role.equals("2") && !role.equals("3")) || userId .equals("-1")) return "redirect:/";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        if (characters.length() == 0 || performances.length() == 0 || sotrudniks.length() == 0 || id.length() == 0)
            return "redirect:/participants";
        Database database = context.getBean("Database", Database.class);
        Uchastnik uchastnik = new Uchastnik();
        uchastnik.setId_spisok_uchastnikov(Integer.parseInt(id));
        uchastnik.setCharacters(characters);
        uchastnik.setSotrudnik_id(Integer.parseInt(sotrudniks));
        uchastnik.setPerformance_id(Integer.parseInt(performances));
        database.changeUchastnik(uchastnik);
        return "Participants";
    }
    @PostMapping("/removeparticipants")
    public String removeparticipants(@RequestParam String id,
                                     @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                                     @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if ((!role.equals("2") && !role.equals("3")) || userId .equals("-1")) return "redirect:/";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        if (id.length() == 0)
            return "redirect:/participants";
        Database database = context.getBean("Database", Database.class);
        Uchastnik uchastnik = new Uchastnik();
        uchastnik.setId_spisok_uchastnikov(Integer.parseInt(id));
        database.deleteUchastnik(uchastnik);
        return "Participants";
    }
    class ParticipantsView{
        int id;
        String characters;
        String performance_name;
        String date;
        String otchestvo;
        String name;
        String familia;
        String doljnost;
        String oklad;
        String voice;
        String pol;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCharacters() {
            return characters;
        }

        public void setCharacters(String characters) {
            this.characters = characters;
        }

        public String getPerformance_name() {
            return performance_name;
        }

        public void setPerformance_name(String performance_name) {
            this.performance_name = performance_name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getOtchestvo() {
            return otchestvo;
        }

        public void setOtchestvo(String otchestvo) {
            this.otchestvo = otchestvo;
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

        public String getDoljnost() {
            return doljnost;
        }

        public void setDoljnost(String doljnost) {
            this.doljnost = doljnost;
        }

        public String getOklad() {
            return oklad;
        }

        public void setOklad(String oklad) {
            this.oklad = oklad;
        }

        public String getVoice() {
            return voice;
        }

        public void setVoice(String voice) {
            this.voice = voice;
        }

        public String getPol() {
            return pol;
        }

        public void setPol(String pol) {
            this.pol = pol;
        }
    }
}
