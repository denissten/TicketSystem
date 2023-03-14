package database.classes;

public class Ticket {
    private int id_ticket;
    private int kolichestvo_biletov;
    private String naimenovanie_organizacii;
    private int status;
    private int user_id;
    private int zal_id;
    private int performance_id;
    private float final_price;

    public Ticket(int id_ticket, int kolichestvo_biletov, String naimenovanie_organizacii, int status, int user_id, int zal_id, int performance_id, float final_price) {
        this.id_ticket = id_ticket;
        this.kolichestvo_biletov = kolichestvo_biletov;
        this.naimenovanie_organizacii = naimenovanie_organizacii;
        this.status = status;
        this.user_id = user_id;
        this.zal_id = zal_id;
        this.performance_id = performance_id;
        this.final_price = final_price;
    }

    public Ticket(){

    }

    public int getId_ticket() {
        return id_ticket;
    }

    public void setId_ticket(int id_ticket) {
        this.id_ticket = id_ticket;
    }

    public int getKolichestvo_biletov() {
        return kolichestvo_biletov;
    }

    public void setKolichestvo_biletov(int kolichestvo_biletov) {
        this.kolichestvo_biletov = kolichestvo_biletov;
    }

    public String getNaimenovanie_organizacii() {
        return naimenovanie_organizacii;
    }

    public void setNaimenovanie_organizacii(String naimenovanie_organizacii) {
        this.naimenovanie_organizacii = naimenovanie_organizacii;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getZal_id() {
        return zal_id;
    }

    public void setZal_id(int zal_id) {
        this.zal_id = zal_id;
    }

    public int getPerformance_id() {
        return performance_id;
    }

    public void setPerformance_id(int performance_id) {
        this.performance_id = performance_id;
    }

    public float getFinal_price() {
        return final_price;
    }

    public void setFinal_price(float final_price) {
        this.final_price = final_price;
    }
}
