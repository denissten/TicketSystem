package database.classes;

public class Uchastnik {
    private int id_spisok_uchastnikov;
    private String characters;
    private int performance_id;
    private int sotrudnik_id;

    public Uchastnik(){}

    public Uchastnik(int id_spisok_uchastnikov, String characters, int performance_id, int sotrudnik_id) {
        this.id_spisok_uchastnikov = id_spisok_uchastnikov;
        this.characters = characters;
        this.performance_id = performance_id;
        this.sotrudnik_id = sotrudnik_id;
    }

    public int getId_spisok_uchastnikov() {
        return id_spisok_uchastnikov;
    }

    public void setId_spisok_uchastnikov(int id_spisok_uchastnikov) {
        this.id_spisok_uchastnikov = id_spisok_uchastnikov;
    }

    public String getCharacters() {
        return characters;
    }

    public void setCharacters(String characters) {
        this.characters = characters;
    }

    public int getPerformance_id() {
        return performance_id;
    }

    public void setPerformance_id(int performance_id) {
        this.performance_id = performance_id;
    }

    public int getSotrudnik_id() {
        return sotrudnik_id;
    }

    public void setSotrudnik_id(int sotrudnik_id) {
        this.sotrudnik_id = sotrudnik_id;
    }
}
