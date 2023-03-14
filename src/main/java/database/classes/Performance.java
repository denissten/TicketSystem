package database.classes;


import java.sql.Date;

public class Performance {
    private int id_perfomance;
    private String name_perfomance;
    private Date data;
    private float price;

    public Performance(int id_perfomance, String name_perfomance, Date data, float price) {
        this.id_perfomance = id_perfomance;
        this.name_perfomance = name_perfomance;
        this.data = data;
        this.price = price;
    }

    public Performance(){

    }

    public int getId_perfomance() {
        return id_perfomance;
    }

    public void setId_perfomance(int id_perfomance) {
        this.id_perfomance = id_perfomance;
    }

    public String getName_perfomance() {
        return name_perfomance;
    }

    public void setName_perfomance(String name_perfomance) {
        this.name_perfomance = name_perfomance;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
