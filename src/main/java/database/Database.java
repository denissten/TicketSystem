package database;

import database.classes.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Database {
    private String url;
    private String username;
    private String password;

    public Database(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private ResultSet executeQuery(String query){
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return rs;
    }

    private int executeUpdate(String query){
        Connection con = null;
        Statement stmt = null;
        int i = -1;
        try {
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            i = stmt.executeUpdate(query);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return i;
    }

    public boolean loginIsRegistered(String login){
        String query = "select count(*) from `user` where login = '" + login + "'";
        ResultSet resultSet = executeQuery(query);
        if (resultSet == null){
            System.out.println("Ошибка при выполнении запроса.");
            return true;
        }
        try {
            resultSet.next();
            int i = resultSet.getInt(1);
            if (i == 0) return false;
            else return true;
        } catch (Exception exception){
            exception.printStackTrace();
            return true;
        }
    }

    public boolean accountRegistration(User user){
        if (loginIsRegistered(user.getLogin()))
            return false;

        String query = "insert into `user` (`familia`, `name`, `otchestvo`, `login`, `password`, `role`) values (" +
                "'" + user.getFamilia() + "', '" + user.getName() + "', '" + user.getOtchestvo() + "', '" + user.getLogin() +
                "', '" + user.getPassword() + "', '" + user.getRole() + "')";
        int i = executeUpdate(query);
        System.out.println(i);
        return i == 1;
    }

    public User userAuthorization(String login, String password){
        String query = "select * from `user` where login = '" + login + "' and password = '" + password +"'";
        User user = new User();
        user.setUser_id(-1);
        ResultSet resultSet = executeQuery(query);
        if (resultSet == null){
            System.out.println("Ошибка при выполнении запроса.");
            return user;
        }
        try {
            resultSet.next();
            if (resultSet.getRow() <= 0){
                return user;
            }
            user.setUser_id(resultSet.getInt("user_id"));
            user.setFamilia(resultSet.getString("familia"));
            user.setName(resultSet.getString("name"));
            user.setOtchestvo(resultSet.getString("otchestvo"));
            user.setLogin(resultSet.getString("login"));
            user.setPassword(resultSet.getString("password"));
            user.setRole(resultSet.getInt("role"));
            return user;
        } catch (Exception exception){
            exception.printStackTrace();
            return user;
        }
    }

    public User getUser(int user_id){
        String query = "SELECT * FROM `user` WHERE user_id = '" + user_id + "'";
        User user = new User();
        ResultSet resultSet = executeQuery(query);
        if (resultSet == null){
            System.out.println("Ошибка при выполнении запроса.");
            return user;
        }
        try {
            resultSet.next();
            user.setUser_id(resultSet.getInt("user_id"));
            user.setFamilia(resultSet.getString("familia"));
            user.setName(resultSet.getString("name"));
            user.setOtchestvo(resultSet.getString("otchestvo"));
            user.setLogin(resultSet.getString("login"));
            user.setPassword(resultSet.getString("password"));
            user.setRole(resultSet.getInt("role"));
        } catch (Exception exception){
            exception.printStackTrace();
            return user;
        }
        return user;
    }

    public ArrayList<User> getUsers(){
        String query = "SELECT * FROM `user`";
        ArrayList<User> users = new ArrayList<>();
        ResultSet resultSet = executeQuery(query);
        if (resultSet == null){
            System.out.println("Ошибка при выполнении запроса.");
            return users;
        }
        try {
            while (resultSet.next()){
                User user = new User();
                user.setUser_id(resultSet.getInt("user_id"));
                user.setFamilia(resultSet.getString("familia"));
                user.setName(resultSet.getString("name"));
                user.setOtchestvo(resultSet.getString("otchestvo"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getInt("role"));
                users.add(user);
            }
        } catch (Exception exception){
            exception.printStackTrace();
            return users;
        }
        return users;
    }
    public boolean changeUser(User user){
        String query = "UPDATE `user` SET `familia`='" + user.getFamilia() + "',`name`='" + user.getName() +
                "',`otchestvo`='" + user.getOtchestvo() + "',`login`='" + user.getLogin() + "',`password`='" + user.getPassword() +
                "',`role`='" + user.getRole() + "' WHERE user_id = '" + user.getUser_id() + "'";
        int i = executeUpdate(query);
        return i == 1;
    }
    public boolean deleteUser(User user){
        String query = "DELETE FROM `user` WHERE user_id = '" + user.getUser_id() + "'";
        int i = executeUpdate(query);
        return i == 1;
    }
    public boolean addCard(Card card){
        String query = "INSERT INTO `cards`(`bank_name`, `card_number`, `cvv`, `date`, `user_id`, `balance`)" +
                " VALUES ('" + card.getBankName() + "', '" + card.getCardNumber() + "', '" + card.getCVV() + "', '" + card.getDate() +
                "', '" + card.getUser_id() + "', '" + card.getBalance() + "')";
        int i = executeUpdate(query);
        return i == 1;
    }
    public Card getCard(int userId, int id_card){
        String query = "SELECT `id_card`, `bank_name`, `card_number`, `cvv`, `date`, `user_id`, `balance` FROM `cards` WHERE user_id = '" + userId + "' AND id_card = '" + id_card + "'";
        ResultSet resultSet = executeQuery(query);
        Card card = new Card();
        if (resultSet == null){
            System.out.println("Ошибка при выполнении запроса.");
            return card;
        }
        try {
            while (resultSet.next()){
                card.setId_card(resultSet.getInt("id_card"));
                card.setBankName(resultSet.getString("bank_name"));
                card.setCardNumber(resultSet.getString("card_number"));
                card.setCVV(resultSet.getString("cvv"));
                card.setDate(resultSet.getString("date"));
                card.setUser_id(resultSet.getInt("user_id"));
                card.setBalance(resultSet.getFloat("balance"));
            }
        } catch (Exception exception){
            exception.printStackTrace();
            return card;
        }
        return card;
    }
    public boolean updateCardBalance(Card card){
        String query = "UPDATE `cards` SET `balance`='" + card.getBalance() + "' WHERE id_card = '" +
                card.getId_card() +"' and user_id = '" + card.getUser_id() +"'";
        int i = executeUpdate(query);
        return i == 1;
    }

    public ArrayList<Card> getCards(int userId){
        String query = "SELECT `id_card`, `bank_name`, `card_number`, `cvv`, `date`, `user_id`, `balance` FROM `cards` WHERE user_id = '" + userId + "'";
        ArrayList<Card> cards = new ArrayList<>();
        ResultSet resultSet = executeQuery(query);
        if (resultSet == null){
            System.out.println("Ошибка при выполнении запроса.");
            return cards;
        }
        try {
            while (resultSet.next()){
                Card card = new Card();
                card.setId_card(resultSet.getInt("id_card"));
                card.setBankName(resultSet.getString("bank_name"));
                card.setCardNumber(resultSet.getString("card_number"));
                card.setCVV(resultSet.getString("cvv"));
                card.setDate(resultSet.getString("date"));
                card.setUser_id(resultSet.getInt("user_id"));
                card.setBalance(resultSet.getFloat("balance"));
                cards.add(card);
            }
        } catch (Exception exception){
            exception.printStackTrace();
            return cards;
        }
        return cards;
    }

    public boolean deleteCard(Card card){
        String query = "DELETE FROM `cards` WHERE id_card = '" + card.getId_card() + "' and user_id = '" + card.getUser_id() + "'";
        int i = executeUpdate(query);
        return i == 1;
    }

    public ArrayList<Performance> getPerformances(){
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String query = "SELECT * FROM `performance`";
        ArrayList<Performance> performances = new ArrayList<>();
        ResultSet resultSet = executeQuery(query);
        if (resultSet == null){
            System.out.println("Ошибка при выполнении запроса.");
            return performances;
        }
        try {
            while (resultSet.next()){
                Performance performance = new Performance();
                performance.setId_perfomance(resultSet.getInt("id_performance"));
                performance.setName_perfomance(resultSet.getString("name_performance"));
                java.util.Date date = sdf.parse(resultSet.getString("data"));
                performance.setData(new java.sql.Date(date.getTime()));
                performance.setPrice(resultSet.getFloat("price"));
                performances.add(performance);
            }
        } catch (Exception exception){
            exception.printStackTrace();
            return performances;
        }
        return performances;
    }

    public Performance getPerformance(int performance_id){
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String query = "SELECT * FROM `performance` WHERE id_performance = '" + performance_id + "'";
        Performance performance = new Performance();
        performance.setName_perfomance("");
        ResultSet resultSet = executeQuery(query);
        if (resultSet == null){
            System.out.println("Ошибка при выполнении запроса.");
            return performance;
        }
        try {
            resultSet.next();
            performance.setId_perfomance(resultSet.getInt("id_performance"));
            performance.setName_perfomance(resultSet.getString("name_performance"));
            java.util.Date date = sdf.parse(resultSet.getString("data"));
            performance.setData(new java.sql.Date(date.getTime()));
            performance.setPrice(resultSet.getInt("price"));
            return performance;
        } catch (Exception exception){
            exception.printStackTrace();
            return performance;
        }
    }

    public boolean addPerformance(Performance performance){
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String query = "INSERT INTO `performance`(`name_performance`, `data`, `price`) VALUES ('" + performance.getName_perfomance() +
                "','" + sdf.format(performance.getData()) +"','" + performance.getPrice() + "')";
        int i = executeUpdate(query);
        return i == 1;
    }

    public boolean changePerformance(Performance performance){
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String query = "UPDATE `performance` SET `name_performance`='" + performance.getName_perfomance() +
                "',`data`='" + sdf.format(performance.getData()) + "',`price`='" + performance.getPrice() +  "' WHERE id_performance = '" +
                performance.getId_perfomance() +"'";
        int i = executeUpdate(query);
        return i == 1;
    }

    public boolean deletePerformance(Performance performance){
        String query = "DELETE FROM `performance` WHERE id_performance = '" + performance.getId_perfomance() +"'";
        int i = executeUpdate(query);
        return i == 1;
    }
    public boolean addTicket(Ticket ticket){
        String query = "INSERT INTO `check_t`(`kolichestvo_biletov`, `naimenovanie_organizacii`, `status`, `user_id`, `zal_id`, `performance_id`, `final_price`) " +
                "VALUES ('" + ticket.getKolichestvo_biletov() + "','" + ticket.getNaimenovanie_organizacii() + "','" + ticket.getStatus() +
                "','" + ticket.getUser_id() + "','" + ticket.getZal_id() + "','" + ticket.getPerformance_id() + "','" + ticket.getFinal_price() + "')";
        int i = executeUpdate(query);
        return i == 1;
    }

    public boolean changeTicket(Ticket ticket){
        String query = "UPDATE `check_t` SET `kolichestvo_biletov`='" + ticket.getKolichestvo_biletov() +
                "',`naimenovanie_organizacii`='" + ticket.getNaimenovanie_organizacii() + "',`status`='" + ticket.getStatus() +
                "',`user_id`='" + ticket.getUser_id() + "',`zal_id`='" + ticket.getZal_id() +
                "',`performance_id`='" + ticket.getPerformance_id() + "',`final_price`='" + ticket.getFinal_price() + "' WHERE id_ticket = '" + ticket.getId_ticket() + "'";
        int i = executeUpdate(query);
        return i == 1;
    }
    public boolean changeTicketStatus(int status, int ticketId, int userId){
        String query = "UPDATE `check_t` SET `status`='" + status +
                "' WHERE id_ticket = '" + ticketId + "' AND user_id = '" + userId + "'";
        int i = executeUpdate(query);
        return i == 1;
    }
    public ArrayList<Ticket> getAllTickets(){
        String query = "SELECT * FROM `check_t`";
        ArrayList<Ticket> tickets = new ArrayList<>();
        ResultSet resultSet = executeQuery(query);
        if (resultSet == null){
            System.out.println("Ошибка при выполнении запроса.");
            return tickets;
        }
        try {
            while (resultSet.next()){
                Ticket ticket = new Ticket();
                ticket.setId_ticket(resultSet.getInt("id_ticket"));
                ticket.setKolichestvo_biletov(resultSet.getInt("kolichestvo_biletov"));
                ticket.setNaimenovanie_organizacii(resultSet.getString("naimenovanie_organizacii"));
                ticket.setStatus(resultSet.getInt("status"));
                ticket.setUser_id(resultSet.getInt("user_id"));
                ticket.setZal_id(resultSet.getInt("zal_id"));
                ticket.setPerformance_id(resultSet.getInt("performance_id"));
                ticket.setFinal_price(resultSet.getFloat("final_price"));
                tickets.add(ticket);
            }
        } catch (Exception exception){
            exception.printStackTrace();
            return tickets;
        }
        return tickets;
    }

    public ArrayList<Ticket> getUserTickets(int user_id){
        String query = "SELECT * FROM `check_t` where user_id = '" + user_id + "'";
        ArrayList<Ticket> tickets = new ArrayList<>();
        ResultSet resultSet = executeQuery(query);
        if (resultSet == null){
            System.out.println("Ошибка при выполнении запроса.");
            return tickets;
        }
        try {
            while (resultSet.next()){
                Ticket ticket = new Ticket();
                ticket.setId_ticket(resultSet.getInt("id_ticket"));
                ticket.setKolichestvo_biletov(resultSet.getInt("kolichestvo_biletov"));
                ticket.setNaimenovanie_organizacii(resultSet.getString("naimenovanie_organizacii"));
                ticket.setStatus(resultSet.getInt("status"));
                ticket.setUser_id(resultSet.getInt("user_id"));
                ticket.setZal_id(resultSet.getInt("zal_id"));
                ticket.setPerformance_id(resultSet.getInt("performance_id"));
                ticket.setFinal_price(resultSet.getFloat("final_price"));
                tickets.add(ticket);
            }
        } catch (Exception exception){
            exception.printStackTrace();
            return tickets;
        }
        return tickets;
    }

    public Zal getZal(int id_zal){
        String query = "SELECT * FROM `zal` WHERE id_zal = '" + id_zal + "'";
        Zal zal = new Zal();
        ResultSet resultSet = executeQuery(query);
        if (resultSet == null){
            System.out.println("Ошибка при выполнении запроса.");
            return zal;
        }
        try {
            resultSet.next();
            zal.setId_zal(resultSet.getInt("id_zal"));
            zal.setKolichestvo_mest(resultSet.getInt("kolichestvo_mest"));
            zal.setAdres(resultSet.getString("adres"));
        } catch (Exception exception){
            exception.printStackTrace();
            return zal;
        }
        return zal;
    }

    public boolean addZal(Zal zal){
        String query = "INSERT INTO `zal`(`kolichestvo_mest`, `adres`) VALUES ('" + zal.getKolichestvo_mest() + "','" + zal.getAdres() + "')";
        int i = executeUpdate(query);
        return i == 1;
    }

    public ArrayList<Genre> getAllGenres(){
        String query = "SELECT * FROM `genre`";
        ArrayList<Genre> genres = new ArrayList<>();
        ResultSet resultSet = executeQuery(query);
        if (resultSet == null){
            System.out.println("Ошибка при выполнении запроса.");
            return genres;
        }
        try {
            while (resultSet.next()){
                Genre genre = new Genre();
                genre.setId_genre(resultSet.getInt("id_genre"));
                genre.setName_genre(resultSet.getString("name_genre"));
                genres.add(genre);
            }
        } catch (Exception exception){
            exception.printStackTrace();
            return genres;
        }
        return genres;
    }

    public boolean addGenre(Genre genre){
        String query = "INSERT INTO `genre`(`name_genre`) VALUES ('" + genre.getName_genre() + "')";
        int i = executeUpdate(query);
        return i == 1;
    }

    public Genre getGenre(int id_genre){
        String query = "SELECT * FROM `genre` WHERE id_genre = '" + id_genre + "'";
        Genre genre = new Genre();
        genre.setName_genre("");
        ResultSet resultSet = executeQuery(query);
        if (resultSet == null){
            System.out.println("Ошибка при выполнении запроса.");
            return genre;
        }
        try {
            resultSet.next();
            genre.setId_genre(resultSet.getInt("id_genre"));
            genre.setName_genre(resultSet.getString("name_genre"));
        } catch (Exception exception){
            exception.printStackTrace();
            return genre;
        }
        return genre;
    }

    public boolean changeGenreName(Genre genre){
        String query = "UPDATE `genre` SET `name_genre`='" + genre.getName_genre() + "' WHERE id_genre = '" + genre.getId_genre() + "'";
        int i = executeUpdate(query);
        return i == 1;
    }

    public boolean deleteGenre(Genre genre){
        String query = "DELETE FROM `genre` WHERE id_genre = '" + genre.getId_genre() + "'";
        int i = executeUpdate(query);
        return i == 1;
    }

    public boolean addVoice(Voice voice){
        String query = "INSERT INTO `voice`(`tembr_voice`) VALUES ('" + voice.getTembr_voice() + "')";
        int i = executeUpdate(query);
        return i == 1;
    }

    public Voice getVoice(int voice_id){
        String query = "SELECT * FROM `voice` WHERE voice_id = '" + voice_id + "'";
        Voice voice = new Voice();
        voice.setTembr_voice("");
        ResultSet resultSet = executeQuery(query);
        if (resultSet == null){
            System.out.println("Ошибка при выполнении запроса.");
            return voice;
        }
        try {
            resultSet.next();
            voice.setVoice_id(resultSet.getInt("voice_id"));
            voice.setTembr_voice(resultSet.getString("tembr_voice"));
        } catch (Exception exception){
            exception.printStackTrace();
            return voice;
        }
        return voice;
    }

    public ArrayList<Voice> getAllVoices(){
        String query = "SELECT * FROM `voice`";
        ArrayList<Voice> voices = new ArrayList<>();
        ResultSet resultSet = executeQuery(query);
        if (resultSet == null){
            System.out.println("Ошибка при выполнении запроса.");
            return voices;
        }
        try {
            while (resultSet.next()) {
                Voice voice = new Voice();
                voice.setVoice_id(resultSet.getInt("voice_id"));
                voice.setTembr_voice(resultSet.getString("tembr_voice"));
                voices.add(voice);
            }
        } catch (Exception exception){
            exception.printStackTrace();
            return voices;
        }
        return voices;
    }

    public boolean deleteDoljnost(Doljnost doljnost){
        String query = "DELETE FROM `doljnost` WHERE id_doljnost = '" + doljnost.getId_doljnost() + "'";
        int i = executeUpdate(query);
        return i == 1;
    }

    public boolean addDoljnost(Doljnost doljnost){
        String query = "INSERT INTO `doljnost`(`name_doljnost`, `oklad`) VALUES ('" + doljnost.getName_doljnost() + "', '" + doljnost.getOklad() + "')";
        int i = executeUpdate(query);
        return i == 1;
    }

    public ArrayList<Doljnost> getAllDoljnosts(){
        String query = "SELECT * FROM `doljnost`";
        ArrayList<Doljnost> doljnosts = new ArrayList<>();
        ResultSet resultSet = executeQuery(query);
        if (resultSet == null){
            System.out.println("Ошибка при выполнении запроса.");
            return doljnosts;
        }
        try {
            while (resultSet.next()) {
                Doljnost doljnost = new Doljnost();
                doljnost.setId_doljnost(resultSet.getInt("id_doljnost"));
                doljnost.setName_doljnost(resultSet.getString("name_doljnost"));
                doljnost.setOklad(resultSet.getFloat("oklad"));
                doljnosts.add(doljnost);
            }
        } catch (Exception exception){
            exception.printStackTrace();
            return doljnosts;
        }
        return doljnosts;
    }

    public boolean changeDoljnost(Doljnost doljnost){
        String query = "UPDATE `doljnost` SET `name_doljnost`='" + doljnost.getName_doljnost() + "',`oklad`='" + doljnost.getOklad() +
                "' WHERE id_doljnost = '" + doljnost.getId_doljnost() + "'";
        int i = executeUpdate(query);
        return i == 1;
    }

    public Doljnost getDoljnost(int id_doljnost){
        String query = "SELECT * FROM `doljnost` WHERE id_doljnost = '" + id_doljnost + "'";
        Doljnost doljnost = new Doljnost();
        doljnost.setName_doljnost("");
        ResultSet resultSet = executeQuery(query);
        if (resultSet == null){
            System.out.println("Ошибка при выполнении запроса.");
            return doljnost;
        }
        try {
            resultSet.next();
            doljnost.setId_doljnost(resultSet.getInt("id_doljnost"));
            doljnost.setName_doljnost(resultSet.getString("name_doljnost"));
            doljnost.setOklad(resultSet.getFloat("oklad"));
        } catch (Exception exception){
            exception.printStackTrace();
            return doljnost;
        }
        return doljnost;
    }

    public ArrayList<Pol> getAllPol(){
        String query = "SELECT * FROM `pol`";
        ArrayList<Pol> pols = new ArrayList<>();
        ResultSet resultSet = executeQuery(query);
        if (resultSet == null){
            System.out.println("Ошибка при выполнении запроса.");
            return pols;
        }
        try {
            while (resultSet.next()) {
                Pol pol = new Pol();
                pol.setId_pol(resultSet.getInt("id_pol"));
                pol.setName_pol(resultSet.getString("name_pol"));
                pols.add(pol);
            }
        } catch (Exception exception){
            exception.printStackTrace();
            return pols;
        }
        return pols;
    }

    public Pol getPol(int id_pol){
        String query = "SELECT * FROM `pol` WHERE id_pol = '" + id_pol + "'";
        Pol pol = new Pol();
        pol.setName_pol("");
        ResultSet resultSet = executeQuery(query);
        if (resultSet == null){
            System.out.println("Ошибка при выполнении запроса.");
            return pol;
        }
        try {
            resultSet.next();
            pol.setId_pol(resultSet.getInt("id_pol"));
            pol.setName_pol(resultSet.getString("name_pol"));
        } catch (Exception exception){
            exception.printStackTrace();
            return pol;
        }
        return pol;
    }

    public boolean addSotrudnik(Sotrudnik sotrudnik){
        String query = "INSERT INTO `sotrudnik`(`familia`, `name`, `otchestvo`, `pol_id`, `voice_id`, `doljnost_id`) VALUES " +
                "('" + sotrudnik.getFamilia() + "','" + sotrudnik.getName() +
                "','" + sotrudnik.getOtchestvo() + "','" + sotrudnik.getPol_id() +
                "','" + sotrudnik.getVoice_id() + "','" + sotrudnik.getDoljnost_id() + "')";
        int i = executeUpdate(query);
        return i == 1;
    }
    public Sotrudnik getSotrudnik(int id_sotrudnik){
        String query = "SELECT * FROM `sotrudnik` WHERE id_sotrudnik = '" + id_sotrudnik + "'";
        Sotrudnik sotrudnik = new Sotrudnik();
        ResultSet resultSet = executeQuery(query);
        if (resultSet == null){
            System.out.println("Ошибка при выполнении запроса.");
            return sotrudnik;
        }
        try {
            while (resultSet.next()) {
                sotrudnik.setId_sotrudnik(resultSet.getInt("id_sotrudnik"));
                sotrudnik.setFamilia(resultSet.getString("familia"));
                sotrudnik.setName(resultSet.getString("name"));
                sotrudnik.setOtchestvo(resultSet.getString("otchestvo"));
                sotrudnik.setPol_id(resultSet.getInt("pol_id"));
                sotrudnik.setVoice_id(resultSet.getInt("voice_id"));
                sotrudnik.setDoljnost_id(resultSet.getInt("doljnost_id"));
            }
        } catch (Exception exception){
            exception.printStackTrace();
            return sotrudnik;
        }
        return sotrudnik;
    }
    public ArrayList<Sotrudnik> getAllSotrudniks(){
        String query = "SELECT * FROM `sotrudnik`";
        ArrayList<Sotrudnik> sotrudniks = new ArrayList<>();
        ResultSet resultSet = executeQuery(query);
        if (resultSet == null){
            System.out.println("Ошибка при выполнении запроса.");
            return sotrudniks;
        }
        try {
            while (resultSet.next()) {
                Sotrudnik sotrudnik = new Sotrudnik();
                sotrudnik.setId_sotrudnik(resultSet.getInt("id_sotrudnik"));
                sotrudnik.setFamilia(resultSet.getString("familia"));
                sotrudnik.setName(resultSet.getString("name"));
                sotrudnik.setOtchestvo(resultSet.getString("otchestvo"));
                sotrudnik.setPol_id(resultSet.getInt("pol_id"));
                sotrudnik.setVoice_id(resultSet.getInt("voice_id"));
                sotrudnik.setDoljnost_id(resultSet.getInt("doljnost_id"));
                sotrudniks.add(sotrudnik);
            }
        } catch (Exception exception){
            exception.printStackTrace();
            return sotrudniks;
        }
        return sotrudniks;
    }
    public boolean changeSotrudnik(Sotrudnik sotrudnik){
        String query = "UPDATE `sotrudnik` SET `familia`='" + sotrudnik.getFamilia() + "',`name`='" + sotrudnik.getName() +"'," +
                "`otchestvo`='" + sotrudnik.getOtchestvo() + "',`pol_id`='" + sotrudnik.getPol_id() + "'," +
                "`voice_id`='" + sotrudnik.getVoice_id() + "',`doljnost_id`='" + sotrudnik.getDoljnost_id() + "' WHERE id_sotrudnik = '" + sotrudnik.getId_sotrudnik() + "'";
        int i = executeUpdate(query);
        return i == 1;
    }

    public boolean deleteSotrudnik(Sotrudnik sotrudnik){
        String query = "DELETE FROM `sotrudnik` WHERE id_sotrudnik = '" + sotrudnik.getId_sotrudnik() + "'";
        int i = executeUpdate(query);
        return i == 1;
    }

    public boolean addUchastnik(Uchastnik uchastnik){
        String query = "INSERT INTO `spisok_uchastnikov`(`characters`, `performance_id`, `sotrudnik_id`) VALUES ('" + uchastnik.getCharacters() +
                "','" + uchastnik.getPerformance_id() + "','" + uchastnik.getSotrudnik_id() + "')";
        int i = executeUpdate(query);
        return i == 1;
    }

    public Uchastnik getUchastnik(int id_spisok_uchastnikov){
        String query = "SELECT * FROM `spisok_uchastnikov` WHERE id_spisok_uchastnikov = '" + id_spisok_uchastnikov + "'";
        Uchastnik uchastnik = new Uchastnik();
        ResultSet resultSet = executeQuery(query);
        if (resultSet == null){
            System.out.println("Ошибка при выполнении запроса.");
            return uchastnik;
        }
        try {
            resultSet.next();
            uchastnik.setId_spisok_uchastnikov(resultSet.getInt("id_spisok_uchastnikov"));
            uchastnik.setCharacters(resultSet.getString("characters"));
            uchastnik.setPerformance_id(resultSet.getInt("performance_id"));
            uchastnik.setSotrudnik_id(resultSet.getInt("sotrudnik_id"));
        } catch (Exception exception){
            exception.printStackTrace();
            return uchastnik;
        }
        return uchastnik;

    }
    public ArrayList<Uchastnik> getSpisokUchastnikov(){
        String query = "SELECT * FROM `spisok_uchastnikov`";
        ArrayList<Uchastnik> uchastniks = new ArrayList<>();
        ResultSet resultSet = executeQuery(query);
        if (resultSet == null){
            System.out.println("Ошибка при выполнении запроса.");
            return uchastniks;
        }
        try {
            while (resultSet.next()) {
                Uchastnik uchastnik = new Uchastnik();
                uchastnik.setId_spisok_uchastnikov(resultSet.getInt("id_spisok_uchastnikov"));
                uchastnik.setCharacters(resultSet.getString("characters"));
                uchastnik.setPerformance_id(resultSet.getInt("performance_id"));
                uchastnik.setSotrudnik_id(resultSet.getInt("sotrudnik_id"));
                uchastniks.add(uchastnik);
            }
        } catch (Exception exception){
            exception.printStackTrace();
            return uchastniks;
        }
        return uchastniks;
    }
    public boolean changeUchastnik(Uchastnik uchastnik){
        String query = "UPDATE `spisok_uchastnikov` SET `characters`='" + uchastnik.getCharacters() +
                "',`performance_id`='" + uchastnik.getPerformance_id() + "',`sotrudnik_id`='" + uchastnik.getSotrudnik_id() + "' WHERE id_spisok_uchastnikov ='" +
                uchastnik.getId_spisok_uchastnikov() + "'";
        int i = executeUpdate(query);
        return i == 1;
    }
    public boolean deleteUchastnik(Uchastnik uchastnik){
        String query = "DELETE FROM `spisok_uchastnikov` WHERE id_spisok_uchastnikov ='" + uchastnik.getId_spisok_uchastnikov() + "'";
        int i = executeUpdate(query);
        return i == 1;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
