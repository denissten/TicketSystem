package database.classes;

public class Doljnost {
    private int id_doljnost;
    private String name_doljnost;
    private float oklad;

    public Doljnost(){};

    public Doljnost(int id_doljnost, String name_doljnost, float oklad) {
        this.id_doljnost = id_doljnost;
        this.name_doljnost = name_doljnost;
        this.oklad = oklad;
    }

    public int getId_doljnost() {
        return id_doljnost;
    }

    public void setId_doljnost(int id_doljnost) {
        this.id_doljnost = id_doljnost;
    }

    public String getName_doljnost() {
        return name_doljnost;
    }

    public void setName_doljnost(String name_doljnost) {
        this.name_doljnost = name_doljnost;
    }

    public float getOklad() {
        return oklad;
    }

    public void setOklad(float oklad) {
        this.oklad = oklad;
    }
}
