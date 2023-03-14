package database.classes;

public class Pol {
    private int id_pol;
    private String name_pol;

    public Pol(){

    }

    public Pol(int id_pol, String name_pol) {
        this.id_pol = id_pol;
        this.name_pol = name_pol;
    }

    public int getId_pol() {
        return id_pol;
    }

    public void setId_pol(int id_pol) {
        this.id_pol = id_pol;
    }

    public String getName_pol() {
        return name_pol;
    }

    public void setName_pol(String name_pol) {
        this.name_pol = name_pol;
    }
}
