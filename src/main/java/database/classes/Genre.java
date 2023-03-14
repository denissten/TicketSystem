package database.classes;

public class Genre {
    private int id_genre;
    private String name_genre;

    public Genre(int id_genre, String name_genre) {
        this.id_genre = id_genre;
        this.name_genre = name_genre;
    }

    public Genre() {
    }

    public int getId_genre() {
        return id_genre;
    }

    public void setId_genre(int id_genre) {
        this.id_genre = id_genre;
    }

    public String getName_genre() {
        return name_genre;
    }

    public void setName_genre(String name_genre) {
        this.name_genre = name_genre;
    }
}
