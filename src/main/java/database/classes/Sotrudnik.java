package database.classes;

public class Sotrudnik {
    private int id_sotrudnik;
    private String familia;
    private String name;
    private String otchestvo;
    private int pol_id;
    private int voice_id;
    private int doljnost_id;

    public Sotrudnik(){}

    public Sotrudnik(int id_sotrudnik, String familia, String name, String otchestvo, int pol_id, int voice_id, int doljnost_id) {
        this.id_sotrudnik = id_sotrudnik;
        this.familia = familia;
        this.name = name;
        this.otchestvo = otchestvo;
        this.pol_id = pol_id;
        this.voice_id = voice_id;
        this.doljnost_id = doljnost_id;
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

    public int getPol_id() {
        return pol_id;
    }

    public void setPol_id(int pol_id) {
        this.pol_id = pol_id;
    }

    public int getVoice_id() {
        return voice_id;
    }

    public void setVoice_id(int voice_id) {
        this.voice_id = voice_id;
    }

    public int getDoljnost_id() {
        return doljnost_id;
    }

    public void setDoljnost_id(int doljnost_id) {
        this.doljnost_id = doljnost_id;
    }
}
