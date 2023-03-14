package database.classes;

public class Zal {
    private int id_zal;
    private int kolichestvo_mest;
    private String adres;

    public Zal(int id_zal, int kolichestvo_mest, String adres) {
        this.id_zal = id_zal;
        this.kolichestvo_mest = kolichestvo_mest;
        this.adres = adres;
    }

    public Zal() {
    }

    public int getId_zal() {
        return id_zal;
    }

    public void setId_zal(int id_zal) {
        this.id_zal = id_zal;
    }

    public int getKolichestvo_mest() {
        return kolichestvo_mest;
    }

    public void setKolichestvo_mest(int kolichestvo_mest) {
        this.kolichestvo_mest = kolichestvo_mest;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }
}
