package database.classes;

public class User {
    private int user_id;
    private String familia;
    private String name;
    private String otchestvo;
    private String login;
    private String password;
    private int role;

    public User(int user_id, String familia, String name, String otchestvo, String login, String password, int role) {
        this.user_id = user_id;
        this.familia = familia;
        this.name = name;
        this.otchestvo = otchestvo;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User(){

    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
