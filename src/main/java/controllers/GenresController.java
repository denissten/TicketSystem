package controllers;

import database.Database;
import database.classes.Genre;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GenresController {
    @GetMapping("/genres")
    public String genresPage(Model model,
                             @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                             @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if ((!role.equals("2") && !role.equals("3")) || userId .equals("-1")) return "redirect:/";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        model.addAttribute("genres", database.getAllGenres());
        return "Genres";
    }
    @PostMapping("/addgenre")
    public String addGenre(@RequestParam String name_genre,
                           @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                           @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if ((!role.equals("2") && !role.equals("3")) || userId .equals("-1")) return "redirect:/";
        if (name_genre.length() == 0) return "redirect:/genres";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        Genre genre = new Genre();
        genre.setName_genre(name_genre);
        database.addGenre(genre);
        return "redirect:/genres";
    }
    @PostMapping("/changegenre")
    public String changeGenre(@RequestParam String name_genre, @RequestParam String id,
                           @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                           @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if ((!role.equals("2") && !role.equals("3")) || userId .equals("-1")) return "redirect:/";
        if (name_genre.length() == 0 || id.length() == 0) return "redirect:/genres";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        Genre genre = new Genre();
        genre.setName_genre(name_genre);
        genre.setId_genre(Integer.parseInt(id));
        database.changeGenreName(genre);
        return "redirect:/genres";
    }
    @PostMapping("/removegenre")
    public String removeGenre(@RequestParam String id,
                           @CookieValue(value = "role", required = false, defaultValue = "none") String role,
                           @CookieValue(value = "userid", required = false, defaultValue = "-1") String userId){
        if ((!role.equals("2") && !role.equals("3")) || userId .equals("-1")) return "redirect:/";
        if (id.length() == 0) return "redirect:/genres";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextWeb.xml");
        Database database = context.getBean("Database", Database.class);
        Genre genre = new Genre();
        genre.setId_genre(Integer.parseInt(id));
        database.deleteGenre(genre);
        return "redirect:/genres";
    }
}
